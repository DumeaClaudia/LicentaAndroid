package com.claudia.restaurants.comment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.claudia.restaurants.R;
import com.claudia.restaurants.server.DownloadCommentList;
import com.claudia.restaurants.server.ServerConfig;

import java.util.Date;

public class CommentActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    CommentListServices commentListServices;

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


        SharedPreferences sharedPref =this.getSharedPreferences( this.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String username = sharedPref.getString(this.getString(R.string.preference_saved_username), "");

        final ImageButton sendButton = findViewById(R.id.sendComment_imageButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextView descriptionTextView = findViewById(R.id.addComment_editText);
                String description = descriptionTextView.getText().toString();

                Date sendDate = new Date();

                recyclerView.smoothScrollToPosition(commentListServices.count());
                if (!description.isEmpty()) {
                    commentListServices.addComment(new CommentItem(username, description, sendDate.toString(), true));
                    listViewAdapter.notifyDataSetChanged();
                    descriptionTextView.setText("");
                }
            }
        });

    }


    class DownloadCommentsUpdateListTask extends AsyncTask<String, Void, String> {
        private CommentListViewAdapter commentViewAdapter;
        private CommentListServices commentListServices;

        public DownloadCommentsUpdateListTask(CommentListViewAdapter commentViewAdapter, CommentListServices commentListServices){
            this.commentViewAdapter = commentViewAdapter;
            this.commentListServices = commentListServices;
        }

        protected String doInBackground(String... urls) {
            new DownloadCommentList(urls[0], commentListServices).invoke();
            return "";
        }

        protected void onPostExecute(String s) {
            commentViewAdapter.notifyDataSetChanged();


        }

    }
}
