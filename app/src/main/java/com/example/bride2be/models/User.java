package com.example.bride2be.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.FieldValue;
import com.google.type.DateTime;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Entity
public class User implements Serializable {
    final public static String COLLECTION_NAME = "Users_new";
    @PrimaryKey
    @NonNull
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String passwordHash;
    private String country;
    private String city;
    private String street;
    Long updateDate = new Long(0);

    private User(){}

    public User(String firstName, String lastName, String email, String phoneNumber,
                String passwordHash, String country, String city, String street){
        this.id = IdGenerator.instance.getUserId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.country = country;
        this.city = city;
        this.street = street;

        this.updateDate = (long)DateTime.getDefaultInstance().getSeconds();
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("email", email);
        json.put("phoneNumber", phoneNumber);
        json.put("passwordHash", passwordHash);
        json.put("country", "Israel");
        json.put("city", city);
        json.put("street", street);
        json.put("updateDate", updateDate);
        return json;
    }

    public static User create(Map<String, Object> json) {
        String id = (String) json.get("id");
        String firstName = (String) json.get("firstName");
        String lastName = (String) json.get("lastName");
        String email = (String)json.get("email");
        String phoneNumber = (String)json.get("phoneNumber");
        String passwordHash = (String)json.get("passwordHash");
        String country = (String)json.get("country");
        String city = (String)json.get("city");
        String street = (String)json.get("street");

        int lastUpdate = DateTime.getDefaultInstance().getSeconds();
        User user = new User(firstName,lastName,email, phoneNumber, passwordHash, country,
                city, street);
        user.setId(id);
        user.setUpdateDate((long)lastUpdate);
        return user;
    }

}
