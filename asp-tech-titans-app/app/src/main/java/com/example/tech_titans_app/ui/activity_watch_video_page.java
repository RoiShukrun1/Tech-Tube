package com.example.tech_titans_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.mainActivity.FilterUtils;
import com.example.tech_titans_app.ui.adapters.VideosListAdapter;
import com.example.tech_titans_app.ui.mainActivity.MainActivity;
import com.example.tech_titans_app.ui.viewmodels.MainVideoViewModel;


public class activity_watch_video_page extends AppCompatActivity {

    private Video currentVideo;
    private VideosListAdapter adapter;
    private MainVideoViewModel videoViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_watch_video_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startVideoPlayer();

        RecyclerView lstVideos = findViewById(R.id.lstVideos);
        lstVideos.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VideosListAdapter();
        lstVideos.setAdapter(adapter);

        videoViewModel = new ViewModelProvider(this).get(MainVideoViewModel.class);
        videoViewModel.getAllVideos().observe(this, videos -> adapter.setVideos(videos));

    }

    private void startVideoPlayer() {
        Intent intent = new Intent(this, VideoPlayer.class);
        startActivity(intent);
    }

    public void setCurrentVideo(Video currentVideo) {
        this.currentVideo = currentVideo;
    }

    public Video getCurrentVideo() {
        return currentVideo;
    }

}