package com.claudia.restaurants.cart;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.claudia.restaurants.R;
import com.claudia.restaurants.comment.CommentActivity;
import com.claudia.restaurants.server.DownloadCurrentCart;
import com.claudia.restaurants.server.ServerConfig;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class CartActivity extends AppCompatActivity {
    public CurrentCartExpandableListViewAdapter currentCartExpandableListViewAdapter;
    public static final String PRODUCT_ID_ARG = "PRODUCT_ID_ARG";
    String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);


            }
        }); */

        productId = getIntent().getStringExtra(PRODUCT_ID_ARG);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        ExpandableListView expandableListView = findViewById(R.id.cart_expandableListView);
        currentCartExpandableListViewAdapter = new CurrentCartExpandableListViewAdapter(this);
        expandableListView.setAdapter(currentCartExpandableListViewAdapter);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateCart();
                handler.postDelayed(this, 10000);
            }
        });

        Button button = this.findViewById(R.id.sendCommand_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckoutDialogFragment dialogFragment = new CheckoutDialogFragment();

                Bundle bundle = new Bundle();
                //bundle.putString("Telefon", "");
                dialogFragment.setArguments(bundle);


                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);


                dialogFragment.show(ft, "dialog");
            }
        });
    }
    public void updateCart(){
        new CartActivity.DownloadCartsUpdateCartTask(CartActivity.this).execute(ServerConfig.getServletURL("get_current_cart", ""));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.comments_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_comments) {
            Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class DownloadCartsUpdateCartTask extends AsyncTask<String, Void, String> {
        private CartActivity cartDetailsActivity;
        private List<UserProductsItem> userProductsItemList;

        public DownloadCartsUpdateCartTask(CartActivity cartDetailsActivity) {
            this.cartDetailsActivity = cartDetailsActivity;

        }

        protected String doInBackground(String... urls) {
            userProductsItemList = new DownloadCurrentCart(urls[0]).invoke();
            return "";
        }

        protected void onPostExecute(String s) {
            currentCartExpandableListViewAdapter.setList(userProductsItemList);
            double total = 0;
            for (UserProductsItem p : userProductsItemList) {
                total += p.getTotalPrice();
            }
            TextView totalPrice = cartDetailsActivity.findViewById(R.id.total_textView);
            totalPrice.setText("Total: " + String.format("%.2f", total) + " RON");

            Button sendButton = cartDetailsActivity.findViewById(R.id.sendCommand_button);
            if(total==0.0){
                sendButton.setEnabled(false);
            }else{
                sendButton.setEnabled(true);
            }

        }




    }

}
