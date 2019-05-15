package com.claudia.restaurants.server;

import android.util.Log;

import com.claudia.restaurants.restaurants.details.ProductItem;
import com.claudia.restaurants.restaurants.details.RestaurantProductsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownloadRestaurantProductList {
    private String url;

    public DownloadRestaurantProductList(String url) {
        this.url = url;
    }

    public RestaurantProductsItem invoke() {
        String urldisplay = url;
        String json = "";
        RestaurantProductsItem restaurantDetailsItem = new RestaurantProductsItem();
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

            JSONObject restaurantDetailsObj = new JSONObject(json);

            Long restaurantId = restaurantDetailsObj.getLong("id");
            String restaurantName = restaurantDetailsObj.getString("name");
            String restaurantImage = restaurantDetailsObj.getString("image");

            JSONArray productsDetailsItemsObj = restaurantDetailsObj.getJSONArray("products");

            List<ProductItem> productDetailsItems = new ArrayList<>();

            for (int index = 0; index < productsDetailsItemsObj.length(); index++) {
                JSONObject productItem = productsDetailsItemsObj.getJSONObject(index);

                long idProduct = productItem.getLong("id");
                long idRestaurant = productItem.getLong("idRestaurant");
                String image = productItem.getString("image");
                String name = productItem.getString("name");
                String category = productItem.getString("category");
                String description = productItem.getString("description");
                double price = productItem.getDouble("price");
                int discount = productItem.getInt("discount");

                ProductItem product = new ProductItem(idProduct, idRestaurant, image, name, category, description, String.format("%.2f", price), discount);
                productDetailsItems.add(product);
            }

            restaurantDetailsItem = new RestaurantProductsItem(restaurantId, restaurantName, restaurantImage, productDetailsItems);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return restaurantDetailsItem;
    }
}
