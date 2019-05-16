package com.claudia.restaurants.cart;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.claudia.restaurants.R;
import com.claudia.restaurants.comment.CommentActivity;
import com.claudia.restaurants.history.details.CartDetailsExpandableListViewAdapter;
import com.claudia.restaurants.history.details.CartDetailsItem;
import com.claudia.restaurants.server.DownloadCartDetails;
import com.claudia.restaurants.server.ServerConfig;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class CartActivity extends AppCompatActivity {
    public CartDetailsExpandableListViewAdapter cartDetailsExpandableListViewAdapter;
    public static final String PRODUCT_ID_ARG = "PRODUCT_ID_ARG";
    String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);


            }
        });
        productId = getIntent().getStringExtra(PRODUCT_ID_ARG);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        ExpandableListView expandableListView = findViewById(R.id.cart_expandableListView);
        cartDetailsExpandableListViewAdapter = new CartDetailsExpandableListViewAdapter(this);
        expandableListView.setAdapter(cartDetailsExpandableListViewAdapter);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                new CartActivity.DownloadCartsUpdateCartTask(CartActivity.this).execute(ServerConfig.getServletURL("get_current_cart", ""));
                handler.postDelayed(this, 10000);
            }
        });

        final ImageView sendButton = findViewById(R.id.addProduct_imageView);

        if (productId != null) {
            new CartActivity.UploadNewProductTask(productId)
                    .execute(ServerConfig.getServletURL("add_new_product", "productId=" + productId), "", "");
        }
    }

    class DownloadCartsUpdateCartTask extends AsyncTask<String, Void, String> {
        private AppCompatActivity cartDetailsActivity;
        private CartDetailsItem cartDetailsItem;

        public DownloadCartsUpdateCartTask(AppCompatActivity cartDetailsActivity) {
            this.cartDetailsActivity = cartDetailsActivity;

        }

        protected String doInBackground(String... urls) {
            cartDetailsItem = new DownloadCartDetails(urls[0]).invoke();
            return "";
        }

        protected void onPostExecute(String s) {
            cartDetailsExpandableListViewAdapter.setItem(cartDetailsItem);
        }

    }


    public class UploadNewProductTask extends AsyncTask<String, Void, String> {
        String productId;

        public UploadNewProductTask(String productId) {
            this.productId = productId;
        }

        protected String doInBackground(String... urls) {

            String urldisplay = urls[0];

            HttpURLConnection conn;
            try {
                conn = (HttpURLConnection) new URL(urldisplay).openConnection();
                conn.setReadTimeout(60000 /* milliseconds */);
                conn.setConnectTimeout(65000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                outputStream.write(productId);
                outputStream.flush();

                int response = conn.getResponseCode();
                Log.d("CLAU_LOG", "The response is: " + response);


            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }

        protected void onPostExecute(String s) {
            new CartActivity.DownloadCartsUpdateCartTask(CartActivity.this).execute(ServerConfig.getServletURL("get_current_cart", ""));
        }

    }

}
