package com.claudia.restaurants.history.details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.claudia.restaurants.R;
import com.claudia.restaurants.server.DownloadImageTask;
import com.claudia.restaurants.server.ServerConfig;

public class CartDetailsExpandableListViewAdapter extends BaseExpandableListAdapter {

    AppCompatActivity baseActivity;
    Context context;
    CartDetailsItem item;

    public CartDetailsExpandableListViewAdapter(AppCompatActivity baseActivity) {
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.restaurant_group_item, null);
        }
        final RestaurantProductsItem restaurantProductsItem = item.getRestaurantProducts().get(groupPosition);

        final ImageView mapLink  = convertView.findViewById(R.id.map_link);
        mapLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+restaurantProductsItem.getRestaurantGeolocation());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (mapIntent.resolveActivity( baseActivity.getApplicationContext().getPackageManager()) != null) {

                    baseActivity.getApplicationContext().startActivity(mapIntent);
                }
                else {
                    Snackbar.make(mapLink,  "Could not open Maps", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });


        final ImageView shareRestaurant  = convertView.findViewById(R.id.share_restaurant);
        shareRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                //share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                //share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                share.putExtra(Intent.EXTRA_TEXT, "Eating at : \nhttp://192.168.43.12:8080/ui/pages/shareRestaurant.xhtml?id="+restaurantProductsItem.getRestaurantId()+"\n");

                share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               // baseActivity.getApplicationContext().startActivity(Intent.createChooser(share, "Share"));
                baseActivity.getApplicationContext().startActivity(share);
            }
        });


        TextView listTitleTextView = convertView.findViewById(R.id.restaurantName_textView);
        listTitleTextView.setText(restaurantProductsItem.getRestaurantName());

        ImageView imageViewRestaurant = convertView.findViewById(R.id.restaurant_imageView);
        DownloadImageTask downloadImageTask = new DownloadImageTask(imageViewRestaurant);
        downloadImageTask.execute(ServerConfig.getImageURI( restaurantProductsItem.getRestaurantId() + "/" + restaurantProductsItem.getRestaurantImage()));

        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.product_list_item, null);
        }
        final ProductDetailsItem product = item.getRestaurantProducts().get(groupPosition).getProducts().get(childPosition);

        final ImageView shareProduct  = convertView.findViewById(R.id.share_product);
        shareProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, "Eating... : \nhttp://192.168.43.12:8080/ui/pages/shareProduct.xhtml?id="+product.getIdProduct()+"\n");
                share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                baseActivity.getApplicationContext().startActivity(share);
            }
        });


        TextView listTitleTextView = convertView
                .findViewById(R.id.product_textView);

        String restaurantId = product.getIdRestaurant()+"";
        listTitleTextView.setText(product.getName());



        TextView priceTextView = convertView
                .findViewById(R.id.price_textView);
        priceTextView.setText(String.format("%.2f", product.getPrice()) + " RON");

        ImageView imageViewProduct = convertView.findViewById(R.id.product_imageView);
        DownloadImageTask downloadImageTask = new DownloadImageTask(imageViewProduct);

        if (!product.getImage().equals("null") && !product.getImage().isEmpty()) {
            downloadImageTask.execute(ServerConfig.getImageURI(restaurantId + '/' + product.getImage()));
            imageViewProduct.setVisibility(ImageView.VISIBLE);
        } else {
            imageViewProduct.setVisibility(ImageView.GONE);
           // downloadImageTask.execute(ServerConfig.getImageURI("grey.jpg"));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
