package com.claudia.restaurants.restaurants.details;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;

import com.claudia.restaurants.R;
import com.claudia.restaurants.server.DownloadImageTask;
import com.claudia.restaurants.server.DownloadRestaurantProductList;
import com.claudia.restaurants.server.ServerConfig;

public class RestaurantDetailsActivity extends AppCompatActivity {
    public static final String  RESTAURANT_ID_ARG = "RESTAURANT_ID_ARG";
    String restaurantId;
    public RestaurantDetailsExpandableListViewAdapter restaurantDetailsExpandableListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        restaurantId = getIntent().getStringExtra(RESTAURANT_ID_ARG);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        new RestaurantDetailsActivity.DownloadRestaurantProductsTask(RestaurantDetailsActivity.this).execute(ServerConfig.getServletURL("get_restaurant_details", "restaurantId="+restaurantId));


        ExpandableListView expandableListView = findViewById(R.id.products_expandableListView);
        restaurantDetailsExpandableListViewAdapter = new RestaurantDetailsExpandableListViewAdapter(this);
        expandableListView.setAdapter(restaurantDetailsExpandableListViewAdapter);


        DownloadImageTask.init_cache();
    }

    class DownloadRestaurantProductsTask extends AsyncTask<String, Void, String> {
        private AppCompatActivity restaurantDetailsActivity;
        private RestaurantProductsItem restaurantDetailsItem;

        public DownloadRestaurantProductsTask(AppCompatActivity restaurantDetailsActivity) {
            this.restaurantDetailsActivity = restaurantDetailsActivity;

        }

        protected String doInBackground(String... urls) {
            restaurantDetailsItem = new DownloadRestaurantProductList(urls[0]).invoke();
            return "";
        }

        protected void onPostExecute(String s) {
            restaurantDetailsExpandableListViewAdapter.setItem(restaurantDetailsItem);
        }

    }

}
