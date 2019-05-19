package com.claudia.restaurants.cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.claudia.restaurants.R;

import java.util.ArrayList;
import java.util.List;

public class CurrentCartExpandableListViewAdapter extends BaseExpandableListAdapter {

    AppCompatActivity baseActivity;
    Context context;
    List<UserProductsItem> userProductsItemList;

    public CurrentCartExpandableListViewAdapter(AppCompatActivity baseActivity) {
        this.baseActivity = baseActivity;
        this.context = baseActivity.getApplicationContext();
        this.userProductsItemList = new ArrayList<>();

    }

    public void setList(List<UserProductsItem> item) {
        userProductsItemList = item;
        this.notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return userProductsItemList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return userProductsItemList.get(groupPosition).getCartDetails().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return userProductsItemList.get(groupPosition).getCartDetails();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return userProductsItemList.get(groupPosition).getCartDetails().get(childPosition);
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
            convertView = layoutInflater.inflate(R.layout.cart_user_group_item, null);
        }
        final UserProductsItem userItem = this.userProductsItemList.get(groupPosition);

        final TextView usernameTextView = convertView.findViewById(R.id.username_textView);
        usernameTextView.setText(userItem.getUsername());

        final TextView subTotalTextView = convertView.findViewById(R.id.subtotal_textView);
        subTotalTextView.setText("Subtotal: " + userItem.getTotalPrice() + " RON");
        SharedPreferences sharedPref = baseActivity.getSharedPreferences(baseActivity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String username = sharedPref.getString(baseActivity.getString(R.string.preference_saved_username), "");

        /*
        if (userItem.getUsername().equals(username)) {
            final ExpandableListView mExpandableListView = (ExpandableListView) parent;
            mExpandableListView.expandGroup(groupPosition);
            mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                @Override
                public void onGroupCollapse(int groupPosition) {
                    if (mExpandableListView.isGroupExpanded(groupPosition)) {
                        mExpandableListView.collapseGroup(groupPosition);
                    } else{
                        mExpandableListView.expandGroup(groupPosition);
                    }
                }
            });
        } */

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.cart_user_product_list_item, null);
        }
        final ProductDetailsCartItem product = this.userProductsItemList.get(groupPosition).getCartDetails().get(childPosition);

        TextView productTextView = convertView
                .findViewById(R.id.product_textView);

        productTextView.setText(product.getNrProducts() + "x " + product.getProductName());

        TextView restaurantTextView = convertView
                .findViewById(R.id.restaurantName_textView);

        restaurantTextView.setText(product.getRestaurantName());

        TextView priceTextView = convertView
                .findViewById(R.id.price_textView);
        priceTextView.setText(product.getNrProducts() + "x " + product.getPrice() + " RON");


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
