package com.claudia.restaurants.cart.list;

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

    public CartSummaryItem() {
        this.idCart = "";
        this.cartActive = false;
        this.createdDate = "";
        this.cartDescription = "";
    }

    public String getIdCart() {
        return idCart;
    }

    public void setIdCart(String idCart) {
        this.idCart = idCart;
    }

    public boolean isCartActive() {
        return cartActive;
    }

    public void setCartActive(boolean cartActive) {
        this.cartActive = cartActive;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCartDescription() {
        return cartDescription;
    }

    public void setCartDescription(String cartDescription) {
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
