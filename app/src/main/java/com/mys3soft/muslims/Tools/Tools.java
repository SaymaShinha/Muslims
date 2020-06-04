package com.mys3soft.muslims.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Tools {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    // Function to convert ArrayList<String> to String[]
    public static String[] GetStringArray(ArrayList<String> arr)
    {

        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // Convert ArrayList to object array
        Object[] objArr = arr.toArray();

        // Iterating and converting to String
        int i = 0;
        for (Object obj : objArr) {
            str[i++] = (String)obj;
        }

        return str;
    }

    public static String get_Today_DD_MM_YYYY_FormatedDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    public static String get_Today_FormatedDate(String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date();
        return formatter.format(date);
    }

    public static String get_Now_HH_mm_FormatedTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return formatter.format(date);
    }

    public static void SaveDataToSharePrefarence(Context context, String key, String value){
        SharedPreferences sp = context.getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String GetDataFromSharePrefarence(Context context, String key){
        SharedPreferences sharedPref = context.getSharedPreferences("Data", Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }


    public static int GetTwo_HH_mm_TimeDiff(String time1, String time2){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");

        try {
            Date date1 = simpleDateFormat.parse(time1);
            Date date2 = simpleDateFormat.parse(time2);
            long difference = Objects.requireNonNull(date2).getTime() - Objects.requireNonNull(date1).getTime();
            int days = (int) (difference / (1000*60*60*24));
            int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
            int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
            hours = (hours < 0 ? -hours : hours);

            return min;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String GetTwo_hh_mm_TimeDiff_String_Value(String time1, String time2){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");

        try {
            Date date1 = simpleDateFormat.parse(time1);
            Date date2 = simpleDateFormat.parse(time2);
            long difference = Objects.requireNonNull(date2).getTime() - Objects.requireNonNull(date1).getTime();
            int days = (int) (difference / (1000*60*60*24));
            int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
            int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
            hours = (hours < 0 ? -hours : hours);

            return time2+" ( - "+String.valueOf(hours)+":"+min+" )";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertToArabic(int value)
    {
        String newValue =   (((((((((((value+"").replaceAll("1", "١"))
                .replaceAll("2", "٢")).replaceAll("3", "٣"))
                .replaceAll("4", "٤")).replaceAll("5", "٥"))
                .replaceAll("6", "٦")).replaceAll("7", "٧"))
                .replaceAll("8", "٨")).replaceAll("9", "٩"))
                .replaceAll("0", "٠"));
        return newValue;
    }

    public static boolean hasPreview(RecyclerView recyclerView) {
        return getCurrentItem(recyclerView) > 0;
    }

    public static boolean hasNext(RecyclerView recyclerView) {
        return recyclerView.getAdapter() != null &&
                getCurrentItem(recyclerView) < (recyclerView.getAdapter().getItemCount()- 1);
    }

    public static void preview(RecyclerView recyclerView) {
        int position = getCurrentItem(recyclerView);
        if (position > 0)
            setCurrentItem(position -1, true, recyclerView);
    }

    public static void next(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null)
            return;

        int position = getCurrentItem(recyclerView);
        int count = adapter.getItemCount();
        if (position < (count -1))
            setCurrentItem(position + 1, true, recyclerView);
    }

    public static int getCurrentItem(RecyclerView recyclerView){
        return ((LinearLayoutManager)recyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

    public static void setCurrentItem(int position, boolean smooth, RecyclerView recyclerView){
        if (smooth)
            recyclerView.smoothScrollToPosition(position);
        else
            recyclerView.scrollToPosition(position);
    }

    public static String converDatePattern(String OLD_FORMAT, String oldDateString, String NEW_FORMAT){
        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date d = null;
        try {
            d = sdf.parse(oldDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern(NEW_FORMAT);
        String newDateString = sdf.format(d);
        return newDateString;
    }

}
