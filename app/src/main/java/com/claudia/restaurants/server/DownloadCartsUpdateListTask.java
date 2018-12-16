package com.claudia.restaurants.server;

import android.os.AsyncTask;
import android.util.Log;

import com.claudia.restaurants.cart.CartItem;
import com.claudia.restaurants.cart.CartListViewAdapter;
import com.claudia.restaurants.cart.CartServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

