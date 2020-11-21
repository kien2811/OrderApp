package com.example.oderapp.Model;

public
class Product_hot {
    private int Id;
    private String Name;
    private String Image;


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

    public Product_hot(int id, String name, String image) {
        Id = id;
        Name = name;
        Image = image;
    }
}
