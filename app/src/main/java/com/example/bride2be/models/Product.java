package com.example.bride2be.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.type.DateTime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Product implements Serializable {
    final public static String COLLECTION_NAME = "Products_new";
    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String description;
    private Double price;
    private String picture;
    private String uploaderId;
    private Long updateDate;


    public Product()
    {
        this.id = IdGenerator.instance.getProductId();
    }

    public Product(String title, String description, Double price, String picture, String uploaderId){
        this.id = IdGenerator.instance.getProductId();
        this.title = title;
        this.description = description;
        this.price = price;
        this.picture = picture;
        this.uploaderId = uploaderId;
        this.updateDate = (long)DateTime.getDefaultInstance().getSeconds();
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("id", id);
        json.put("title", title);
        json.put("description", description);
        json.put("price", price);
        json.put("picture", picture);
        json.put("uploaderId", uploaderId);
        json.put("updateDate", updateDate);
        return json;
    }

    public static Product create(Map<String, Object> json) {
        String id = (String) json.get("id");
        String title = (String) json.get("title");
        String description = (String) json.get("description");
        String price = Double.toString((Double) json.get("price"));
        String picture = (String)json.get("picture");
        String uploaderId = (String)json.get("uploaderId");

        int lastUpdate = DateTime.getDefaultInstance().getSeconds();
        Product product = new Product(title, description, Double.parseDouble(price), picture, uploaderId);
        product.setUpdateDate((long)lastUpdate);
        product.setId(id);
        return product;
    }
}
