package com.iti.java.medicano.model.databaselayer;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.iti.java.medicano.model.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUsers();

    /*@Query("SELECT * FROM User WHERE id = id")
    User getUser(String id);*/

    @Insert(onConflict = REPLACE)
    void insertUser(User user);
}
