package com.claudia.restaurants.cart;

public class CartItem {
    public String createdDate;

    public String restaurantName;


    public CartItem( String restaurantName, String createdDate) {
        this.createdDate = createdDate;
        this.restaurantName = restaurantName;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "createdDate='" + createdDate + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                '}';
    }
}
