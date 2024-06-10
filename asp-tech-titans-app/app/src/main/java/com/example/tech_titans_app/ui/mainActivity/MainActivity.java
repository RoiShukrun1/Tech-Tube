package com.example.tech_titans_app.ui.mainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.adapters.VideosListAdapter;

import com.example.tech_titans_app.ui.viewmodels.MainVideoViewModel;

public class MainActivity extends AppCompatActivity {
    private MainVideoViewModel videoViewModel;
    private VideosListAdapter adapter;

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

    }
}
