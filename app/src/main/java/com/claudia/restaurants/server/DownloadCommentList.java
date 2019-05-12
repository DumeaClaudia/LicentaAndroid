package com.claudia.restaurants.server;

import android.util.Log;

import com.claudia.restaurants.comment.CommentItem;
import com.claudia.restaurants.comment.CommentListServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadCommentList {
    private String url;
    private CommentListServices commentListServices;

    public DownloadCommentList(String url, CommentListServices commentListServices) {

        this.url = url;
        this.commentListServices = commentListServices;
    }

    public void invoke() {
        String urldisplay = url;
        String json = "";

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
            BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = bufferedInputStream.readLine();
            while (line != null) {
                json = json + line;
                line = bufferedInputStream.readLine();
            }

            JSONArray array = new JSONArray(json);
            if(commentListServices!=null){
                commentListServices.removeElements();
            }

            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                String username = item.getString("username");
                String description = item.getString("description");
                String date = item.getString("date");
                boolean ownComment = item.getBoolean("ownComment");

                CommentItem commentItem = new CommentItem(username, description, date, ownComment);
                commentListServices.addComment(commentItem);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
