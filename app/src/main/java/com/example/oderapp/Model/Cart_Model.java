package com.example.oderapp.Model;

public class Cart_Model {
    private int id;
    private String name;
    private int price;
    private String avatar;
    private String description;
    private int quantity;
    private int categoryid;

    public Cart_Model(int id, String name, int price, String avatar, String description, int quantity, int categoryid) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.avatar = avatar;
        this.description = description;
        this.quantity = quantity;
        this.categoryid = categoryid;
    }

    public Cart_Model() {

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    @Override
    public String toString() {
        return "Cart_Model{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", avatar='" + avatar + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", categoryid=" + categoryid +
                '}';
    }
}
