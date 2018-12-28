package com.claudia.restaurants.cart.details;

public class ProductDetailsItem {

    public String image;
    public String name;
    public String category;
    public String description;
    public double price;
    public int discount;

    public ProductDetailsItem(String image, String name, String category, String description, double price, int discount) {
        this.image = image;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.discount = discount;
    }

    public ProductDetailsItem() {
        image = "";
        name = "";
        category = "";
        description = "";
        price = 0;
        discount = 0;
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
        return "ProductDetailsItem{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
