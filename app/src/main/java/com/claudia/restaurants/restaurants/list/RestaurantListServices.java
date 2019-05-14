package com.claudia.restaurants.restaurants.list;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListServices {

    private List<RestaurantItem> restaurantItems = new ArrayList<>();

    public RestaurantItem getRestaurantAtPosition(int position) {
        return restaurantItems.get(position);
    }

    public int count() {
        return restaurantItems.size();
    }


   public void addRestaurant(RestaurantItem restaurantItem){
        restaurantItems.add(restaurantItem);
   }

   public void removeElements(){
        restaurantItems.clear();
   }

   public RestaurantItem getRestaurantById(Long id){
       for (RestaurantItem restaurantItem : restaurantItems) {
           if(restaurantItem.restaurantId == id){
               return restaurantItem;
           }
       }
       return null;
   }
}
