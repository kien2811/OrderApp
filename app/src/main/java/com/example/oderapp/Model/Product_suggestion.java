package com.example.oderapp.Model;

import java.io.Serializable;

public
class Product_suggestion implements Serializable {
    private int id;
    private String name;
    private String image;
    private int price;
    private String details;
    private int product_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product_suggestion(int id, String name, String image, int price, String details, int product_id, int amount) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.details = details;
        this.product_id = product_id;
        this.amount = amount;
    }

    private int amount;


}
