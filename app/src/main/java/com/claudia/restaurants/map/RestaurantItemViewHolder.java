package com.claudia.restaurants.map;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.claudia.restaurants.R;

public class RestaurantItemViewHolder extends RecyclerView.ViewHolder{
    final TextView restaurantNameTextView;
    final TextView restaurantAddressTextView;
    final TextView restaurantPositionTextView;
    final ImageButton restaurantRouteImageButton;

    public RestaurantItemViewHolder(View view) {
        super(view);
        restaurantNameTextView = view.findViewById( R.id.restaurantName_textView);
        restaurantAddressTextView = view.findViewById( R.id.restaurantAddress_textView);
        restaurantPositionTextView = view.findViewById(R.id.positionRestaurant_textView);
        restaurantRouteImageButton = view.findViewById(R.id.routeRestaurant_imageButton);

    }
}
