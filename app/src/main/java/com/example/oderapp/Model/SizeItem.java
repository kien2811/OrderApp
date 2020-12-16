package com.example.oderapp.Model;

public class SizeItem {
    private int id;
    private int id_product;
    private int size;
    private int quantity;

    public SizeItem(int id, int id_product, int size, int quantity) {
        this.id = id;
        this.id_product = id_product;
        this.size = size;
        this.quantity = quantity;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SizeItem{" +
                "id=" + id +
                ", id_product=" + id_product +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
