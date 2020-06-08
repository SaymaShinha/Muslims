package com.mys3soft.muslims;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;
import com.mys3soft.muslims.Adapter.CalenderAdapter;
import com.mys3soft.muslims.DataBase.DBHelper;
import com.mys3soft.muslims.Models.Calender;
import com.mys3soft.muslims.Tools.ReadAndWriteFiles;
import com.mys3soft.muslims.Tools.Tools;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class CalenderActivity extends AppCompatActivity {
    private static ProgressDialog mProgressDialog;
    private static DBHelper db;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        Toolbar toolbar = findViewById(R.id.calender_toolbar_id);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Calender");
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.calender_recycler_view_id);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CalenderActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        db = new DBHelper(this);
        String string_date = Tools.get_Today_DD_MM_YYYY_FormatedDate();

        showCalender(string_date);
        onStart();
    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    public void showCalender(String string_date) {
        String[] date = string_date.split("/");
        List<Calender> calender = db.getCalenderByGregorianMonthNumber(Integer.valueOf(date[1]));
        if (calender.size() == 0){
            downloadData(string_date);
        } else{
            CalenderAdapter calenderAdapter = new CalenderAdapter(CalenderActivity.this, calender);
            recyclerView.setAdapter(calenderAdapter);
            recyclerView.scrollToPosition(Integer.valueOf(date[0])-1);
        }
    }

    private void downloadData(String urls) {
        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(CalenderActivity.this);
        mProgressDialog.setMessage("Please Wait While Loading Data");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(true);

        final DownloadTask downloadTask = new DownloadTask(CalenderActivity.this);
        downloadTask.execute(urls);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);//cancel the task
                onStart();
            }
        });
    }

    // usually, subclasses of AsyncTask are declared inside the activity class.
    // that way, you can easily modify the UI thread from here
    private static class DownloadTask extends AsyncTask<String, Integer, String> {
        @SuppressLint("StaticFieldLeak")
        private Context context;
        private PowerManager.WakeLock mWakeLock;

        private DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input;
            HttpURLConnection connection = null;
            OutputStream output;
            try {
                String[] string_date = sUrl[0].split("/");

                // execute this when the downloader must be fired
                URL url = new URL("http://api.aladhan.com/v1/gToHCalendar/" + string_date[1] + "/" + string_date[2]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();
                // download the file
                input = connection.getInputStream();
                InputStream in = new BufferedInputStream(input);
                convertStreamToString(in);

                output = new FileOutputStream(ReadAndWriteFiles.getAppExternalFilesDir(context) + "/Muslims/Calender/" +
                        string_date[1] + "_" + string_date[2] + ".json");

                byte[] data = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        in.close();
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            }
             catch (Exception e) {
                return e.toString();
            } finally {
                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = Objects.requireNonNull(pm).newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire(10*60*1000L /*10 minutes*/);
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.cancel();
            if (result != null)
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            else{
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static void convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            String response = sb.toString();
            JSONArray jsonArray = new JSONObject(response).getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json_date = jsonArray.getJSONObject(i);
                JSONObject hijri_json = json_date.getJSONObject("hijri");
                JSONObject gregorian_json = json_date.getJSONObject("gregorian");
                String hijri_date = hijri_json.getString("date");
                int hijri_day_number = hijri_json.getInt("day");
                int hijri_month_number = hijri_json.getJSONObject("month").getInt("number");
                String hijri_month_en = hijri_json.getJSONObject("month").getString("en");
                String hijri_month_ar = hijri_json.getJSONObject("month").getString("ar");
                int hijri_year_number = hijri_json.getInt("year");
                String hijri_weakday_en = hijri_json.getJSONObject("weekday").getString("en");
                String hijri_weakday_ar = hijri_json.getJSONObject("weekday").getString("ar");
                String hijri_designation_abbreviated = hijri_json.getJSONObject("designation").getString("abbreviated");
                String hijri_designation_expanded = hijri_json.getJSONObject("designation").getString("expanded");
                String hijri_holiday = hijri_json.getString("holidays").replaceAll("[\\[\\]()]", "");
                String gregorian_date = gregorian_json.getString("date");
                int gregorian_day_number = gregorian_json.getInt("day");
                int gregorian_month_number = gregorian_json.getJSONObject("month").getInt("number");
                String gregorian_month_en = gregorian_json.getJSONObject("month").getString("en");
                int gregorian_year_number = gregorian_json.getInt("year");
                String gregorian_weakday_en = gregorian_json.getJSONObject("weekday").getString("en");
                String gregorian_designation_abbreviated = gregorian_json.getJSONObject("designation").getString("abbreviated");
                String gregorian_designation_expanded = gregorian_json.getJSONObject("designation").getString("expanded");

                db.insertCalenderDay(hijri_date, hijri_day_number, hijri_month_number, hijri_month_en,
                        hijri_month_ar, hijri_year_number, hijri_weakday_en, hijri_weakday_ar, hijri_designation_abbreviated,
                        hijri_designation_expanded, hijri_holiday, gregorian_date, gregorian_day_number, gregorian_month_number,
                        gregorian_month_en, gregorian_year_number, gregorian_weakday_en,
                        gregorian_designation_abbreviated, gregorian_designation_expanded);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}

