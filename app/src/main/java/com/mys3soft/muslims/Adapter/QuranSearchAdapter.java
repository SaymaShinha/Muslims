package com.mys3soft.muslims.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mys3soft.muslims.Models.Ayah;
import com.mys3soft.muslims.R;
import com.mys3soft.muslims.SurahActivity;
import com.mys3soft.muslims.Tools.Tools;

import java.util.List;

public class QuranSearchAdapter extends RecyclerView.Adapter<QuranSearchAdapter.ViewHolder> {
    private Context context;
    private List<Ayah> ayahList;
    private View mMainView;
    private String ayah_text_en;

    public QuranSearchAdapter(Context context, List<Ayah> ayahList) {
        this.context = context;
        this.ayahList = ayahList;
    }


    @NonNull
    @Override
    public QuranSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mMainView = LayoutInflater.from(context).inflate(R.layout.single_ayah_layout, parent, false);
        return new QuranSearchAdapter.ViewHolder(mMainView);
    }


    @Override
    public void onBindViewHolder(@NonNull QuranSearchAdapter.ViewHolder holder, final int position) {
        Ayah ayah = ayahList.get(position);
        final int surah_num = ayah.getSurah_number();
        final int ayah_num = Tools.getSurahAyahNumberBySurahNumber(surah_num, ayah.getAyah_number());

        ayah_text_en = ayah_num + ". " + ayah.getText();

        holder.mAyahTV.setText(ayah_text_en);
        holder.msurahNameTV.setText(ayah.getSurah_en_name());

        holder.mEnTransAyah.setTextSize(0);

        final String s_name = ayah.getSurah_en_name();
        final int s_total_ayah = Tools.getSurahTotalAyahBySurahNumber(surah_num);

        holder.mAyahTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.SaveDataToSharePrefarence(context,"last_recite_surah_name", s_name);
                Tools.SaveDataToSharePrefarence(context,"last_recite_surah_total_ayah", String.valueOf(s_total_ayah));
                Tools.SaveDataToSharePrefarence(context,"last_recite_surah_number", String.valueOf(surah_num));
                Tools.SaveDataToSharePrefarence(context,"ayah_last_position", String.valueOf(ayah_num-1));

                Intent intent= new Intent(context, SurahActivity.class);
                intent.putExtra("surah_name", s_name);
                intent.putExtra("total_ayah", s_total_ayah);
                intent.putExtra("surah_number", surah_num);
                intent.putExtra("model", "whole_surah");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ayahList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView msurahNameTV, mEnTransAyah, mAyahTV;
        private TableLayout mTableLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            msurahNameTV = itemView.findViewById(R.id.ayah_id);
            mEnTransAyah = itemView.findViewById(R.id.ayah_en_translation);
            mAyahTV = itemView.findViewById(R.id.ayah_translation_1_id);

            mTableLayout = itemView.findViewById(R.id.single_ayah_table_layout);
        }
    }
}

