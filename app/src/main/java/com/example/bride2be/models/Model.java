package com.example.bride2be.models;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();
    private List<Product> data = new LinkedList<Product>();

    private Model(){
        for(long i = 0L; i < 100L; i++){
            Product s = new Product(i, "White Dress", "none", 34.3D, null,
                    new User(i+2L,"Hadas", "Adam","hadasadam@gmail.com",
                            "0402345432", "hhj293423", "Israel",
                            "Rishon Lezion","Nilus 3"));
            data.add(s);
        }
    }

    public List<Product> getAllProducts(){
        return data;
    }

    public void addProduct(Product product){
        data.add(product);
    }
}
