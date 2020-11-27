package com.example.oderapp.Model;

import java.io.Serializable;

public class DashboardSanPham implements Serializable {
    private int id;
    private String name;
    private int price;
    private String avatar;
    private String description;
    private int categoryid;

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

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public DashboardSanPham(int id, String name, int price, String avatar, String description, int categoryid, int amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.avatar = avatar;
        this.description = description;
        this.categoryid = categoryid;
        this.amount = amount;
    }

    private int amount;

}
