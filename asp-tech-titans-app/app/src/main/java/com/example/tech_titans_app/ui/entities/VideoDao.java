package com.example.tech_titans_app.ui.entities;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VideoDao {
    @Query("SELECT * FROM video")
    List<Video> getAllVideos();
    @Query("SELECT * FROM video WHERE id = :id")
    Video getVideoById(int id);

    @Insert
    void insert(Video... videos);
    @Update
    void update(Video... videos);
    @Delete
    void delete(Video... videos);
}
