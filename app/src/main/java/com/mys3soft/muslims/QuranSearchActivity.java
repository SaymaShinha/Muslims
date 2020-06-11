package com.mys3soft.muslims;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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

import com.mys3soft.muslims.Adapter.AyahAdapter;
import com.mys3soft.muslims.Adapter.QuranSearchAdapter;
import com.mys3soft.muslims.DataBase.DBHelper;
import com.mys3soft.muslims.Models.Ayah;
import com.mys3soft.muslims.Tools.ReadAndWriteFiles;
import com.mys3soft.muslims.Tools.Tools;

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
                                optionDialog.cancel();
                                break;
                            case "English Transliteration":
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran", "English_Transliteration");
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran_btn_txt", "En Transliteration");
                                optionDialog.cancel();
                                break;
                            case "Bangla Quran":
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran", "Bn_Muhiuddin_Khan");
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran_btn_txt", "Bangla");
                                optionDialog.cancel();
                                break;
                            case "English Quran":
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran", "En_Ahmed_Raza_Khan");
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran_btn_txt", "English");
                                optionDialog.cancel();
                                break;
                            case "Urdu Quran":
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran", "Ur_Ahmed_Raza_Khan");
                                Tools.SaveDataToSharePrefarence(QuranSearchActivity.this, "search_quran_btn_txt", "Urdu");
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
}
