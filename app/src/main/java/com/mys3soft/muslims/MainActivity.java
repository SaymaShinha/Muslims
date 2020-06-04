package com.mys3soft.muslims;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mys3soft.muslims.DataBase.DBHelper;
import com.mys3soft.muslims.Tools.ReadAndWriteFiles;
import com.mys3soft.muslims.Tools.Tools;
import android.os.PowerManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private static final String LOG_TAG = "ExternalStorageDemo";
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private FrameLayout mFrameLayout;
    private DBHelper db;

    String[] mDefaultDataUrls = {"/Muslims/Quran/islam_public_quran__info.json",
            "/Muslims/Quran/islam_public_quran__info__according__to__revelation.json",
            "/Muslims/Prayer/islam_public_world_cities.json"};
    String[] mDefaultFiles = {"Muslims/Quran", "Muslims/Prayer", "Muslims/Calender"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar_id);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        BottomNavigationView mBottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottom_navigation_id);
        db = new DBHelper(this);

        mBottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(PrayersFragment.newInstance("", ""));

        askPermissionAndWriteFile();
        askPermissionToReadFile();
    }

    @Override
    protected void onStart() {
        super.onStart();

        int currentNightMode = getChangingConfigurations();
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                break;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Fragments

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home_tab_id:
                            openFragment(HomeFragment.newInstance("", ""));
                            return true;
                        case R.id.prayer_tab_id:
                            openFragment(PrayersFragment.newInstance("", ""));
                            return true;
                        case R.id.quran_tab_id:
                            openFragment(QuranFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };


///////////////////////////////////////////////////////////////////////////////
    
///////////////////////////////////////////////////////////////////////////////
    // Read And Write File

    private void askPermissionAndWriteFile() {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!canWrite) {
            Toast.makeText(this,
                    "You do not allow this app to write files.", Toast.LENGTH_LONG).show();
            return;
        }
        ReadAndWriteFiles.writeFile(this, mDefaultFiles);
        downloadDefaultData(mDefaultDataUrls);
    }

    private void askPermissionToReadFile() {
        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        //
        if (!canRead) {
            Toast.makeText(this,
                    "You do not allow this app to read files.", Toast.LENGTH_LONG).show();
            return;
        }
        ReadAndWriteFiles.readFileAndSaveToDataBase( this,"/Muslims/Quran/islam_public_quran__info.json", "Surah_T");
        ReadAndWriteFiles.readFileAndSaveToDataBase( this,"/Muslims/Quran/islam_public_quran__info__according__to__revelation.json", "Surah_R");
        ReadAndWriteFiles.readFileAndSaveToDataBase( this,"/Muslims/Prayer/islam_public_world_cities.json", "World_Cities");
        ReadAndWriteFiles.readFileAndSaveQuranToDataBase( this,"/Muslims/Prayer/islam_public_ar__uthmani.json", "Ar_Uthamani");
        ReadAndWriteFiles.readFileAndSaveQuranToDataBase( this,"/Muslims/Prayer/islam_public_en__english__transliteration.json", "English_Transliteration");
        addLanguage();
    }

    // With Android Level >= 23, you have to ask the user
    // for permission with device (For example read/write data on the device).
    private boolean askPermission(int requestId, String permissionName) {


        Log.i(LOG_TAG, "Ask for Permission: " + permissionName);
        Log.i(LOG_TAG, "Build.VERSION.SDK_INT: " + android.os.Build.VERSION.SDK_INT);

        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Check if we have permission
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);

            Log.i(LOG_TAG, "permission: " + permission);
            Log.i(LOG_TAG, "PackageManager.PERMISSION_GRANTED: " + PackageManager.PERMISSION_GRANTED);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }

    // As soon as the user decides, allows or doesn't allow.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        // Note: If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_ID_WRITE_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        ReadAndWriteFiles.writeFile(this, mDefaultFiles);
                        downloadDefaultData(mDefaultDataUrls);
                    }
                }
                case REQUEST_ID_READ_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        mProgressDialog = new ProgressDialog(MainActivity.this);
                        mProgressDialog.setMessage("Please Wait While Loading Data");
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        mProgressDialog.setCancelable(true);
                        ReadAndWriteFiles.readFileAndSaveToDataBase( this,"/Muslims/Quran/islam_public_quran__info.json", "Surah_T");
                        ReadAndWriteFiles.readFileAndSaveToDataBase( this,"/Muslims/Quran/islam_public_quran__info__according__to__revelation.json", "Surah_R");
                        ReadAndWriteFiles.readFileAndSaveToDataBase( this,"/Muslims/Prayer/islam_public_world_cities.json", "World_Cities");
                        ReadAndWriteFiles.readFileAndSaveQuranToDataBase( this,"/Muslims/Prayer/islam_public_ar__uthmani.json", "Ar_Uthamani");
                        ReadAndWriteFiles.readFileAndSaveQuranToDataBase( this,"/Muslims/Quran/islam_public_en__english__transliteration.json", "English_Transliteration");
                        addLanguage();
                        mProgressDialog.cancel();
                    }
                }
            }
        } else {
            Toast.makeText(this, "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }


///////////////////////////////////////////////////////////////////////////////
    // Download From Urls

    private String[] notExistedFiles(String[] filename){
        ArrayList<String> notExistFileName = new ArrayList<String>();
        File extStore = ReadAndWriteFiles.getAppExternalFilesDir(this);
        for (String item : filename){
            File file = new File(extStore.getAbsolutePath()+item);
            if (!file.exists()){
                notExistFileName.add(item);
            }
        }
        return Tools.GetStringArray(notExistFileName);
    }

    private void downloadDefaultData(String[] fileNames){
        String[] notExistedFilenames = notExistedFiles(fileNames);
        if(notExistedFilenames.length>0){
            downloadData(notExistedFilenames);
        }
    }

    private void downloadData(String[] urls) {
        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("Please Wait While Loading Data");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        // execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(MainActivity.this);
        downloadTask.execute(urls);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true); //cancel the task
            }
        });
    }


    // usually, subclasses of AsyncTask are declared inside the activity class.
    // that way, you can easily modify the UI thread from here
    public class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                for (String stringUrl : sUrl ){
                    String last = stringUrl.substring(stringUrl.lastIndexOf('/') + 1);
                    URL url = new URL("https://raw.githubusercontent.com/SaymaShinha/islamDB/master/"+last);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    // expect HTTP 200 OK, so we don't mistakenly save error report
                    // instead of the file
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        return "Server returned HTTP " + connection.getResponseCode()
                                + " " + connection.getResponseMessage();
                    }

                    // this will be useful to display download percentage
                    // might be -1: server did not report the length
                    int fileLength = connection.getContentLength();
                    // download the file
                    input = connection.getInputStream();

                    output = new FileOutputStream(ReadAndWriteFiles.getAppExternalFilesDir(context) + stringUrl);

                    byte data[] = new byte[4096];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        // allow canceling with back button
                        if (isCancelled()) {
                            input.close();
                            return null;
                        }
                        total += count;
                        // publishing the progress....
                        if (fileLength > 0) // only if total length is known
                            publishProgress((int) (total * 100 / fileLength));
                        output.write(data, 0, count);
                    }
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
        }
    }

    public void addLanguage(){
        String[] languageList = new String[]{"Bn_Muhiuddin_Khan", "Ur_Ahmed_Raza_Khan", "En_Abdullah_Yusuf_Ali"};
        for(int i = 0; i < languageList.length; i++){
            db.insertLanguage(languageList[i], false);
        }
    }

}


