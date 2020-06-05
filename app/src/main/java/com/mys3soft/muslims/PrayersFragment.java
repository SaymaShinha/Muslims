package com.mys3soft.muslims;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.os.PowerManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.mys3soft.muslims.Adapter.PrayerLocationAdapter;
import com.mys3soft.muslims.DataBase.DBHelper;
import com.mys3soft.muslims.Models.PrayerTimings;
import com.mys3soft.muslims.Models.World_Cities;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PrayersFragment} interface
 * to handle interaction events.
 * Use the {@link PrayersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrayersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String prayer_url = "'https://api.aladhan.com/timingsByAddress/' + today[2] + '-' + today[1] + '-' + today[\n" +
            "            0] + '?address=' + address + '&method=' + method";
    private Context context;
    private ProgressDialog mProgressDialog;
    private String location;
    private String format = "dd-MM-yyyy";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button mLocationBtn, mMethodBtn;
    private View mMainView;
    private TextView mImsakTV, mFajrTV, mSunriseTV, mDhuhrTV,
            mAsrTV, mSunsetTV, mMagribTV, mIshaTV, mMidnightTV, mTodayDateTV,
            mImsakTxt, mFajrTxt, mSunriseTxt, mDhuhrTxt, mAsrTxt,
            mSunsetTxt, mMagribTxt, mIshaTxt, mMidnightTxt, mTodayDateTxt;


    private static final String LOG_TAG = "Ex.St:PrayersFragment";
    private DBHelper db;
    private String[] string_date;
    public PrayersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrayersFragment.
     */
    // TODO: Rename and change types and number of parameters
    static PrayersFragment newInstance(String param1, String param2) {
        PrayersFragment fragment = new PrayersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_prayers, container, false);

        mLocationBtn = mMainView.findViewById(R.id.prayer_fragment_location_id);

        mTodayDateTV = mMainView.findViewById(R.id.prayer_fragment_today_date);
        mImsakTV = mMainView.findViewById(R.id.prayers_fragment_imsak_textView_id);
        mFajrTV = mMainView.findViewById(R.id.prayers_fragment_fajr_textView_id);
        mSunriseTV = mMainView.findViewById(R.id.prayers_fragment_sunrise_textView_id);
        mDhuhrTV = mMainView.findViewById(R.id.prayers_fragment_zhuhr_textView_id);
        mAsrTV = mMainView.findViewById(R.id.prayers_fragment_asr_textView_id);
        mSunsetTV = mMainView.findViewById(R.id.prayers_fragment_sunset_textView_id);
        mMagribTV = mMainView.findViewById(R.id.prayers_fragment_maghrib_textView_id);
        mIshaTV = mMainView.findViewById(R.id.prayers_fragment_isha_textView_id);
        mMidnightTV = mMainView.findViewById(R.id.prayers_fragment_midnight_textView_id);

        mImsakTxt = mMainView.findViewById(R.id.prayers_fragment_imsak_textView_id_txt);
        mFajrTxt = mMainView.findViewById(R.id.prayers_fragment_fajr_textView_id_txt);
        mSunriseTxt = mMainView.findViewById(R.id.prayers_fragment_sunrise_textView_id_txt);
        mDhuhrTxt = mMainView.findViewById(R.id.prayers_fragment_zhuhr_textView_id_txt);
        mAsrTxt = mMainView.findViewById(R.id.prayers_fragment_asr_textView_id_txt);
        mSunsetTxt = mMainView.findViewById(R.id.prayers_fragment_sunset_textView_id_txt);
        mMagribTxt = mMainView.findViewById(R.id.prayers_fragment_maghrib_textView_id_txt);
        mIshaTxt = mMainView.findViewById(R.id.prayers_fragment_isha_textView_id_txt);
        mMidnightTxt = mMainView.findViewById(R.id.prayers_fragment_midnight_textView_id_txt);

        context = mMainView.getContext();
        db = new DBHelper(context);

        // Inflate the layout for this fragment
        return mMainView;
    }


    @Override
    public void onStart() {
        super.onStart();

        string_date  =  Tools.get_Today_DD_MM_YYYY_FormatedDate().split("/");
        location = Tools.GetDataFromSharePrefarence(mMainView.getContext(), "location");
        if (!location.equals("")) {
            mLocationBtn.setText(location);
            try {
                PrayerTimings prayerTimings = db.getPrayerTime(location,
                        Integer.valueOf(string_date[0]), Integer.valueOf(string_date[1]),
                        Integer.valueOf(string_date[2]));

                if (prayerTimings.getId() != 0){
                    String imsak = prayerTimings.getImsak().split("[(]")[0];
                    String fajr = prayerTimings.getFajr().split("[(]")[0];
                    String sunrise = prayerTimings.getSunrise().split("[(]")[0];
                    String dhuhr = prayerTimings.getDhuhr().split("[(]")[0];
                    String asr = prayerTimings.getAsr().split("[(]")[0];
                    String sunset = prayerTimings.getSunset().split("[(]")[0];
                    String maghrib = prayerTimings.getMaghrib().split("[(]")[0];
                    String isha = prayerTimings.getIsha().split("[(]")[0];
                    String midnight = prayerTimings.getMidnight().split("[(]")[0];

                    String date = string_date[0]+"/"+string_date[1]+"/"+string_date[2];

                    mTodayDateTV.setText(date);
                    mImsakTV.setText(imsak);
                    mFajrTV.setText(fajr);
                    mSunriseTV.setText(sunrise);
                    mDhuhrTV.setText(dhuhr);
                    mAsrTV.setText(asr);
                    mSunsetTV.setText(sunset);
                    mMagribTV.setText(maghrib);
                    mIshaTV.setText(isha);
                    mMidnightTV.setText(midnight);

                    ShowCurrentPrayerTime(imsak, fajr, sunrise, dhuhr, asr, sunset, maghrib, isha, midnight);
                }else{
                    downloadData(location);
                }
            } catch (Exception e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            }
        }

        mLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.select_prayer_location);
                dialog.setTitle("Search For City");
                dialog.show();

                final EditText search_city = dialog.findViewById(R.id.prayer_location_search_id);
                final ListView search_city_LV = dialog.findViewById(R.id.prayer_location_listView_id);

                search_city.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        List<World_Cities> world_cities = new ArrayList<>();
                        world_cities.clear();
                        world_cities = db.getSearchWorldLocationData(search_city.getText().toString());
                        PrayerLocationAdapter adapter = new PrayerLocationAdapter(context, world_cities);
                        search_city_LV.setAdapter(adapter);

                        search_city_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                World_Cities world_city = (World_Cities) parent.getItemAtPosition(position);
                                location = world_city.getCity() + "/" + world_city.getCountry();
                                PrayerTimings prayerTimings = db.getPrayerTime(location,
                                        Integer.valueOf(string_date[0]), Integer.valueOf(string_date[1]),
                                        Integer.valueOf(string_date[2]));

                                if (prayerTimings.getId() == 0) {
                                    downloadData(location);
                                }

                                dialog.cancel();
                            }
                        });
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //do whatever you want the back key to do
                        dialog.dismiss();
                        onStart();
                    }
                });
            }
        });
    }


    private void downloadData(String urls) {
        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Please Wait While Loading Data");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        final DownloadTask downloadTask = new DownloadTask(context);
        downloadTask.execute(urls);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true); //cancel the task
            }
        });
    }


    // usually, subclasses of AsyncTask are declared inside the activity class.
    // that way, you can easily modify the UI thread from here
    @SuppressLint("StaticFieldLeak")
    private class DownloadTask extends AsyncTask<String, Integer, String> {
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
                location = sUrl[0];

                String[] url_value = sUrl[0].split("/");
                // execute this when the downloader must be fired
                URL url = new URL("http://api.aladhan.com/v1/calendarByAddress?address=" + url_value[0] + "," + url_value[1] +
                        "&method=2&month=" + string_date[1] + "&year=" + string_date[2]);
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
                convertStreamToString(in, sUrl[0]);

                output = new FileOutputStream(ReadAndWriteFiles.getAppExternalFilesDir(context) +
                        "/Muslims/Prayer/" + url_value[0] + "_" +
                        url_value[1] + "_" + string_date[1] + "_" + string_date[2]+".json");

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
            } catch (Exception e) {
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
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show();
            else{
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
                Tools.SaveDataToSharePrefarence(context, "location", location);
            }
        }
    }


    private void convertStreamToString(InputStream is, String location) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            String response = sb.toString();
            JSONArray jsonArray = new JSONObject(response).getJSONArray("data");

            String format = jsonArray.getJSONObject(1)
                    .getJSONObject("date").getJSONObject("gregorian").getString("format").toLowerCase();


            int month = Integer.valueOf(jsonArray.getJSONObject(1)
                    .getJSONObject("date").getJSONObject("gregorian").getJSONObject("month").getString("number"));

            int year = Integer.valueOf(jsonArray.getJSONObject(1)
                    .getJSONObject("date").getJSONObject("gregorian").getString("year"));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject p_time = jsonArray.getJSONObject(i).getJSONObject("timings");

                int day = Integer.valueOf(jsonArray.getJSONObject(i)
                        .getJSONObject("date").getJSONObject("gregorian").getString("day"));

                db.insertPrayerTime(p_time.getString("Imsak"),
                        p_time.getString("Fajr"),
                        p_time.getString("Sunrise"),
                        p_time.getString("Dhuhr"),
                        p_time.getString("Asr"),
                        p_time.getString("Sunset"),
                        p_time.getString("Maghrib"),
                        p_time.getString("Isha"),
                        p_time.getString("Midnight"),
                        day,
                        month,
                        year,
                        location);
            }

            Tools.SaveDataToSharePrefarence(context, "Prayer_Date_Format", format);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    private void ShowCurrentPrayerTime(String imsak, String fajr, String sunrise, String dhuhr, String asr,
                                       String sunset, String maghrib, String isha, String midnight) {

        String now = Tools.get_Now_HH_mm_FormatedTime();

        if (Tools.GetTwo_HH_mm_TimeDiff(now, imsak)<0){
            if (Tools.GetTwo_HH_mm_TimeDiff(now, fajr)<0){
                if (Tools.GetTwo_HH_mm_TimeDiff(now, sunrise)<0){
                    if (Tools.GetTwo_HH_mm_TimeDiff(now, dhuhr)<0){
                        if (Tools.GetTwo_HH_mm_TimeDiff(now, asr)<0){
                            if (Tools.GetTwo_HH_mm_TimeDiff(now, sunset)<0){
                                if (Tools.GetTwo_HH_mm_TimeDiff(now, maghrib)<0){
                                    if (Tools.GetTwo_HH_mm_TimeDiff(now, isha)<0){
                                        if (Tools.GetTwo_HH_mm_TimeDiff(now, midnight)>0){
                                            mImsakTV.setTextColor(Color.GREEN);
                                            mImsakTxt.setTextColor(Color.GREEN);
                                            mImsakTV.setText(Tools.GetTwo_hh_mm_TimeDiff_String_Value(now,imsak));
                                        }else{
                                            mMidnightTV.setTextColor(Color.GREEN);
                                            mMidnightTxt.setTextColor(Color.GREEN);
                                        }
                                    }else{
                                        mIshaTV.setTextColor(Color.GREEN);
                                        mIshaTxt.setTextColor(Color.GREEN);
                                        mIshaTV.setText(Tools.GetTwo_hh_mm_TimeDiff_String_Value(now,isha));
                                    }
                                }else{
                                    mMagribTV.setTextColor(Color.RED);
                                    mMagribTxt.setTextColor(Color.RED);
                                    mMagribTV.setText(Tools.GetTwo_hh_mm_TimeDiff_String_Value(now,maghrib));
                                }
                            }else{
                                mSunsetTV.setTextColor(Color.GREEN);
                                mSunsetTxt.setTextColor(Color.GREEN);
                                mSunsetTV.setText(Tools.GetTwo_hh_mm_TimeDiff_String_Value(now,sunset));
                            }
                        }else {
                            mAsrTV.setTextColor(Color.GREEN);
                            mAsrTxt.setTextColor(Color.GREEN);
                            mAsrTV.setText(Tools.GetTwo_hh_mm_TimeDiff_String_Value(now,asr));
                        }
                    }else{
                        mDhuhrTV.setTextColor(Color.RED);
                        mDhuhrTxt.setTextColor(Color.RED);
                        mDhuhrTV.setText(Tools.GetTwo_hh_mm_TimeDiff_String_Value(now,dhuhr));
                    }
                }else{
                    mSunriseTV.setTextColor(Color.GREEN);
                    mSunriseTxt.setTextColor(Color.GREEN);
                    mSunriseTV.setText(Tools.GetTwo_hh_mm_TimeDiff_String_Value(now,sunrise));
                }
            }else {
                mFajrTV.setTextColor(Color.GREEN);
                mFajrTxt.setTextColor(Color.GREEN);
                mFajrTV.setText(Tools.GetTwo_hh_mm_TimeDiff_String_Value(now,fajr));
            }
        } else{
            mImsakTV.setTextColor(Color.GREEN);
            mImsakTxt.setTextColor(Color.GREEN);
            mImsakTV.setText(Tools.GetTwo_hh_mm_TimeDiff_String_Value(now,imsak));
        }

    }

}
