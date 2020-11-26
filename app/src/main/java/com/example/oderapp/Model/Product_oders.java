package com.example.oderapp.Model;

public
class Product_oders {
    private int id;
    private String name;
    private int price;
    private String Image;
    private String details;
    private int product_id;
    private  int amount;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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

    public Product_oders(int id, String name, int price, String image, String details, int product_id, int amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        Image = image;
        this.details = details;
        this.product_id = product_id;
        this.amount = amount;
    }
}
