package com.claudia.restaurants;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadJSONTask extends AsyncTask<String, Void, String> {
    private TextView bmImage;

    public DownloadJSONTask(TextView bmImage) {
        this.bmImage = bmImage;
    }

    protected String doInBackground(String... urls) {
        String urldisplay = urls[0];
        String json = null;

        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) new URL(urldisplay).openConnection();
            conn.setReadTimeout(60000 /* milliseconds */);
            conn.setConnectTimeout(65000 /* milliseconds */);
            conn.setRequestMethod("GET");


            conn.setDoInput(true);
            conn.connect();

            int response = conn.getResponseCode();
            Log.d("CLAU_LOG", "The response is: " + response);
            BufferedReader bufferedInputStream = new BufferedReader( new InputStreamReader(conn.getInputStream()));
            json = bufferedInputStream.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return json;
    }

    protected void onPostExecute(String result) {
        //bmImage.setImageBitmap(result);
        bmImage.setText(result);
    }
}
