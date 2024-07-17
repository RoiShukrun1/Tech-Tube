package com.example.tech_titans_app.ui.entities;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.tech_titans_app.ui.Converters.Converters;

@Database(entities = {Video.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class VideoDB extends RoomDatabase {

    private static volatile com.example.tech_titans_app.ui.entities.VideoDB INSTANCE;

    public abstract VideoDao videoDao();

    public static com.example.tech_titans_app.ui.entities.VideoDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (com.example.tech_titans_app.ui.models.account.UsersDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    com.example.tech_titans_app.ui.entities.VideoDB.class,
                                    "videos")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
