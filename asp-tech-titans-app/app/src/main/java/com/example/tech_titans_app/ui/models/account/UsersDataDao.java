package com.example.tech_titans_app.ui.models.account;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UsersDataDao {
    @Query("SELECT * FROM userdata WHERE username = :id")
    UserData getUserById(int id);

    @Query("SELECT * FROM userdata WHERE username = :username")
    UserData getUserByUsername(String username);

    @Query("SELECT * FROM userdata")
    List<UserData> getAllUsers();

    @Query("SELECT image FROM userdata WHERE username = :username")
    String getUserProfilePicture(String username);

    @Delete
    void delete(UserData... users);

    @Insert
    void insert(UserData userData);
}
