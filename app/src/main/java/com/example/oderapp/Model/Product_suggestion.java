package com.example.oderapp.Model;

public
class Product_suggestion {
    private  int id;
    private String name;
    private String image;
    private int Price;

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
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public Product_suggestion(int id, String name, String image, int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        Price = price;
    }
}
