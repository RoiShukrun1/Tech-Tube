package com.example.tech_titans_app.ui.models.account;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.tech_titans_app.ui.Converters.Converters;

@Database(entities = { UserData.class }, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class UsersDB extends RoomDatabase {
    public abstract UsersDataDao usersDao();
}