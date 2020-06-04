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
import com.mys3soft.muslims.Models.Surah;
import com.mys3soft.muslims.R;
import com.mys3soft.muslims.SurahActivity;
import com.mys3soft.muslims.Tools.Tools;

import java.util.List;


public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.ViewHolder> {
    private Context context;
    private List<Surah> surahList;
    private View mMainView;

    public SurahAdapter(Context context, List<Surah> surahList) {
        this.context = context;
        this.surahList = surahList;
    }

    @NonNull
    @Override
    public SurahAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mMainView = LayoutInflater.from(context).inflate(R.layout.single_surah_layout, parent,false);
        return new ViewHolder(mMainView);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahAdapter.ViewHolder holder, final int position) {
        Surah surah = surahList.get(position);
        String s_num = surah.getSurah_Number()+".";
        final String s_name = surah.getSurah_En_Name();
        final int s_total_ayah = surah.getTotal_Ayah();
        String s_name_trans = surah.getSurah_En_Name_Translation()+"( "+surah.getTotal_Ayah()+" )";
        String s_rev_type = surah.getRevelation_Type();
        final int surah_number = surah.getSurah_Number();

        holder.mSurahNumberTV.setText(s_num);
        holder.mSurahArNameTV.setText(surah.getSurah_Ar_Name());
        holder.mSurahNameTV.setText(s_name);
        holder.mSurahTransNameTV.setText(s_name_trans);
        holder.mSurahRevTypeTV.setText(s_rev_type);

        holder.mSingleSurahTableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.SaveDataToSharePrefarence(context,"last_recite_surah_name", s_name);
                Tools.SaveDataToSharePrefarence(context,"last_recite_surah_total_ayah", String.valueOf(s_total_ayah));
                Tools.SaveDataToSharePrefarence(context,"last_recite_surah_number", String.valueOf(surah_number));
                Tools.SaveDataToSharePrefarence(context,"ayah_last_position", String.valueOf(1));

                Intent intent= new Intent(context, SurahActivity.class);
                intent.putExtra("surah_name", s_name);
                intent.putExtra("total_ayah", s_total_ayah);
                intent.putExtra("surah_number", surah_number);
                intent.putExtra("model", "whole_surah");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return surahList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mSurahNumberTV, mSurahArNameTV, mSurahNameTV, mSurahTransNameTV, mSurahRevTypeTV;
        private TableLayout mSingleSurahTableLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSurahArNameTV = (TextView) itemView.findViewById(R.id.surah_ar_name_id);
            mSurahNameTV = (TextView) itemView.findViewById(R.id.surah_name_id);
            mSurahTransNameTV = (TextView) itemView.findViewById(R.id.surah_trans_name_id);
            mSurahNumberTV = (TextView) itemView.findViewById(R.id.surah_number_id);
            mSurahRevTypeTV = (TextView) itemView.findViewById(R.id.surah_rev_type_id);
            mSingleSurahTableLayout = (TableLayout) itemView.findViewById(R.id.single_surah_layout_id);
        }
    }
}
