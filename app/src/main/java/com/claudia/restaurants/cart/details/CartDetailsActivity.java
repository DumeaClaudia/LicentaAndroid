package com.claudia.restaurants.cart.details;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.claudia.restaurants.R;
import com.claudia.restaurants.cart.list.CartSummaryItem;
import com.claudia.restaurants.server.DownloadCartDetails;
import com.claudia.restaurants.server.DownloadImageTask;
import com.claudia.restaurants.server.ServerConfig;

public  class CartDetailsActivity extends AppCompatActivity {
    public static final String CART_ID_ARG = "CART_ID";
    String cartId;
    public CartDetailsExpandableListViewAdapter cartDetailsExpandableListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cartId = getIntent().getStringExtra(CART_ID_ARG);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        new DownloadCartsUpdateCartTask(this, cartId).execute(ServerConfig.getServletURL("get_cart_details", "cartId="+cartId));
        ExpandableListView expandableListView = findViewById(R.id.cart_expandableListView);
        cartDetailsExpandableListViewAdapter = new CartDetailsExpandableListViewAdapter(this);
        expandableListView.setAdapter(cartDetailsExpandableListViewAdapter);

        DownloadImageTask.init_cache();

    }

    public void setCart(CartSummaryItem cartSummaryItem) {
        getSupportActionBar().setTitle(cartSummaryItem.idCart);
        TextView textView = findViewById(R.id.restaurantName_textView);
        textView.setText(cartSummaryItem.cartDescription);
       // new DownloadImageTask((ImageView) findViewById(R.id.restaurant_image_view)).execute(ServerConfig.getImageURI(cartSummaryItem.cartDescription));

    }

    class DownloadCartsUpdateCartTask extends AsyncTask<String, Void, String> {
        private CartDetailsActivity cartDetailsActivity;
        private String idCart;
        private CartDetailsItem cartDetailsItem;

        public DownloadCartsUpdateCartTask(CartDetailsActivity cartDetailsActivity, String idCart) {
            this.cartDetailsActivity = cartDetailsActivity;
            this.idCart = idCart;
        }

        protected String doInBackground(String... urls) {
            cartDetailsItem = new DownloadCartDetails(urls[0], idCart).invoke();
            return "";
        }

        protected void onPostExecute(String s) {
            cartDetailsExpandableListViewAdapter.setItem(cartDetailsItem);
        }

    }
}
