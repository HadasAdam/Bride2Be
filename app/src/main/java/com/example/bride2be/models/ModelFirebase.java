package com.example.bride2be.models;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ModelFirebase(){
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
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
                        listener.onComplete();
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
                        listener.onComplete();
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
}
