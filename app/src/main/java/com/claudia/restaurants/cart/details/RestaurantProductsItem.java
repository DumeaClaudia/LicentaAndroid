package com.claudia.restaurants.cart.details;

import java.util.ArrayList;
import java.util.List;

public class RestaurantProductsItem {
    public String restaurantName;
    public String restaurantImage;
    public String restaurantAddress;

    public List<ProductDetailsItem> products;

    public RestaurantProductsItem(String restaurantName, String restaurantImage, String restaurantAddress, List<ProductDetailsItem> products) {
        this.restaurantName = restaurantName;
        this.restaurantImage = restaurantImage;
        this.restaurantAddress = restaurantAddress;
        this.products = products;
    }

    public RestaurantProductsItem() {
        restaurantName = "";
        restaurantImage = "";
        restaurantAddress = "";
        products = new ArrayList<ProductDetailsItem>();
    }


    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public List<ProductDetailsItem> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDetailsItem> products) {
        this.products = products;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        for(ProductDetailsItem p : products){
            builder.append(p.toString());
            builder.append(",\n");

        }
        return "RestaurantProductsItem {" +
                " restaurantName = " + restaurantName + '\n' +
                " restaurantImage = " + restaurantImage + '\n' +
                " restaurantAddress = " + restaurantAddress + '\n' +
                " products= [" + builder.toString() +
                "]" + '}';

    }
}
