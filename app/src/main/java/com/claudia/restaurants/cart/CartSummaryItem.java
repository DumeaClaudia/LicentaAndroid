package com.claudia.restaurants.cart;

public class CartSummaryItem {

    public String idCart;
    public boolean cartActive;
    public String createdDate;
    public String cartDescription;

    public CartSummaryItem(String idCart, boolean cartActive, String createdDate, String cartDescription) {
        this.idCart = idCart;
        this.cartActive = cartActive;
        this.createdDate = createdDate;
        this.cartDescription = cartDescription;
    }

    @Override
    public String toString() {
        return "CartSummaryItem{" +
                "idCart='" + idCart + '\'' +
                ", cartActive=" + cartActive +
                ", createdDate='" + createdDate + '\'' +
                ", cartDescription='" + cartDescription + '\'' +
                '}';
    }
}
