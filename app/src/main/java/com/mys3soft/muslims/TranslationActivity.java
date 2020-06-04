package com.mys3soft.muslims;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.mys3soft.muslims.Adapter.LanguageAdapter;
import com.mys3soft.muslims.DataBase.DBHelper;
import com.mys3soft.muslims.Models.Language;
import com.mys3soft.muslims.Tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TranslationActivity extends AppCompatActivity {
    private EditText search_language_ET;
    private DBHelper db;
    private ListView languageLV;
    private LanguageAdapter languageAdapter;
    private List<Language> languageArrayList;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.translation_toolbar_id);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.translation);
        setSupportActionBar(toolbar);

        db = new DBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();


        final RecyclerView languageLV = (RecyclerView) findViewById(R.id.language_recyclerView_id);
        final EditText searchLanguageET = (EditText) findViewById(R.id.language_search_id);

        List<Language> languageList = db.getQuranTranslator();
        languageAdapter = new LanguageAdapter(TranslationActivity.this, languageList);
        linearLayoutManager = new LinearLayoutManager(this);
        languageLV.setLayoutManager(linearLayoutManager);
        languageLV.setAdapter(languageAdapter);
        languageAdapter.notifyDataSetChanged();

        searchLanguageET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Language> languages = db.getSearchLanguageData(searchLanguageET.getText().toString());
                languageAdapter = new LanguageAdapter(TranslationActivity.this, languages);
                languageLV.setLayoutManager(new LinearLayoutManager(TranslationActivity.this));
                languageLV.setAdapter(languageAdapter);
                languageAdapter.notifyDataSetChanged();
                finish();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
