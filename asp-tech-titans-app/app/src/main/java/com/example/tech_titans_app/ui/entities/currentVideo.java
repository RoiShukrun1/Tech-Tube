package com.example.tech_titans_app.ui.entities;

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
