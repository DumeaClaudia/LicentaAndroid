package com.claudia.restaurants.cart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.claudia.restaurants.MainActivity;
import com.claudia.restaurants.R;

import java.util.List;

public class CartListViewAdapter   extends RecyclerView.Adapter<CartItemViewHolder> {

    private final MainActivity mParentActivity;
    private final CartServices cartServices;

    public CartListViewAdapter(
            MainActivity parent, CartServices services) {
        mParentActivity = parent;
        cartServices = services;
    }

    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item, parent, false);

        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartItemViewHolder holder, int position) {
        final CartItem item = cartServices.getCartAtPostion(position);
        holder.restaurantTextView.setText(item.restaurantName);
        holder.createdDateTextView.setText(item.createdDate);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

               // Intent intent = new Intent(context, PictureItemDetailActivity.class);
               // intent.putExtra(PictureItemDetailActivity.ARG_ITEM_ID, item.id);

               // context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartServices.count();
    }


}
