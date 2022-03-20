package com.example.bride2be.models.localDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bride2be.models.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("select * from Product")
    LiveData<List<Product>> getAllProducts();

    @Query("select * from Product where uploaderId = :userId")
    LiveData<List<Product>> getAllProductsByUserId(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Product... products);

    @Update
    void updateProducts(Product... products);

    @Delete
    void delete(Product product);
}
