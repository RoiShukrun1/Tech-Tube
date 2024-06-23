package com.example.tech_titans_app.ui.entities;

import android.net.Uri;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.viewmodels.VideosRepository;

public class currentVideo {
    private static currentVideo instance;
    private Video currentVideo;

    private currentVideo() {
    }

    public static synchronized currentVideo getInstance() {
        if (instance == null) {
            instance = new currentVideo();
        }
        return instance;
    }

    public Video getCurrentVideo() {
        return currentVideo;
    }

    public void setCurrentVideo(Video currentVideo) {
        this.currentVideo = currentVideo;
    }
}
