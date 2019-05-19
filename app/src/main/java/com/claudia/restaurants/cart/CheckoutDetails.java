package com.claudia.restaurants.cart;

public class CheckoutDetails {
    String telephone;
    String address;
    String payment;

    public CheckoutDetails(String telephone, String address, String payment) {
        this.telephone = telephone;
        this.address = address;
        this.payment = payment;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "{" +
                "\"telephone\":\"" + telephone + '\"' +
                ", \"address\":\"" + address + '\"' +
                ", \"payment\":\"" + payment + '\"' +
                '}';
    }
}
