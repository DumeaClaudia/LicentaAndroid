package com.claudia.restaurants.map;

import com.claudia.restaurants.history.list.CartSummaryItem;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListServices {

    private List<RestaurantMapItem> restaurantMapItems = new ArrayList<>();

    public RestaurantMapItem getRestaurantAtPostion(int position) {
        return restaurantMapItems.get(position);
    }

    public int count() {
        return restaurantMapItems.size();
    }


    public void addRestaurant(RestaurantMapItem restaurantMapItem) {
        restaurantMapItems.add(restaurantMapItem);
    }

    public void removeElements() {
        restaurantMapItems.clear();
    }

}
