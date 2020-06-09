package com.mys3soft.muslims;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.mys3soft.muslims.Tools.Tools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ArabicLangActivity extends AppCompatActivity {
    private ArrayAdapter<String> arabic_lang_adapter;
    private ListView arabicLangLV;
    private EditText search_arabic_lang_ET;
    private String[] arabic_lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arabic_lang);

        Toolbar toolbar = findViewById(R.id.arabic_lang_toolbar_id);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Arabic");
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String toggle_value = bundle.getString("toggle_value");

        assert toggle_value != null;
        if (toggle_value.equals("Disable")){
            arabic_lang = new String[]{"Enable", "Ar_Uthamani"};
        } else{
            arabic_lang = new String[]{"Disable", "Ar_Uthamani"};
        }

        arabicLangLV = findViewById(R.id.arabic_lang_listview_id);
        List<String> arabic_lang_list = new ArrayList<>(Arrays.asList(arabic_lang));
        arabic_lang_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arabic_lang_list);
        arabicLangLV.setAdapter(arabic_lang_adapter);

        arabic_lang_adapter.getItem(0);

        search_arabic_lang_ET = findViewById(R.id.search_arabic_lang_id);

        search_arabic_lang_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArabicLangActivity.this.arabic_lang_adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        arabicLangLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String arabic_lang = arabicLangLV.getItemAtPosition(position).toString();
                Tools.SaveDataToSharePrefarence(ArabicLangActivity.this, "Arabic_Lang", arabic_lang);
                finish();
            }
        });
    }
}
