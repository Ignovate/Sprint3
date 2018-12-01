package camp.abc.com.campignapp.services;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import camp.abc.com.campignapp.MainActivity;
import camp.abc.com.campignapp.database.DBHelper;
import camp.abc.com.campignapp.object.Campign;
import camp.abc.com.campignapp.object.Content;
import camp.abc.com.campignapp.utils.Const;
import camp.abc.com.campignapp.utils.Utils;

import static camp.abc.com.campignapp.utils.Const.LOCATION;

public class DateJobService extends JobService {

    private static final String TAG = "DateJobService";
    ArrayList<Campign> alCampign = new ArrayList<>();
    private boolean jobCancelled = false;
    private DBHelper dbHelper;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");

        dbHelper = new DBHelper(this);
        loadCampignData(params);
        return true;
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }

    private void loadCampignData(final JobParameters params) {
        if (jobCancelled) {
            return;
        }

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Response : ", ": " + response);


                        try {
                            JSONArray responseArray = new JSONArray(response);
                            for (int i = 0; i < responseArray.length(); i++) {

                                JSONObject campignObject = responseArray.getJSONObject(i);

                                String campignId = campignObject.getString(Const.CAMPIGN_ID);
                                String adSpace = campignObject.getString(Const.AD_SPACE);
                                String campignDate = campignObject.getString(Const.CAMPIGN_DATE);
                                String campignHour = campignObject.getString(Const.CAMPIGN_HOUR);
                                String campignSeconds = campignObject.getString(Const.CAMPIGN_SECONDS);
                                String campignLength = campignObject.getString(Const.CAMPIGN_LENGTH);

                                Campign campign = new Campign(campignId, adSpace, campignDate, campignHour, campignSeconds, campignLength);

                                dbHelper.insertCampignData(campign);
                                JSONArray contentArray = campignObject.getJSONArray(Const.CONTENTS);

                                for (int j = 0; j < contentArray.length(); j++) {

                                    JSONObject contentObject = contentArray.getJSONObject(j);
                                    String contentId = contentObject.getString(Const.CONTENT_ID);
                                    String contentURL = contentObject.getString(Const.CONTENT_URL);
                                    String contentType = contentObject.getString(Const.CONTENT_TYPE);
                                    String contentLength = contentObject.getString(Const.CONTENT_LENGTH);
                                    String contentDesc = contentObject.getString(Const.CONTENT_DESC);

                                    Content content = new Content(campignId, adSpace, contentId, contentURL, contentType, contentLength, contentDesc);
                                    dbHelper.insertContentData(content);
                                }
                            }
                            new DownloadFileFromURL(params).execute();


                        } catch (Exception e) {

                            Log.e("Exception : ", "" + e.toString());

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            file.mkdirs();
        }
        return file;
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        JobParameters params;
        String root = Environment.getExternalStorageDirectory().toString();
        private String filepath = "MyFileStorage";
        private File myExternalFile;

        public DownloadFileFromURL(JobParameters params) {
            this.params = params;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("Dateirbu", "Starting download");

        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {

                alCampign = dbHelper.getCampignData();


                for (int i = 0; i < alCampign.size(); i++) {

                    Campign campign = alCampign.get(i);

                    String campignId = campign.getCampignId();
                    String adSpace = campign.getAdSpace();

                    if (adSpace.equals(Const.SD)) {

                        ArrayList<Content> alContent = dbHelper.getContent(campignId, adSpace);

                        for (int j = 0; j < alContent.size(); j++) {

                            Content content = alContent.get(j);

                            String contentId = content.getContentId();
                            String contentURL = content.getContentURL();
                            URL url = new URL(contentURL);
                            URLConnection conection = url.openConnection();
                            conection.connect();
                            File outputFile;
                            if (contentURL.endsWith(".mp4")) {

                                outputFile = new File(getExternalFilesDir(LOCATION), adSpace + campignId + contentId + ".mp4");
                            } else {
                                outputFile = new File(getExternalFilesDir(LOCATION), adSpace + campignId + contentId + ".png");
                            }

                            Log.e("IMAge", outputFile.toString());

                            InputStream input = new BufferedInputStream(url.openStream());

                            OutputStream output = new FileOutputStream(outputFile, false);
                            byte data[] = new byte[1024];

                            long total = 0;
                            while ((count = input.read(data)) != -1) {
                                total += count;

                                output.write(data, 0, count);

                            }
                            output.flush();

                            output.close();
                            input.close();
                        }

                    }

                }

            } catch (Exception e) {
                Log.e("Error: fggngf ", e.getMessage());
            }

            return null;
        }

        /**
         * After completing background task
         **/
        @Override
        protected void onPostExecute(String file_url) {

            Log.e("Dateirbu", "Downloaded");
            jobFinished(params, false);
        }
    }


}
