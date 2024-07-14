package com.example.tech_titans_app.ui.Converters;

import android.net.Uri;

import androidx.room.TypeConverter;
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
}