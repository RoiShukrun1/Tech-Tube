package com.example.tech_titans_app.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.entities.Video;

public class activity_watch_video_page extends AppCompatActivity {

    private Video currentVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_watch_video_page);

        startVideoPlayer();
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