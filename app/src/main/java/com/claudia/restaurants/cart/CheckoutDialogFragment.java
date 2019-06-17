package com.claudia.restaurants.cart;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.claudia.restaurants.R;
import com.claudia.restaurants.map.MapsActivity;
import com.claudia.restaurants.server.ServerConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class CheckoutDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Date livrare");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.checkout_dialog, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //final EditText editText = view.findViewById(R.id.telephone_textView);

        // if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("email")))
        //     editText.setText(getArguments().getString("email"));
        final View dialog = view;
        Button btnDone = view.findViewById(R.id.checkout_button);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {

                EditText telephoneTextView = dialog.findViewById(R.id.telephone_textView);
                EditText addressTextView = dialog.findViewById(R.id.address_textView);
                RadioGroup paymentRadio = dialog.findViewById(R.id.payment_radioGroup);

                String telephone = telephoneTextView.getText().toString();
                String address = addressTextView.getText().toString();
                int paymentId = paymentRadio.getCheckedRadioButtonId();
                RadioButton payment = dialog.findViewById(paymentId);
                String paymentValue = payment.getText().toString();

                if (telephone.isEmpty() || address.isEmpty() || paymentValue.isEmpty()) {
                    Toast t = Toast.makeText(CheckoutDialogFragment.this.getContext(), "Toate câmpurile sunt obligatorii!", Toast.LENGTH_LONG);
                    t.show();

                } else {
                      CheckoutDetails details = new CheckoutDetails(telephone, address, paymentValue);
                       new CheckoutDialogFragment.CheckoutTask(CheckoutDialogFragment.this, details).execute(ServerConfig.getServletURL("checkout_cart", ""));
                    //Intent intent = new Intent(CheckoutDialogFragment.this.getContext(), MapsActivity.class);
                   // intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    //CheckoutDialogFragment.this.getContext().startActivity(intent);

                }

                //dismiss();
            }
        });
    }

    class CheckoutTask extends AsyncTask<String, Void, String> {

        private CheckoutDetails details;
        CheckoutDialogFragment dialog;
        long cartId = 0;

        public CheckoutTask(CheckoutDialogFragment dialog, CheckoutDetails details) {
            this.dialog = dialog;
            this.details = details;
        }

        protected String doInBackground(String... urls) {

            String urldisplay = urls[0];

            HttpURLConnection conn;
            try {
                conn = (HttpURLConnection) new URL(urldisplay).openConnection();
                conn.setReadTimeout(60000 /* milliseconds */);
                conn.setConnectTimeout(65000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                //conn.connect();
                JSONObject json = new JSONObject();
                try {
                    json.put("telephone", details.getTelephone());
                    json.put("address", details.getAddress());
                    json.put("payment", details.getPayment());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                outputStream.write(json.toString());
                outputStream.flush();

                int response = conn.getResponseCode();
                Log.d("CLAU_LOG", "The response is: " + response);

                BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String resp = "";
                String line = bufferedInputStream.readLine();
                while (line != null) {
                    resp = resp + line;
                    line = bufferedInputStream.readLine();
                }
                 cartId = Long.parseLong(resp.replace('\"', ' ').trim());


            } catch (IOException e) {
                e.printStackTrace();
            }


            return "";
        }

        protected void onPostExecute(String s) {
            dialog.dismiss();
                   Intent intent = new Intent(CheckoutDialogFragment.this.getContext(), MapsActivity.class);
                   intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                   intent.putExtra(MapsActivity.CART_ID_ARG, cartId);
            CheckoutDialogFragment.this.getContext().startActivity(intent);
        }
    }

}