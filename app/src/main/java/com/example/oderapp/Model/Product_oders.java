package com.example.oderapp.Model;

public
class Product_oders {
    private int id;
    private String name;
    private int price;
    private String Image;

    @Override
    public String toString() {
        return "Product_oders{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", Image='" + Image + '\'' +
                '}';
    }

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

    public Product_oders(int id, String name, int price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        Image = image;
    }
}
