package com.claudia.restaurants.restaurants.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.claudia.restaurants.MainActivity;
import com.claudia.restaurants.R;
import com.claudia.restaurants.restaurants.details.RestaurantDetailsActivity;
import com.claudia.restaurants.server.DownloadImageTask;
import com.claudia.restaurants.server.ServerConfig;

public class RestaurantListViewAdapter extends RecyclerView.Adapter<RestaurantItemViewHolder> {

    private final MainActivity mParentActivity;
    private final RestaurantListServices restaurantListServices;

    public RestaurantListViewAdapter(
            MainActivity parent, RestaurantListServices services) {
        mParentActivity = parent;
        restaurantListServices = services;
    }

    @Override
    public RestaurantItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_list_item, parent, false);

        return new RestaurantItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RestaurantItemViewHolder holder, int position) {
        final RestaurantItem item = restaurantListServices.getRestaurantAtPosition(position);

        holder.restaurantNameTextView.setText(item.restaurantName);
        holder.restaurantDescriptionTextView.setText(item.restaurantDescription);

        DownloadImageTask downloadImageTask = new DownloadImageTask(holder.restaurantImageView);

        downloadImageTask.execute(ServerConfig.getImageURI(item.restaurantId+"/"+item.restaurantImage));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                Intent intent = new Intent(context, RestaurantDetailsActivity.class);
                intent.putExtra(RestaurantDetailsActivity.RESTAURANT_ID_ARG, item.restaurantId+"");

               context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantListServices.count();
    }


}
