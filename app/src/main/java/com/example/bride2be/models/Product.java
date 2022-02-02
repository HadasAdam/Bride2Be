package com.example.bride2be.models;

import android.graphics.Picture;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Product implements Serializable {
    final public static String COLLECTION_NAME = "products";
    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String description;
    private Double price;
    private Picture picture;
    private User uploader;

    public Product(String title, String description, Double price, Picture picture, User uploader){
        this.title = title;
        this.description = description;
        this.price = price;
        this.picture = picture;
        this.uploader = uploader;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }
}
