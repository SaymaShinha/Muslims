package com.mys3soft.muslims;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
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
    private static ProgressDialog mProgressDialog;
    private static final String LOG_TAG = "ExternalStorageDemo";
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private FrameLayout mFrameLayout;
    private DBHelper db;

    String[] mDefaultDataUrls = {"/Muslims/Quran/islam_public_quran__info.json",
            "/Muslims/Quran/islam_public_quran__info__according__to__revelation.json",
            "/Muslims/Quran/islam_public_en__ahmed__raza__khan.json",
            "/Muslims/Quran/islam_public_ar__uthmani.json",
            "/Muslims/Quran/islam_public_en__english__transliteration.json"};
    String[] mDefaultFiles = {"Muslims/Quran", "Muslims/Prayer", "Muslims/Calender"};


    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar_id);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        BottomNavigationView mBottomNavigationView = findViewById(R.id.main_bottom_navigation_id);
        db = new DBHelper(this);

        mBottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(PrayersFragment.newInstance("", ""));

        askPermissionAndWriteFile();
        askPermissionToReadFile();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int currentNightMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
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

        ThreadDemo T1 = new ThreadDemo("Surah_T");
        T1.start();

        ThreadDemo T2 = new ThreadDemo("Surah_R");
        T2.start();

        ThreadDemo T3 = new ThreadDemo("Ar_Uthamani");
        T3.start();

        ThreadDemo T4 = new ThreadDemo("English_Transliteration");
        T4.start();

        ThreadDemo T5 = new ThreadDemo("En_Ahmed_Raza_Khan");
        T5.start();

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

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
                        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setMessage("Please Wait While Loading Data");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setCancelable(true);
                        progressDialog.show();

                        ThreadDemo T1 = new ThreadDemo("Surah_T");
                        T1.start();

                        ThreadDemo T2 = new ThreadDemo("Surah_R");
                        T2.start();

                        ThreadDemo T3 = new ThreadDemo("Ar_Uthamani");
                        T3.start();

                        ThreadDemo T4 = new ThreadDemo("English_Transliteration");
                        T4.start();

                        ThreadDemo T5 = new ThreadDemo("En_Ahmed_Raza_Khan");
                        T5.start();

                        addLanguage();
                        progressDialog.cancel();
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
        ArrayList<String> notExistFileName = new ArrayList<>();
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
    public static class DownloadTask extends AsyncTask<String, Integer, String> {
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

                    byte[] data = new byte[4096];
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
            assert pm != null;
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire(10*60*1000L /*10 minutes*/);
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
        String[] languageList = new String[]{"Bn_Muhiuddin_Khan", "Ur_Ahmed_Raza_Khan", "En_Ahmed_Raza_Khan", "En_Abdullah_Yusuf_Ali"};
        for (String s : languageList) {
            db.insertLanguage(s, false);
        }
    }

    class ThreadDemo extends Thread {
        private Thread t;
        private String threadName;
        private Context context = MainActivity.this;

        ThreadDemo( String name) {
            threadName = name;
            System.out.println("Creating " +  threadName );
        }

        public void run() {
            System.out.println("Running " +  threadName );
            try {
                String last = threadName;
                switch (last) {
                    case "Surah_T":
                        ReadAndWriteFiles.readFileAndSaveToDataBase(context, "/Muslims/Quran/islam_public_quran__info.json", "Surah_T");
                        break;
                    case "Surah_R":
                        ReadAndWriteFiles.readFileAndSaveToDataBase(context, "/Muslims/Quran/islam_public_quran__info__according__to__revelation.json", "Surah_R");
                        break;
                    case "Ar_Uthamani":
                        ReadAndWriteFiles.readFileAndSaveQuranToDataBase(context, "/Muslims/Quran/islam_public_ar__uthmani.json", "Ar_Uthamani");
                        break;
                    case "English_Transliteration":
                        ReadAndWriteFiles.readFileAndSaveQuranToDataBase(context, "/Muslims/Quran/islam_public_en__english__transliteration.json", "English_Transliteration");
                        break;
                    case "En_Ahmed_Raza_Khan":
                        ReadAndWriteFiles.readFileAndSaveQuranToDataBase(context, "/Muslims/Quran/islam_public_en__ahmed__raza__khan.json", "En_Ahmed_Raza_Khan");
                        break;

                }

                // Let the thread sleep for a while.
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("Thread " +  threadName + " interrupted.");
            }
            System.out.println("Thread " +  threadName + " exiting.");
        }

        public void start () {
            System.out.println("Starting " +  threadName );
            if (t == null) {
                t = new Thread (this, threadName);
                t.start ();
            }
        }
    }

    private void OnGPS() {
        String date = Tools.GetDataFromSharePrefarence(this, "Date");

        if (date.equals("")){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                Tools.SaveDataToSharePrefarence(this, "latitude", latitude);
                Tools.SaveDataToSharePrefarence(this, "longitude", longitude);
            } else {
                //Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}



