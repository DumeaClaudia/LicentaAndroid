package com.claudia.restaurants.cart;

public class ProductDetailsCartItem {
	
	private String restaurantName;
	private String productName;
	private double price;
	private int nrProducts;

	public ProductDetailsCartItem(String restaurantName, String productName, double price, int nrProducts) {
		this.restaurantName = restaurantName;
		this.productName = productName;
		this.price = price;
		this.nrProducts = nrProducts;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNrProducts() {
		return nrProducts;
	}

	public void setNrProducts(int nrProducts) {
		this.nrProducts = nrProducts;
	}

}
