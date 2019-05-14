package com.claudia.restaurants.restaurants.list;

public class RestaurantItem {
    public long restaurantId;
    public String restaurantName;
    public String restaurantImage;
    public String restaurantDescription;

    public RestaurantItem(long restaurantId, String restaurantName, String restaurantImage, String restaurantDescription) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantImage = restaurantImage;
        this.restaurantDescription = restaurantDescription;
    }

    public RestaurantItem() {
        restaurantId = 0;
        restaurantName = "";
        restaurantImage = "";
        restaurantDescription = "";
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public String getRestaurantDescription() {
        return restaurantDescription;
    }

    public void setRestaurantDescription(String restaurantDescription) {
        this.restaurantDescription = restaurantDescription;
    }

    @Override
    public String toString() {

        return "RestaurantProductsItem{" +
                "restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                ", restaurantImage='" + restaurantImage + '\''+
                ", restaurantDescription='" + restaurantDescription + '\'' +
                '}';
    }
}
