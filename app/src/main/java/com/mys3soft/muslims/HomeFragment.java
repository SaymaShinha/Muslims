package com.mys3soft.muslims;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.system.StructUtsname;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mys3soft.muslims.Models.Surah;
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


public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String LOG_TAG = "HomeFragment";
    private View mMainView;
    private ArrayAdapter<String> home_adapter;
    private ListView homeLV;
    private EditText search_home_ET;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_home, container, false);
        homeLV = (ListView) mMainView.findViewById(R.id.home_listview_id);

        return mMainView;
    }

    @Override
    public void onStart() {
        super.onStart();

        final String[] home_string = new String[]{"Important Quran Ayah", "Calender"};
        List<String> home_string_list = new ArrayList<String>(Arrays.asList(home_string));
        home_adapter = new ArrayAdapter<String>(mMainView.getContext(), R.layout.single_home_textview, home_string_list);
        homeLV.setAdapter(home_adapter);

        homeLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String homeStringLV = homeLV.getItemAtPosition(position).toString();
                if (homeStringLV.equals("Important Quran Ayah")){
                    Intent impQuranAyahIntent = new Intent(mMainView.getContext(),ImportantQuranAyahActivity.class);
                    startActivity(impQuranAyahIntent);
                } else if (homeStringLV.equals("Calender")){
                    Intent impQuranAyahIntent = new Intent(mMainView.getContext(),CalenderActivity.class);
                    startActivity(impQuranAyahIntent);
                }
            }
        });

        search_home_ET = (EditText) mMainView.findViewById(R.id.search_home_id);

        search_home_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                home_adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
