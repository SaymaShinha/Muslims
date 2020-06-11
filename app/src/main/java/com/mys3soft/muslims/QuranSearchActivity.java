package com.mys3soft.muslims;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mys3soft.muslims.Adapter.AyahAdapter;
import com.mys3soft.muslims.Adapter.LanguageAdapter;
import com.mys3soft.muslims.Adapter.QuranSearchAdapter;
import com.mys3soft.muslims.DataBase.DBHelper;
import com.mys3soft.muslims.Models.Ayah;
import com.mys3soft.muslims.Tools.ReadAndWriteFiles;
import com.mys3soft.muslims.Tools.Tools;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class QuranSearchActivity extends AppCompatActivity {
    private EditText quranSearchET;
    private RecyclerView recyclerView;
    private Button btnSearchBy;
    private DBHelper db;

    private ArrayAdapter<String> sortAdapter;
    private ListView sortLV;
    private EditText search_sort_ET;
    private String language, search_key;
    private List<Ayah> ayahList = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    private String[] urls = {"", ""};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_search);

        quranSearchET = findViewById(R.id.quran_search_et_id);
        btnSearchBy = findViewById(R.id.search_quran_btn_id);
        recyclerView = findViewById(R.id.quran_search_recycler_id);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        db = new DBHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        language = Tools.GetDataFromSharePrefarence(QuranSearchActivity.this, "search_quran");
        String btn_txt = Tools.GetDataFromSharePrefarence(this, "search_quran_btn_txt");

        if (!btn_txt.equals("")){
            btnSearchBy.setText(btn_txt);
        }

        ayahList.clear();
        if (!language.equals("")){
            ayahList = db.searchQuranAyah(language, search_key);
        } else{
            ayahList = db.searchQuranAyah("En_Ahmed_Raza_Khan", search_key);
        }
        QuranSearchAdapter searchAdapter = new QuranSearchAdapter(QuranSearchActivity.this, ayahList);
        recyclerView.setAdapter(searchAdapter );


        quranSearchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                language = Tools.GetDataFromSharePrefarence(QuranSearchActivity.this, "search_quran");
                ayahList.clear();
                search_key = s.toString();
                if (!language.equals("")){
                    ayahList = db.searchQuranAyah(language, search_key);
                } else{
                    ayahList = db.searchQuranAyah("En_Ahmed_Raza_Khan", search_key);
                }
                QuranSearchAdapter searchAdapter = new QuranSearchAdapter(QuranSearchActivity.this, ayahList);
                recyclerView.setAdapter(searchAdapter );
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnSearchBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog optionDialog = new Dialog(QuranSearchActivity.this);
                optionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                optionDialog.setContentView(R.layout.quran_sort_layout);
                optionDialog.show();

                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.70);
                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                Objects.requireNonNull(optionDialog.getWindow()).setLayout(width, height);

                sortLV = optionDialog.findViewById(R.id.sort_listview_id);

                String[] home_string = new String[]{"Arabic Quran", "English Transliteration", "Bangla Quran",
                        "English Quran", "Urdu Quran"};
                List<String> home_string_list = new ArrayList<>(Arrays.asList(home_string));
                sortAdapter = new ArrayAdapter<>(QuranSearchActivity.this, android.R.layout.simple_list_item_1, home_string_list);
                sortLV.setAdapter(sortAdapter);

                search_sort_ET = optionDialog.findViewById(R.id.search_sort_id);

                search_sort_ET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        sortAdapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                sortLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String lang = Objects.requireNonNull(sortAdapter.getItem(position));
                        switch (lang) {
                            case "Arabic Quran":
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran", "Ar_Uthamani");
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran_btn_txt", "Arabic");
                                urls = new String[]{"/Muslims/Quran/islam_public_ar__uthmani.json", "Ar_Uthamani"};
                                downloadData(urls);
                                optionDialog.cancel();
                                break;
                            case "English Transliteration":
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran", "English_Transliteration");
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran_btn_txt", "En Transliteration");
                                urls = new String[]{"/Muslims/Quran/islam_public_en__english__transliteration.json", "English_Transliteration"};
                                downloadData(urls);
                                optionDialog.cancel();
                                break;
                            case "Bangla Quran":
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran", "Bn_Muhiuddin_Khan");
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran_btn_txt", "Bangla");
                                urls = new String[]{"/Muslims/Quran/islam_public_bn__muhiuddin__khan.json", "Bn_Muhiuddin_Khan"};
                                downloadData(urls);
                                optionDialog.cancel();
                                break;
                            case "English Quran":
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran", "En_Ahmed_Raza_Khan");
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran_btn_txt", "English");
                                urls = new String[]{"/Muslims/Quran/islam_public_en__ahmed__raza__khan.json", "En_Ahmed_Raza_Khan"};
                                downloadData(urls);
                                optionDialog.cancel();
                                break;
                            case "Urdu Quran":
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran", "Ur_Ahmed_Raza_Khan");
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran_btn_txt", "Urdu");
                                urls = new String[]{"/Muslims/Quran/islam_public_ur__ahmed__raza__khan.json", "Ur_Ahmed_Raza_Khan"};
                                downloadData(urls);
                                optionDialog.cancel();
                                break;
                        }
                    }
                });

                optionDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        onStart();
                    }
                });
            }
        });

    }

    private void downloadData(String[] urls) {
        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait While Loading Data");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(true);

        // execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(this);
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
    private class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                String last = sUrl[0].substring(sUrl[0].lastIndexOf('/') + 1);
                URL url = new URL("https://raw.githubusercontent.com/SaymaShinha/islamDB/master/" + last);
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

                output = new FileOutputStream(ReadAndWriteFiles.getAppExternalFilesDir(context) + sUrl[0]);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
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
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null) {
                    connection.disconnect();
                    ReadAndWriteFiles.readFileAndSaveQuranToDataBase(context, sUrl[0], sUrl[1]);
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
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
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }
}
