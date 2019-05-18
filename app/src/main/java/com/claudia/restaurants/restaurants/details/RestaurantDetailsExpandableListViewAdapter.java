package com.claudia.restaurants.restaurants.details;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.claudia.restaurants.R;
import com.claudia.restaurants.server.DownloadImageTask;
import com.claudia.restaurants.server.ServerConfig;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestaurantDetailsExpandableListViewAdapter extends BaseExpandableListAdapter {

    AppCompatActivity baseActivity;
    Context context;
    RestaurantProductsItem item;

    public RestaurantDetailsExpandableListViewAdapter(AppCompatActivity baseActivity) {
        this.baseActivity = baseActivity;
        this.context = baseActivity.getApplicationContext();
        this.item = null;
       // baseActivity.get

    }

    public void setItem(RestaurantProductsItem item) {
        this.item = item;
        this.notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
//        if (item !=null && item.getProducts()!=null && item.getProducts().size() != 0) {
//            return (item.getProducts()).size();
//        }
        if (item != null) {
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return item.getProducts().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return item.getProducts();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return item.getProducts().get(childPosition);
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
            convertView = layoutInflater.inflate(R.layout.restaurant_details_item, null);
        }
        final RestaurantProductsItem restaurantProductsItem = item;

        TextView listTitleTextView = convertView.findViewById(R.id.restaurantName_textView);
        listTitleTextView.setText(restaurantProductsItem.getRestaurantName());

        ImageView imageViewRestaurant = convertView.findViewById(R.id.restaurant_imageView);
        DownloadImageTask downloadImageTask = new DownloadImageTask(imageViewRestaurant);
        downloadImageTask.execute(ServerConfig.getImageURI(restaurantProductsItem.getRestaurantId() + "/" + restaurantProductsItem.getRestaurantImage()));

        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.restaurant_product_item, null);
        }
        final ProductItem product = item.getProducts().get(childPosition);

        TextView listTitleTextView = convertView
                .findViewById(R.id.product_textView);

        String restaurantId = product.getIdRestaurant() + "";
        listTitleTextView.setText(product.getName());

        TextView priceTextView = convertView
                .findViewById(R.id.price_textView);
        priceTextView.setText(product.getPrice() + " RON");

        ImageView imageViewProduct = convertView.findViewById(R.id.product_imageView);
        DownloadImageTask downloadImageTask = new DownloadImageTask(imageViewProduct);

        if (!product.getImage().equals("null") && !product.getImage().isEmpty()) {
            downloadImageTask.execute(ServerConfig.getImageURI(restaurantId + '/' + product.getImage()));
            imageViewProduct.setVisibility(ImageView.VISIBLE);
        } else {
            imageViewProduct.setVisibility(ImageView.GONE);
        }


        final ImageView addProduct = convertView.findViewById(R.id.addProduct_imageView);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (product.idProduct != 0) {
                    /*
                    Context context = view.getContext();

                    Intent intent = new Intent(context, CartActivity.class);
                    intent.putExtra(CartActivity.PRODUCT_ID_ARG, product.idProduct+"");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent); */
                    new RestaurantDetailsExpandableListViewAdapter.UploadNewProductTask(RestaurantDetailsExpandableListViewAdapter.this.context, product.idProduct +"")
                            .execute(ServerConfig.getServletURL("add_new_product", "productId=" + product.idProduct), "", "");
                }

            }
        });

        return  convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public class UploadNewProductTask extends AsyncTask<String, Void, String> {
        String productId;
        Context context;

        public UploadNewProductTask(Context context, String productId) {
            this.productId = productId;
            this.context = context;
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
            CharSequence text = "Produsul a fost adaugat!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

}
