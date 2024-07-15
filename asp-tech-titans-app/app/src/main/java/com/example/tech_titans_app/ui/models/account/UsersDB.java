package com.example.tech_titans_app.ui.models.account;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.tech_titans_app.ui.Converters.Converters;

@Database(entities = {UserData.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class UsersDB extends RoomDatabase {

    private static volatile UsersDB INSTANCE;

    public abstract UsersDataDao usersDao();

    public static UsersDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UsersDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UsersDB.class, "usersDb")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
