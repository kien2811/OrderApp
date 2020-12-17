package com.example.oderapp.Model;

public class Message {
    private String id;
    private String img;
    private boolean from_id_user;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isFrom_id_user() {
        return from_id_user;
    }

    public void setFrom_id_user(boolean from_id_user) {
        this.from_id_user = from_id_user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message(String id, String img, boolean from_id_user, String message) {
        this.id = id;
        this.img = img;
        this.from_id_user = from_id_user;
        this.message = message;
    }
}
