package com.mys3soft.muslims.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mys3soft.muslims.Models.Calender;
import com.mys3soft.muslims.R;

import java.util.List;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder> {
    private Context context;
    private View mMainView;
    private List<Calender> dayList;

    public CalenderAdapter(Context context, List<Calender> dayList){
        this.context = context;
        this.dayList = dayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mMainView = LayoutInflater.from(context).inflate(R.layout.single_day_calender_layout, parent, false);
        return new ViewHolder(mMainView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Calender day = dayList.get(position);

        String hijri_day = day.getHijri_day_number()+" "+day.getHijri_month_en()+" "+day.getHijri_year_number();

        holder.mGreCalWDTV.setText(day.getGregorian_weakday_en());
        holder.mGreCalDTV.setText(String.valueOf(day.getGregorian_day_number()));
        holder.mGreCalMEnTV.setText(day.getGregorian_month_en());
        holder.mHijriCalWDTV.setText(day.getHijri_weakday_en());
        holder.mHijriCalDMTV.setText(hijri_day);
        holder.mHijriCalNoteTV.setText(day.getHijri_holiday().replaceAll("[\\[\\]\\(\\)\"\"]", ""));
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mHijriCalWDTV, mHijriCalDMTV, mHijriCalNoteTV, mGreCalWDTV, mGreCalDTV, mGreCalMEnTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mHijriCalWDTV = (TextView) itemView.findViewById(R.id.hijri_calender_weakday_en_id);
            mHijriCalDMTV = (TextView) itemView.findViewById(R.id.hijri_calender_day_month_id);
            mHijriCalNoteTV = (TextView) itemView.findViewById(R.id.hijri_calender_note_id);
            mGreCalWDTV = (TextView) itemView.findViewById(R.id.gregorian_calender_Weakday_en_id);
            mGreCalDTV = (TextView) itemView.findViewById(R.id.gregorian_calender_day_number_id);
            mGreCalMEnTV = (TextView) itemView.findViewById(R.id.gregorian_calender_month_en_id);
        }
    }
}
