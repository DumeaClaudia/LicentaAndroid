package com.claudia.restaurants.history.list;

import java.util.ArrayList;
import java.util.List;

public class CartListServices {

    private List<CartSummaryItem> cartSummaryItems = new ArrayList<>();

    public CartSummaryItem getCartAtPostion(int position) {
        return cartSummaryItems.get(position);
    }

    public int count() {
        return cartSummaryItems.size();
    }


   public void addCart(CartSummaryItem cartSummaryItem){
        cartSummaryItems.add(cartSummaryItem);
   }

   public void removeElements(){
        cartSummaryItems.clear();
   }

   public CartSummaryItem getCartById(String id){
       for (CartSummaryItem cartSummaryItem : cartSummaryItems) {
           if(cartSummaryItem.idCart.equals(id)){
               return cartSummaryItem;
           }
       }
       return null;
   }
}
