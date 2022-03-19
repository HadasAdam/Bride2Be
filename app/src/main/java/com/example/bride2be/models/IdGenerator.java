package com.example.bride2be.models;

public class IdGenerator {
    public static final IdGenerator instance = new IdGenerator();

    public String getUserId(){
        return Long.toString(System.currentTimeMillis());
    }

    public String getProductId(){
        return Long.toString(System.currentTimeMillis());
    }
}
