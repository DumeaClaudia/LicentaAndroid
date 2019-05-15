package com.claudia.restaurants.restaurants.details;

import java.util.List;

public class RestaurantProductsItem {

    public Long restaurantId;
    public String restaurantName;
    public String restaurantImage;
    public String restaurantDescription;
    public String restaurantAddress;
    public String restaurantGeolocation;

    public List<ProductItem> products;

    public RestaurantProductsItem(Long restaurantId, String restaurantName, String restaurantImage, List<ProductItem> products) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantImage = restaurantImage;
        this.products = products;
    }

    public RestaurantProductsItem() {
    }

    public RestaurantProductsItem(long restaurantId, String restaurantName, String restaurantImage, String restaurantDescription, String restaurantAddress, String restaurantGeolocation, List<ProductItem> products) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantImage = restaurantImage;
        this.restaurantDescription = restaurantDescription;
        this.restaurantAddress = restaurantAddress;
        this.restaurantGeolocation = restaurantGeolocation;
        this.products = products;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
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

    public String getRestaurantDescription() {
        return restaurantDescription;
    }

    public void setRestaurantDescription(String restaurantDescription) {
        this.restaurantDescription = restaurantDescription;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getRestaurantGeolocation() {
        return restaurantGeolocation;
    }

    public void setRestaurantGeolocation(String restaurantGeolocation) {
        this.restaurantGeolocation = restaurantGeolocation;
    }

    public List<ProductItem> getProducts() {
        return products;
    }

    public void setProducts(List<ProductItem> products) {
        this.products = products;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        for(ProductItem product : products){
            builder.append(products.toString());
            builder.append(",\n");
        }
        builder.append(",\n");

        return "RestaurantProductsItem{" +
                "restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                ", restaurantImage='" + restaurantImage + '\'' +
                ", restaurantDescription='" + restaurantDescription + '\'' +
                ", restaurantAddress='" + restaurantAddress + '\'' +
                ", restaurantGeolocation='" + restaurantGeolocation + '\'' +
                "restaurantProducts = [" + builder.toString() +
                '}';
    }

}
