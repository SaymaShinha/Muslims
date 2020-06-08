package com.mys3soft.muslims.Tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;
import com.mys3soft.muslims.DataBase.DBHelper;
import com.mys3soft.muslims.MainActivity;
import com.mys3soft.muslims.Models.Ayah;
import com.mys3soft.muslims.Models.RevSurah;
import com.mys3soft.muslims.Models.Surah;
import com.mys3soft.muslims.Models.World_Cities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReadAndWriteFiles {
    private ProgressDialog mProgressDialog;
    private static final String LOG_TAG = "ExternalStorageDemo";

    // IMPORTANT!!
    public static File getAppExternalFilesDir(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            // /storage/emulated/0/Android/data/org.o7planning.externalstoragedemo/files
            return context.getExternalFilesDir(null);
        } else {
            // @Deprecated in API 29.
            // /storage/emulated/0
            return Environment.getExternalStorageDirectory();
        }
    }


    public static void writeFile(Context context, String[] fileName) {
        try {
            File extStore = getAppExternalFilesDir(context);
            for (String file : fileName) {
                File mainFile = new File(extStore.getAbsolutePath() + "/" + file);
                if (!mainFile.exists()) {
                    boolean canWrite = extStore.canWrite();
                    Log.i(LOG_TAG, "Can write: " + extStore.getAbsolutePath() + " : " + canWrite);

                    // ==> /storage/emulated/0/note.txt  (API < 29)
                    // ==> /storage/emulated/0/Android/data/org.o7planning.externalstoragedemo/files/note.txt (API >=29)
                    Log.i(LOG_TAG, file + "File Created");
                    mainFile.mkdirs();
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "Write Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Write Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void readFileAndSaveToDataBase(Context context, String fileName, String model) {
        File extStore = getAppExternalFilesDir(context);

        // ==> /storage/emulated/0/note.txt  (API < 29)
        // ==> /storage/emulated/0/Android/data/org.o7planning.externalstoragedemo/files (API >=29)
        String path = extStore.getAbsolutePath() + fileName;
        Log.i(LOG_TAG, "Read file: " + path);

        try {
            File file = new File(path);
            if (file.exists()) {
                DBHelper db = new DBHelper(context);
                if (model.equals("Surah_T")) {
                    int surah_list_row_number = db.getRowNumber("surah_list");
                    if (surah_list_row_number < 114) {
                        FileInputStream fIn = new FileInputStream(file);
                        for (Surah surah : ReadJsonData.readSurahsJsonStream(fIn)) {
                            db.insertSurah(surah.getSurah_Number(), surah.getSurah_Ar_Name(),
                                    surah.getSurah_En_Name(), surah.getSurah_En_Name_Translation(),
                                    surah.getRevelation_Type(), surah.getTotal_Ayah());
                        }
                    }
                } else if (model.equals("Surah_R")) {
                    int surah_list_row_number = db.getRowNumber("rev_surah_list");
                    if (surah_list_row_number < 114) {
                        FileInputStream fIn = new FileInputStream(file);
                        for (RevSurah revSurah : ReadJsonData.readRevSurahsJsonStream(fIn)) {
                            db.insertRevSurah(revSurah.getChronological_Order(), revSurah.getTraditional_Order(),
                                    revSurah.getSurah_Ar_Name(),
                                    revSurah.getSurah_En_Name(), revSurah.getSurah_En_Name_Translation(),
                                    revSurah.getRevelation_Type(), revSurah.getTotal_Ayah(), revSurah.getNote());
                        }
                    }
                } else if (model.equals("World_Cities")) {
                    int world_city_row_number = db.getRowNumber("world_locations");
                    if (world_city_row_number < 23019) {
                        FileInputStream fIn = new FileInputStream(file);
                        for (World_Cities world_location : ReadJsonData.readWorld_CitiesJsonStream(fIn)) {
                            db.insertWorldLocation(world_location.getCity(), world_location.getCountry(), world_location.getSubcountry());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void readFileAndSaveQuranToDataBase(Context context, String fileName, String model) {
        File extStore = getAppExternalFilesDir(context);

        // ==> /storage/emulated/0/note.txt  (API < 29)
        // ==> /storage/emulated/0/Android/data/org.o7planning.externalstoragedemo/files (API >=29)
        String path = extStore.getAbsolutePath() + fileName;
        Log.i(LOG_TAG, "Read file: " + path);

        try {
            File file = new File(path);
            if (file.exists()) {
                FileInputStream fIn = new FileInputStream(file);
                DBHelper db = new DBHelper(context);
                int ayah_row = db.getRowNumber(model);
                if (ayah_row < 6236){
                    for (Ayah ayah : ReadJsonData.readAyahsJsonStream(fIn)) {
                        db.insertAyahToQuran(model, ayah.getIdentifier(), ayah.getSurah_number(),
                                ayah.getSurah_ar_name(), ayah.getSurah_en_name(), ayah.getSurah_en_name_translation(),
                                ayah.getRevelation_type(), ayah.getAyah_number(), ayah.getText(),
                                ayah.getJuz(), ayah.getManzil(), ayah.getPage(), ayah.getRuku(),
                                ayah.getHizb_quarter(), ayah.getSajda(), ayah.getSajda_id(), ayah.getSajda_recommended(),
                                ayah.getSajda_obligatory(), ayah.getLanguage(), ayah.getName(), ayah.getTranselator_en_name(),
                                ayah.getFormat(), ayah.getType());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void listExternalStorages() {
        StringBuilder sb = new StringBuilder();

        sb.append("Data Directory: ").append("\n - ")
                .append(Environment.getDataDirectory().toString()).append("\n");

        sb.append("Download Cache Directory: ").append("\n - ")
                .append(Environment.getDownloadCacheDirectory().toString()).append("\n");

        sb.append("External Storage State: ").append("\n - ")
                .append(Environment.getExternalStorageState().toString()).append("\n");

        sb.append("External Storage Directory: ").append("\n - ")
                .append(Environment.getExternalStorageDirectory().toString()).append("\n");

        sb.append("Is External Storage Emulated?: ").append("\n - ")
                .append(Environment.isExternalStorageEmulated()).append("\n");

        sb.append("Is External Storage Removable?: ").append("\n - ")
                .append(Environment.isExternalStorageRemovable()).append("\n");

        sb.append("External Storage Public Directory (Music): ").append("\n - ")
                .append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString()).append("\n");

        sb.append("Download Cache Directory: ").append("\n - ")
                .append(Environment.getDownloadCacheDirectory().toString()).append("\n");

        sb.append("Root Directory: ").append("\n - ")
                .append(Environment.getRootDirectory().toString()).append("\n");

        Log.i(LOG_TAG, sb.toString());
    }


}
