package com.claudia.restaurants.history;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.claudia.restaurants.R;
import com.claudia.restaurants.history.list.CartListServices;
import com.claudia.restaurants.history.list.CartListViewAdapter;
import com.claudia.restaurants.server.DownloadCartList;
import com.claudia.restaurants.server.DownloadImageTask;
import com.claudia.restaurants.server.ServerConfig;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView recyclerView2;

    CartListServices cartListServices;
    CartListServices cartListServices2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        recyclerView = findViewById(R.id.cart_list_view);



        cartListServices = new CartListServices();
        cartListServices2 = new CartListServices();

        final CartListViewAdapter listViewAdapter = new CartListViewAdapter(this, cartListServices);
        final CartListViewAdapter listViewAdapter2 = new CartListViewAdapter(this, cartListServices2);

        DownloadImageTask.init_cache();
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                new HistoryActivity.DownloadCartsUpdateListTask(listViewAdapter, cartListServices, true).execute(ServerConfig.getServletURL("get_cart_list", ""), "", "");
                new HistoryActivity.DownloadCartsUpdateListTask(listViewAdapter2, cartListServices2, false).execute(ServerConfig.getServletURL("get_cart_list", ""), "", "");
                handler.postDelayed(this, 10000);
            }
        });

        recyclerView.setAdapter(listViewAdapter);


        recyclerView2 = findViewById(R.id.cart_list_view2);

        recyclerView2.setAdapter(listViewAdapter2);



    }

    class DownloadCartsUpdateListTask extends AsyncTask<String, Void, String> {
        private CartListViewAdapter cartViewAdapter;
        private CartListServices cartListServices;
        private boolean activeCarts;

        public DownloadCartsUpdateListTask(CartListViewAdapter cartViewAdapter, CartListServices cartListServices, boolean activeCarts){
            this.cartViewAdapter = cartViewAdapter;
            this.cartListServices = cartListServices;
            this.activeCarts = activeCarts;
        }

        protected String doInBackground(String... urls) {
            new DownloadCartList(urls[0], cartListServices, activeCarts).invoke();
            return "";
        }

        protected void onPostExecute(String s) {
            cartViewAdapter.notifyDataSetChanged();


        }

    }




}
