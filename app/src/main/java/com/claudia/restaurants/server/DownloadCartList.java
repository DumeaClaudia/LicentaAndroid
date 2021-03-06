package com.claudia.restaurants.server;

import android.util.Log;

import com.claudia.restaurants.history.list.CartSummaryItem;
import com.claudia.restaurants.history.list.CartListServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadCartList {
    private String url;
    private CartListServices cartListServices;
    private boolean activeCarts;

    public DownloadCartList(String url, CartListServices cartListServices, boolean activeCarts) {

        this.url = url;
        this.cartListServices = cartListServices;
        this.activeCarts = activeCarts;
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
            BufferedReader bufferedInputStream = new BufferedReader( new InputStreamReader(conn.getInputStream()));

            String line = bufferedInputStream.readLine();
            while(line  != null )
            {
                json = json + line;
                line = bufferedInputStream.readLine();
            }

            JSONArray array = new JSONArray(json);
            cartListServices.removeElements();
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);

                boolean cartActive = item.getBoolean("cartActive");
                if (cartActive == this.activeCarts) {
                    String idCart = item.getString("idCart");

                    String date = item.getString("createdDate");
                    String cartDescription = item.getString("cartDescription");
                    String nrProducts = item.getString("nrProducts");
                    String imageRestaurant = item.getString("imageRestaurant");
                    double totalPrice = item.getDouble("totalPrice");

                    CartSummaryItem cartSummaryItem = new CartSummaryItem(idCart, cartActive, date, cartDescription, nrProducts, imageRestaurant, totalPrice);
                    cartListServices.addCart(cartSummaryItem);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
