package com.example.bride2be.models;

import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.bride2be.models.localDB.AppLocalDb;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.SettableFuture;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    final static String UPLOADS_FOLDER = "Uploads";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageReference;

    public ModelFirebase(){
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storageReference = FirebaseStorage.getInstance().getReference(UPLOADS_FOLDER);
        String f = "3";
    }

    /**************************************   STORAGE   **************************************/

    public String uploadPictureInStorage(String fileExtension, Uri imageUri) {
        String fileName = System.currentTimeMillis() + "." + fileExtension;
        StorageReference fileReference = storageReference.child("/" + fileName);
        fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("TAG", "Upload Successful.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "Upload Unsuccessful.");
            }
        });
        return fileName;
    }

    public void loadPictureFromStorage(String path, ImageView imageView)
    {
        StorageReference ref = storageReference.child(path);
        ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri imageUri = task.getResult();
                    Picasso.get().load(imageUri).fit().centerInside().into(imageView);

                    Log.d("TAG", "Succeeded to load image from storage.");
                } else {
                    Log.d("TAG", "Failed to load image from storage.");
                }
            }
        });
    }

    /**************************************   USERS   **************************************/

    public void getAllUsers(Model.GetAllUsersListener listener) {
        db.collection(User.COLLECTION_NAME)
                .get()
                .addOnCompleteListener(task -> {
                    List<User> list = new LinkedList<User>();
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot doc : task.getResult()){
                            User user = User.create(doc.getData());
                            if (user != null){
                                list.add(user);
                            }
                        }
                    }
                    listener.onComplete(list);
                });

//        AppLocalDb.getInstance().userDao().getAllUsers(); // get all users from localdb
    }

    public void addUser(User user, Model.AddUserListener listener) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("firstName", user.getFirstName());
        userData.put("lastName", user.getLastName());
        userData.put("email", user.getEmail());
        userData.put("phoneNumber", user.getPhoneNumber());
        userData.put("passwordHash", user.getPasswordHash());
        userData.put("country", "Israel");
        userData.put("city", user.getCity());
        userData.put("street", user.getStreet());
        userData.put("updateDate", user.getUpdateDate());

        db.collection(User.COLLECTION_NAME)
                .document(user.getId())
                .set(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "User added with ID: " + user.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding user", e);
                    }
                });

//        AppLocalDb.getInstance().userDao().insertAll(user); // add user to localdb
    }

    public void updateUser(User user, Model.UpdateUserListener listener) {
        addUser(user, listener);
    }

    public void getUser(String id, Model.GetUserListener listener) {
        db.collection(User.COLLECTION_NAME)
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        User user = null;
                        if (task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            if(doc != null)
                            {
                                user = task.getResult().toObject(User.class);
                            }
                        }
                        listener.onComplete(user);
                    }
                });
    }

    public void deleteUser(User user, Model.DeleteUserListener listener) {
        db.collection(User.COLLECTION_NAME).document(user.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete();
                    }
                });
    }

    /**************************************   PRODUCTS   **************************************/

    public void getAllProducts(Model.GetAllProductsListener listener) {
        db.collection(Product.COLLECTION_NAME)
                .get()
                .addOnCompleteListener(task -> {
                    List<Product> list = new LinkedList<Product>();
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot doc : task.getResult()){
                            Product product = Product.create(doc.getData());
                            if (product != null){
                                list.add(product);
                            }
                        }
                    }
                    listener.onComplete(list);
                });
    }

    public void addProduct(Product product, Model.AddProductListener listener) {
        Map<String, Object> productData = new HashMap<>();
        productData.put("id", product.getId());
        productData.put("title", product.getTitle());
        productData.put("description", product.getDescription());
        productData.put("price", product.getPrice());
        productData.put("picture", product.getPicture());
        productData.put("uploaderId", product.getUploaderId());
        productData.put("updateDate",product.getUpdateDate());

        db.collection(Product.COLLECTION_NAME)
                .document(product.getId())
                .set(productData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Product added with ID: " + product.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding product", e);
                    }
                });
    }

    public void getProduct(String id, Model.GetProductListener listener) {
        db.collection(Product.COLLECTION_NAME)
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Product product = null;
                        if (task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            if(doc != null)
                            {
                                product = task.getResult().toObject(Product.class);
                            }
                        }
                        listener.onComplete(product);
                    }
                });
    }

    public void updateProduct(Product product, Model.UpdateProductListener listener) {
        addProduct(product, listener);
    }

    public void deleteProduct(Product product, Model.DeleteProductListener listener) {
        db.collection(Product.COLLECTION_NAME).document(product.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete();
                    }
                });
    }

    /**************************************   FILTERS   **************************************/

    public void getProductsByUserId(String userId, Model.GetProductsByUserIdListener listener) {
        db.collection(Product.COLLECTION_NAME)
                .whereEqualTo("uploaderId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    List<Product> list = new LinkedList<Product>();
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot doc : task.getResult()){
                            Product product = Product.create(doc.getData());
                            if (product != null){
                                list.add(product);
                            }
                        }
                    }
                    listener.onComplete(list);
                });
    }

    public void getProductsByCity(String city, Model.GetProductsByCityListener listener) {
        db.collection(User.COLLECTION_NAME)
                .whereEqualTo("city", city)
                .get()
                .addOnCompleteListener(task -> {
                    ArrayList<User> userIdsInCity = new ArrayList<User>();
                    if (task.isSuccessful()){
                        ArrayList<Product> productsByCity = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : task.getResult()){
                            User user = User.create(doc.getData());
                            if (user != null){
                                getProductsByUserId(user.getId(), new Model.GetProductsByUserIdListener() {
                                    @Override
                                    public void onComplete(List<Product> products) {
                                        for(Product product : products)
                                        {
                                            productsByCity.add(product);
                                        }
                                        listener.onComplete(productsByCity);
                                    }
                                });
                            }
                        }
                    }
                });

    }
}
