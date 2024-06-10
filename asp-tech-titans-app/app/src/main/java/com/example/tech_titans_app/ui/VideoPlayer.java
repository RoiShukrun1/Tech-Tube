package com.example.tech_titans_app.ui;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.adapters.VideosListAdapter;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.viewmodels.MainVideoViewModel;

public class VideoPlayer extends AppCompatActivity {
    private Video currentVideo;
    private VideosListAdapter adapter;
    private MainVideoViewModel videoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_video_page); // Ensure this points to your main layout file

        // Find the VideoView inside the included layout
        VideoView videoView = findViewById(R.id.videoView);

        // Set the video URI (you can also use a URL or a file path)
        this.currentVideo = new Video(R.drawable.image1, "Video 1 Title",
                "Publisher 1",  R.drawable.image1,
                "1M views", "10/10/2020", "First Video Test",
                R.raw.video4);

        String videoPath = "android.resource://"
                + getPackageName() + "/" + this.currentVideo.getVideoUrl();

        Uri videoUri = Uri.parse(videoPath);

        // Set up MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Set the URI to the VideoView
        videoView.setVideoURI(videoUri);

        // Start the video
        videoView.start();

        RecyclerView lstVideos = findViewById(R.id.listVideosVWP);
        lstVideos.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VideosListAdapter();
        lstVideos.setAdapter(adapter);

        videoViewModel = new ViewModelProvider(this).get(MainVideoViewModel.class);
        videoViewModel.getAllVideos().observe(this, videos -> adapter.setVideos(videos));
    }

}
