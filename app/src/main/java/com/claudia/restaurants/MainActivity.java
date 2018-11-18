package com.claudia.restaurants;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                new DownloadImageTask((ImageView) findViewById(R.id.imageView2))
                      .execute("http://192.168.0.1:8080/ui/resources/images/pizza.jpg");

                new DownloadJSONTask((TextView) findViewById(R.id.textView2))
                        .execute(   "http://192.168.0.1:8080/ui/jsonservlet/get_cart_example");



            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //new DownloadImageTask((ImageView) findViewById(R.id.imageView2))
         //       .execute("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Drawable doInBackground(String... urls) {
            String urldisplay = urls[0];
            Drawable mIcon11 = null;
            /*try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = Drawable.createFromStream(in, "Image");
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            } */


            URL url = null;
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



    private class DownloadJSONTask extends AsyncTask<String, Void, String> {
        TextView bmImage;

        public DownloadJSONTask(TextView bmImage) {
            this.bmImage = bmImage;
        }

        protected String doInBackground(String... urls) {
            String urldisplay = urls[0];
            String json = null;
            /*try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                json = Drawable.createFromStream(in, "Image");
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            } */


            HttpURLConnection conn = null;
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
}
