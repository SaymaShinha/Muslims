package com.mys3soft.muslims;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mys3soft.muslims.Adapter.AyahAdapter;
import com.mys3soft.muslims.Adapter.LanguageAdapter;
import com.mys3soft.muslims.Adapter.SurahAdapter;
import com.mys3soft.muslims.DataBase.DBHelper;
import com.mys3soft.muslims.Models.Ayah;
import com.mys3soft.muslims.Models.Language;
import com.mys3soft.muslims.Tools.ReadAndWriteFiles;
import com.mys3soft.muslims.Tools.Tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SurahActivity extends AppCompatActivity {
    private static ProgressDialog mProgressDialog;
    private RecyclerView mAyahRecyclerView;
    private List<Ayah> ayahs, en_trans;
    private List<Ayah> trans_ayahs = new ArrayList<>();
    private static final String LOG_TAG = "Ex.Store: SurahActivity";
    private AyahAdapter ayahAdapter;
    private ArrayAdapter<Integer> ayah_number_adapter;
    private TextView ayah_number_TV,surah_translation_TV;
    private EditText search_ayah_number_ET;
    private ListView ayah_number_LV;
    private String surah_name, model;
    private int total_ayah, surah_number;
    private DBHelper db;
    private List<Language> languageArrayList;
    private LinearLayoutManager ayahlayoutManager;
    private String toggle_en_trans, arabic_lang;
    private int ayah_position_num;
    private int selected_ayah_number;
    String[] mDefaultDataUrls = {
            "/Muslims/Prayer/islam_public_ar__uthmani.json",
            "/Muslims/Prayer/islam_public_en__english__transliteration.json"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        surah_name = bundle.getString("surah_name");
        total_ayah = bundle.getInt("total_ayah");
        surah_number = bundle.getInt("surah_number");
        model = bundle.getString("model");

        Toolbar toolbar = findViewById(R.id.surah_toolbar_id);
        toolbar.setTitle(surah_name);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ayah_number_TV = findViewById(R.id.surah_name_id);
        mAyahRecyclerView = findViewById(R.id.activity_surah_recycler_id);

        ayahlayoutManager = new LinearLayoutManager(this);
        mAyahRecyclerView.setLayoutManager(ayahlayoutManager);

        db = new DBHelper(this);
        languageArrayList = db.getQuranTranslator();
    }


    @Override
    public void onStart() {
        super.onStart();

        downloadDefaultData(mDefaultDataUrls);

        mAyahRecyclerView.scrollToPosition(ayah_position_num);

        arabic_lang = Tools.GetDataFromSharePrefarence(SurahActivity.this, "Arabic_Lang");
        toggle_en_trans = Tools.GetDataFromSharePrefarence(SurahActivity.this, "toggle_en_trans");

        switch (model) {
            case "whole_surah": {
                ayahs = null;
                if (!arabic_lang.equals("Disable")) {
                    if (!arabic_lang.equals("")) {
                        ayahs = db.getAllQuranAyah(arabic_lang, surah_number);
                    } else {
                        ayahs = db.getAllQuranAyah("Ar_Uthamani", surah_number);
                    }
                }

                en_trans = null;

                if (!toggle_en_trans.equals("Disable")) {
                    en_trans = db.getAllQuranAyah("English_Transliteration", surah_number);
                }

                List<Language> selected_trans = db.getSelectedLanguage();
                if (selected_trans.size() != 0) {
                    for (Language language : selected_trans) {
                        trans_ayahs = db.getAllQuranAyah(language.getLanguage(), surah_number);
                    }
                }

                if (arabic_lang.equals("Disable") && toggle_en_trans.equals("Disable") && selected_trans.size() == 0) {
                    Tools.SaveDataToSharePrefarence(SurahActivity.this, "Arabic_Lang", "Ar_Uthamani");
                    ayahs = db.getAllQuranAyah("Ar_Uthamani", surah_number);
                }

                ayahAdapter = new AyahAdapter(this, ayahs, en_trans, trans_ayahs, total_ayah);
                mAyahRecyclerView.setAdapter(ayahAdapter);

                ayah_number_TV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(SurahActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.ayah_number_layout);
                        dialog.show();

                        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.30);
                        int height = WindowManager.LayoutParams.WRAP_CONTENT;

                        Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);

                        ayah_number_LV = dialog.findViewById(R.id.ayah_number_listview_id);
                        final List<Integer> ayah_number = new ArrayList<>();
                        int i = 1;
                        while (i <= total_ayah) {
                            ayah_number.add(i);
                            i++;
                        }
                        ayah_number_adapter = new ArrayAdapter<>(dialog.getContext(), R.layout.single_ayah_number, ayah_number);
                        ayah_number_LV.setAdapter(ayah_number_adapter);

                        search_ayah_number_ET = dialog.findViewById(R.id.search_ayah_number_id);

                        search_ayah_number_ET.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                SurahActivity.this.ayah_number_adapter.getFilter().filter(s);
                            }
                        });

                        ayah_number_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // go to ayah
                                selected_ayah_number = Integer.valueOf(parent.getItemAtPosition(position).toString());
                                mAyahRecyclerView.scrollToPosition(selected_ayah_number);
                                Tools.SaveDataToSharePrefarence(SurahActivity.this, "ayah_last_position", String.valueOf(selected_ayah_number));
                                dialog.dismiss();
                            }
                        });
                    }
                });

                mAyahRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            ayah_position_num = Tools.getCurrentItem(mAyahRecyclerView);
                            Tools.SaveDataToSharePrefarence(SurahActivity.this, "last_recite_surah_name", surah_name);
                            Tools.SaveDataToSharePrefarence(SurahActivity.this, "last_recite_surah_total_ayah", String.valueOf(total_ayah));
                            Tools.SaveDataToSharePrefarence(SurahActivity.this, "last_recite_surah_number", String.valueOf(surah_number));
                            if (ayah_position_num == 0) {
                                Tools.SaveDataToSharePrefarence(SurahActivity.this, "ayah_last_position", String.valueOf(1));
                            } else {
                                Tools.SaveDataToSharePrefarence(SurahActivity.this, "ayah_last_position", String.valueOf(ayah_position_num));
                            }
                        }
                    }
                });
                break;
            }
            case "last_surah": {
                String ayah_last_position = Tools.GetDataFromSharePrefarence(SurahActivity.this, "ayah_last_position");

                if (!ayah_last_position.equals("")) {
                    ayah_position_num = Integer.valueOf(ayah_last_position);
                }
                mAyahRecyclerView.scrollToPosition(ayah_position_num - 1);

                ayahs = null;
                if (!arabic_lang.equals("Disable")) {
                    if (!arabic_lang.equals("")) {
                        ayahs = db.getAllQuranAyah(arabic_lang, surah_number);
                    } else {
                        ayahs = db.getAllQuranAyah("Ar_Uthamani", surah_number);
                    }
                }

                en_trans = null;

                if (!toggle_en_trans.equals("Disable")) {
                    en_trans = db.getAllQuranAyah("English_Transliteration", surah_number);
                }

                List<Language> selected_trans = db.getSelectedLanguage();
                if (selected_trans.size() != 0) {
                    for (Language language : selected_trans) {
                        trans_ayahs = db.getAllQuranAyah(language.getLanguage(), surah_number);
                    }
                }

                if (arabic_lang.equals("Disable") && toggle_en_trans.equals("Disable") && selected_trans.size() == 0) {
                    Tools.SaveDataToSharePrefarence(SurahActivity.this, "Arabic_Lang", "Ar_Uthamani");
                    ayahs = db.getAllQuranAyah("Ar_Uthamani", surah_number);
                }

                ayahAdapter = new AyahAdapter(this, ayahs, en_trans, trans_ayahs, total_ayah);
                mAyahRecyclerView.setAdapter(ayahAdapter);

                ayah_number_TV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(SurahActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.ayah_number_layout);
                        dialog.show();

                        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.30);
                        int height = WindowManager.LayoutParams.WRAP_CONTENT;

                        Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);

                        ayah_number_LV = dialog.findViewById(R.id.ayah_number_listview_id);
                        List<Integer> ayah_number = new ArrayList<>();
                        int i = 1;
                        while (i <= total_ayah) {
                            ayah_number.add(i);
                            i++;
                        }
                        ayah_number_adapter = new ArrayAdapter<>(dialog.getContext(), R.layout.single_ayah_number, ayah_number);
                        ayah_number_LV.setAdapter(ayah_number_adapter);

                        search_ayah_number_ET = dialog.findViewById(R.id.search_ayah_number_id);

                        search_ayah_number_ET.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                SurahActivity.this.ayah_number_adapter.getFilter().filter(s);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        ayah_number_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // go to ayah
                                selected_ayah_number = Integer.valueOf(parent.getItemAtPosition(position).toString());
                                mAyahRecyclerView.scrollToPosition(selected_ayah_number);
                                Tools.SaveDataToSharePrefarence(SurahActivity.this, "ayah_last_position", String.valueOf(selected_ayah_number));
                                dialog.dismiss();
                            }
                        });
                    }
                });

                mAyahRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            ayah_position_num = Tools.getCurrentItem(mAyahRecyclerView);
                            Tools.SaveDataToSharePrefarence(SurahActivity.this, "last_recite_surah_name", surah_name);
                            Tools.SaveDataToSharePrefarence(SurahActivity.this, "last_recite_surah_total_ayah", String.valueOf(total_ayah));
                            Tools.SaveDataToSharePrefarence(SurahActivity.this, "last_recite_surah_number", String.valueOf(surah_number));
                            if (ayah_position_num == 0) {
                                Tools.SaveDataToSharePrefarence(SurahActivity.this, "ayah_last_position", String.valueOf(1));
                            } else {
                                Tools.SaveDataToSharePrefarence(SurahActivity.this, "ayah_last_position", String.valueOf(ayah_position_num));
                            }
                        }
                    }
                });
                break;
            }
            case "ayah_ul_kursi": {
                ayahs = null;
                if (!arabic_lang.equals("Disable")) {
                    if (!arabic_lang.equals("")) {
                        ayahs = db.getAyahUlKursi(arabic_lang);
                    } else {
                        ayahs = db.getAyahUlKursi("Ar_Uthamani");
                    }
                }

                en_trans = null;

                if (!toggle_en_trans.equals("Disable")) {
                    en_trans = db.getAyahUlKursi("English_Transliteration");
                }

                List<Language> selected_trans = db.getSelectedLanguage();
                if (selected_trans.size() != 0) {
                    for (Language language : selected_trans) {
                        trans_ayahs = db.getAyahUlKursi(language.getLanguage());
                    }
                }

                if (arabic_lang.equals("Disable") && toggle_en_trans.equals("Disable") && selected_trans.size() == 0) {
                    Tools.SaveDataToSharePrefarence(SurahActivity.this, "Arabic_Lang", "Ar_Uthamani");
                    ayahs = db.getAyahUlKursi("Ar_Uthamani");
                }

                ayahAdapter = new AyahAdapter(this, ayahs, en_trans, trans_ayahs, total_ayah);
                mAyahRecyclerView.setAdapter(ayahAdapter);

                ayah_number_TV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(SurahActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.ayah_number_layout);
                        dialog.show();

                        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.30);
                        int height = WindowManager.LayoutParams.WRAP_CONTENT;

                        Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);

                        ayah_number_LV = dialog.findViewById(R.id.ayah_number_listview_id);
                        List<Integer> ayah_number = new ArrayList<>();
                        int i = 1;
                        while (i <= total_ayah) {
                            ayah_number.add(i);
                            i++;
                        }
                        ayah_number_adapter = new ArrayAdapter<>(dialog.getContext(), R.layout.single_ayah_number, ayah_number);
                        ayah_number_LV.setAdapter(ayah_number_adapter);

                        search_ayah_number_ET = dialog.findViewById(R.id.search_ayah_number_id);

                        search_ayah_number_ET.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                SurahActivity.this.ayah_number_adapter.getFilter().filter(s);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        ayah_number_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // go to ayah
                                ayahlayoutManager.scrollToPosition(position);
                                dialog.dismiss();
                            }
                        });
                    }
                });
                break;
            }
            case "al_baqara_285_286_ayah": {
                ayahs = null;
                if (!arabic_lang.equals("Disable")) {
                    if (!arabic_lang.equals("")) {
                        ayahs = db.getAlBaqara285_286Ayah(arabic_lang);
                    } else {
                        ayahs = db.getAlBaqara285_286Ayah("Ar_Uthamani");
                    }
                }

                en_trans = null;

                if (!toggle_en_trans.equals("Disable")) {
                    en_trans = db.getAlBaqara285_286Ayah("English_Transliteration");
                }

                List<Language> selected_trans = db.getSelectedLanguage();
                if (selected_trans.size() != 0) {
                    for (Language language : selected_trans) {
                        trans_ayahs = db.getAlBaqara285_286Ayah(language.getLanguage());
                    }
                }

                if (arabic_lang.equals("Disable") && toggle_en_trans.equals("Disable") && selected_trans.size() == 0) {
                    Tools.SaveDataToSharePrefarence(SurahActivity.this, "Arabic_Lang", "Ar_Uthamani");
                    ayahs = db.getAlBaqara285_286Ayah("Ar_Uthamani");
                }

                ayahAdapter = new AyahAdapter(this, ayahs, en_trans, trans_ayahs, total_ayah);
                mAyahRecyclerView.setAdapter(ayahAdapter);

                ayah_number_TV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(SurahActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.ayah_number_layout);
                        dialog.show();

                        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.30);
                        int height = WindowManager.LayoutParams.WRAP_CONTENT;

                        Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);

                        ayah_number_LV = dialog.findViewById(R.id.ayah_number_listview_id);
                        List<Integer> ayah_number = new ArrayList<>();
                        int i = 1;
                        while (i <= total_ayah) {
                            ayah_number.add(i);
                            i++;
                        }
                        ayah_number_adapter = new ArrayAdapter<>(dialog.getContext(), R.layout.single_ayah_number, ayah_number);
                        ayah_number_LV.setAdapter(ayah_number_adapter);

                        search_ayah_number_ET = dialog.findViewById(R.id.search_ayah_number_id);

                        search_ayah_number_ET.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                SurahActivity.this.ayah_number_adapter.getFilter().filter(s);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        ayah_number_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // go to ayah
                                ayahlayoutManager.scrollToPosition(position);
                                dialog.dismiss();
                            }
                        });
                    }
                });
                break;
            }
            case "al_hashr_20_24_ayah": {
                ayahs = null;
                if (!arabic_lang.equals("Disable")) {
                    if (!arabic_lang.equals("")) {
                        ayahs = db.getAlHashr20_24Ayah(arabic_lang);
                    } else {
                        ayahs = db.getAlHashr20_24Ayah("Ar_Uthamani");
                    }
                }

                en_trans = null;

                if (!toggle_en_trans.equals("Disable")) {
                    en_trans = db.getAlHashr20_24Ayah("English_Transliteration");
                }

                List<Language> selected_trans = db.getSelectedLanguage();
                if (selected_trans.size() != 0) {
                    for (Language language : selected_trans) {
                        trans_ayahs = db.getAlHashr20_24Ayah(language.getLanguage());
                    }
                }

                if (arabic_lang.equals("Disable") && toggle_en_trans.equals("Disable") && selected_trans.size() == 0) {
                    Tools.SaveDataToSharePrefarence(SurahActivity.this, "Arabic_Lang", "Ar_Uthamani");
                    ayahs = db.getAlHashr20_24Ayah("Ar_Uthamani");
                }

                ayahAdapter = new AyahAdapter(this, ayahs, en_trans, trans_ayahs, total_ayah);
                mAyahRecyclerView.setAdapter(ayahAdapter);

                ayah_number_TV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(SurahActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.ayah_number_layout);
                        dialog.show();

                        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.30);
                        int height = WindowManager.LayoutParams.WRAP_CONTENT;

                        Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);

                        ayah_number_LV = dialog.findViewById(R.id.ayah_number_listview_id);
                        List<Integer> ayah_number = new ArrayList<>();
                        int i = 1;
                        while (i <= total_ayah) {
                            ayah_number.add(i);
                            i++;
                        }
                        ayah_number_adapter = new ArrayAdapter<>(dialog.getContext(), R.layout.single_ayah_number, ayah_number);
                        ayah_number_LV.setAdapter(ayah_number_adapter);

                        search_ayah_number_ET = dialog.findViewById(R.id.search_ayah_number_id);

                        search_ayah_number_ET.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                SurahActivity.this.ayah_number_adapter.getFilter().filter(s);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        ayah_number_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // go to ayah
                                ayahlayoutManager.scrollToPosition(position);
                                dialog.dismiss();
                            }
                        });
                    }
                });
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.surah_menu, menu);
        if (toggle_en_trans.equals("Disable")){
            menu.findItem(R.id.toggle_english_transliteration).setTitle("Enable");
        }else{
            menu.findItem(R.id.toggle_english_transliteration).setTitle("Disable");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.arabic_lang:
                Intent arabicLangIntent = new Intent(this, ArabicLangActivity.class);
                startActivity(arabicLangIntent);
                break;
            case R.id.toggle_english_transliteration:
                toggle_en_trans = item.toString();
                if (toggle_en_trans.equals("Disable")){
                    item.setTitle("Enable");
                    Tools.SaveDataToSharePrefarence(SurahActivity.this, "toggle_en_trans", toggle_en_trans);
                    onStart();
                }else {
                    item.setTitle("Disable");
                    Tools.SaveDataToSharePrefarence(SurahActivity.this, "toggle_en_trans", toggle_en_trans);
                    onStart();
                }
                break;
            case R.id.translation:
                Intent translationIntent = new Intent(this, TranslationActivity.class);
                startActivity(translationIntent);
                break;
            default:
                return false;
        }
        return false;
    }

    // Download From Urls

    private String[] notExistedFiles(String[] filename){
        ArrayList<String> notExistFileName = new ArrayList<>();
        File extStore = ReadAndWriteFiles.getAppExternalFilesDir(this);
        for (String item : filename){
            File file = new File(extStore.getAbsolutePath()+item);
            if (!file.exists()){
                notExistFileName.add(item);
            }
        }
        return Tools.GetStringArray(notExistFileName);
    }

    private void downloadDefaultData(String[] fileNames){
        String[] notExistedFilenames = notExistedFiles(fileNames);
        if(notExistedFilenames.length>0){
            downloadData(notExistedFilenames);
        }
    }

    private void downloadData(String[] urls) {
        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(SurahActivity.this);
        mProgressDialog.setMessage("Please Wait While Loading Data");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        // execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(SurahActivity.this);
        downloadTask.execute(urls);
        ReadAndWriteFiles.readFileAndSaveQuranToDataBase( this,"/Muslims/Prayer/islam_public_ar__uthmani.json", "Ar_Uthamani");
        ReadAndWriteFiles.readFileAndSaveQuranToDataBase( this,"/Muslims/Quran/islam_public_en__english__transliteration.json", "English_Transliteration");
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true); //cancel the task
            }
        });
    }


    // usually, subclasses of AsyncTask are declared inside the activity class.
    // that way, you can easily modify the UI thread from here
    public static class DownloadTask extends AsyncTask<String, Integer, String> {
        @SuppressLint("StaticFieldLeak")
        private Context context;
        private PowerManager.WakeLock mWakeLock;

        private DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                for (String stringUrl : sUrl ){
                    String last = stringUrl.substring(stringUrl.lastIndexOf('/') + 1);
                    URL url = new URL("https://raw.githubusercontent.com/SaymaShinha/islamDB/master/"+last);
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

                    output = new FileOutputStream(ReadAndWriteFiles.getAppExternalFilesDir(context) + stringUrl);

                    byte[] data = new byte[4096];
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
            assert pm != null;
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
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
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
        }
    }

}
