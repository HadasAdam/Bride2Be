package com.example.bride2be.models;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.LinkedList;
import java.util.List;

public class Model {

    public static final Model instance = new Model();
    private ModelFirebase modelFirebase = new ModelFirebase();
    private ModelSQL modelSQL = new ModelSQL();
    private List<Product> productList = new LinkedList<Product>();
    private List<User> userList = new LinkedList<>();

    private Model(){
        initializeLocalData();
    }

    //this method should be deleted in the end
    private void initializeLocalData()
    {
        for(long i = 0L; i < 10L; i++){
            User user = new User("Johnny", "Levis",
                    "johnny" + i + "@gmail.com", "0502233412",
                    "0832c1202da8d382318e329a7c133ea0", "Israel", "Holon",
                    "Moshe Dayan 23");
            userList.add(user);
        }

        for(long i = 0L; i < 20L; i++){
            Product product = new Product("White Dress", "none", 34.3D, null, userList.get(0).getId());
            productList.add(product);
        }
    }

    /**************************************   PRODUCTS   **************************************/

    //these methods are still needed fot the list view;
    public List<Product> getAllProducts(){
        return productList;
    }

    public List<User> getAllUsers(){
        return userList;
    }



    public interface GetAllProductsListener {
        void onComplete(List<Product> products);
    }

    public interface AddProductListener {
        void onComplete();
    }

    public interface UpdateProductListener extends AddProductListener {
        void onComplete();
    }

    public interface GetProductListener {
        void onComplete();
    }

    public interface DeleteProductListener extends AddProductListener{
        void onComplete();
    }

    public void getAllProducts(final GetAllProductsListener listener)
    {
        modelFirebase.getAllProducts(listener);
    }

    public void getProduct(String id, final GetProductListener listener)
    {
        modelFirebase.getProduct(id, listener);
    }

    public void addProduct(final Product product, final AddProductListener listener)
    {
        modelFirebase.addProduct(product, listener);
    }

    public void updateProduct(final Product product, final UpdateProductListener listener)
    {
        modelFirebase.updateProduct(product, listener);
    }

    public void deleteProduct(final Product product, final DeleteProductListener listener)
    {
        modelFirebase.deleteProduct(product, listener);
    }

    /**************************************   USERS   **************************************/

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
