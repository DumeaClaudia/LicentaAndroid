package com.claudia.restaurants.cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.claudia.restaurants.R;
import com.claudia.restaurants.server.ServerConfig;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CurrentCartExpandableListViewAdapter extends BaseExpandableListAdapter {

    CartActivity baseActivity;
    Context context;
    List<UserProductsItem> userProductsItemList;
    String username = "";
    public CurrentCartExpandableListViewAdapter(CartActivity baseActivity) {
        this.baseActivity = baseActivity;
        this.context = baseActivity.getApplicationContext();
        this.userProductsItemList = new ArrayList<>();

    }

    public void setList(List<UserProductsItem> item) {
        userProductsItemList = item;
        this.notifyDataSetChanged();
        SharedPreferences sharedPref = baseActivity.getSharedPreferences(baseActivity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        username = sharedPref.getString(baseActivity.getString(R.string.preference_saved_username), "");
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
        subTotalTextView.setText("Subtotal: " + String.format("%.2f", userItem.getTotalPrice()) + " RON");


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

        restaurantTextView.setText("(Restaurant: " + product.getRestaurantName() + ")");

        TextView priceTextView = convertView
                .findViewById(R.id.price_textView); // String.format("%.2f", price)
        priceTextView.setText("Pret: " + product.getNrProducts() + "x " + String.format("%.2f", product.getPrice()) + " RON");


        final ImageButton removeProduct = convertView.findViewById(R.id.removeProduct_imageButton);

        if (!this.username.equals(this.userProductsItemList.get(groupPosition).getUsername())) {
            removeProduct.setVisibility(ImageButton.INVISIBLE);
        }else{
            removeProduct.setVisibility(ImageButton.VISIBLE);
        }
        removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (product.getProductId() != 0) {
                    new CurrentCartExpandableListViewAdapter.UploadOldProductTask(CurrentCartExpandableListViewAdapter.this.context, baseActivity,product.getProductId() + "")
                            .execute(ServerConfig.getServletURL("remove_product", "productId=" + product.getProductId()), "", "");

                }

            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public class UploadOldProductTask extends AsyncTask<String, Void, String> {
        String productId;
        Context context;
        CartActivity baseActivity;

        public UploadOldProductTask(Context context, CartActivity baseActivity, String productId) {
            this.productId = productId;
            this.context = context;
            this.baseActivity = baseActivity;
        }

        protected String doInBackground(String... urls) {

            String urldisplay = urls[0];

            HttpURLConnection conn;
            try {
                conn = (HttpURLConnection) new URL(urldisplay).openConnection();
                conn.setReadTimeout(60000 /* milliseconds */);
                conn.setConnectTimeout(65000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                outputStream.write(productId);
                outputStream.flush();

                int response = conn.getResponseCode();
                Log.d("CLAU_LOG", "The response is: " + response);


            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }

        protected void onPostExecute(String s) {
            CharSequence text = "Produsul a fost eliminat!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            baseActivity.updateCart();
        }
    }
}
