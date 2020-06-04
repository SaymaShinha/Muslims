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
import com.mys3soft.muslims.Models.RevSurah;
import com.mys3soft.muslims.Models.Surah;
import com.mys3soft.muslims.R;
import com.mys3soft.muslims.SurahActivity;
import com.mys3soft.muslims.Tools.Tools;

import java.util.List;

public class RevSurahAdapter extends RecyclerView.Adapter<RevSurahAdapter.ViewHolder>  {
    private Context context;
    private List<RevSurah> revSurahList;
    private View mMainView;

    public RevSurahAdapter(Context context, List<RevSurah> revSurahList) {
        this.context = context;
        this.revSurahList = revSurahList;
    }
    @NonNull
    @Override
    public RevSurahAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mMainView = LayoutInflater.from(context).inflate(R.layout.single_rev_surah_layout, parent,false);
        return new RevSurahAdapter.ViewHolder(mMainView);
    }

    @Override
    public void onBindViewHolder(@NonNull RevSurahAdapter.ViewHolder holder, int position) {
        RevSurah revSurah = revSurahList.get(position);
        String s_num = revSurah.getChronological_Order()+".";
        final String s_name = revSurah.getSurah_En_Name();
        final int s_total_ayah = revSurah.getTotal_Ayah();
        String s_name_trans = revSurah.getSurah_En_Name_Translation()+"( "+revSurah.getTotal_Ayah()+" )";
        String s_rev_type = revSurah.getRevelation_Type();
        final int surah_number = revSurah.getTraditional_Order();

        holder.mRevSurahCorTV.setText(s_num);
        holder.mRevSurahArNameTV.setText(revSurah.getSurah_Ar_Name());
        holder.mRevSurahEnNameTV.setText(s_name);
        holder.mRevSurahTransNameTV.setText(s_name_trans);
        holder.mRevSurahRevTypeTV.setText(s_rev_type);
        holder.mRevSurahNoteTV.setText(revSurah.getNote());

        holder.mSingleRevSurahTableLayout.setOnClickListener(new View.OnClickListener() {
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
        return revSurahList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mRevSurahCorTV, mRevSurahArNameTV, mRevSurahEnNameTV, mRevSurahTransNameTV, mRevSurahRevTypeTV, mRevSurahNoteTV;
        private TableLayout mSingleRevSurahTableLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRevSurahCorTV = (TextView) itemView.findViewById(R.id.cor_surah_number_id);
            mRevSurahEnNameTV = (TextView) itemView.findViewById(R.id.rev_surah_en_name_id);
            mRevSurahArNameTV = (TextView) itemView.findViewById(R.id.rev_surah_ar_name_id);
            mRevSurahTransNameTV = (TextView) itemView.findViewById(R.id.rev_surah_trans_name_id);
            mRevSurahRevTypeTV = (TextView) itemView.findViewById(R.id.rev_surah_rev_type_id);
            mRevSurahNoteTV = (TextView) itemView.findViewById(R.id.rev_surah_note_id);
            mSingleRevSurahTableLayout = (TableLayout) itemView.findViewById(R.id.single_rev_surah_layout_id);
        }
    }
}
