package com.claudia.restaurants.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.claudia.restaurants.MainActivity;
import com.claudia.restaurants.R;
import com.claudia.restaurants.server.ServerConfig;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class UserLoginTask extends AsyncTask<String, Void, Boolean> {
    LoginActivity loginActivity;
    String username;  String password;


    public UserLoginTask(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        CookieHandler.setDefault(ServerConfig.CookieManager);


        username = params[0];

        password = params[1];

        // String text = String.format(getResources().getString(R.string.userLogged), username);

        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) new URL(ServerConfig.getNotAuthenticatedServletURL
                    ("login", "username=" + username + "&pwd=" + password)).openConnection();

            conn.setReadTimeout(60000 /* milliseconds */);
            conn.setConnectTimeout(65000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            int response = conn.getResponseCode();
            Log.d("CLAU_LOG", "The response is: " + response);

            if (response == 401) {
                return false;
            } else {

           List<HttpCookie> cookies = ServerConfig.CookieManager.getCookieStore().getCookies();
                Log.d("CLAU_LOG", "Cookies" + cookies.size());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {

        if (success) {
            Context context = loginActivity.getApplicationContext();

            SharedPreferences sharedPref = loginActivity.getSharedPreferences(
                    loginActivity.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(loginActivity.getString(R.string.preference_saved_username), username);
            editor.putString(loginActivity.getString(R.string.preference_saved_password), password);


            editor.apply();


            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            loginActivity.finish();
        } else {


            loginActivity.passwordView.setError(loginActivity.getString(R.string.error_incorrect_password));
            // nu e bine asa din motive de securitate... ar trebui sa zic ca userul sau parola nu sunt corecte, si sa le resetez pe amandoua

            loginActivity.passwordView.requestFocus();
        }
    }

    @Override
    protected void onCancelled() {
    }
}
