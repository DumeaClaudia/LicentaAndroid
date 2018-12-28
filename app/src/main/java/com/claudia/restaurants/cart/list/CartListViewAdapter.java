package com.claudia.restaurants.cart.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.claudia.restaurants.MainActivity;
import com.claudia.restaurants.R;

public class CartListViewAdapter   extends RecyclerView.Adapter<CartItemViewHolder> {

    private final MainActivity mParentActivity;
    private final CartListServices cartListServices;

    public CartListViewAdapter(
            MainActivity parent, CartListServices services) {
        mParentActivity = parent;
        cartListServices = services;
    }

    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item, parent, false);

        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartItemViewHolder holder, int position) {
        final CartSummaryItem item = cartListServices.getCartAtPostion(position);

        holder.restaurantTextView.setText(item.cartDescription);
        holder.createdDateTextView.setText(item.createdDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                Intent intent = new Intent(context, CartDetailsActivity.class);
                intent.putExtra(CartDetailsActivity.CART_ID_ARG, item.idCart);

               context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartListServices.count();
    }


}