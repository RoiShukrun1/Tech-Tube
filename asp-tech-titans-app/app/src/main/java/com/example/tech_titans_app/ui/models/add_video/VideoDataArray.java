package com.example.tech_titans_app.ui.models.add_video;

import java.util.ArrayList;
import java.util.List;

public class VideoDataArray {

    private static VideoDataArray instance;
    private List<VideoData> videoDataArray;

    private VideoDataArray() {
        videoDataArray = new ArrayList<>();
    }

    public static synchronized VideoDataArray getInstance() {
        if (instance == null) {
            instance = new VideoDataArray();
        }
        return instance;
    }

    public List<VideoData> getVideoArray() {
        return videoDataArray;
    }

    public void addVideo(VideoData videoData) {
        videoDataArray.add(videoData);
    }

    public int getLength() {
        return videoDataArray.size();
    }

}
