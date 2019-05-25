package com.claudia.restaurants.cart;

public class ProductDetailsCartItem {
	
	private String restaurantName;
	private String restaurantGeolocation;
	private String restaurantAddress;
	private String productName;
	private double price;
	private int nrProducts;
	private long productId;

	public ProductDetailsCartItem(String restaurantName, String restaurantGeolocation, String restaurantAddress,  long productId, String productName, double price, int nrProducts) {
		this.restaurantName = restaurantName;
		this.restaurantGeolocation = restaurantGeolocation;
		this.restaurantAddress = restaurantAddress;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.nrProducts = nrProducts;
	}

	public ProductDetailsCartItem(String restaurantGeolocation, String restaurantAddress){
		this.restaurantGeolocation = restaurantGeolocation;
		this.restaurantAddress = restaurantAddress;
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


	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getRestaurantGeolocation() {
		return restaurantGeolocation;
	}

	public void setRestaurantGeolocation(String restaurantGeolocation) {
		this.restaurantGeolocation = restaurantGeolocation;
	}

	public String getRestaurantAddress() {
		return restaurantAddress;
	}

	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}
}
