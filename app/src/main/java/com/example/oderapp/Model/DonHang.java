package com.example.oderapp.Model;

public class DonHang {
    private int id;
    private int id_product;
    private String name;
    private int price;
    private String avatar;
    private String description;
    private int quantity;
    private String status;
    private int id_size;
    private int name_size;
    private int product_id;

    public DonHang(int id, int id_product, String name, int price, String avatar, String description, int quantity, String status, int id_size, int name_size, int product_id) {
        this.id = id;
        this.id_product = id_product;
        this.name = name;
        this.price = price;
        this.avatar = avatar;
        this.description = description;
        this.quantity = quantity;
        this.status = status;
        this.id_size = id_size;
        this.name_size = name_size;
        this.product_id = product_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId_size() {
        return id_size;
    }

    public void setId_size(int id_size) {
        this.id_size = id_size;
    }

    public int getName_size() {
        return name_size;
    }

    public void setName_size(int name_size) {
        this.name_size = name_size;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "DonHang{" +
                "id=" + id +
                ", id_product=" + id_product +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", avatar='" + avatar + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                ", id_size=" + id_size +
                ", name_size=" + name_size +
                ", product_id=" + product_id +
                '}';
    }
}
