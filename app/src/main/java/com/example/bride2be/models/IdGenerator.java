package com.example.bride2be.models;

public class IdGenerator {
    public static final IdGenerator instance = new IdGenerator();
    int userId = 0;
    int productId = 1;

    public String getUserId(){
        return "" + (++userId);
    }

    public String getProductId(){
        return "" + (++productId);
    }
}
