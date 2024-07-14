package com.example.tech_titans_app.ui.Converters;

import android.net.Uri;

import androidx.room.TypeConverter;

import com.example.tech_titans_app.ui.entities.Comment;
import com.example.tech_titans_app.ui.entities.Video;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;


public class Converters {

    @TypeConverter
    public String fromList(List<String> list) {
        return list != null ? String.join(",", list) : null;
    }

    @TypeConverter
    public List<String> toList(String concatenated) {
        return concatenated != null ? Arrays.asList(concatenated.split(",")) : null;
    }

    @TypeConverter
    public String fromStringArray(String[] array) {
        return array != null ? String.join(",", array) : null;
    }

    @TypeConverter
    public String[] toStringArray(String concatenated) {
        return concatenated != null ? concatenated.split(",") : null;
    }
    @TypeConverter
    public String fromUri(Uri uri) {
        return uri != null ? uri.toString() : null;
    }
    @TypeConverter
    public Uri toUri(String uriString) {
        return uriString != null ? Uri.parse(uriString) : null;
    }

    @TypeConverter
    public static String fromVideoList(List<Video> videos) {
        if (videos == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Video>>() {}.getType();
        return gson.toJson(videos, type);
    }

    @TypeConverter
    public static List<Video> toVideoList(String videoString) {
        if (videoString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Video>>() {}.getType();
        return gson.fromJson(videoString, type);
    }

    @TypeConverter
    public static String fromCommentList(List<Comment> comments) {
        if (comments == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Comment>>() {}.getType();
        return gson.toJson(comments, type);
    }

    @TypeConverter
    public static List<Comment> toCommentList(String commentString) {
        if (commentString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Comment>>() {}.getType();
        return gson.fromJson(commentString, type);
    }

    @TypeConverter
    public static String fromIntegerList(List<Integer> integers) {
        if (integers == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {}.getType();
        return gson.toJson(integers, type);
    }

    @TypeConverter
    public static List<Integer> toIntegerList(String integerString) {
        if (integerString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {}.getType();
        return gson.fromJson(integerString, type);
    }
}