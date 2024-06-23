package com.example.tech_titans_app.ui.mainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.CheckVideoActivity;
import com.example.tech_titans_app.ui.UploadVideoActivity;
import com.example.tech_titans_app.ui.adapters.VideosListAdapter;

import com.example.tech_titans_app.ui.viewmodels.MainVideoViewModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {
    private MainVideoViewModel videoViewModel;
    private VideosListAdapter adapter;
    private ImageView darkModeButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FilterUtils().setupFilterClickListeners(findViewById(android.R.id.content));
        new SearchBarUtils(findViewById(android.R.id.content));

        RecyclerView lstVideos = findViewById(R.id.lstVideos);
        lstVideos.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VideosListAdapter();
        lstVideos.setAdapter(adapter);

        videoViewModel = new ViewModelProvider(this).get(MainVideoViewModel.class);
        videoViewModel.getAllVideos().observe(this, videos -> adapter.setVideos(videos));

        TextView homeButton = findViewById(R.id.home);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ImageView addvideoButton = findViewById(R.id.add);
        addvideoButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UploadVideoActivity.class);
            startActivity(intent);
        });

        TextView checkButton = findViewById(R.id.you);
        checkButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CheckVideoActivity.class);
            startActivity(intent);
        });

        EditText searchInput = findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                videoViewModel.filterVideos(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        darkModeButton = findViewById(R.id.dark_mode);
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Check the current mode
        boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        darkModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
                if (isDarkModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("isDarkModeOn", false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("isDarkModeOn", true);
                }
                editor.apply();
            }
        });

    }
}
