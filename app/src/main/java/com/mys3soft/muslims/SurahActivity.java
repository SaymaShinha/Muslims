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
    private RecyclerView mAyahRecyclerView;
    private List<Ayah> ayahs = new ArrayList<>();
    private List<Ayah> en_trans = new ArrayList<>();
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
    private String toggle_en_trans;
    private String arabic_lang;
    private int ayah_position_num;
    private int selected_ayah_number;



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

        mAyahRecyclerView.scrollToPosition(ayah_position_num);

        arabic_lang = Tools.GetDataFromSharePrefarence(SurahActivity.this, "Arabic_Lang");
        toggle_en_trans = Tools.GetDataFromSharePrefarence(SurahActivity.this, "toggle_en_trans");

        switch (model) {
            case "whole_surah": {
                if (!arabic_lang.equals("Disable")) {
                    if (!arabic_lang.equals("")) {
                        ayahs = db.getAllQuranAyah(arabic_lang, surah_number);
                    } else {
                        ayahs = db.getAllQuranAyah("Ar_Uthamani", surah_number);
                    }
                }

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
}
