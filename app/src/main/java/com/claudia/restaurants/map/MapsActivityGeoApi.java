package com.claudia.restaurants.map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MapsActivityGeoApi extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    SupportMapFragment mapFragment;
    private List<UserProductsItem> userProductsItemList;
    private static final int overview = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        new MapsActivityGeoApi.DownloadCartsUpdateMapTask(this).execute(ServerConfig.getServletURL("get_current_cart", ""));

    }

    void addMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
                mo.flat(true);
                mo.snippet(address);

                Marker marker = googleMap.addMarker(mo);
                marker.showInfoWindow();

            }
        }
        UiSettings settings = googleMap.getUiSettings();
        settings.setMapToolbarEnabled(true);
        settings.setMyLocationButtonEnabled(true);
        settings.setCompassEnabled(true);


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


        setupGoogleMapScreenSettings(googleMap);
        DirectionsResult results = getDirectionsDetails("483 George St, Sydney NSW 2000, Australia", "182 Church St, Parramatta NSW 2150, Australia", TravelMode.DRIVING);
        if (results != null) {
            addPolyline(results, googleMap);
            positionCamera(results.routes[overview], googleMap);
            addMarkersToMap(results, googleMap);
        }
    }


    private DirectionsResult getDirectionsDetails(String origin, String destination, TravelMode mode) {
        DateTime now = new DateTime();
        try {
            return DirectionsApi.newRequest(getGeoContext().setApiKey(getString(R.string.google_maps_key2)))
                    .mode(mode)
                    .origin(origin)
                    .destination(destination)
                    .departureTime(now)
                    .await();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return null;
        } catch (com.google.maps.errors.ApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void setupGoogleMapScreenSettings(GoogleMap mMap) {
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setTrafficEnabled(true);
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
    }

    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(results.routes[overview].legs[overview].startLocation.lat, results.routes[overview].legs[overview].startLocation.lng))
                .title(results.routes[overview].legs[overview].startAddress));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(results.routes[overview].legs[overview].endLocation.lat, results.routes[overview].legs[overview].endLocation.lng))
                .title(results.routes[overview].legs[overview].startAddress)
                .snippet(getEndLocationTitle(results)));
    }

    private void positionCamera(DirectionsRoute route, GoogleMap mMap) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(route.legs[overview].startLocation.lat, route.legs[overview].startLocation.lng), 12));
    }

    private void addPolyline(DirectionsResult results, GoogleMap mMap) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[overview].overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
    }

    private String getEndLocationTitle(DirectionsResult results) {
        return "Time :" + results.routes[overview].legs[overview].duration.humanReadable + " Distance :" + results.routes[overview].legs[overview].distance.humanReadable;
    }

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext
                .setQueryRateLimit(3)
                .setApiKey(getString(R.string.google_maps_key2))
                .setConnectTimeout(5, TimeUnit.SECONDS)
                .setReadTimeout(5,TimeUnit.SECONDS)
                .setWriteTimeout(5, TimeUnit.SECONDS);
    }

    static class DownloadCartsUpdateMapTask extends AsyncTask<String, Void, String> {
        private List<UserProductsItem> userProductsItemList;
        MapsActivityGeoApi mapsActivity;

        public DownloadCartsUpdateMapTask(MapsActivityGeoApi mapsActivity) {
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
