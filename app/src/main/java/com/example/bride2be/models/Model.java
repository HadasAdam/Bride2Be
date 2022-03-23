package com.example.bride2be.models;

import android.net.Uri;
import android.widget.ImageView;

import com.example.bride2be.models.localDB.AppLocalDb;
import com.example.bride2be.models.localDB.ProductModelSql;
import com.example.bride2be.models.localDB.UserModelSql;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Model {

    public static final Model instance = new Model();
    private ModelFirebase modelFirebase = new ModelFirebase();
    private List<Product> productList = new LinkedList<Product>();
    private List<User> userList = new LinkedList<>();
    private ArrayList<City> cities = new ArrayList<>();
    private User loggedInUser = null;
    private UserModelSql userModelSql = new UserModelSql();
    private ProductModelSql productModelSql = new ProductModelSql();

    private Model(){
        initializeCities();
        initializeLocalData();
    }

    //this method should be deleted in the end
    private void initializeLocalData()
    {
        for(long i = 0L; i < 10L; i++){
            User user = new User("Johnny", "Levis",
                    "johnny" + i + "@gmail.com", "0502233412",
                    "202cb962ac59075b964b07152d234b70", "Israel", cities.get(3).getName(),
                    "Moshe Dayan 23");
            userList.add(user);
        }

        for(long i = 0L; i < 20L; i++){
            Product product = new Product("White Dress", "none", 34.3D, null, userList.get(0).getId());
            productList.add(product);
        }
    }

    private void initializeCities()
    {
        cities.add(new City("Ashdod",31.799293d, 34.653821d));
        cities.add(new City("Beer Sheva",31.262376d,34.796737d));
        cities.add(new City("Haifa",32.807829d, 34.985453d));
        cities.add(new City("Holon",32.019266d, 34.788493d));
        cities.add(new City("Jerusalem",31.782785d,35.212797d));
        cities.add(new City("Rehovot",31.891392d,34.811594d));
        cities.add(new City("Rishon Lezion",31.958685d,34.751263d));
        cities.add(new City("Tel Aviv",32.080340d,34.793264d));
        cities.add(new City("Yavne",31.885384d, 34.731217d));
        cities.add(new City("Natanya",32.329985d, 34.8540231d));
        cities.add(new City("Marom Hagolan",33.135012d, 35.775956d));
        cities.add(new City("Eylat", 29.539523, 34.941682));
        cities.add(new City("Yam Hamelach", 31.449183, 35.387405));
        cities.add(new City("Tveria",32.787528d, 35.540971d));
        cities.add(new City("Ashkelon",31.665704, 34.572476));
        cities.add(new City("Sderot", 31.519833, 34.595750));
    }

    public ArrayList<City> getCities()
    {
        return this.cities;
    }

    /**************************************   Storage   **************************************/

    public String uploadPictureInStorage(String fileExtension, Uri imageUri) {
        return modelFirebase.uploadPictureInStorage(fileExtension, imageUri);
    }

    public void loadPictureFromStorage(String path, ImageView imageView) {
        modelFirebase.loadPictureFromStorage(path, imageView);
    }

    /**************************************   Logging in   **************************************/

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void logOut() {
        this.loggedInUser = null;
    }


    /**************************************   PRODUCTS   **************************************/

    //these methods are still needed fot the list view;
    public List<Product> getAllProducts(){
        return productList;
    }

//    public List<User> getAllUsers(){
//        return userList;
//    }

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
        void onComplete(Product product);
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
        void onComplete(User user);
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

    /**************************************   FILTERS   **************************************/

    public interface GetProductsByUserIdListener {
        void onComplete(List<Product> products);
    }

    public void getProductsByUserId(String userId, final GetProductsByUserIdListener listener)
    {
        modelFirebase.getProductsByUserId(userId, listener);
    }

    public interface GetProductsByCityListener {
        void onComplete(List<Product> products);
    }

    public void getProductsByCity(String city, final GetProductsByCityListener listener)
    {
        modelFirebase.getProductsByCity(city, listener);
    }

}
