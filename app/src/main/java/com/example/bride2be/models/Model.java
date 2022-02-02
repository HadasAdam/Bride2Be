package com.example.bride2be.models;

import java.util.LinkedList;
import java.util.List;

public class Model {

    public static final Model instance = new Model();
    private ModelFirebase modelFirebase = new ModelFirebase();
    private ModelSQL modelSQL = new ModelSQL();
    private List<Product> productList = new LinkedList<Product>();
    private List<User> userList = new LinkedList<>();

    private Model(){
        for(long i = 0L; i < 20L; i++){
            Product product = new Product("White Dress", "none", 34.3D, null,
                    new User("Hadas", "Adam","hadasadam@gmail.com",
                            "0402345432", "hhj293423", "Israel",
                            "Rishon Lezion","Nilus 3"));
            productList.add(product);
        }

        for(long i = 0L; i < 10L; i++){
            User user = new User("Johnny", "Levis",
                    "johnny" + i + "@gmail.com", "0502233412",
                    "0832c1202da8d382318e329a7c133ea0", "Israel", "Holon",
                    "Moshe Dayan 23");
            userList.add(user);
        }
    }

    public List<Product> getAllProducts(){
        return productList;
    }

    public List<User> getAllUsers(){
        return userList;
    }

    public void addProduct(Product product){
        productList.add(product);
    }

    public void addUser(User user){
        userList.add(user);
    }

    public interface GetAllProductsListener {
        void onComplete(List<Product> products);
    }


    public interface AddProductListener {
        void onComplete();
    }


    public interface GetAllUsersListener {
        void onComplete(List<User> users);
    }


    public interface AddUserListener {
        void onComplete();
    }

    public interface UpdateUserListener extends AddUserListener {
        void onComplete();
    }

    public interface GetUserListener {
        void onComplete();
    }

    public interface DeleteUserListener extends AddUserListener{
        void onComplete();
    }

    public void getAllUsers(final GetAllUsersListener listener)
    {
        modelFirebase.getAllUsers(listener);
    }

    public void getUser(String id, final GetUserListener listener)
    {
        modelFirebase.getUser(id, listener);
    }

    public void addUser(final User user, final AddUserListener listener)
    {
        modelFirebase.addUser(user, listener);
    }

    public void updateUser(final User user, final UpdateUserListener listener)
    {
        modelFirebase.updateUser(user, listener);
    }

    public void deleteUser(final User user, final DeleteUserListener listener)
    {
        modelFirebase.deleteUser(user, listener);
    }

}
