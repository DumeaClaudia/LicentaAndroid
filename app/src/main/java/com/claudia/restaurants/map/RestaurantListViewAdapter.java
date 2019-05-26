package com.claudia.restaurants.map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.claudia.restaurants.R;
import com.claudia.restaurants.history.HistoryActivity;
import com.claudia.restaurants.history.details.CartDetailsActivity;
import com.claudia.restaurants.history.list.CartItemViewHolder;
import com.claudia.restaurants.history.list.CartListServices;
import com.claudia.restaurants.history.list.CartSummaryItem;
import com.claudia.restaurants.server.DownloadImageTask;
import com.claudia.restaurants.server.ServerConfig;

public class RestaurantListViewAdapter extends RecyclerView.Adapter<RestaurantItemViewHolder> {

    private final MapsActivity mParentActivity;
    private final RestaurantListServices restaurantListServices;

    public RestaurantListViewAdapter(
            MapsActivity parent, RestaurantListServices services) {
        mParentActivity = parent;
        restaurantListServices = services;
    }

    @Override
    public RestaurantItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_map_list_item, parent, false);

        return new RestaurantItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RestaurantItemViewHolder holder, int position) {
        final RestaurantMapItem item = restaurantListServices.getRestaurantAtPostion(position);

        holder.restaurantNameTextView.setText(item.getRestaurantName());
        holder.restaurantAddressTextView.setText(item.getRestaurantAddress());
        holder.restaurantPositionTextView.setText(item.getRestaurantPosition().toString() + ".");
        holder.restaurantRouteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + item.getResturantLocation().latitude + ","+ item.getResturantLocation().longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (mapIntent.resolveActivity(mParentActivity.getApplicationContext().getPackageManager()) != null) {
                    mParentActivity.getApplicationContext().startActivity(mapIntent);
                } else {
                    Snackbar.make(v, "Could not open Maps", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.getRestaurantMarker().showInfoWindow();
                mParentActivity.moveMapTo(item.getResturantLocation());
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantListServices.count();
    }


}
