package com.example.oderapp.Model;

import java.io.Serializable;

public
class Product_new implements Serializable {
    private int Id;
    private String Name;
    private String Image;
    private int Price;
    private String Details;
    private int product_id;
    private int amount;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
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

    public Product_new(int id, String name, String image, int price, String details, int product_id, int amount) {
        Id = id;
        Name = name;
        Image = image;
        Price = price;
        Details = details;
        this.product_id = product_id;
        this.amount = amount;
    }


}
