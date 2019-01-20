package com.claudia.restaurants.cart.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.claudia.restaurants.R;
import com.claudia.restaurants.server.DownloadImageTask;
import com.claudia.restaurants.server.ServerConfig;

public class CartDetailsExpandableListViewAdapter extends BaseExpandableListAdapter {

    CartDetailsActivity baseActivity;
    Context context;
    CartDetailsItem item;

    public CartDetailsExpandableListViewAdapter(CartDetailsActivity baseActivity) {
        this.baseActivity = baseActivity;
        this.context = baseActivity.getApplicationContext();
        this.item = new CartDetailsItem();
    }

    public void setItem(CartDetailsItem item) {
        this.item = item;
        this.notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return item.getRestaurantProducts().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return item.getRestaurantProducts().get(groupPosition).getProducts().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return item.getRestaurantProducts().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return item.getRestaurantProducts().get(groupPosition).getProducts();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.restaurant_group_item, null);
        }

        TextView listTitleTextView = convertView.findViewById(R.id.restaurantName_textView);

        RestaurantProductsItem restaurantItem = item.getRestaurantProducts().get(groupPosition);
        listTitleTextView.setText(restaurantItem.getRestaurantName());


        ImageView imageViewRestaurant = convertView.findViewById(R.id.restaurant_imageView);
        DownloadImageTask downloadImageTask = new DownloadImageTask(imageViewRestaurant);

        downloadImageTask.execute(ServerConfig.getImageURI(restaurantItem.getRestaurantName() + '/' + restaurantItem.getRestaurantImage()));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.product_list_item, null);
        }
        TextView listTitleTextView = convertView
                .findViewById(R.id.product_textView);

        ProductDetailsItem product = item.getRestaurantProducts().get(groupPosition).getProducts().get(childPosition);
        String restaurantName = item.getRestaurantProducts().get(groupPosition).getRestaurantName();

        listTitleTextView.setText(product.getName());

        ImageView imageViewProduct = convertView.findViewById(R.id.product_imageView);
        DownloadImageTask downloadImageTask = new DownloadImageTask(imageViewProduct);

        if (!product.getImage().isEmpty()) {
            downloadImageTask.execute(ServerConfig.getImageURI(restaurantName + '/' + product.getImage()));
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
