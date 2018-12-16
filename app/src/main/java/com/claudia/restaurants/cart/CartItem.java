package com.claudia.restaurants.cart;

public class CartItem {

    public String idCart;
    public String createdDate;
    public String restaurantName;
    public String restaurantImage;

    public CartItem(String idCart, String restaurantName, String createdDate, String restaurantImage) {
        this.idCart = idCart;
        this.createdDate = createdDate;
        this.restaurantName = restaurantName;
        this.restaurantImage = restaurantImage;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "createdDate='" + createdDate + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                '}';
    }
}
