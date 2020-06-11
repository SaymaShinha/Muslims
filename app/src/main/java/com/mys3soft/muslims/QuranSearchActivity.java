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

        if (!btn_txt.equals("")) {
            btnSearchBy.setText(btn_txt);
        }

        ayahList.clear();
        if (!language.equals("")) {
            ayahList = db.searchQuranAyah(language, search_key);
        } else {
            ayahList = db.searchQuranAyah("En_Ahmed_Raza_Khan", search_key);
        }
        QuranSearchAdapter searchAdapter = new QuranSearchAdapter(QuranSearchActivity.this, ayahList);
        recyclerView.setAdapter(searchAdapter);


        quranSearchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                language = Tools.GetDataFromSharePrefarence(QuranSearchActivity.this, "search_quran");
                ayahList.clear();
                search_key = s.toString();
                if (!language.equals("")) {
                    ayahList = db.searchQuranAyah(language, search_key);
                } else {
                    ayahList = db.searchQuranAyah("En_Ahmed_Raza_Khan", search_key);
                }
                QuranSearchAdapter searchAdapter = new QuranSearchAdapter(QuranSearchActivity.this, ayahList);
                recyclerView.setAdapter(searchAdapter);
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
                        ThreadDemo thread = new ThreadDemo(lang);
                        thread.start();
                        optionDialog.cancel();
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

    private void downloadData(String[] sUrl) {
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
                Toast.makeText(this, "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage(), Toast.LENGTH_LONG).show();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();
            // download the file
            input = connection.getInputStream();

            output = new FileOutputStream(ReadAndWriteFiles.getAppExternalFilesDir(QuranSearchActivity.this) + sUrl[0]);

            byte[] data = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
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
                ReadAndWriteFiles.readFileAndSaveQuranToDataBase(this, sUrl[0], sUrl[1]);
            }
        }
    }


    class ThreadDemo extends Thread {
        private Thread t;
        private String threadName;
        private Context context = QuranSearchActivity.this;

        ThreadDemo(String name) {
            threadName = name;
            System.out.println("Creating " + threadName);
        }

        public void run() {
            System.out.println("Running " + threadName);
            try {
                String lang = threadName;
                switch (lang) {
                    case "Arabic Quran":
                        Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran", "Ar_Uthamani");
                        Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran_btn_txt", "Arabic");
                        db.updateQuranTranslatorSelected("Ar_Uthamani", true);
                        downloadData(urls);
                        break;
                    case "English Transliteration":
                        Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran", "English_Transliteration");
                        Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran_btn_txt", "En Transliteration");
                        urls = new String[]{"/Muslims/Quran/islam_public_en__english__transliteration.json", "English_Transliteration"};
                        downloadData(urls);
                        break;
                    case "Bangla Quran":
                        Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran", "Bn_Muhiuddin_Khan");
                        Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran_btn_txt", "Bangla");
                        db.updateQuranTranslatorSelected("Bn_Muhiuddin_Khan", true);
                        urls = new String[]{"/Muslims/Quran/islam_public_bn__muhiuddin__khan.json", "Bn_Muhiuddin_Khan"};
                        downloadData(urls);
                        break;
                    case "English Quran":
                        Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran", "En_Ahmed_Raza_Khan");
                        Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran_btn_txt", "English");
                        db.updateQuranTranslatorSelected("En_Ahmed_Raza_Khan", true);
                        downloadData(urls);
                        break;
                    case "Urdu Quran":
                        Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran", "Ur_Ahmed_Raza_Khan");
                        Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran_btn_txt", "Urdu");
                        urls = new String[]{"/Muslims/Quran/islam_public_ur__ahmed__raza__khan.json", "Ur_Ahmed_Raza_Khan"};
                        db.updateQuranTranslatorSelected("Ur_Ahmed_Raza_Khan", true);
                        downloadData(urls);
                        break;
                }


                // Let the thread sleep for a while.
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("Thread " + threadName + " interrupted.");
            }
            System.out.println("Thread " + threadName + " exiting.");
        }

        public void start() {
            System.out.println("Starting " + threadName);
            if (t == null) {
                t = new Thread(this, threadName);
                t.start();
            }
        }
    }

}
