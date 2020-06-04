package com.mys3soft.muslims;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.mys3soft.muslims.Adapter.AyahAdapter;
import com.mys3soft.muslims.DataBase.DBHelper;
import com.mys3soft.muslims.Models.Ayah;
import com.mys3soft.muslims.Models.Language;
import com.mys3soft.muslims.Tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImportantQuranAyahActivity extends AppCompatActivity {
    private ArrayAdapter<String> important_quran_ayah_adapter;
    private ListView important_quran_ayahLV;
    private EditText search_important_quran_ayah_ET;
    private List<Ayah> ayahs;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_quran_ayah);

        Toolbar toolbar = (Toolbar) findViewById(R.id.important_quran_ayah_toolbar_id);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Important Quran Ayah");
        setSupportActionBar(toolbar);

        db = new DBHelper(ImportantQuranAyahActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        important_quran_ayahLV = (ListView) findViewById(R.id.important_quran_ayah_listview_id);
        String[] important_quran_ayah_lang = new String[]{"Ayah-ul Kursi", "Al-Baqara 285-286 Ayah", "Al-Hashr 20-24 Ayah"};
        List<String> important_quran_ayah_list = new ArrayList<String>(Arrays.asList(important_quran_ayah_lang));
        important_quran_ayah_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, important_quran_ayah_list);
        important_quran_ayahLV.setAdapter(important_quran_ayah_adapter);

        search_important_quran_ayah_ET = (EditText) findViewById(R.id.search_important_quran_ayah_id);

        search_important_quran_ayah_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ImportantQuranAyahActivity.this.important_quran_ayah_adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        important_quran_ayahLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String quran_ayah = important_quran_ayahLV.getItemAtPosition(position).toString();
                if (quran_ayah.equals("Ayah-ul Kursi")){
                    Intent intent= new Intent(ImportantQuranAyahActivity.this, SurahActivity.class);
                    intent.putExtra("surah_name", "Ayah-ul Kursi");
                    intent.putExtra("total_ayah", 1);
                    intent.putExtra("surah_number", 2);
                    intent.putExtra("model", "ayah_ul_kursi");
                    startActivity(intent);
                } else if(quran_ayah.equals("Al-Baqara 285-286 Ayah")){
                    Intent intent= new Intent(ImportantQuranAyahActivity.this, SurahActivity.class);
                    intent.putExtra("surah_name", "Al-Baqara 285-286 Ayah");
                    intent.putExtra("total_ayah", 2);
                    intent.putExtra("surah_number", 2);
                    intent.putExtra("model", "al_baqara_285_286_ayah");
                    startActivity(intent);
                } else if(quran_ayah.equals("Al-Hashr 20-24 Ayah")){
                    Intent intent= new Intent(ImportantQuranAyahActivity.this, SurahActivity.class);
                    intent.putExtra("surah_name", "Al-Baqara 285-286 Ayah");
                    intent.putExtra("total_ayah", 5);
                    intent.putExtra("surah_number", 2);
                    intent.putExtra("model", "al_hashr_20_24_ayah");
                    startActivity(intent);
                }
            }
        });
    }

    public void getAyah(String table_name, int surah_number){

    }
}
