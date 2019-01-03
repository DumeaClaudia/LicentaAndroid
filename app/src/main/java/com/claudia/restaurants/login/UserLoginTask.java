package com.claudia.restaurants.login;

import android.os.AsyncTask;

public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

    public final String username;
    public final String password;

    UserLoginTask(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    @Override
    protected Boolean doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return false;
        }

        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(username)) {
                // Account exists, return true if the password matches.
                return pieces[1].equals(password);
            }
        }

        // TODO: register the new account here.
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
//        mAuthTask = null;
//        showProgress(false);
//
//        if (success) {
//            finish();
//        } else {
//            mPasswordView.setError(getString(R.string.error_incorrect_password));
//            mPasswordView.requestFocus();
//        }
    }

    @Override
    protected void onCancelled() {
//        mAuthTask = null;
//        showProgress(false);
    }
}
