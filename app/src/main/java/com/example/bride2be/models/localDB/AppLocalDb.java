package com.example.bride2be.models.localDB;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bride2be.Bride2BeApplication;
import com.example.bride2be.models.Product;
import com.example.bride2be.models.User;

@Database(entities = {Product.class, User.class}, version = 0)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract UserDao userDao();
}

public class AppLocalDb {
    static public AppLocalDbRepository db =
            Room.databaseBuilder(Bride2BeApplication.context,
                    AppLocalDbRepository.class,
                    "dbBride2Be.db")
                    .fallbackToDestructiveMigration()
                    .build();
}

