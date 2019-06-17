package com.claudia.restaurants.map;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.claudia.restaurants.R;
import com.claudia.restaurants.cart.ProductDetailsCartItem;
import com.claudia.restaurants.cart.UserProductsItem;
import com.claudia.restaurants.server.DownloadCurrentCart;
import com.claudia.restaurants.server.ServerConfig;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap googleMap;
    SupportMapFragment mapFragment;
    private List<UserProductsItem> userProductsItemList;
    Location current_location;
    Marker current_position_marker = null;
    RestaurantListServices restaurantListServices;
    RestaurantListViewAdapter restaurantListViewAdapter;

    private FusedLocationProviderClient fusedLocationClient;

    //private Polyline mMutablePolyline;

    FrameLayout frame1;
    FrameLayout frame2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        new MapsActivity.DownloadCartsUpdateMapTask(this).execute(ServerConfig.getServletURL("get_current_cart", ""));

        restaurantListServices = new RestaurantListServices();
        restaurantListViewAdapter = new RestaurantListViewAdapter(this, restaurantListServices);
        RecyclerView restaurantsRecyclerView = findViewById(R.id.restaurantList_recyclerView);
        restaurantsRecyclerView.setAdapter(restaurantListViewAdapter);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        frame1 = findViewById(R.id.frame1);
        frame2 = findViewById(R.id.frame2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.delivery_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_zoom) {
            RecyclerView restaurantsRecyclerView = findViewById(R.id.restaurantList_recyclerView);

            if (item.isChecked()) {
                restaurantsRecyclerView.setVisibility(View.VISIBLE);
                item.setChecked(false);
            } else {
                restaurantsRecyclerView.setVisibility(View.GONE);
                item.setChecked(true);

            }

        }

        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_delivery:
                    frame1.setVisibility(FrameLayout.VISIBLE);
                    frame2.setVisibility(FrameLayout.INVISIBLE);
                    return true;
                case R.id.navigation_map:
                    frame1.setVisibility(FrameLayout.INVISIBLE);
                    frame2.setVisibility(FrameLayout.VISIBLE);
                    return true;

            }
            return false;
        }
    };

    void addMap() {

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


      /*  PolylineOptions polylineOptions = new PolylineOptions()
                .color(Color.BLUE)
                .width(2);
*/

        Map<String, ProductDetailsCartItem> restaurantGeolocations = new HashMap<>();
        for (UserProductsItem userProductsItem : userProductsItemList) {
            for (ProductDetailsCartItem productDetailsCartItem : userProductsItem.getCartDetails()) {
                ProductDetailsCartItem restaurantDetails = new ProductDetailsCartItem(productDetailsCartItem.getRestaurantGeolocation(), productDetailsCartItem.getRestaurantAddress());
                restaurantGeolocations.put(productDetailsCartItem.getRestaurantName(), restaurantDetails);

            }
        }
        restaurantListServices.removeElements();
        LatLng position = new LatLng(0, 0);
        String address = "";
        int restaurantPosition = 0;
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
                // polylineOptions.add(position);
                RestaurantMapItem restaurantMapItem = new RestaurantMapItem(restaurantDetails.getKey(), restaurantDetails.getValue().getRestaurantAddress(), ++restaurantPosition, position, marker);
                restaurantListServices.addRestaurant(restaurantMapItem);

            }
        }

        UiSettings settings = googleMap.getUiSettings();
        settings.setMapToolbarEnabled(true);
        settings.setMyLocationButtonEnabled(true);
        settings.setCompassEnabled(true);
        googleMap.setContentDescription("restaurants");
        googleMap.setTrafficEnabled(true);


        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 12));

        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMyLocationClickListener(this);

        checkPermissions();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);

        restaurantListViewAdapter.notifyDataSetChanged();
        /*if (mMutablePolyline != null) {
            mMutablePolyline.remove();
        }
        mMutablePolyline = googleMap.addPolyline(polylineOptions);
*/
    }

    public void moveMapTo(LatLng position) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 12));
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


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (checkPermissions()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            current_location = location;
                            markCurrentLocation(location);

                        }
                    });
        }


    }

    @Override
    public void onMyLocationClick(Location location) {
        markCurrentLocation(location);
    }

    private void markCurrentLocation(Location location) {
        if (location != null) {

            LatLng position;
            current_location = location;


            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            position = new LatLng(latitude, longitude);
            if (current_position_marker == null) {
                MarkerOptions mo = new MarkerOptions().position(position).title("My location");
                mo.visible(true);
                //  mo.flat(true);
                //mo.snippet("my location");

                mo.icon(BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_AZURE));
                googleMap.addMarker(mo);
            } else {
                current_position_marker.setPosition(position);
            }
            // marker.showInfoWindow();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 12));
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        markCurrentLocation(current_location);
        return false;
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
