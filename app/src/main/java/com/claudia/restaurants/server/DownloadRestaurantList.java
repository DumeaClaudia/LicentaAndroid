package com.claudia.restaurants.server;

import android.util.Log;

import com.claudia.restaurants.restaurants.list.RestaurantItem;
import com.claudia.restaurants.restaurants.list.RestaurantListServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadRestaurantList {
    private String url;
    private RestaurantListServices restaurantListServices;

    public DownloadRestaurantList(String url, RestaurantListServices restaurantListServices) {

        this.url = url;
        this.restaurantListServices = restaurantListServices;
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
            restaurantListServices.removeElements();
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);

                Long restaurantId = item.getLong("id");

                String restaurantName = item.getString("name");
                String restaurantImage = item.getString("image");
                String restaurantDescription = item.getString("description");

                RestaurantItem restaurantItem = new RestaurantItem(restaurantId, restaurantName, restaurantImage, restaurantDescription);
                restaurantListServices.addRestaurant(restaurantItem);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
