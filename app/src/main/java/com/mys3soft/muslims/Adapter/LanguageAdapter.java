package com.mys3soft.muslims.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mys3soft.muslims.DataBase.DBHelper;
import com.mys3soft.muslims.Models.Language;
import com.mys3soft.muslims.R;
import com.mys3soft.muslims.Tools.ReadAndWriteFiles;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {
    private Context context;
    private List<Language> languageList;
    private DBHelper db;
    private View mMainView;
    private ProgressDialog mProgressDialog;


    public LanguageAdapter(Context context, List<Language> languageList) {
        this.context = context;
        this.languageList = languageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mMainView = LayoutInflater.from(context).inflate(R.layout.single_language_checkbox, parent, false);
        return new ViewHolder(mMainView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        db = new DBHelper(context);

        holder.checkBox.setText(languageList.get(position).getLanguage());
        holder.checkBox.setChecked(languageList.get(position).isSelected());
        holder.checkBox.setTag(position);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected_position;
                Integer pos = (Integer) holder.checkBox.getTag();

                if (((CheckBox) v).isChecked()) {
                    for (int i = 0; i < languageList.size(); i++) {
                        String language = languageList.get(i).getLanguage();
                        if (i == pos) {
                            if (languageList.get(i).isSelected()) {
                                languageList.get(i).setSelected(false);
                                notifyItemRangeChanged(i, languageList.size());
                                db.updateQuranTranslatorSelected(language, false);
                            } else {
                                Dialog dialog = new Dialog(context);
                                languageList.get(i).setSelected(true);
                                notifyItemRangeChanged(i, languageList.size());
                                db.updateQuranTranslatorSelected(language, true);

                                if (language.equals("Bn_Muhiuddin_Khan")) {
                                    String[] urls = {"/Muslims/Quran/islam_public_bn__muhiuddin__khan.json", "Bn_Muhiuddin_Khan"};
                                    downloadData(urls);
                                } else if (language.equals("Ur_Ahmed_Raza_Khan")) {
                                    String[] urls = {"/Muslims/Quran/islam_public_ur__ahmed__raza__khan.json", "Ur_Ahmed_Raza_Khan"};
                                    downloadData(urls);
                                } else if (language.equals("En_Abdullah_Yusuf_Ali")) {
                                    String[] urls = {"/Muslims/Quran/islam_public_en__abdullah__yusuf__ali.json", "En_Abdullah_Yusuf_Ali"};
                                    downloadData(urls);
                                }
                            }
                        } else {
                            languageList.get(i).setSelected(false);
                            notifyItemRangeChanged(i, languageList.size());
                            db.updateQuranTranslatorSelected(language, false);
                        }
                    }
                } else {
                    selected_position = -1;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        protected CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox_language_id);
        }
    }

    private void downloadData(String[] urls) {
        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Please Wait While Loading Data");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(true);

        // execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(context);
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
    private class DownloadTask extends AsyncTask<String, Integer, String> {
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
                String last = sUrl[0].substring(sUrl[0].lastIndexOf('/') + 1);
                URL url = new URL("https://raw.githubusercontent.com/SaymaShinha/islamDB/master/" + last);
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

                output = new FileOutputStream(ReadAndWriteFiles.getAppExternalFilesDir(context) + sUrl[0]);

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

                if (connection != null) {
                    connection.disconnect();
                    ReadAndWriteFiles.readFileAndSaveQuranToDataBase(context, sUrl[0], sUrl[1]);
                }
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
        }
    }

}