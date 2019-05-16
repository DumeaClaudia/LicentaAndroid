package com.claudia.restaurants.restaurants.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.claudia.restaurants.R;

public class RestaurantsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RestaurantListServices restaurantListServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*recyclerView = findViewById(R.id.restaurant_list_view);

        restaurantListServices = new RestaurantListServices();

        final RestaurantListViewAdapter restaurantListViewAdapter = new RestaurantListViewAdapter(this, restaurantListServices);

        recyclerView.setAdapter(restaurantListViewAdapter);


        DownloadImageTask.init_cache();
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                new RestaurantsActivity.DownloadRestaurantsUpdateListTask(restaurantListViewAdapter, restaurantListServices).execute(ServerConfig.getServletURL("get_restaurants", ""), "", "");
                handler.postDelayed(this, 10000);
            }
        });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }

    /*class DownloadRestaurantsUpdateListTask extends AsyncTask<String, Void, String> {
        private RestaurantListViewAdapter restaurantViewAdapter;
        private RestaurantListServices restaurantListServices;

        public DownloadRestaurantsUpdateListTask(RestaurantListViewAdapter restaurantViewAdapter, RestaurantListServices restaurantListServices){
            this.restaurantViewAdapter = restaurantViewAdapter;
            this.restaurantListServices = restaurantListServices;
        }

        protected String doInBackground(String... urls) {
            new DownloadRestaurantList(urls[0], restaurantListServices).invoke();
            return "";
        }

        protected void onPostExecute(String s) {
            restaurantViewAdapter.notifyDataSetChanged();
        }

    }*/
}
