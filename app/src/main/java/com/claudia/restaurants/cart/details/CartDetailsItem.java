package com.claudia.restaurants.cart.details;

import com.claudia.restaurants.cart.list.CartSummaryItem;

import java.util.ArrayList;
import java.util.List;

public class CartDetailsItem {

    public CartSummaryItem cartSummary;
    public List<RestaurantProductsItem> restaurantProducts;

    public CartDetailsItem(CartSummaryItem cartSummary, List<RestaurantProductsItem> restaurantProducts) {
        this.cartSummary = cartSummary;
        this.restaurantProducts = restaurantProducts;
    }

    public CartDetailsItem() {
        cartSummary = new CartSummaryItem();
    }

    public CartSummaryItem getCartSummary() {
        return cartSummary;
    }

    public void setCartSummary(CartSummaryItem cartSummary) {
        this.cartSummary = cartSummary;
    }

    public List<RestaurantProductsItem> getRestaurantProducts() {
        return restaurantProducts;
    }

    public void setRestaurantProducts(List<RestaurantProductsItem> restaurantProducts) {
        this.restaurantProducts = restaurantProducts;
    }


    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        for(RestaurantProductsItem products : restaurantProducts){
            builder.append(products.toString());
            builder.append(",\n");
        }
        builder.append(",\n");

        return "CartDetailsItem {" +
                "cartSummary = " + cartSummary + '\n' +
                "restaurantProducts = [" + builder.toString() +
                "]" +
                '}';
    }
}
