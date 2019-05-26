package com.claudia.restaurants.map;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class RestaurantMapItem {
    private String restaurantName;
    private String restaurantAddress;
    private Integer restaurantPosition;
    private LatLng resturantLocation;
    private Marker restaurantMarker;

    public String getRestaurantName() {
        return restaurantName;
    }

    public RestaurantMapItem(String restaurantName, String restaurantAddress, int restaurantPosition, LatLng resturantLocation, Marker restaurantMarker) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantPosition = restaurantPosition;
        this.resturantLocation = resturantLocation;
        this.restaurantMarker = restaurantMarker;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public Integer getRestaurantPosition() {
        return restaurantPosition;
    }

    public void setRestaurantPosition(int restaurantPosition) {
        this.restaurantPosition = restaurantPosition;
    }

    public LatLng getResturantLocation() {
        return resturantLocation;
    }

    public void setResturantLocation(LatLng resturantLocation) {
        this.resturantLocation = resturantLocation;
    }

    public Marker getRestaurantMarker() {
        return restaurantMarker;
    }

    public void setRestaurantMarker(Marker restaurantMarker) {
        this.restaurantMarker = restaurantMarker;
    }
}
