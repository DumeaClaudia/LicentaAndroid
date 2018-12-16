package com.claudia.restaurants.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartServices {

    private List<CartItem> cartItems = new ArrayList<>();

    public CartItem getCartAtPostion(int position) {
        return cartItems.get(position);
    }

    public int count() {
        return cartItems.size();
    }


   public void addCart(CartItem cartItem){
        cartItems.add(cartItem);
   }

   public void removeElements(){
        cartItems.clear();
   }

   public CartItem getCartById(String id){
       for (CartItem cartItem: cartItems) {
           if(cartItem.idCart.equals(id)){
               return cartItem;
           }
       }
       return null;
   }
}
