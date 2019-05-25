package com.claudia.restaurants.server;

import android.util.Log;

import com.claudia.restaurants.cart.ProductDetailsCartItem;
import com.claudia.restaurants.cart.UserProductsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DownloadCurrentCart {
    private String url;

    public DownloadCurrentCart(String url) {
        this.url = url;
    }

    public  List<UserProductsItem>  invoke() {
        String urldisplay = url;
        String json = "";
        List<UserProductsItem> userProductsItemList = new ArrayList<>();
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

            JSONArray userListObj = new JSONArray(json);
            for(int nr = 0; nr<userListObj.length(); nr++){

                JSONObject userItemObj = userListObj.getJSONObject(nr);


                String username = userItemObj.getString("username");
                Double totalPrice = userItemObj.getDouble("totalPrice");
                JSONArray productsArrayObj = userItemObj.getJSONArray("cartDetails");
                List<ProductDetailsCartItem> cartProducts = new ArrayList<>();

                for (int i = 0; i < productsArrayObj.length(); i++) {
                    JSONObject item = productsArrayObj.getJSONObject(i);

                    String restaurantName = item.getString("restaurantName");
                    long productId = item.getInt("productId");
                    String productName = item.getString("productName");
                    Double productPrice = item.getDouble("price");
                    int nrProducts = item.getInt("nrProducts");

                    ProductDetailsCartItem productDetailsCartItem = new ProductDetailsCartItem(restaurantName, productId, productName, productPrice, nrProducts);
                    cartProducts.add(productDetailsCartItem);
                }


                UserProductsItem userProductsItem = new UserProductsItem(username, totalPrice, cartProducts);
                userProductsItemList.add(userProductsItem);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userProductsItemList;
    }
}
