package com.claudia.restaurants.cart.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.claudia.restaurants.R;

public class CartItemViewHolder extends RecyclerView.ViewHolder{
    final TextView restaurantTextView;
    final TextView createdDateTextView;

    public CartItemViewHolder(View view) {
        super(view);
        restaurantTextView = view.findViewById( R.id.restaurant_textView);
        createdDateTextView = view.findViewById( R.id.createdDate_textView);

    }
}
