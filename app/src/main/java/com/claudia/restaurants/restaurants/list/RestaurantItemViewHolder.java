package com.claudia.restaurants.restaurants.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.claudia.restaurants.R;

public class RestaurantItemViewHolder extends RecyclerView.ViewHolder{
    final TextView restaurantNameTextView;
    final TextView restaurantDescriptionTextView;
    final ImageView restaurantImageView;

    public RestaurantItemViewHolder(View view) {
        super(view);
        restaurantNameTextView = view.findViewById( R.id.restaurantName_textView);
        restaurantDescriptionTextView = view.findViewById( R.id.restaurantDescription_textView);
        restaurantImageView = view.findViewById(R.id.imageRestaurant_imageView);

    }
}
