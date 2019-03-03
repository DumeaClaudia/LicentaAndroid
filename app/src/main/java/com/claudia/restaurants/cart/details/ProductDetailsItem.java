package com.claudia.restaurants.cart.details;

public class ProductDetailsItem {

    public long idProduct;
    public long idRestaurant;
    public String image;
    public String name;
    public String category;
    public String description;
    public double price;
    public int discount;

    public ProductDetailsItem(long idProduct, long idRestaurant, String image, String name, String category, String description, double price, int discount) {
        this.idProduct = idProduct;
        this.idRestaurant = idRestaurant;
        this.image = image;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.discount = discount;
    }

    public ProductDetailsItem() {
        idProduct = 0;
        idRestaurant = 0;
        image = "";
        name = "";
        category = "";
        description = "";
        price = 0;
        discount = 0;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "ProductDetailsItem {" +
                " image = " + image + '\n' +
                " name = " + name + '\n' +
                " category = " + category + '\n' +
                " description = " + description + '\n' +
                " price = " + price + '\n' +
                " discount = " + discount +
                "} \n";
    }
}
