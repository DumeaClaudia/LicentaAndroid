package com.claudia.restaurants.server;

import android.util.Log;

import com.claudia.restaurants.cart.details.CartDetailsItem;
import com.claudia.restaurants.cart.details.ProductDetailsItem;
import com.claudia.restaurants.cart.details.RestaurantProductsItem;
import com.claudia.restaurants.cart.list.CartSummaryItem;

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

public class DownloadCartDetails {
    private String url;

    public DownloadCartDetails(String url, String idCart) {
        this.url = url;
    }

    public CartDetailsItem invoke() {
        String urldisplay = url;
        String json = "";
        CartDetailsItem cartDetailsItem = new CartDetailsItem();
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

            JSONObject cartDetailsObj = new JSONObject(json);

            JSONObject cartSummaryObj = cartDetailsObj.getJSONObject("cartSummary");

            String idCart = cartSummaryObj.getString("idCart");
            boolean cartActive = cartSummaryObj.getBoolean("cartActive");
            String date = cartSummaryObj.getString("createdDate");
            String cartDescription = cartSummaryObj.getString("cartDescription");

            CartSummaryItem cartSummaryItem = new CartSummaryItem(idCart, cartActive, date, cartDescription);

            JSONArray productsArrayObj = cartDetailsObj.getJSONArray("restaurantProducts");
            List<RestaurantProductsItem> restaurantProductsItemList = new ArrayList<RestaurantProductsItem>();

            for (int i = 0; i < productsArrayObj.length(); i++) {
                JSONObject item = productsArrayObj.getJSONObject(i);

                long restaurantId = item.getLong("restaurantId");
                String restaurantName = item.getString("restaurantName");
                String restaurantImage = item.getString("restaurantImage");
                String restaurantAddress = item.getString("restaurantAddress");

                JSONArray productsDetailsItemsObj = item.getJSONArray("products");
                List<ProductDetailsItem> productDetailsItems = new ArrayList<ProductDetailsItem>();

                for (int index = 0; index < productsDetailsItemsObj.length(); index++) {
                    JSONObject productItem = productsDetailsItemsObj.getJSONObject(index);

                    long idProduct = productItem.getLong("idProduct");
                    long idRestaurant = productItem.getLong("idRestaurant");
                    String image = productItem.getString("image");
                    String name = productItem.getString("name");
                    String category = productItem.getString("category");
                    String description = productItem.getString("description");
                    double price = productItem.getDouble("price");
                    int discount = productItem.getInt("discount");


                    ProductDetailsItem product = new ProductDetailsItem(idProduct, idRestaurant, image, name, category, description, price, discount);
                    productDetailsItems.add(product);
                }

                RestaurantProductsItem restaurantProductsItem = new RestaurantProductsItem(restaurantId, restaurantName, restaurantImage, restaurantAddress, productDetailsItems);
                restaurantProductsItemList.add(restaurantProductsItem);
            }

            cartDetailsItem = new CartDetailsItem(cartSummaryItem, restaurantProductsItemList);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cartDetailsItem;
    }
}
