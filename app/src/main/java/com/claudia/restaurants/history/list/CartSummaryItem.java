package com.claudia.restaurants.history.list;


public class CartSummaryItem {

    public String idCart;
    public boolean cartActive;
    public String createdDate;
    public  String cartDescription;
    public String nrProducts;
    public String imageRestaurant;
    public double totalPrice;

    public CartSummaryItem(String idCart, boolean cartActive, String createdDate, String cartDescription, String nrProducts,
                           String imageRestaurant, double totalPrice) {
        this.idCart = idCart;
        this.cartActive = cartActive;
        this.createdDate = createdDate;
        this.cartDescription = cartDescription;
        this.nrProducts = nrProducts;
        this.imageRestaurant = imageRestaurant;
        this.totalPrice = totalPrice;

    }

    public CartSummaryItem() {
        this.idCart ="";
        this.cartActive = false;
        this.createdDate = "";
        this.cartDescription = "";
        this.nrProducts = "";
        this.imageRestaurant = "";
        this.totalPrice = 0.0;
    }

    @Override
    public String toString() {
        return "RestaurantItem{" +
                "idCart='" + idCart + '\'' +
                ", cartActive=" + cartActive +
                ", createdDate='" + createdDate + '\'' +
                ", cartDescription='" + cartDescription + '\'' +
                ", nrProducts='" + nrProducts + '\'' +
                ", imageRestaurant='" + imageRestaurant + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}