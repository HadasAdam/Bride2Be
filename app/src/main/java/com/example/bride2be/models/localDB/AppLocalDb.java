package com.example.bride2be.models.localDB;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bride2be.Bride2BeApplication;
import com.example.bride2be.models.Product;
import com.example.bride2be.models.User;

import java.util.ArrayList;

@Database(entities = {Product.class, User.class}, version = 0)
public abstract class AppLocalDb extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract UserDao userDao();
    static synchronized public AppLocalDb getInstance() {
        if(db == null){
            db = Room.databaseBuilder(Bride2BeApplication.context,
                    AppLocalDb.class,
                    "dbBride2Be.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }


    static  private AppLocalDb db;
}


