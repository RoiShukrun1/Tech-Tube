package com.example.tech_titans_app.ui.mainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDelegate;

public class DarkModeManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public DarkModeManager(Activity activity, ImageView darkModeButton) {
        // Initialize SharedPreferences and editor
        sharedPreferences = activity.getSharedPreferences("themeSharedPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Check and apply current theme setting
        boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Set click listener for dark mode button
        darkModeButton.setOnClickListener(v -> {
            if (isDarkModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("isDarkModeOn", false);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("isDarkModeOn", true);
            }
            editor.apply();
            activity.recreate();
        });
    }
}
