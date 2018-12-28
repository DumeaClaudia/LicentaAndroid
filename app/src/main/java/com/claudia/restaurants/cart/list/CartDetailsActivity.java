package com.claudia.restaurants.cart.list;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.claudia.restaurants.R;
import com.claudia.restaurants.server.DownloadCartList;
import com.claudia.restaurants.server.DownloadImageTask;
import com.claudia.restaurants.server.ServerConfig;

public class CartDetailsActivity extends AppCompatActivity {
    public static final String CART_ID_ARG = "CART_ID";
    String cartId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cartId = getIntent().getStringExtra(CART_ID_ARG);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        new DownloadCartsUpdateCartTask(this, new CartListServices(), cartId).execute(ServerConfig.getServletURL("get_cart_list"));


    }

    public void setCart(CartSummaryItem cartSummaryItem) {
        getSupportActionBar().setTitle(cartSummaryItem.idCart);
        TextView textView = findViewById(R.id.restaurantName_textView);
        textView.setText(cartSummaryItem.cartDescription);
        new DownloadImageTask((ImageView) findViewById(R.id.restaurant_image_view)).execute(ServerConfig.getImageURL(cartSummaryItem.cartDescription));

    }

    class DownloadCartsUpdateCartTask extends AsyncTask<String, Void, String> {
        private CartDetailsActivity cartDetailsActivity;
        private CartListServices cartListServices;
        private String idCart;

        public DownloadCartsUpdateCartTask(CartDetailsActivity cartDetailsActivity, CartListServices cartListServices, String idCart) {
            this.cartDetailsActivity = cartDetailsActivity;
            this.cartListServices = cartListServices;
            this.idCart = idCart;
        }

        protected String doInBackground(String... urls) {
            new DownloadCartList(urls[0], cartListServices).invoke();
            return "";
        }

        protected void onPostExecute(String s) {
            cartDetailsActivity.setCart(cartListServices.getCartById(idCart));


        }

    }
}
