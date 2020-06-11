package com.mys3soft.muslims;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mys3soft.muslims.Adapter.PrayerLocationAdapter;
import com.mys3soft.muslims.Adapter.RevSurahAdapter;
import com.mys3soft.muslims.Adapter.SurahAdapter;
import com.mys3soft.muslims.DataBase.DBHelper;
import com.mys3soft.muslims.Models.RevSurah;
import com.mys3soft.muslims.Models.Surah;
import com.mys3soft.muslims.Models.World_Cities;
import com.mys3soft.muslims.Tools.ReadAndWriteFiles;
import com.mys3soft.muslims.Tools.ReadJsonData;
import com.mys3soft.muslims.Tools.Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link QuranFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuranFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String LOG_TAG = "Ex.Store: QuranFragment";
    private Button quran_sort_btn;
    private View mMainView;
    private RecyclerView mRecyclerView;
    private List<Surah> surahs;
    private List<RevSurah> revSurahs;
    private EditText search_quran_ET;
    private DBHelper db;
    private ArrayAdapter<String> sortAdapter;
    private ListView sortLV;
    private EditText search_sort_ET;
    private String selectedSort = "";
    private int surah_position_num;
    private Context context;
    private String last_recite_surah_name;
    private int last_recite_surah_total_ayah, last_recite_surah_number;
    private SurahAdapter surahAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuranFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuranFragment.
     */
    // TODO: Rename and change types and number of parameters
    static QuranFragment newInstance(String param1, String param2) {
        QuranFragment fragment = new QuranFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_quran, container, false);
        search_quran_ET = mMainView.findViewById(R.id.search_surah_id);

        mRecyclerView = mMainView.findViewById(R.id.quran_fragment_recycler_id);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        db = new DBHelper(getContext());
        context = mMainView.getContext();
        // Inflate the layout for this fragment
        return mMainView;
    }

    @Override
    public void onStart () {
        super.onStart();

        ////////////////////////////////////////////////////////////////////////
        last_recite_surah_name = Tools.GetDataFromSharePrefarence(context, "last_recite_surah_name");
        String last_recite_surah_total_ayah_str = Tools.GetDataFromSharePrefarence(context, "last_recite_surah_total_ayah");
        if (!last_recite_surah_total_ayah_str.equals("")) {
            last_recite_surah_total_ayah = Integer.valueOf(last_recite_surah_total_ayah_str);
        }else {
            last_recite_surah_total_ayah = 7;
        }

        String last_recite_surah_number_str = Tools.GetDataFromSharePrefarence(context, "last_recite_surah_number");
        if (!last_recite_surah_number_str.equals("")) {
            last_recite_surah_number = Integer.valueOf(last_recite_surah_number_str);
        } else {
            last_recite_surah_number = 1;
        }

        String ayah_last_position = Tools.GetDataFromSharePrefarence(context, "ayah_last_position");

        LinearLayout last_recite_linearLayout = mMainView.findViewById(R.id.quran_recite_last_position_LinearLayout_id);
        TextView textView = mMainView.findViewById(R.id.quran_recite_last_position_tv_id);

        if(!ayah_last_position.equals("")){
            String tv_last_reciting_surah = last_recite_surah_name + " ( " + String.valueOf(Integer.valueOf(ayah_last_position)+1) + " ) ";
            textView.setText(tv_last_reciting_surah);
        } else{
            textView.setText("");
        }


        last_recite_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mMainView.getContext(), SurahActivity.class);
                intent.putExtra("surah_name", last_recite_surah_name);
                intent.putExtra("total_ayah", last_recite_surah_total_ayah);
                intent.putExtra("surah_number", last_recite_surah_number);
                intent.putExtra("model", "last_surah");
                context.startActivity(intent);
            }
        });
        ///////////////////////////////////////////////////



        final String surah_position = Tools.GetDataFromSharePrefarence(mMainView.getContext(), "surah_position");
        if (!surah_position.equals("")) {
            surah_position_num = Integer.valueOf(surah_position);
        } else {
            surah_position_num = 0;
        }

        selectedSort = Tools.GetDataFromSharePrefarence(mMainView.getContext(), "quran_sort_type");

        switch (selectedSort) {
            case "":
            case "Traditional Order":
                surahs = db.getAllSurah();
                surahAdapter = new SurahAdapter(mMainView.getContext(), surahs);
                mRecyclerView.setAdapter(surahAdapter);

                search_quran_ET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        surahs.clear();
                        surahs = db.getSearchSurah(search_quran_ET.getText().toString());
                        SurahAdapter surahAdapter = new SurahAdapter(mMainView.getContext(), surahs);
                        mRecyclerView.setAdapter(surahAdapter);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case "Desc Surah Name":
                surahs = db.getAllSurahDescSurahName();
                surahAdapter = new SurahAdapter(mMainView.getContext(), surahs);
                mRecyclerView.setAdapter(surahAdapter);

                search_quran_ET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        surahs.clear();
                        surahs = db.getSearchSurah(search_quran_ET.getText().toString());
                        SurahAdapter surahAdapter = new SurahAdapter(mMainView.getContext(), surahs);
                        mRecyclerView.setAdapter(surahAdapter);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case "Asc Surah Name":
                surahs = db.getAllSurahAscSurahName();
                surahAdapter = new SurahAdapter(mMainView.getContext(), surahs);
                mRecyclerView.setAdapter(surahAdapter);

                search_quran_ET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        surahs.clear();
                        surahs = db.getSearchSurah(search_quran_ET.getText().toString());
                        SurahAdapter surahAdapter = new SurahAdapter(mMainView.getContext(), surahs);
                        mRecyclerView.setAdapter(surahAdapter);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case "Meccan Surah":
                surahs = db.getAllMeccanSurah();
                surahAdapter = new SurahAdapter(mMainView.getContext(), surahs);
                mRecyclerView.setAdapter(surahAdapter);

                search_quran_ET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        surahs.clear();
                        surahs = db.getSearchSurah(search_quran_ET.getText().toString());
                        SurahAdapter surahAdapter = new SurahAdapter(mMainView.getContext(), surahs);
                        mRecyclerView.setAdapter(surahAdapter);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case "Medinan Surah":
                surahs = db.getAllMedinanSurah();
                surahAdapter = new SurahAdapter(mMainView.getContext(), surahs);
                mRecyclerView.setAdapter(surahAdapter);

                search_quran_ET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        surahs.clear();
                        surahs = db.getSearchSurah(search_quran_ET.getText().toString());
                        SurahAdapter surahAdapter = new SurahAdapter(mMainView.getContext(), surahs);
                        mRecyclerView.setAdapter(surahAdapter);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case "Desc Surah Total Ayah":
                surahs = db.getAllSurahOrderByTotalAyahDesc();
                surahAdapter = new SurahAdapter(mMainView.getContext(), surahs);
                mRecyclerView.setAdapter(surahAdapter);

                search_quran_ET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        surahs.clear();
                        surahs = db.getSearchSurah(search_quran_ET.getText().toString());
                        SurahAdapter surahAdapter = new SurahAdapter(mMainView.getContext(), surahs);
                        mRecyclerView.setAdapter(surahAdapter);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case "Asc Surah Total Ayah":
                surahs = db.getAllSurahOrderByTotalAyahAsc();
                surahAdapter = new SurahAdapter(mMainView.getContext(), surahs);
                mRecyclerView.setAdapter(surahAdapter);

                search_quran_ET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        surahs.clear();
                        surahs = db.getSearchSurah(search_quran_ET.getText().toString());
                        SurahAdapter surahAdapter = new SurahAdapter(mMainView.getContext(), surahs);
                        mRecyclerView.setAdapter(surahAdapter);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case "According To Revelation":
                revSurahs = db.getAllRevSurah();
                final RevSurahAdapter revSurahAdapter = new RevSurahAdapter(mMainView.getContext(), revSurahs);
                mRecyclerView.setAdapter(revSurahAdapter);

                search_quran_ET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        revSurahs.clear();
                        revSurahs = db.getSearchRevSurah(search_quran_ET.getText().toString());
                        RevSurahAdapter revSurahAdapter = new RevSurahAdapter(mMainView.getContext(), revSurahs);
                        mRecyclerView.setAdapter(revSurahAdapter);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
        }

        quran_sort_btn = mMainView.findViewById(R.id.quran_sort_btn_id);

        quran_sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mMainView.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.quran_sort_layout);
                dialog.show();

                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.70);
                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);

                sortLV = dialog.findViewById(R.id.sort_listview_id);

                String[] home_string = new String[]{"Traditional Order", "According To Revelation", "Desc Surah Name",
                        "Asc Surah Name", "Desc Surah Total Ayah", "Asc Surah Total Ayah", "Meccan Surah", "Medinan Surah"};
                List<String> home_string_list = new ArrayList<>(Arrays.asList(home_string));
                sortAdapter = new ArrayAdapter<>(mMainView.getContext(), android.R.layout.simple_list_item_1, home_string_list);
                sortLV.setAdapter(sortAdapter);

                search_sort_ET = dialog.findViewById(R.id.search_sort_id);

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
                        selectedSort = sortAdapter.getItem(position);
                        Tools.SaveDataToSharePrefarence(mMainView.getContext(), "quran_sort_type", selectedSort);
                        dialog.cancel();
                    }
                });

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        onStart();
                    }
                });
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.quran_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.search_on_quran:
                Intent quranSearchIntent = new Intent(context, QuranSearchActivity.class);
                startActivity(quranSearchIntent);
                break;
            default:
                return false;
        }
        return false;
    }
}