package com.claudia.restaurants.cart;

import java.util.ArrayList;
import java.util.List;

public class CartServices {

    private List<CartItem> cartItems = new ArrayList<>();

    public CartItem getCartAtPostion(int position) {
        return cartItems.get(position);
    }

    public int count() {
        return cartItems.size();
    }

    public void refresh(){
        cartItems.add(new CartItem("Restaurant1", "12.12.2012"));
        cartItems.add(new CartItem("Restaurant1", "12.12.2012"));
        cartItems.add(new CartItem("Restaurant1", "12.12.2012"));

    }
}
