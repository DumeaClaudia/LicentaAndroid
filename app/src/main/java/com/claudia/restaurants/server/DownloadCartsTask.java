package com.claudia.restaurants.server;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.claudia.restaurants.cart.CartItem;
import com.claudia.restaurants.cart.CartListViewAdapter;
import com.claudia.restaurants.cart.CartServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadCartsTask extends AsyncTask<String, Void, Void> {
    private CartListViewAdapter cartViewAdapter;
    private CartServices cartServices;

    public DownloadCartsTask(CartListViewAdapter cartViewAdapter, CartServices cartServices){
        this.cartViewAdapter = cartViewAdapter;
        this.cartServices = cartServices;
    }

    protected Void doInBackground(String... urls) {
        String urldisplay = urls[0];
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
            BufferedReader bufferedInputStream = new BufferedReader( new InputStreamReader(conn.getInputStream()));

          String line = bufferedInputStream.readLine();
          while(line  != null )
          {
              json = json + line;
              line = bufferedInputStream.readLine();
          }

            JSONArray array = new JSONArray(json);
            cartServices.removeElements();
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                String restaurant = item.getString("restaurantName");
                String date = item.getString("createdDate");
                CartItem cartItem = new CartItem(restaurant, date);
                cartServices.addCart(cartItem);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    protected void onPostExecute() {
        cartViewAdapter.notifyDataSetChanged();


    }
}
