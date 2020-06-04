package com.mys3soft.muslims.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mys3soft.muslims.Models.Ayah;
import com.mys3soft.muslims.R;
import com.mys3soft.muslims.Tools.Tools;
import java.util.List;


public class AyahAdapter extends RecyclerView.Adapter<AyahAdapter.ViewHolder> {
    private Context context;
    private List<Ayah> ayahList, transAyahList_1, englishTransList;
    private View mMainView;
    private int textAling, textDir, total_ayah_number;

    public AyahAdapter(Context context, List<Ayah> ayahList, List<Ayah> englishTransList, List<Ayah> transAyahList_1, Integer total_ayah_number) {
        this.context = context;
        this.ayahList = ayahList;
        this.transAyahList_1 = transAyahList_1;
        this.englishTransList = englishTransList;
        this.total_ayah_number = total_ayah_number;
    }


    @NonNull
    @Override
    public AyahAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (transAyahList_1.size() != 0){
            String language = transAyahList_1.get(0).getLanguage();
            if(language.equals("ar") || language.equals("ur") || language.equals("fa")|| language.equals("sd")){
                textAling = View.TEXT_ALIGNMENT_VIEW_END;
                textDir = View.TEXT_DIRECTION_ANY_RTL;
            }else {
                textAling = View.TEXT_ALIGNMENT_TEXT_START;
                textDir = View.TEXT_DIRECTION_LTR;
            }
        }
        mMainView = LayoutInflater.from(context).inflate(R.layout.single_ayah_layout, parent, false);
        return new ViewHolder(mMainView);
    }


    @Override
    public void onBindViewHolder(@NonNull AyahAdapter.ViewHolder holder, final int position) {
        if (ayahList != null){
            Ayah ayah = ayahList.get(position);
            String ayah_ar = ayah.getText()+context.getResources().getString(R.string.end_ayah_1)+
                    Tools.convertToArabic(position+1)+context.getResources().getString(R.string.end_ayah_2);
            holder.mAyahTV.setText(ayah_ar);
        }else {
            holder.mAyahTV.setTextSize(0);
        }

        if (transAyahList_1.size() != 0){
            Ayah trans_ayah = transAyahList_1.get(position);
            String ayah_trans_1 = position+1+". "+trans_ayah.getText();
            holder.mAyahTrasTV_1.setText(ayah_trans_1);
        }else {
            holder.mAyahTrasTV_1.setTextSize(0);
        }

        if (englishTransList != null){
            Ayah en_trans_ayah = englishTransList.get(position);
            String ayah_trans_1 = position+1+". "+en_trans_ayah.getText();
            holder.mEnTransAyah.setText(ayah_trans_1);
        }else {
            holder.mEnTransAyah.setTextSize(0);
        }
    }

    @Override
    public int getItemCount() {
        return total_ayah_number;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mAyahTV, mEnTransAyah, mAyahTrasTV_1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mAyahTV = itemView.findViewById(R.id.ayah_id);
            mEnTransAyah = itemView.findViewById(R.id.ayah_en_translation);
            mAyahTrasTV_1 = itemView.findViewById(R.id.ayah_translation_1_id);
            mAyahTrasTV_1.setTextAlignment(textAling);
            mAyahTrasTV_1.setTextDirection(textDir);
        }
    }
}
