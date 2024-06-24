package com.example.tech_titans_app.ui.models.add_video;

import android.app.Application;
import android.net.Uri;

public class UploadedVideo extends Application {
    private Uri videoUri;

    // Getter for videoUri
    public Uri getVideoUri() {
        return videoUri;
    }

    // Setter for videoUri
    public void setVideoUri(Uri videoUri) {
        this.videoUri = videoUri;
    }

    // Clear the videoUri
    public void clearVideoUri() {
        this.videoUri = null;
    }
}
