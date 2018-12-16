package com.claudia.restaurants;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Drawable> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Drawable doInBackground(String... urls) {
        String urldisplay = urls[0];
        Drawable mIcon11 = null;

        URL url;
        try {
            url = new URL(urldisplay);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000 /* milliseconds */);
            conn.setConnectTimeout(65000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            int response = conn.getResponseCode();
            Log.d("CLAU_LOG", "The response is: " + response);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());

            mIcon11 = Drawable.createFromStream(bufferedInputStream, urldisplay);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return mIcon11;
    }

    protected void onPostExecute(Drawable result) {
        //bmImage.setImageBitmap(result);
        bmImage.setBackground(result);
    }




}
