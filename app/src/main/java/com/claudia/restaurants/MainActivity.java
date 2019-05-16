package com.claudia.restaurants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.claudia.restaurants.cart.CartActivity;
import com.claudia.restaurants.history.HistoryActivity;
import com.claudia.restaurants.login.LoginActivity;
import com.claudia.restaurants.restaurants.list.RestaurantListServices;
import com.claudia.restaurants.restaurants.list.RestaurantListViewAdapter;
import com.claudia.restaurants.server.DownloadImageTask;
import com.claudia.restaurants.server.DownloadRestaurantList;
import com.claudia.restaurants.server.ServerConfig;

import java.net.CookieManager;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView;
    RestaurantListServices restaurantListServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPref = this.getSharedPreferences(this.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String username = sharedPref.getString(this.getString(R.string.preference_saved_username), "");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        TextView usernameTextView = header.findViewById(R.id.username_textView);
        usernameTextView.setText(username);

        recyclerView = findViewById(R.id.restaurant_list_view);

        restaurantListServices = new RestaurantListServices();

        final RestaurantListViewAdapter restaurantListViewAdapter = new RestaurantListViewAdapter(this, restaurantListServices);

        recyclerView.setAdapter(restaurantListViewAdapter);

        DownloadImageTask.init_cache();
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                new MainActivity.DownloadRestaurantsUpdateListTask(restaurantListViewAdapter, restaurantListServices).execute(ServerConfig.getServletURL("get_restaurants", ""), "", "");
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
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_current_cart) {
            Intent intent = new Intent(getApplicationContext(), CartActivity.class);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            ServerConfig.CookieManager = new CookieManager();
            SharedPreferences sharedPref = this.getSharedPreferences(
                    this.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(this.getString(R.string.preference_saved_username), "");
            editor.putString(this.getString(R.string.preference_saved_password), "");

            editor.apply();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);

            this.finish();

        } else if (id == R.id.nav_cart) {
            Intent intent = new Intent(getApplicationContext(), CartActivity.class);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);

        } else if (id == R.id.nav_restaurants) {
        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, "Instaleaza aplicatia...");
            share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            getApplicationContext().startActivity(share);

        } else if (id == R.id.nav_send) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (mapIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {

                getApplicationContext().startActivity(mapIntent);
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }

    class DownloadRestaurantsUpdateListTask extends AsyncTask<String, Void, String> {
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

    }
}


