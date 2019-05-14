package com.claudia.restaurants.comment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.claudia.restaurants.R;
import com.claudia.restaurants.server.DownloadCommentList;
import com.claudia.restaurants.server.ServerConfig;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CommentActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    CommentListServices commentListServices;
    Boolean scroll = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        recyclerView = findViewById(R.id.commentList_recyclerView);
        commentListServices = new CommentListServices();


        final CommentListViewAdapter listViewAdapter = new CommentListViewAdapter(this, commentListServices);
        recyclerView.setAdapter(listViewAdapter);
        listViewAdapter.notifyDataSetChanged();

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                new CommentActivity.DownloadCommentsUpdateListTask(listViewAdapter, commentListServices).execute(ServerConfig.getServletURL("get_comments", ""), "", "");
                handler.postDelayed(this, 10000);
            }
        });

        final ImageButton sendButton = findViewById(R.id.sendComment_imageButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView descriptionTextView = findViewById(R.id.addComment_editText);
                String description = descriptionTextView.getText().toString();

                if (!description.isEmpty()) {
                    descriptionTextView.setText("");
                    new CommentActivity.UploadCommentsTask(listViewAdapter, commentListServices, description)
                            .execute(ServerConfig.getServletURL("add_comment", ""), "", "");
                    scroll = true;
                }



            }
        });

    }


    class DownloadCommentsUpdateListTask extends AsyncTask<String, Void, String> {
        private CommentListViewAdapter commentViewAdapter;
        private CommentListServices commentListServices;

        public DownloadCommentsUpdateListTask(CommentListViewAdapter commentViewAdapter, CommentListServices commentListServices) {
            this.commentViewAdapter = commentViewAdapter;
            this.commentListServices = commentListServices;
        }

        protected String doInBackground(String... urls) {
            new DownloadCommentList(urls[0], commentListServices).invoke();
            return "";
        }

        protected void onPostExecute(String s) {
            commentViewAdapter.notifyDataSetChanged();
            if (scroll) {
                recyclerView.smoothScrollToPosition(commentListServices.count());
                scroll = false;
            }
        }

    }


    class UploadCommentsTask extends AsyncTask<String, Void, String> {
        private CommentListViewAdapter commentViewAdapter;
        private CommentListServices commentListServices;
        private String comment;

        public UploadCommentsTask(CommentListViewAdapter commentViewAdapter, CommentListServices commentListServices, String comment) {
            this.commentViewAdapter = commentViewAdapter;
            this.commentListServices = commentListServices;
            this.comment = comment;
        }

        protected String doInBackground(String... urls) {

            String urldisplay = urls[0];

            HttpURLConnection conn;
            try {
                conn = (HttpURLConnection) new URL(urldisplay).openConnection();
                conn.setReadTimeout(60000 /* milliseconds */);
                conn.setConnectTimeout(65000 /* milliseconds */);
                conn.setRequestMethod("POST");
                //conn.setRequestProperty("Content-Type", "application/json; utf-8");
              //  conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                //conn.connect();

                BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                outputStream.write(comment);
                outputStream.flush();

                int response = conn.getResponseCode();
                Log.d("CLAU_LOG", "The response is: " + response);


            } catch (IOException e) {
                e.printStackTrace();
            }


            return "";
        }

        protected void onPostExecute(String s) {

            new CommentActivity.DownloadCommentsUpdateListTask(commentViewAdapter, commentListServices)
                    .execute(ServerConfig.getServletURL("get_comments", ""), "", "");

        }

    }
}
