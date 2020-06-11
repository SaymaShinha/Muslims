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

    public static int getSurahTotalAyahBySurahName(String surah_name){
        switch (surah_name){
            case "Al-Faatiha":
                return 7;
            case "Al-Baqara":
                return 286;
            case "Aal-i-Imraan":
                return 200;
            case "An-Nisaa":
                return 176;
            case "Al-Maaida":
                return 120;
            case "Al-An'aam":
                return 165;
            case "Al-A'raaf":
                return 206;
            case "Al-Anfaal":
                return 75;
            case "At-Tawba":
                return 129;
            case "Yunus":
                return 109;
            case "Hud":
                return 123;
            case "Yusuf":
                return 111;
            case "Ar-Ra'd":
                return 43;
            case "Ibrahim":
                return 52;
            case "Al-Hijr":
                return 99;
            case "An-Nahl":
                return 128;
            case "Al-Israa":
                return 111;
            case "Al-Kahf":
                return 110;
            case "Maryam":
                return 98;
            case "Taa-Haa":
                return 135;
            case "Al-Anbiyaa":
                return 112;
            case "Al-Hajj":
                return 78;
            case "Al-Muminoon":
                return 118;
            case "An-Noor":
                return 64;
            case "Al-Furqaan":
                return 77;
            case "Ash-Shu'araa":
                return 227;
            case "An-Naml":
                return 93;
            case "Al-Qasas":
                return 88;
            case "Al-Ankaboot":
                return 69;
            case "Ar-Room":
                return 60;
            case "Luqman":
                return 34;
            case "As-Sajda":
                return 30;
            case "Al-Ahzaab":
                return 73;
            case "Saba":
                return 54;
            case "Faatir":
                return 45;
            case "Yaseen":
                return 83;
            case "As-Saaffaat":
                return 182;
            case "Saad":
                return 88;
            case "Az-Zumar":
                return 75;
            case "Ghafir":
                return 85;
            case "Fussilat":
                return 54;
            case "Ash-Shura":
                return 53;
            case "Az-Zukhruf":
                return 89;
            case "Ad-Dukhaan":
                return 59;
            case "Al-Jaathiya":
                return 37;
            case "Al-Ahqaf":
                return 35;
            case "Muhammad":
                return 38;
            case "Al-Fath":
                return 29;
            case "Al-Hujuraat":
                return 18;
            case "Qaaf":
                return 45;
            case "Adh-Dhaariyat":
                return 60;
            case "At-Tur":
                return 49;
            case "An-Najm":
                return 62;
            case "Al-Qamar":
                return 55;
            case "Ar-Rahmaan":
                return 78;
            case "Al-Waaqia":
                return 96;
            case "Al-Hadid":
                return 29;
            case "Al-Mujaadila":
                return 22;
            case "Al-Hashr":
                return 24;
            case "Al-Mumtahana":
                return 13;
            case "As-Saff":
                return 14;
            case "Al-Jumu'a":
                return 11;
            case "Al-Munaafiqoon":
                return 11;
            case "At-Taghaabun":
                return 18;
            case "At-Talaaq":
                return 12;
            case "At-Tahrim":
                return 12;
            case "Al-Mulk":
                return 30;
            case "Al-Qalam":
                return 52;
            case "Al-Haaqqa":
                return 52;
            case "Al-Ma'aarij":
                return 44;
            case "Nooh":
                return 28;
            case "Al-Jinn":
                return 28;
            case "Al-Muzzammil":
                return 20;
            case "Al-Muddaththir":
                return 56;
            case "Al-Qiyaama":
                return 40;
            case "Al-Insaan":
                return 31;
            case "Al-Mursalaat":
                return 50;
            case "An-Naba":
                return 40;
            case "An-Naazi'aat":
                return 46;
            case "Abasa":
                return 42;
            case "At-Takwir":
                return 29;
            case "Al-Infitaar":
                return 19;
            case "Al-Mutaffifin":
                return 36;
            case "Al-Inshiqaaq":
                return 25;
            case "Al-Burooj":
                return 22;
            case "At-Taariq":
                return 17;
            case "Al-A'laa":
                return 19;
            case "Al-Ghaashiya":
                return 26;
            case "Al-Fajr":
                return 30;
            case "Al-Balad":
                return 20;
            case "Ash-Shams":
                return 15;
            case "Al-Lail":
                return 21;
            case "Ad-Dhuhaa":
                return 11;
            case "Ash-Sharh":
                return 8;
            case "At-Tin":
                return 8;
            case "Al-Alaq":
                return 19;
            case "Al-Qadr":
                return 5;
            case "Al-Bayyina":
                return 8;
            case "Az-Zalzala":
                return 8;
            case "Al-Aadiyaat":
                return 11;
            case "Al-Qaari'a":
                return 11;
            case "At-Takaathur":
                return 8;
            case "Al-Asr":
                return 3;
            case "Al-Humaza":
                return 9;
            case "Al-Fil":
                return 5;
            case "Quraish":
                return 4;
            case "Al-Maa'un":
                return 7;
            case "Al-Kawthar":
                return 3;
            case "Al-Kaafiroon":
                return 6;
            case "An-Nasr":
                return 3;
            case "Al-Masad":
                return 5;
            case "Al-Ikhlaas":
                return 4;
            case "Al-Falaq":
                return 5;
            case "An-Naas":
                return 6;
            default:
                return 0;
        }
    }

    public static int getSurahTotalAyahBySurahNumber(int surah_number){
        switch (surah_number){
            case 1:
                return 7;
            case 2:
                return 286;
            case 3:
                return 200;
            case 4:
                return 176;
            case 5:
                return 120;
            case 6:
                return 165;
            case 7:
                return 206;
            case 8:
                return 75;
            case 9:
                return 129;
            case 10:
                return 109;
            case 11:
                return 123;
            case 12:
                return 111;
            case 13:
                return 43;
            case 14:
                return 52;
            case 15:
                return 99;
            case 16:
                return 128;
            case 17:
                return 111;
            case 18:
                return 110;
            case 19:
                return 98;
            case 20:
                return 135;
            case 21:
                return 112;
            case 22:
                return 78;
            case 23:
                return 118;
            case 24:
                return 64;
            case 25:
                return 77;
            case 26:
                return 227;
            case 27:
                return 93;
            case 28:
                return 88;
            case 29:
                return 69;
            case 30:
                return 60;
            case 31:
                return 34;
            case 32:
                return 30;
            case 33:
                return 73;
            case 34:
                return 54;
            case 35:
                return 45;
            case 36:
                return 83;
            case 37:
                return 182;
            case 38:
                return 88;
            case 39:
                return 75;
            case 40:
                return 85;
            case 41:
                return 54;
            case 42:
                return 53;
            case 43:
                return 89;
            case 44:
                return 59;
            case 45:
                return 37;
            case 46:
                return 35;
            case 47:
                return 38;
            case 48:
                return 29;
            case 49:
                return 18;
            case 50:
                return 45;
            case 51:
                return 60;
            case 52:
                return 49;
            case 53:
                return 62;
            case 54:
                return 55;
            case 55:
                return 78;
            case 56:
                return 96;
            case 57:
                return 29;
            case 58:
                return 22;
            case 59:
                return 24;
            case 60:
                return 13;
            case 61:
                return 14;
            case 62:
                return 11;
            case 63:
                return 11;
            case 64:
                return 18;
            case 65:
                return 12;
            case 66:
                return 12;
            case 67:
                return 30;
            case 68:
                return 52;
            case 69:
                return 52;
            case 70:
                return 44;
            case 71:
                return 28;
            case 72:
                return 28;
            case 73:
                return 20;
            case 74:
                return 56;
            case 75:
                return 40;
            case 76:
                return 31;
            case 77:
                return 50;
            case 78:
                return 40;
            case 79:
                return 46;
            case 80:
                return 42;
            case 81:
                return 29;
            case 82:
                return 19;
            case 83:
                return 36;
            case 84:
                return 25;
            case 85:
                return 22;
            case 86:
                return 17;
            case 87:
                return 19;
            case 88:
                return 26;
            case 89:
                return 30;
            case 90:
                return 20;
            case 91:
                return 15;
            case 92:
                return 21;
            case 93:
                return 11;
            case 94:
                return 8;
            case 95:
                return 8;
            case 96:
                return 19;
            case 97:
                return 5;
            case 98:
                return 8;
            case 99:
                return 8;
            case 100:
                return 11;
            case 101:
                return 11;
            case 102:
                return 8;
            case 103:
                return 3;
            case 104:
                return 9;
            case 105:
                return 5;
            case 106:
                return 4;
            case 107:
                return 7;
            case 108:
                return 3;
            case 109:
                return 6;
            case 110:
                return 3;
            case 111:
                return 5;
            case 112:
                return 4;
            case 113:
                return 5;
            case 114:
                return 6;
            default:
                return 0;
        }
    }

    public static int getSurahAyahNumberBySurahNumber(int surah_num, int ayah_num){
        switch (surah_num) {
            case 2:
                return ayah_num - 7;
            case 3:
                return ayah_num - 293;
            case 4:
                return ayah_num - 493;
            case 5:
                return ayah_num - 669;
            case 6:
                return ayah_num - 789;
            case 7:
                return ayah_num - 954;
            case 8:
                return ayah_num - 1160;
            case 9:
                return ayah_num - 1235;
            case 10:
                return ayah_num - 1364;
            case 11:
                return ayah_num - 1473;
            case 12:
                return ayah_num - 1596;
            case 13:
                return ayah_num - 1707;
            case 14:
                return ayah_num - 1750;
            case 15:
                return ayah_num - 1802;
            case 16:
                return ayah_num - 1901;
            case 17:
                return ayah_num - 2029;
            case 18:
                return ayah_num - 2140;
            case 19:
                return ayah_num - 2250;
            case 20:
                return ayah_num - 2348;
            case 21:
                return ayah_num - 2483;
            case 22:
                return ayah_num - 2595;
            case 23:
                return ayah_num - 2673;
            case 24:
                return ayah_num - 2791;
            case 25:
                return ayah_num - 2855;
            case 26:
                return ayah_num - 2932;
            case 27:
                return ayah_num - 3159;
            case 28:
                return ayah_num - 3252;
            case 29:
                return ayah_num - 3340;
            case 30:
                return ayah_num - 3409;
            case 31:
                return ayah_num - 3469;
            case 32:
                return ayah_num - 3503;
            case 33:
                return ayah_num - 3533;
            case 34:
                return ayah_num - 3606;
            case 35:
                return ayah_num - 3660;
            case 36:
                return ayah_num - 3705;
            case 37:
                return ayah_num - 3788;
            case 38:
                return ayah_num - 3970;
            case 39:
                return ayah_num - 4058;
            case 40:
                return ayah_num - 4133;
            case 41:
                return ayah_num - 4218;
            case 42:
                return ayah_num - 4272;
            case 43:
                return ayah_num - 4325;
            case 44:
                return ayah_num - 4414;
            case 45:
                return ayah_num - 4473;
            case 46:
                return ayah_num - 4510;
            case 47:
                return ayah_num - 4545;
            case 48:
                return ayah_num - 4583;
            case 49:
                return ayah_num - 4612;
            case 50:
                return ayah_num - 4630;
            case 51:
                return ayah_num - 4675;
            case 52:
                return ayah_num - 4735;
            case 53:
                return ayah_num - 4784;
            case 54:
                return ayah_num - 4846;
            case 55:
                return ayah_num - 4901;
            case 56:
                return ayah_num - 4979;
            case 57:
                return ayah_num - 5075;
            case 58:
                return ayah_num - 5104;
            case 59:
                return ayah_num - 5126;
            case 60:
                return ayah_num - 5150;
            case 61:
                return ayah_num - 5163;
            case 62:
                return ayah_num - 5177;
            case 63:
                return ayah_num - 5188;
            case 64:
                return ayah_num - 5199;
            case 65:
                return ayah_num - 5217;
            case 66:
                return ayah_num - 5229;
            case 67:
                return ayah_num - 5241;
            case 68:
                return ayah_num - 5271;
            case 69:
                return ayah_num - 5323;
            case 70:
                return ayah_num - 5375;
            case 71:
                return ayah_num - 5419;
            case 72:
                return ayah_num - 5447;
            case 73:
                return ayah_num - 5475;
            case 74:
                return ayah_num - 5495;
            case 75:
                return ayah_num - 5551;
            case 76:
                return ayah_num - 5591;
            case 77:
                return ayah_num - 5622;
            case 78:
                return ayah_num - 5672;
            case 79:
                return ayah_num - 5712;
            case 80:
                return ayah_num - 5758;
            case 81:
                return ayah_num - 5800;
            case 82:
                return ayah_num - 5829;
            case 83:
                return ayah_num - 5848;
            case 84:
                return ayah_num - 5884;
            case 85:
                return ayah_num - 5909;
            case 86:
                return ayah_num - 5931;
            case 87:
                return ayah_num - 5948;
            case 88:
                return ayah_num - 5967;
            case 89:
                return ayah_num - 5993;
            case 90:
                return ayah_num - 6023;
            case 91:
                return ayah_num - 6043;
            case 92:
                return ayah_num - 6058;
            case 93:
                return ayah_num - 6079;
            case 94:
                return ayah_num - 6090;
            case 95:
                return ayah_num - 6098;
            case 96:
                return ayah_num - 6106;
            case 97:
                return ayah_num - 6125;
            case 98:
                return ayah_num - 6130;
            case 99:
                return ayah_num - 6138;
            case 100:
                return ayah_num - 6146;
            case 101:
                return ayah_num - 6157;
            case 102:
                return ayah_num - 6168;
            case 103:
                return ayah_num - 6176;
            case 104:
                return ayah_num - 6179;
            case 105:
                return ayah_num - 6188;
            case 106:
                return ayah_num - 6193;
            case 107:
                return ayah_num - 6197;
            case 108:
                return ayah_num - 6204;
            case 109:
                return ayah_num - 6207;
            case 110:
                return ayah_num - 6213;
            case 111:
                return ayah_num - 6216;
            case 112:
                return ayah_num - 6221;
            case 113:
                return ayah_num - 6225;
            case 114:
                return ayah_num - 6230;
            default:
                return ayah_num;
        }
    }
}
