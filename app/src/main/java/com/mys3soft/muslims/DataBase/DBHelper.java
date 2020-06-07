package com.mys3soft.muslims.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.mys3soft.muslims.Models.Ayah;
import com.mys3soft.muslims.Models.Calender;
import com.mys3soft.muslims.Models.Language;
import com.mys3soft.muslims.Models.PrayerTimings;
import com.mys3soft.muslims.Models.RevSurah;
import com.mys3soft.muslims.Models.Surah;
import com.mys3soft.muslims.Models.World_Cities;

import java.sql.Date;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Muslims.db";

    private static final String WORLD_LOCATIONS_TABLE = "world_locations";
    private static final String WORLD_LOCATIONS_COLUMN_ID = "id";
    private static final String WORLD_LOCATIONS_CITY = "city";
    private static final String WORLD_LOCATIONS_COUNTRY = "country";
    private static final String WORLD_LOCATIONS_SUBCOUNTRY = "subcountry";

    private static final String SURAHLIST_TABLE = "surah_list";
    private static final String SURAHLIST_COLUMN_ID = "id";
    private static final String SURAHLIST_COLUMN_SURAH_NUMBER = "surah_number";
    private static final String SURAHLIST_COLUMN_SURAH_AR_NAME = "surah_ar_name";
    private static final String SURAHLIST_COLUMN_SURAH_EN_NAME = "surah_en_name";
    private static final String SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME = "surah_en_name_translation";
    private static final String SURAHLIST_COLUMN_REVELATION_TYPE = "revelation_type";
    private static final String SURAHLIST_COLUMN_TOTAL_AYAH = "total_ayah";

    private static final String REV_SURAHLIST_TABLE = "rev_surah_list";
    private static final String REV_SURAHLIST_COLUMN_ID = "id";
    private static final String REV_SURAHLIST_COLUMN_CHORONOLOGICAL_ORDER = "chronological_order";
    private static final String REV_SURAHLIST_COLUMN_TRADITIONAL_ORDER = "traditional_order";
    private static final String REV_SURAHLIST_COLUMN_SURAH_AR_NAME = "surah_ar_name";
    private static final String REV_SURAHLIST_COLUMN_SURAH_EN_NAME = "surah_en_name";
    private static final String REV_SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME = "surah_en_name_translation";
    private static final String REV_SURAHLIST_COLUMN_REVELATION_TYPE = "revelation_type";
    private static final String REV_SURAHLIST_COLUMN_TOTAL_AYAH = "total_ayah";
    private static final String REV_SURAHLIST_COLUMN_NOTE = "note";

    //region:quran tables
    public static final String QURAN_A_J_Arberry_TABLE = "A_J_Arberry";
    public static final String QURAN_Mohammed_Marmaduke_William_Pickthall_TABLE = "Mohammed_Marmaduke_William_Pickthall";
    public static final String QURAN_Taj_Mehmood_Amroti_TABLE = "Taj_Mehmood_Amroti";
    public static final String QURAN_Burhan_Muhammad_Amin_TABLE = "Burhan_Muhammad_Amin";
    public static final String QURAN_Saheeh_International_TABLE = "Saheeh_International";
    public static final String QURAN_Muhammad_Junagarhi_TABLE = "Muhammad_Junagarhi";
    public static final String QURAN_Knut_Bernstrom_TABLE = "Knut_Bernstrom";
    public static final String QURAN_Vasim_Mammadaliyev_and_Ziya_Bunyadov_TABLE = "Vasim_Mammadaliyev_and_Ziya_Bunyadov";
    public static final String QURAN_Abdulbaki_Golpinarli_TABLE = "Abdulbaki_Golpinarli";
    public static final String QURAN_Mohammad_Mahdi_Fooladvand_TABLE = "Mohammad_Mahdi_Fooladvand";
    public static final String QURAN_Edip_Yuksel_TABLE = "Edip_Yuksel";
    public static final String QURAN_A_R_Nykl_TABLE = "A_R_Nykl";
    public static final String QURAN_Mahmud_Muhammad_Abduh_TABLE = "Mahmud_Muhammad_Abduh";
    public static final String QURAN_Salomo_Keyzer_TABLE = "Salomo_Keyzer";
    public static final String QURAN_Tzvetan_Theophanov_TABLE = "Tzvetan_Theophanov";
    public static final String QURAN_Suleyman_Ates_TABLE = "Suleyman_Ates";
    public static final String QURAN_Preklad_I_Hrbek_TABLE = "Preklad_I_Hrbek";
    public static final String QURAN_Sayyed_Jalaloddin_Mojtabavi_TABLE = "Sayyed_Jalaloddin_Mojtabavi";
    public static final String QURAN_Muhammad_Asad_Abdurrasak_Perez_TABLE = "Muhammad_Asad_Abdurrasak_Perez";
    public static final String QURAN_Sherif_Ahmeti_TABLE = "Sherif_Ahmeti";
    public static final String QURAN_Hasan_al_Fatih_Qaribullah_and_Ahmad_Darwish_TABLE = "Hasan_al_Fatih_Qaribullah_and_Ahmad_Darwish";
    public static final String QURAN_Julio_Cortes_TABLE = "Julio_Cortes";
    public static final String QURAN_Abubakar_Mahmoud_Gumi_TABLE = "Abubakar_Mahmoud_Gumi";
    public static final String QURAN_Adel_Theodor_Khoury_TABLE = "Adel_Theodor_Khoury";
    public static final String QURAN_Hamza_Roberto_Piccardo_TABLE = "Hamza_Roberto_Piccardo";
    public static final String QURAN_Muhammad_Hamidullah_TABLE = "Muhammad_Hamidullah";
    public static final String QURAN_Diyanet_Vakfi_TABLE = "Diyanet_Vakfi";
    public static final String QURAN_Diyanet_Isleri_TABLE = "Diyanet_Isleri";
    public static final String QURAN_Besim_Korkut_TABLE = "Besim_Korkut";
    public static final String QURAN_Muhammad_Saleh_TABLE = "Muhammad_Saleh";
    public static final String QURAN_Jalal_ad_Din_al_Mahalli_and_Jalal_ad_Din_as_Suyuti_TABLE = "Jalal_ad_Din_al_Mahalli_and_Jalal_ad_Din_as_Suyuti";
    public static final String QURAN_Yakub_Ibn_Nugman_TABLE = "Yakub_Ibn_Nugman";
    public static final String QURAN_Suat_Yildirim_TABLE = "Suat_Yildirim";
    public static final String QURAN_Simple_Enhanced_TABLE = "Simple_Enhanced";
    public static final String QURAN_Elmalili_Hamdi_Yazir_TABLE = "Elmalili_Hamdi_Yazir";
    public static final String QURAN_Ali_Bulac_TABLE = "Ali_Bulac";
    public static final String QURAN_Magomed_Nuri_Osmanovich_Osmanov_TABLE = "Magomed_Nuri_Osmanovich_Osmanov";
    public static final String QURAN_Mustafa_Mlivo_TABLE = "Mustafa_Mlivo";
    public static final String QURAN_Ahmed_Ali_TABLE = "Ahmed_Ali";
    public static final String QURAN_Tahir_ul_Qadri_TABLE = "Tahir_ul_Qadri";
    public static final String QURAN_Hasan_Efendi_Nahi_TABLE = "Hasan_Efendi_Nahi";
    public static final String QURAN_Fateh_Muhammad_Jalandhry_TABLE = "Fateh_Muhammad_Jalandhry";
    public static final String QURAN_Jan_Turst_Foundation_TABLE = "Jan_Turst_Foundation";
    private static final String QURAN_EN_Abdullah_Yusuf_Ali_TABLE = "En_Abdullah_Yusuf_Ali";
    public static final String QURAN_Abdullah_Muhammad_Basmeih_TABLE = "Abdullah_Muhammad_Basmeih";
    public static final String QURAN_Uthmani_Minimal_TABLE = "Uthmani_Minimal";
    public static final String QURAN_A_S_F_Bubenheim_and_N_Elyas_TABLE = "A_S_F_Bubenheim_and_N_Elyas";
    public static final String QURAN_V_Porokhova_TABLE = "V_Porokhova";
    public static final String QURAN_Muhammad_Quraish_Shihab_et_al_TABLE = "Muhammad_Quraish_Shihab_et_al";
    public static final String QURAN_Muhammad_Sodik_Muhammad_Yusuf_TABLE = "Muhammad_Sodik_Muhammad_Yusuf";
    public static final String QURAN_Ma_Jian_TABLE = "Ma_Jian";
    public static final String QURAN_Samir_El_Hayek_TABLE = "Samir_El_Hayek";
    public static final String QURAN_Abu_Rida_Muhammad_ibn_Ahmad_ibn_Rassoul_TABLE = "Abu_Rida_Muhammad_ibn_Ahmad_ibn_Rassoul";
    public static final String QURAN_Muhammad_Sarwar_TABLE = "Muhammad_Sarwar";
    public static final String QURAN_Abul_A_ala_Maududi_TABLE = "Abul_A_ala_Maududi";
    private static final String QURAN_UR_Ahmed_Raza_Khan_TABLE = "Ur_Ahmed_Raza_Khan";
    public static final String QURAN_Amir_Zaidan_TABLE = "Amir_Zaidan";
    public static final String QURAN_Ministry_of_Awqaf_Egypt_TABLE = "Ministry_of_Awqaf_Egypt";
    public static final String QURAN_Simple_Clean_TABLE = "Simple_Clean";
    public static final String QURAN_Simple_Minimal_TABLE = "Simple_Minimal";
    public static final String QURAN_Einar_Berg_TABLE = "Einar_Berg";
    private static final String QURAN_Tajweed_TABLE = "Tajweed";
    public static final String QURAN_Simple_TABLE = "Simple";
    public static final String QURAN_Mohammad_Habib_Shakir_TABLE = "Mohammad_Habib_Shakir";
    public static final String QURAN_Cheriyamundam_Abdul_Hameed_and_Kunhi_Mohammed_Parappoor_TABLE = "Cheriyamundam_Abdul_Hameed_and_Kunhi_Mohammed_Parappoor";
    public static final String QURAN_Muhammad_Taqi_ud_Din_al_Hilali_and_Muhammad_Muhsin_Khan_TABLE = "Muhammad_Taqi_ud_Din_al_Hilali_and_Muhammad_Muhsin_Khan";
    public static final String QURAN_Hussain_Ansarian_TABLE = "Hussain_Ansarian";
    public static final String QURAN_Elmir_Kuliev_TABLE = "Elmir_Kuliev";
    public static final String QURAN_Ignaty_Yulianovich_Krachkovsky_TABLE = "Ignaty_Yulianovich_Krachkovsky";
    public static final String QURAN_King_Fahad_Quran_Complex_TABLE = "King_Fahad_Quran_Complex";
    public static final String QURAN_Ali_Muhsin_Al_Barwani_TABLE = "Ali_Muhsin_Al_Barwani";
    private static final String QURAN_AR_Uthamani_TABLE = "Ar_Uthamani";
    public static final String QURAN_Abul_Ala_Maududi_TABLE = "Abul_Ala_Maududi";
    public static final String QURAN_Syed_Zeeshan_Haider_Jawadi_TABLE = "Syed_Zeeshan_Haider_Jawadi";
    public static final String QURAN_Naser_Makarem_Shirazi_TABLE = "Naser_Makarem_Shirazi";
    public static final String QURAN_Jozefa_Bielawskiego_TABLE = "Jozefa_Bielawskiego";
    public static final String QURAN_Mahdi_Elahi_Ghomshei_TABLE = "Mahdi_Elahi_Ghomshei";
    private static final String QURAN_English_Transliteration_TABLE = "English_Transliteration";
    public static final String QURAN_Gordy_Semyonovich_Sablukov_TABLE = "Gordy_Semyonovich_Sablukov";
    private static final String QURAN_BN_Muhiuddin_Khan_TABLE = "Bn_Muhiuddin_Khan";
    public static final String QURAN_Muhammad_Farooq_Khan_and_Muhammad_Ahmed_TABLE = "Muhammad_Farooq_Khan_and_Muhammad_Ahmed";
    public static final String QURAN_AbdolMohammad_Ayati_TABLE = "AbdolMohammad_Ayati";
    public static final String QURAN_Alikhan_Musayev_TABLE = "Alikhan_Musayev";
    public static final String QURAN_Mohammad_Kazem_Moezzi_TABLE = "Mohammad_Kazem_Moezzi";
    public static final String QURAN_Abdul_Majid_Daryabadi_TABLE = "Abdul_Majid_Daryabadi";
    public static final String QURAN_Mostafa_Khorramdel_TABLE = "Mostafa_Khorramdel";
    public static final String QURAN_Office_of_the_President_of_Maldives_TABLE = "Office_of_the_President_of_Maldives";
    public static final String QURAN_Muhammet_Abay_TABLE = "Muhammet_Abay";
    public static final String QURAN_Baha_oddin_Khorramshahi_TABLE = "Baha_oddin_Khorramshahi";
    public static final String QURAN_Suhel_Farooq_Khan_and_Saifur_Rahman_Nadwi_TABLE = "Suhel_Farooq_Khan_and_Saifur_Rahman_Nadwi";
    public static final String QURAN_Yasar_Nuri_Ozturk_TABLE = "Yasar_Nuri_Ozturk";
    public static final String QURAN_Feti_Mehdiu_TABLE = "Feti_Mehdiu";
    public static final String QURAN_Abu_Adel_TABLE = "Abu_Adel";
    public static final String QURAN_Muhammad_Asad_TABLE = "Muhammad_Asad";
    public static final String QURAN_Unknown_TABLE = "Unknown";
    public static final String QURAN_Abolfazl_Bahrampour_TABLE = "Abolfazl_Bahrampour";
    public static final String QURAN_George_Grigore_TABLE = "George_Grigore";
    public static final String QURAN_none_TABLE = "none";
    //endregion: quran tables

    //region:quran table entities
    private static final String QURAN_ID = "id";
    private static final String QURAN_IDENTIFIER = "identifier";
    private static final String QURAN_SURAH_NUMBER = "surah_number";
    private static final String QURAN_SURAH_AR_NAME = "surah_ar_name";
    private static final String QURAN_SURAH_EN_NAME = "surah_en_name";
    private static final String QURAN_SURAH_EN_NAME_TRANSLATION = "surah_en_name_translation";
    private static final String QURAN_REVELATION_TYPE = "revelation_type";
    private static final String QURAN_AYAH_NUMBER = "ayah_number";
    private static final String QURAN_TEXT = "text";
    private static final String QURAN_JUZ = "juz";
    private static final String QURAN_MANZIL = "manzil";
    private static final String QURAN_PAGE = "page";
    private static final String QURAN_RUKU = "ruku";
    private static final String QURAN_HIZB_QUARTER = "hizb_quarter";
    private static final String QURAN_SAJDA = "sajda";
    private static final String QURAN_SAJDA_ID = "sajda_id";
    private static final String QURAN_SAJDA_RECOMMENDED = "sajda_recommended";
    private static final String QURAN_SAJDA_OBLIGATORY = "sajda_obligatory";
    private static final String QURAN_LANGUAGE = "language";
    private static final String QURAN_NAME = "name";
    private static final String QURAN_TRANSELATOR_EN_NAME = "transelator_en_name";
    private static final String QURAN_FORMAT = "format";
    private static final String QURAN_TYPE = "type";
    //endregion:quran table entities

    //region:prayer timings
    private static final String PRAYERTIMINGS_TABLE = "prayer_timings";
    private static final String PRAYERTIMINGS_COLUMN_ID = "id";
    private static final String PRAYERTIMINGS_COLUMN_IMSAK = "imsak";
    private static final String PRAYERTIMINGS_COLUMN_FAJR = "fajr";
    private static final String PRAYERTIMINGS_COLUMN_SUNRISE = "sunrise";
    private static final String PRAYERTIMINGS_COLUMN_DHUHR = "dhuhr";
    private static final String PRAYERTIMINGS_COLUMN_ASR = "asr";
    private static final String PRAYERTIMINGS_COLUMN_SUNSET = "sunset";
    private static final String PRAYERTIMINGS_COLUMN_MAGRIB = "magrib";
    private static final String PRAYERTIMINGS_COLUMN_ISHA = "isha";
    private static final String PRAYERTIMINGS_COLUMN_MIDNIGHT = "midnight";
    private static final String PRAYERTIMINGS_COLUMN_DATE = "date";
    private static final String PRAYERTIMINGS_COLUMN_DAY = "day";
    private static final String PRAYERTIMINGS_COLUMN_MONTH = "month";
    private static final String PRAYERTIMINGS_COLUMN_YEAR = "year";
    private static final String PRAYERTIMINGS_COLUMN_LOCATION = "location";
    //endregion:prayer timings

    //region:language
    private static final String LANGUAGE_TABLE = "language";
    private static final String LANGUAGE_COLUMN_ID = "id";
    private static final String LANGUAGE_COLUMN_TRANSLATOR = "translator";
    private static final String LANGUAGE_COLUMN_SELECTED = "selected";
    //endregion:language

    //regipn:Calender
    private static final String CALENDER_TABLE = "calender";
    private static final String CALENDER_COLUMN_ID = "id";
    private static final String CALENDER_COLUMN__HIJRI_DATE = "hijri_date";
    private static final String CALENDER_COLUMN__HIJRI_DAY_NUMBER = "hijri_day_number";
    private static final String CALENDER_COLUMN__HIJRI__MONTH_NUMBER = "hijri_month_number";
    private static final String CALENDER_COLUMN__HIJRI_MONTH_EN = "hijri_month_en";
    private static final String CALENDER_COLUMN__HIJRI_MONTH_AR = "hijri_month_ar";
    private static final String CALENDER_COLUMN__HIJRI_YEAR_NUMBER = "hijri_year_number";
    private static final String CALENDER_COLUMN__HIJRI_WEAKDAY_EN = "hijri_weakday_en";
    private static final String CALENDER_COLUMN__HIJRI_WEAKDAY_AR = "hijri_weakday_ar";
    private static final String CALENDER_COLUMN__HIJRI_DESIGNATION_ABBREVIATED = "hijri_designation_abbreviated";
    private static final String CALENDER_COLUMN__HIJRI_DESIGNATION_EXPANDED = "hijri_designation_expanded";
    private static final String CALENDER_COLUMN__HIJRI_HOLIDAY = "hijri_holiday";
    private static final String CALENDER_COLUMN__GREGORIAN_DATE = "gregorian_date";
    private static final String CALENDER_COLUMN__GREGORIAN_DAY_NUMBER = "gregorian_day_number";
    private static final String CALENDER_COLUMN__GREGORIAN__MONTH_NUMBER = "gregorian_month_number";
    private static final String CALENDER_COLUMN__GREGORIAN_MONTH_EN = "gregorian_month_en";
    private static final String CALENDER_COLUMN__GREGORIAN_YEAR_NUMBER = "gregorian_year_number";
    private static final String CALENDER_COLUMN__GREGORIAN_WEAKDAY_EN = "gregorian_weakday_en";
    private static final String CALENDER_COLUMN__GREGORIAN_DESIGNATION_ABBREVIATED = "gregorian_designation_abbreviated";
    private static final String CALENDER_COLUMN__GREGORIAN_DESIGNATION_EXPANDED = "gregorian_designation_expanded";
    //endregion:Calender


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 19);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + WORLD_LOCATIONS_TABLE + "(" + WORLD_LOCATIONS_COLUMN_ID + " integer primary key, " +
                WORLD_LOCATIONS_CITY + " text, " + WORLD_LOCATIONS_COUNTRY + " text, " + WORLD_LOCATIONS_SUBCOUNTRY + " text)");

        db.execSQL("create table " + SURAHLIST_TABLE + "(" + SURAHLIST_COLUMN_ID + " integer primary key, " +
                SURAHLIST_COLUMN_SURAH_NUMBER + " integer, " + SURAHLIST_COLUMN_SURAH_AR_NAME + " text, " +
                SURAHLIST_COLUMN_SURAH_EN_NAME + " text, " + SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME + " text, " +
                SURAHLIST_COLUMN_REVELATION_TYPE + " text," + SURAHLIST_COLUMN_TOTAL_AYAH + " integer)");

        db.execSQL("create table " + REV_SURAHLIST_TABLE + "(" + REV_SURAHLIST_COLUMN_ID + " integer primary key, " +
                REV_SURAHLIST_COLUMN_CHORONOLOGICAL_ORDER + " integer,"+ REV_SURAHLIST_COLUMN_TRADITIONAL_ORDER + " integer, " +
                REV_SURAHLIST_COLUMN_SURAH_AR_NAME + " text, " + REV_SURAHLIST_COLUMN_SURAH_EN_NAME + " text, " +
                REV_SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME + " text, " + REV_SURAHLIST_COLUMN_REVELATION_TYPE + " text," +
                REV_SURAHLIST_COLUMN_NOTE + " text," + REV_SURAHLIST_COLUMN_TOTAL_AYAH + " integer)");

        db.execSQL("create table " + PRAYERTIMINGS_TABLE + "(" + PRAYERTIMINGS_COLUMN_ID + " integer primary key, " +
                PRAYERTIMINGS_COLUMN_IMSAK + " text, " + PRAYERTIMINGS_COLUMN_FAJR + " text, " +
                PRAYERTIMINGS_COLUMN_SUNRISE + " text, " + PRAYERTIMINGS_COLUMN_DHUHR + " text, " +
                PRAYERTIMINGS_COLUMN_ASR + " text, " + PRAYERTIMINGS_COLUMN_SUNSET + " text, " +
                PRAYERTIMINGS_COLUMN_MAGRIB + " text, " + PRAYERTIMINGS_COLUMN_ISHA + " text, " +
                PRAYERTIMINGS_COLUMN_MIDNIGHT + " text, " + PRAYERTIMINGS_COLUMN_DATE + " text, " +
                PRAYERTIMINGS_COLUMN_DAY + " integer, " + PRAYERTIMINGS_COLUMN_MONTH + " integer, " +
                PRAYERTIMINGS_COLUMN_YEAR + " integer, " + PRAYERTIMINGS_COLUMN_LOCATION + " text)");

        db.execSQL("create table " + QURAN_AR_Uthamani_TABLE + "(" + QURAN_ID + " integer primary key, " +
                QURAN_IDENTIFIER + " text, " + QURAN_SURAH_NUMBER + " integer, " +
                QURAN_SURAH_AR_NAME + " text, " + QURAN_SURAH_EN_NAME + " text, " +
                QURAN_SURAH_EN_NAME_TRANSLATION + " text, " + QURAN_REVELATION_TYPE + " text, " +
                QURAN_AYAH_NUMBER + " integer, " + QURAN_TEXT + " text, " +
                QURAN_JUZ + " integer, " + QURAN_MANZIL + " integer, " +
                QURAN_PAGE + " integer, " + QURAN_RUKU + " integer ," +
                QURAN_HIZB_QUARTER + " integer, " + QURAN_SAJDA + " boolean," +
                QURAN_SAJDA_ID + " integer, " + QURAN_SAJDA_RECOMMENDED + " boolean," +
                QURAN_SAJDA_OBLIGATORY + " boolean, " + QURAN_LANGUAGE + " text," +
                QURAN_NAME + " text, " + QURAN_TRANSELATOR_EN_NAME + " text," +
                QURAN_FORMAT + " text, " + QURAN_TYPE + " text)");

        db.execSQL("create table " + QURAN_Tajweed_TABLE + "(" + QURAN_ID + " integer primary key, " +
                QURAN_IDENTIFIER + " text, " + QURAN_SURAH_NUMBER + " integer, " +
                QURAN_SURAH_AR_NAME + " text, " + QURAN_SURAH_EN_NAME + " text, " +
                QURAN_SURAH_EN_NAME_TRANSLATION + " text, " + QURAN_REVELATION_TYPE + " text, " +
                QURAN_AYAH_NUMBER + " integer, " + QURAN_TEXT + " text, " +
                QURAN_JUZ + " integer, " + QURAN_MANZIL + " integer, " +
                QURAN_PAGE + " integer, " + QURAN_RUKU + " integer ," +
                QURAN_HIZB_QUARTER + " integer, " + QURAN_SAJDA + " boolean," +
                QURAN_SAJDA_ID + " integer, " + QURAN_SAJDA_RECOMMENDED + " boolean," +
                QURAN_SAJDA_OBLIGATORY + " boolean, " + QURAN_LANGUAGE + " text," +
                QURAN_NAME + " text, " + QURAN_TRANSELATOR_EN_NAME + " text," +
                QURAN_FORMAT + " text, " + QURAN_TYPE + " text)");

        db.execSQL("create table " + QURAN_English_Transliteration_TABLE + "(" + QURAN_ID + " integer primary key, " +
                QURAN_IDENTIFIER + " text, " + QURAN_SURAH_NUMBER + " integer, " +
                QURAN_SURAH_AR_NAME + " text, " + QURAN_SURAH_EN_NAME + " text, " +
                QURAN_SURAH_EN_NAME_TRANSLATION + " text, " + QURAN_REVELATION_TYPE + " text, " +
                QURAN_AYAH_NUMBER + " integer, " + QURAN_TEXT + " text, " +
                QURAN_JUZ + " integer, " + QURAN_MANZIL + " integer, " +
                QURAN_PAGE + " integer, " + QURAN_RUKU + " integer ," +
                QURAN_HIZB_QUARTER + " integer, " + QURAN_SAJDA + " boolean," +
                QURAN_SAJDA_ID + " integer, " + QURAN_SAJDA_RECOMMENDED + " boolean," +
                QURAN_SAJDA_OBLIGATORY + " boolean, " + QURAN_LANGUAGE + " text," +
                QURAN_NAME + " text, " + QURAN_TRANSELATOR_EN_NAME + " text," +
                QURAN_FORMAT + " text, " + QURAN_TYPE + " text)");

        db.execSQL("create table " + QURAN_BN_Muhiuddin_Khan_TABLE + "(" + QURAN_ID + " integer primary key, " +
                QURAN_IDENTIFIER + " text, " + QURAN_SURAH_NUMBER + " integer, " +
                QURAN_SURAH_AR_NAME + " text, " + QURAN_SURAH_EN_NAME + " text, " +
                QURAN_SURAH_EN_NAME_TRANSLATION + " text, " + QURAN_REVELATION_TYPE + " text, " +
                QURAN_AYAH_NUMBER + " integer, " + QURAN_TEXT + " text, " +
                QURAN_JUZ + " integer, " + QURAN_MANZIL + " integer, " +
                QURAN_PAGE + " integer, " + QURAN_RUKU + " integer ," +
                QURAN_HIZB_QUARTER + " integer, " + QURAN_SAJDA + " boolean," +
                QURAN_SAJDA_ID + " integer, " + QURAN_SAJDA_RECOMMENDED + " boolean," +
                QURAN_SAJDA_OBLIGATORY + " boolean, " + QURAN_LANGUAGE + " text," +
                QURAN_NAME + " text, " + QURAN_TRANSELATOR_EN_NAME + " text," +
                QURAN_FORMAT + " text, " + QURAN_TYPE + " text)");

        db.execSQL("create table " + QURAN_EN_Abdullah_Yusuf_Ali_TABLE + "(" + QURAN_ID + " integer primary key, " +
                QURAN_IDENTIFIER + " text, " + QURAN_SURAH_NUMBER + " integer, " +
                QURAN_SURAH_AR_NAME + " text, " + QURAN_SURAH_EN_NAME + " text, " +
                QURAN_SURAH_EN_NAME_TRANSLATION + " text, " + QURAN_REVELATION_TYPE + " text, " +
                QURAN_AYAH_NUMBER + " integer, " + QURAN_TEXT + " text, " +
                QURAN_JUZ + " integer, " + QURAN_MANZIL + " integer, " +
                QURAN_PAGE + " integer, " + QURAN_RUKU + " integer ," +
                QURAN_HIZB_QUARTER + " integer, " + QURAN_SAJDA + " boolean," +
                QURAN_SAJDA_ID + " integer, " + QURAN_SAJDA_RECOMMENDED + " boolean," +
                QURAN_SAJDA_OBLIGATORY + " boolean, " + QURAN_LANGUAGE + " text," +
                QURAN_NAME + " text, " + QURAN_TRANSELATOR_EN_NAME + " text," +
                QURAN_FORMAT + " text, " + QURAN_TYPE + " text)");

        db.execSQL("create table " + QURAN_UR_Ahmed_Raza_Khan_TABLE + "(" + QURAN_ID + " integer primary key, " +
                QURAN_IDENTIFIER + " text, " + QURAN_SURAH_NUMBER + " integer, " +
                QURAN_SURAH_AR_NAME + " text, " + QURAN_SURAH_EN_NAME + " text, " +
                QURAN_SURAH_EN_NAME_TRANSLATION + " text, " + QURAN_REVELATION_TYPE + " text, " +
                QURAN_AYAH_NUMBER + " integer, " + QURAN_TEXT + " text, " +
                QURAN_JUZ + " integer, " + QURAN_MANZIL + " integer, " +
                QURAN_PAGE + " integer, " + QURAN_RUKU + " integer ," +
                QURAN_HIZB_QUARTER + " integer, " + QURAN_SAJDA + " boolean," +
                QURAN_SAJDA_ID + " integer, " + QURAN_SAJDA_RECOMMENDED + " boolean," +
                QURAN_SAJDA_OBLIGATORY + " boolean, " + QURAN_LANGUAGE + " text," +
                QURAN_NAME + " text, " + QURAN_TRANSELATOR_EN_NAME + " text," +
                QURAN_FORMAT + " text, " + QURAN_TYPE + " text)");

        db.execSQL("create table " + LANGUAGE_TABLE + "(" + LANGUAGE_COLUMN_ID + " integer primary key, " +
                LANGUAGE_COLUMN_SELECTED + " boolean, " + LANGUAGE_COLUMN_TRANSLATOR + " text)");

        db.execSQL("create table " + CALENDER_TABLE + "(" + CALENDER_COLUMN_ID + " integer primary key, " +
                CALENDER_COLUMN__HIJRI_DATE + " text, " + CALENDER_COLUMN__HIJRI_DAY_NUMBER + " integer, " +
                CALENDER_COLUMN__HIJRI__MONTH_NUMBER + " interger, " + CALENDER_COLUMN__HIJRI_MONTH_EN + " text, " +
                CALENDER_COLUMN__HIJRI_MONTH_AR + " text, " + CALENDER_COLUMN__HIJRI_YEAR_NUMBER + " integer, " +
                CALENDER_COLUMN__HIJRI_WEAKDAY_EN + " text, " + CALENDER_COLUMN__HIJRI_WEAKDAY_AR + " text, " +
                CALENDER_COLUMN__HIJRI_DESIGNATION_ABBREVIATED + " text, " +
                CALENDER_COLUMN__HIJRI_DESIGNATION_EXPANDED + " text, " +
                CALENDER_COLUMN__HIJRI_HOLIDAY + " text, " + CALENDER_COLUMN__GREGORIAN_DATE + " text ," +
                CALENDER_COLUMN__GREGORIAN_DAY_NUMBER + " integer, " +
                CALENDER_COLUMN__GREGORIAN__MONTH_NUMBER + " integer," +
                CALENDER_COLUMN__GREGORIAN_MONTH_EN + " text, " + CALENDER_COLUMN__GREGORIAN_YEAR_NUMBER + " integer," +
                CALENDER_COLUMN__GREGORIAN_WEAKDAY_EN + " text, " + CALENDER_COLUMN__GREGORIAN_DESIGNATION_ABBREVIATED + " text," +
                CALENDER_COLUMN__GREGORIAN_DESIGNATION_EXPANDED + " text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + WORLD_LOCATIONS_TABLE);
        db.execSQL("drop table if exists " + SURAHLIST_TABLE);
        db.execSQL("drop table if exists " + REV_SURAHLIST_TABLE);
        db.execSQL("drop table if exists " + PRAYERTIMINGS_TABLE);
        db.execSQL("drop table if exists " + QURAN_AR_Uthamani_TABLE);
        db.execSQL("drop table if exists " + QURAN_Tajweed_TABLE);
        db.execSQL("drop table if exists " + QURAN_English_Transliteration_TABLE);
        db.execSQL("drop table if exists " + QURAN_BN_Muhiuddin_Khan_TABLE);
        db.execSQL("drop table if exists " + QURAN_EN_Abdullah_Yusuf_Ali_TABLE);
        db.execSQL("drop table if exists " + QURAN_UR_Ahmed_Raza_Khan_TABLE);
        db.execSQL("drop table if exists " + LANGUAGE_TABLE);
        db.execSQL("drop table if exists " + CALENDER_TABLE);
        onCreate(db);
    }

    //region:World cities
    public void insertWorldLocation(String city, String country, String subcountry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("city", city);
        contentValues.put("country", country);
        contentValues.put("subcountry", subcountry);

        db.insert(WORLD_LOCATIONS_TABLE, null, contentValues);

        db.close();
    }

    public List<World_Cities> getSearchWorldLocationData(String searchKey) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<World_Cities> world_cities = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + WORLD_LOCATIONS_TABLE + " where " + WORLD_LOCATIONS_CITY +
                " like '%" + searchKey + "%' or " + WORLD_LOCATIONS_COUNTRY +
                " like '%" + searchKey + "%' or " + WORLD_LOCATIONS_SUBCOUNTRY + " like '%" + searchKey + "%'", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            World_Cities wc = new World_Cities(cursor.getInt(cursor.getColumnIndex(WORLD_LOCATIONS_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(WORLD_LOCATIONS_CITY)),
                    cursor.getString(cursor.getColumnIndex(WORLD_LOCATIONS_COUNTRY)),
                    cursor.getString(cursor.getColumnIndex(WORLD_LOCATIONS_COUNTRY)));
            world_cities.add(wc);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return world_cities;
    }
    //endregion:World cities

    //region:Surah_List
    public void insertSurah(int Surah_Number, String Surah_Ar_Name, String Surah_En_Name, String Surah_En_Name_Translation, String Revelation_Type, int Total_Ayah) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + SURAHLIST_TABLE + " where " +
                SURAHLIST_COLUMN_SURAH_NUMBER+ " = " + Surah_Number, null);

        if (cursor.getCount() == 0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("surah_number", Surah_Number);
            contentValues.put("surah_ar_name", Surah_Ar_Name);
            contentValues.put("surah_en_name", Surah_En_Name);
            contentValues.put("surah_en_name_translation", Surah_En_Name_Translation);
            contentValues.put("revelation_type", Revelation_Type);
            contentValues.put("total_ayah", Total_Ayah);

            db.insert(SURAHLIST_TABLE, null, contentValues);
        }

        cursor.close();
        db.close();
    }

    public List<Surah> getAllSurah() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Surah> surahList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + SURAHLIST_TABLE, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Surah wc = new Surah(cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_AR_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_REVELATION_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_TOTAL_AYAH)));
            surahList.add(wc);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return surahList;
    }

    public List<Surah> getAllSurahDescSurahName() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Surah> surahList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + SURAHLIST_TABLE +" order by "+ REV_SURAHLIST_COLUMN_SURAH_EN_NAME+ " desc", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Surah wc = new Surah(cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_AR_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_REVELATION_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_TOTAL_AYAH)));
            surahList.add(wc);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return surahList;
    }

    public List<Surah> getAllSurahAscSurahName() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Surah> surahList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + SURAHLIST_TABLE +" order by "+ REV_SURAHLIST_COLUMN_SURAH_EN_NAME+ " asc", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Surah wc = new Surah(cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_AR_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_REVELATION_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_TOTAL_AYAH)));
            surahList.add(wc);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return surahList;
    }

    public List<Surah> getAllMeccanSurah() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Surah> surahList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + SURAHLIST_TABLE +" where "+ REV_SURAHLIST_COLUMN_REVELATION_TYPE+ "= '"+"Meccan"+"'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Surah wc = new Surah(cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_AR_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_REVELATION_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_TOTAL_AYAH)));
            surahList.add(wc);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return surahList;
    }

    public List<Surah> getAllMedinanSurah() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Surah> surahList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + SURAHLIST_TABLE +" where "+ REV_SURAHLIST_COLUMN_REVELATION_TYPE+ "= '"+"Medinan"+"'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Surah wc = new Surah(cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_AR_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_REVELATION_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_TOTAL_AYAH)));
            surahList.add(wc);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return surahList;
    }

    public List<Surah> getAllSurahOrderByTotalAyahDesc() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Surah> surahList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + SURAHLIST_TABLE +" order by "+ REV_SURAHLIST_COLUMN_TOTAL_AYAH+ " desc", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Surah wc = new Surah(cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_AR_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_REVELATION_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_TOTAL_AYAH)));
            surahList.add(wc);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return surahList;
    }

    public List<Surah> getAllSurahOrderByTotalAyahAsc() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Surah> surahList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + SURAHLIST_TABLE +" order by "+ REV_SURAHLIST_COLUMN_TOTAL_AYAH+ " asc", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Surah wc = new Surah(cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_AR_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_REVELATION_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_TOTAL_AYAH)));
            surahList.add(wc);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return surahList;
    }

    public List<Surah> getSearchSurah(String searchKey) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Surah> surahList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + SURAHLIST_TABLE + " where " + SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME +
                " like '%" + searchKey +"%' or "+ SURAHLIST_COLUMN_SURAH_EN_NAME +
                " like '%" + searchKey +"%' or " + SURAHLIST_COLUMN_SURAH_AR_NAME +
                " like '%" + searchKey +"%' or " + SURAHLIST_COLUMN_REVELATION_TYPE +
                " like '%" + searchKey +"%' or " + SURAHLIST_COLUMN_SURAH_NUMBER +
                " like '%" + searchKey +"%' or " + SURAHLIST_COLUMN_TOTAL_AYAH +
                " like '%" + searchKey +"%'", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Surah surah = new Surah(cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_AR_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME)),
                    cursor.getString(cursor.getColumnIndex(SURAHLIST_COLUMN_REVELATION_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(SURAHLIST_COLUMN_TOTAL_AYAH)));
            surahList.add(surah);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return surahList;
    }
    //endregion:Surah_List

    //region:Rev_Surah_List
    public void insertRevSurah(int Chronological_Order, int Traditional_Order, String Surah_Ar_Name,
                               String Surah_En_Name, String Surah_En_Name_Translation, String Revelation_Type,
                               int Total_Ayah, String Note) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + REV_SURAHLIST_TABLE + " where " +
                REV_SURAHLIST_COLUMN_TRADITIONAL_ORDER+ " = " + Traditional_Order, null);

        if (cursor.getCount() == 0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("chronological_order", Chronological_Order);
            contentValues.put("traditional_order", Traditional_Order);
            contentValues.put("surah_ar_name", Surah_Ar_Name);
            contentValues.put("surah_en_name", Surah_En_Name);
            contentValues.put("surah_en_name_translation", Surah_En_Name_Translation);
            contentValues.put("revelation_type", Revelation_Type);
            contentValues.put("total_ayah", Total_Ayah);
            contentValues.put("note", Note);

            db.insert(REV_SURAHLIST_TABLE, null, contentValues);
        }

        cursor.close();
        db.close();
    }

    public List<RevSurah> getAllRevSurah() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<RevSurah> revSurahList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + REV_SURAHLIST_TABLE + " order by "+ REV_SURAHLIST_COLUMN_CHORONOLOGICAL_ORDER, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            RevSurah rs = new RevSurah(cursor.getInt(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_CHORONOLOGICAL_ORDER)),
                    cursor.getInt(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_TRADITIONAL_ORDER)),
                    cursor.getString(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_SURAH_AR_NAME)),
                    cursor.getString(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_SURAH_EN_NAME)),
                    cursor.getString(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME)),
                    cursor.getString(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_REVELATION_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_TOTAL_AYAH)),
                    cursor.getString(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_NOTE)));
            revSurahList.add(rs);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return revSurahList;
    }

    public List<RevSurah> getSearchRevSurah(String searchKey) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<RevSurah> revSurahList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + REV_SURAHLIST_TABLE + " where " + REV_SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME +
                " like '%" + searchKey +"%' or "+ REV_SURAHLIST_COLUMN_SURAH_EN_NAME +
                " like '%" + searchKey +"%' or " + REV_SURAHLIST_COLUMN_SURAH_AR_NAME +
                " like '%" + searchKey +"%' or " + REV_SURAHLIST_COLUMN_REVELATION_TYPE +
                " like '%" + searchKey +"%' or " + REV_SURAHLIST_COLUMN_CHORONOLOGICAL_ORDER +
                " like '%" + searchKey +"%' or " + REV_SURAHLIST_COLUMN_TRADITIONAL_ORDER +
                " like '%" + searchKey +"%' or " + REV_SURAHLIST_COLUMN_TOTAL_AYAH +
                " like '%" + searchKey +"%'", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            RevSurah revSurah = new RevSurah(cursor.getInt(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_CHORONOLOGICAL_ORDER)),
                    cursor.getInt(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_TRADITIONAL_ORDER)),
                    cursor.getString(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_SURAH_AR_NAME)),
                    cursor.getString(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_SURAH_EN_NAME)),
                    cursor.getString(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_SURAH_EN_TRANSLATION_NAME)),
                    cursor.getString(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_REVELATION_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_TOTAL_AYAH)),
                    cursor.getString(cursor.getColumnIndex(REV_SURAHLIST_COLUMN_NOTE)));
            revSurahList.add(revSurah);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return revSurahList;
    }
    //endregion:RevSurah_List

    //region:Defaults
    public int getRowNumber(String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db,
                table_name);
        db.close();
        return numRows;
    }
    //endregion:Defaults

    //region:Prayer Timings
    public void insertPrayerTime(String imsak, String fajr, String sunrise, String dhuhr, String asr,
                                 String sunset, String magrib, String isha, String midnight, String date,
                                 int day, int month, int year, String location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("imsak", imsak);
        contentValues.put("fajr", fajr);
        contentValues.put("sunrise", sunrise);
        contentValues.put("dhuhr", dhuhr);
        contentValues.put("asr", asr);
        contentValues.put("sunset", sunset);
        contentValues.put("magrib", magrib);
        contentValues.put("isha", isha);
        contentValues.put("midnight", midnight);
        contentValues.put("date", date);
        contentValues.put("day", day);
        contentValues.put("month", month);
        contentValues.put("year", year);
        contentValues.put("location", location);

        db.insert(PRAYERTIMINGS_TABLE, null, contentValues);

        db.close();
    }

    public void deletePrayerTime(String location, int month) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(PRAYERTIMINGS_TABLE, PRAYERTIMINGS_COLUMN_LOCATION +" = '"+ location + "' and "+
                PRAYERTIMINGS_COLUMN_MONTH + " < "+month, null);
        db.close();
    }

    public PrayerTimings getPrayerTime(String location, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + PRAYERTIMINGS_TABLE +
                " where "+ PRAYERTIMINGS_COLUMN_LOCATION +" = '"+ location + "' and "+
                PRAYERTIMINGS_COLUMN_DATE + " = '"+ date +"'", null);

        cursor.moveToFirst();

        if(cursor.moveToNext()) {
            return new PrayerTimings(cursor.getInt(cursor.getColumnIndex(PRAYERTIMINGS_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(PRAYERTIMINGS_COLUMN_IMSAK)),
                    cursor.getString(cursor.getColumnIndex(PRAYERTIMINGS_COLUMN_FAJR)),
                    cursor.getString(cursor.getColumnIndex(PRAYERTIMINGS_COLUMN_SUNRISE)),
                    cursor.getString(cursor.getColumnIndex(PRAYERTIMINGS_COLUMN_DHUHR)),
                    cursor.getString(cursor.getColumnIndex(PRAYERTIMINGS_COLUMN_ASR)),
                    cursor.getString(cursor.getColumnIndex(PRAYERTIMINGS_COLUMN_SUNSET)),
                    cursor.getString(cursor.getColumnIndex(PRAYERTIMINGS_COLUMN_MAGRIB)),
                    cursor.getString(cursor.getColumnIndex(PRAYERTIMINGS_COLUMN_ISHA)),
                    cursor.getString(cursor.getColumnIndex(PRAYERTIMINGS_COLUMN_MIDNIGHT)),
                    cursor.getString(cursor.getColumnIndex(PRAYERTIMINGS_COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndex(PRAYERTIMINGS_COLUMN_LOCATION)));
        }

        cursor.close();
        db.close();
        return new PrayerTimings(0,"","","","","","","","","","", "");
    }
    //endregion:Prayer Timings

    //region:Ayah_List
    public void insertAyahToQuran(String table_name, String identifier, int surah_number, String surah_ar_name,
                                               String surah_en_name, String surah_en_name_translation, String revelation_type,
                                               int ayah_number, String text, int juz, int manzil, int page, int ruku,
                                               int hizb_quarter, Boolean sajda, int sajda_id, Boolean sajda_recommended,
                                               Boolean sajda_obligatory, String language, String name, String transelator_en_name,
                                               String format, String type){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + table_name + " where " +
                QURAN_AYAH_NUMBER +
                " = " + ayah_number, null);

        if (cursor.getCount() == 0){
            ContentValues contentValues = new ContentValues();
            contentValues.put(QURAN_IDENTIFIER, identifier);
            contentValues.put(QURAN_SURAH_NUMBER, surah_number);
            contentValues.put(QURAN_SURAH_AR_NAME, surah_ar_name);
            contentValues.put(QURAN_SURAH_EN_NAME, surah_en_name);
            contentValues.put(QURAN_SURAH_EN_NAME_TRANSLATION, surah_en_name_translation);
            contentValues.put(QURAN_REVELATION_TYPE, revelation_type);
            contentValues.put(QURAN_AYAH_NUMBER, ayah_number);
            contentValues.put(QURAN_TEXT, text);
            contentValues.put(QURAN_JUZ, juz);
            contentValues.put(QURAN_MANZIL, manzil);
            contentValues.put(QURAN_PAGE, page);
            contentValues.put(QURAN_RUKU, ruku);
            contentValues.put(QURAN_HIZB_QUARTER, hizb_quarter);
            contentValues.put(QURAN_SAJDA, sajda);
            contentValues.put(QURAN_SAJDA_ID, sajda_id);
            contentValues.put(QURAN_SAJDA_RECOMMENDED, sajda_recommended);
            contentValues.put(QURAN_SAJDA_OBLIGATORY, sajda_obligatory);
            contentValues.put(QURAN_LANGUAGE, language);
            contentValues.put(QURAN_NAME, name);
            contentValues.put(QURAN_TRANSELATOR_EN_NAME, transelator_en_name);
            contentValues.put(QURAN_FORMAT, format);
            contentValues.put(QURAN_TYPE, type);

            db.insert(table_name, null, contentValues);
        }

        cursor.close();
        db.close();
    }

    public List<Ayah> getAllQuranAyah(String Table_name, int surah_number){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Ayah> ayahList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from '"+ Table_name +"' where "+ QURAN_SURAH_NUMBER +" = "+ surah_number +" order by "+QURAN_AYAH_NUMBER, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Ayah ayah = new Ayah(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("identifier")),
                    cursor.getInt(cursor.getColumnIndex("surah_number")),
                    cursor.getString(cursor.getColumnIndex("surah_ar_name")),
                    cursor.getString(cursor.getColumnIndex("surah_en_name")),
                    cursor.getString(cursor.getColumnIndex("surah_en_name_translation")),
                    cursor.getString(cursor.getColumnIndex("revelation_type")),
                    cursor.getInt(cursor.getColumnIndex("ayah_number")),
                    cursor.getString(cursor.getColumnIndex("text")),
                    cursor.getInt(cursor.getColumnIndex("juz")),
                    cursor.getInt(cursor.getColumnIndex("manzil")),
                    cursor.getInt(cursor.getColumnIndex("page")),
                    cursor.getInt(cursor.getColumnIndex("ruku")),
                    cursor.getInt(cursor.getColumnIndex("hizb_quarter")),
                    cursor.getString(cursor.getColumnIndex("sajda")).equals("true"),
                    cursor.getInt(cursor.getColumnIndex("sajda_id")),
                    cursor.getString(cursor.getColumnIndex("sajda_recommended")).equals("true") ,
                    cursor.getString(cursor.getColumnIndex("sajda_obligatory")).equals("true"),
                    cursor.getString(cursor.getColumnIndex("language")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("transelator_en_name")),
                    cursor.getString(cursor.getColumnIndex("format")),
                    cursor.getString(cursor.getColumnIndex("type")));
            ayahList.add(ayah);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return ayahList;
    }

    public List<Ayah> getAyahUlKursi(String Table_name){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Ayah> ayahList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+ Table_name +" where "+ QURAN_AYAH_NUMBER +" = 262 order by "+QURAN_AYAH_NUMBER, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Ayah ayah = new Ayah(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("identifier")),
                    cursor.getInt(cursor.getColumnIndex("surah_number")),
                    cursor.getString(cursor.getColumnIndex("surah_ar_name")),
                    cursor.getString(cursor.getColumnIndex("surah_en_name")),
                    cursor.getString(cursor.getColumnIndex("surah_en_name_translation")),
                    cursor.getString(cursor.getColumnIndex("revelation_type")),
                    cursor.getInt(cursor.getColumnIndex("ayah_number")),
                    cursor.getString(cursor.getColumnIndex("text")),
                    cursor.getInt(cursor.getColumnIndex("juz")),
                    cursor.getInt(cursor.getColumnIndex("manzil")),
                    cursor.getInt(cursor.getColumnIndex("page")),
                    cursor.getInt(cursor.getColumnIndex("ruku")),
                    cursor.getInt(cursor.getColumnIndex("hizb_quarter")),
                    cursor.getString(cursor.getColumnIndex("sajda")).equals("true"),
                    cursor.getInt(cursor.getColumnIndex("sajda_id")),
                    cursor.getString(cursor.getColumnIndex("sajda_recommended")).equals("true") ,
                    cursor.getString(cursor.getColumnIndex("sajda_obligatory")).equals("true"),
                    cursor.getString(cursor.getColumnIndex("language")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("transelator_en_name")),
                    cursor.getString(cursor.getColumnIndex("format")),
                    cursor.getString(cursor.getColumnIndex("type")));
            ayahList.add(ayah);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return ayahList;
    }

    public List<Ayah> getAlBaqara285_286Ayah(String Table_name){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Ayah> ayahList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+ Table_name +" where "+ QURAN_AYAH_NUMBER +" between 292 and 293 order by "+QURAN_AYAH_NUMBER, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Ayah ayah = new Ayah(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("identifier")),
                    cursor.getInt(cursor.getColumnIndex("surah_number")),
                    cursor.getString(cursor.getColumnIndex("surah_ar_name")),
                    cursor.getString(cursor.getColumnIndex("surah_en_name")),
                    cursor.getString(cursor.getColumnIndex("surah_en_name_translation")),
                    cursor.getString(cursor.getColumnIndex("revelation_type")),
                    cursor.getInt(cursor.getColumnIndex("ayah_number")),
                    cursor.getString(cursor.getColumnIndex("text")),
                    cursor.getInt(cursor.getColumnIndex("juz")),
                    cursor.getInt(cursor.getColumnIndex("manzil")),
                    cursor.getInt(cursor.getColumnIndex("page")),
                    cursor.getInt(cursor.getColumnIndex("ruku")),
                    cursor.getInt(cursor.getColumnIndex("hizb_quarter")),
                    cursor.getString(cursor.getColumnIndex("sajda")).equals("true"),
                    cursor.getInt(cursor.getColumnIndex("sajda_id")),
                    cursor.getString(cursor.getColumnIndex("sajda_recommended")).equals("true") ,
                    cursor.getString(cursor.getColumnIndex("sajda_obligatory")).equals("true"),
                    cursor.getString(cursor.getColumnIndex("language")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("transelator_en_name")),
                    cursor.getString(cursor.getColumnIndex("format")),
                    cursor.getString(cursor.getColumnIndex("type")));
            ayahList.add(ayah);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return ayahList;
    }

    public List<Ayah> getAlHashr20_24Ayah(String Table_name){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Ayah> ayahList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+ Table_name +" where "+ QURAN_AYAH_NUMBER +" between 5146 and 5150 order by "+QURAN_AYAH_NUMBER, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Ayah ayah = new Ayah(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("identifier")),
                    cursor.getInt(cursor.getColumnIndex("surah_number")),
                    cursor.getString(cursor.getColumnIndex("surah_ar_name")),
                    cursor.getString(cursor.getColumnIndex("surah_en_name")),
                    cursor.getString(cursor.getColumnIndex("surah_en_name_translation")),
                    cursor.getString(cursor.getColumnIndex("revelation_type")),
                    cursor.getInt(cursor.getColumnIndex("ayah_number")),
                    cursor.getString(cursor.getColumnIndex("text")),
                    cursor.getInt(cursor.getColumnIndex("juz")),
                    cursor.getInt(cursor.getColumnIndex("manzil")),
                    cursor.getInt(cursor.getColumnIndex("page")),
                    cursor.getInt(cursor.getColumnIndex("ruku")),
                    cursor.getInt(cursor.getColumnIndex("hizb_quarter")),
                    cursor.getString(cursor.getColumnIndex("sajda")).equals("true"),
                    cursor.getInt(cursor.getColumnIndex("sajda_id")),
                    cursor.getString(cursor.getColumnIndex("sajda_recommended")).equals("true") ,
                    cursor.getString(cursor.getColumnIndex("sajda_obligatory")).equals("true"),
                    cursor.getString(cursor.getColumnIndex("language")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("transelator_en_name")),
                    cursor.getString(cursor.getColumnIndex("format")),
                    cursor.getString(cursor.getColumnIndex("type")));
            ayahList.add(ayah);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return ayahList;
    }
    //endregion:Ayah_List

    //region:Language
    public void insertLanguage(String translator, Boolean selected){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + LANGUAGE_TABLE + " where " +
                LANGUAGE_COLUMN_TRANSLATOR +
                " = '" + translator + "'", null);

        if (cursor.getCount() == 0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("translator", translator);
            contentValues.put("selected", selected);

            db.insert(LANGUAGE_TABLE, null, contentValues);
        }

        cursor.close();
        db.close();
    }

    public List<Language> getQuranTranslator() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Language> languageList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + LANGUAGE_TABLE, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Language language = new Language(
                    cursor.getString(cursor.getColumnIndex(LANGUAGE_COLUMN_TRANSLATOR)),
                    cursor.getString(cursor.getColumnIndex(LANGUAGE_COLUMN_SELECTED)).equals("true"));
            languageList.add(language);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return languageList;
    }

    public void updateQuranTranslatorSelected(String translator, boolean selected) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "update " + LANGUAGE_TABLE+" set "+ LANGUAGE_COLUMN_SELECTED +" = '" + selected + "' where " +
                LANGUAGE_COLUMN_TRANSLATOR + " = '" + translator + "'";
        db.execSQL(query);
        db.close();
    }

    public List<Language> getSearchLanguageData(String searchKey) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Language> languageList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + LANGUAGE_TABLE + " where " + LANGUAGE_COLUMN_TRANSLATOR +
                " like '%" + searchKey +"%'", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Language language = new Language(cursor.getString(cursor.getColumnIndex(LANGUAGE_COLUMN_TRANSLATOR)),
                    cursor.getString(cursor.getColumnIndex(LANGUAGE_COLUMN_SELECTED)).equals("true"));
            languageList.add(language);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return languageList;
    }

    public List<Language> getSelectedLanguage() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Language> languageList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + LANGUAGE_TABLE + " where " + LANGUAGE_COLUMN_SELECTED + " = 'true'", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Language language = new Language(cursor.getString(cursor.getColumnIndex(LANGUAGE_COLUMN_TRANSLATOR)),
                    cursor.getString(cursor.getColumnIndex(LANGUAGE_COLUMN_SELECTED)).equals("true"));
            languageList.add(language);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return languageList;
    }

    //endregion:Language

    //region:Calender
    public void insertCalenderDay(String hijri_date, int hijri_day_number, int hijri_month_number,
                                  String hijri_month_en, String hijri_month_ar, int hijri_year_number,
                                  String hijri_weakday_en, String hijri_weakday_ar,
                                  String hijri_designation_abbreviated, String hijri_designation_expanded,
                                  String hijri_holiday, String gregorian_date, int gregorian_day_number,
                                  int gregorian_month_number, String gregorian_month_en, int gregorian_year_number,
                                  String gregorian_weakday_en, String gregorian_designation_abbreviated,
                                  String gregorian_designation_expanded)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + CALENDER_TABLE + " where " +
                CALENDER_COLUMN__HIJRI_DATE +
                " = '" + hijri_date + "'", null);

        if (cursor.getCount() == 0){
            ContentValues contentValues = new ContentValues();
            contentValues.put(CALENDER_COLUMN__HIJRI_DATE, hijri_date);
            contentValues.put(CALENDER_COLUMN__HIJRI_DAY_NUMBER, hijri_day_number);
            contentValues.put(CALENDER_COLUMN__HIJRI__MONTH_NUMBER, hijri_month_number);
            contentValues.put(CALENDER_COLUMN__HIJRI_MONTH_EN, hijri_month_en);
            contentValues.put(CALENDER_COLUMN__HIJRI_MONTH_AR, hijri_month_ar);
            contentValues.put(CALENDER_COLUMN__HIJRI_YEAR_NUMBER, hijri_year_number);
            contentValues.put(CALENDER_COLUMN__HIJRI_WEAKDAY_EN, hijri_weakday_en);
            contentValues.put(CALENDER_COLUMN__HIJRI_WEAKDAY_AR, hijri_weakday_ar);
            contentValues.put(CALENDER_COLUMN__HIJRI_DESIGNATION_ABBREVIATED, hijri_designation_abbreviated);
            contentValues.put(CALENDER_COLUMN__HIJRI_DESIGNATION_EXPANDED, hijri_designation_expanded);
            contentValues.put(CALENDER_COLUMN__HIJRI_HOLIDAY, hijri_holiday);
            contentValues.put(CALENDER_COLUMN__GREGORIAN_DATE, gregorian_date);
            contentValues.put(CALENDER_COLUMN__GREGORIAN_DAY_NUMBER, gregorian_day_number);
            contentValues.put(CALENDER_COLUMN__GREGORIAN__MONTH_NUMBER, gregorian_month_number);
            contentValues.put(CALENDER_COLUMN__GREGORIAN_MONTH_EN, gregorian_month_en);
            contentValues.put(CALENDER_COLUMN__GREGORIAN_YEAR_NUMBER, gregorian_year_number);
            contentValues.put(CALENDER_COLUMN__GREGORIAN_WEAKDAY_EN, gregorian_weakday_en);
            contentValues.put(CALENDER_COLUMN__GREGORIAN_DESIGNATION_ABBREVIATED, gregorian_designation_abbreviated);
            contentValues.put(CALENDER_COLUMN__GREGORIAN_DESIGNATION_EXPANDED, gregorian_designation_expanded);

            db.insert(CALENDER_TABLE, null, contentValues);
        }

        cursor.close();
        db.close();
    }

    public List<Calender> getCalenderByGregorianMonthNumber(int gregorian_month_number){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Calender> dayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+ CALENDER_TABLE + " where " +
                CALENDER_COLUMN__GREGORIAN__MONTH_NUMBER +
                " = '" + gregorian_month_number + "' order by " + CALENDER_COLUMN__GREGORIAN_DAY_NUMBER , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Calender day = new Calender(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("hijri_date")),
                    cursor.getInt(cursor.getColumnIndex("hijri_day_number")),
                    cursor.getInt(cursor.getColumnIndex("hijri_month_number")),
                    cursor.getString(cursor.getColumnIndex("hijri_month_en")),
                    cursor.getString(cursor.getColumnIndex("hijri_month_ar")),
                    cursor.getInt(cursor.getColumnIndex("hijri_year_number")),
                    cursor.getString(cursor.getColumnIndex("hijri_weakday_en")),
                    cursor.getString(cursor.getColumnIndex("hijri_weakday_ar")),
                    cursor.getString(cursor.getColumnIndex("hijri_designation_abbreviated")),
                    cursor.getString(cursor.getColumnIndex("hijri_designation_expanded")),
                    cursor.getString(cursor.getColumnIndex("hijri_holiday")),
                    cursor.getString(cursor.getColumnIndex("gregorian_date")),
                    cursor.getInt(cursor.getColumnIndex("gregorian_day_number")),
                    cursor.getInt(cursor.getColumnIndex("gregorian_month_number")),
                    cursor.getString(cursor.getColumnIndex("gregorian_month_en")),
                    cursor.getInt(cursor.getColumnIndex("gregorian_year_number")),
                    cursor.getString(cursor.getColumnIndex("gregorian_weakday_en")),
                    cursor.getString(cursor.getColumnIndex("gregorian_designation_abbreviated")),
                    cursor.getString(cursor.getColumnIndex("gregorian_designation_expanded")));
            dayList.add(day);

            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return dayList;
    }
    //endregion:Calender

}
