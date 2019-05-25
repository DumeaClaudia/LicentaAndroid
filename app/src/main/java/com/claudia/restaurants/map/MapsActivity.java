package com.claudia.restaurants.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.claudia.restaurants.R;
import com.claudia.restaurants.cart.ProductDetailsCartItem;
import com.claudia.restaurants.cart.UserProductsItem;
import com.claudia.restaurants.server.DownloadCurrentCart;
import com.claudia.restaurants.server.ServerConfig;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationClickListener {

    private GoogleMap googleMap;
    SupportMapFragment mapFragment;
    private List<UserProductsItem> userProductsItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        new MapsActivity.DownloadCartsUpdateMapTask(this).execute(ServerConfig.getServletURL("get_current_cart", ""));


    }

    void addMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private static final int REQUEST_FINE_LOCATION = 1;

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION);
            return false;
        }
    }

    void addMarkers() {
        Map<String, ProductDetailsCartItem> restaurantGeolocations = new HashMap<>();
        for (UserProductsItem userProductsItem : userProductsItemList) {
            for (ProductDetailsCartItem productDetailsCartItem : userProductsItem.getCartDetails()) {
                ProductDetailsCartItem restaurantDetails = new ProductDetailsCartItem(productDetailsCartItem.getRestaurantGeolocation(), productDetailsCartItem.getRestaurantAddress());
                restaurantGeolocations.put(productDetailsCartItem.getRestaurantName(), restaurantDetails);

            }
        }
        LatLng position = new LatLng(0, 0);
        String address = "";
        for (HashMap.Entry<String, ProductDetailsCartItem> restaurantDetails : restaurantGeolocations.entrySet()) {
            try {
                String geolocation = restaurantDetails.getValue().getRestaurantGeolocation();
                address = restaurantDetails.getValue().getRestaurantAddress();
                String[] lat_long = geolocation.split(",");

                double lat = Double.parseDouble(lat_long[0]);
                double lng = Double.parseDouble(lat_long[1]);
                position = new LatLng(lat, lng);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } finally {
                MarkerOptions mo = new MarkerOptions().position(position).title(restaurantDetails.getKey());
                mo.visible(true);
               // mo.flat(true);
                mo.snippet(address);

                Marker marker = googleMap.addMarker(mo);
                marker.showInfoWindow();

            }
        }
        UiSettings settings = googleMap.getUiSettings();
        settings.setMapToolbarEnabled(true);
        settings.setMyLocationButtonEnabled(true);
        settings.setCompassEnabled(true);


        try {
            //LocationManager locationManager = (LocationManager)
            //        getSystemService(Context.LOCATION_SERVICE);
            //Criteria criteria = new Criteria();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                checkPermissions();
            }
            //Location location = locationManager.getLastKnownLocation(locationManager
            //        .getBestProvider(criteria, true));
            //Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            googleMap.setMyLocationEnabled(true);

            Location location = googleMap.getMyLocation();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            position = new LatLng(latitude, longitude);
            MarkerOptions mo = new MarkerOptions().position(position).title("My location");
            mo.visible(true);
           // mo.flat(true);
           // mo.snippet("my location");

            mo.icon(BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_AZURE));
            Marker marker = googleMap.addMarker(mo);
            marker.showInfoWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 12));

        //googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMyLocationClickListener(this);
    }



    private void setProducts(List<UserProductsItem> userProductsItemList) {
        this.userProductsItemList = userProductsItemList;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        this.googleMap = googleMap;
        this.addMarkers();


    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        LatLng position;

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        position = new LatLng(latitude, longitude);
        MarkerOptions mo = new MarkerOptions().position(position).title("My location");
        mo.visible(true);
      //  mo.flat(true);
        mo.snippet("my location");

        mo.icon(BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_AZURE));
        Marker marker = googleMap.addMarker(mo);
        marker.showInfoWindow();
    }


    static class DownloadCartsUpdateMapTask extends AsyncTask<String, Void, String> {
        private List<UserProductsItem> userProductsItemList;
        MapsActivity mapsActivity;

        public DownloadCartsUpdateMapTask(MapsActivity mapsActivity) {
            this.mapsActivity = mapsActivity;

        }

        protected String doInBackground(String... urls) {
            userProductsItemList = new DownloadCurrentCart(urls[0]).invoke();
            return "";
        }

        protected void onPostExecute(String s) {
            mapsActivity.setProducts(this.userProductsItemList);
            mapsActivity.addMap();
        }
    }

}
