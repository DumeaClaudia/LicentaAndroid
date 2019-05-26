package com.claudia.restaurants.history.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.claudia.restaurants.R;

public class CartItemViewHolder extends RecyclerView.ViewHolder{
    final TextView restaurantTextView;
    final TextView createdDateTextView;
    final TextView nrProductsTextView;
    final ImageView imageRestaurantTextView;

    public CartItemViewHolder(View view) {
        super(view);
        restaurantTextView = view.findViewById( R.id.restaurantName_textView);
        createdDateTextView = view.findViewById( R.id.createdDate_textView);
        nrProductsTextView = view.findViewById(R.id.produse_textView);
        imageRestaurantTextView = view.findViewById(R.id.imageRestaurant_textView);

    }
}
