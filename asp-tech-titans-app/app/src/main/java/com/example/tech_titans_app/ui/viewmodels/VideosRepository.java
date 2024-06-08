package com.example.tech_titans_app.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.entities.Video;
import java.util.ArrayList;
import java.util.List;

public class VideosRepository {

    private MutableLiveData<List<Video>> videos;

    public VideosRepository() {
        videos = new MutableLiveData<>();
        loadVideos();
    }

    private void loadVideos() {
        // This is where you'd normally load data from a database or web service
        List<Video> videoList = new ArrayList<>();
        // Populate with sample data
        videoList.add(new Video(R.drawable.image1, "Video 1 Title", "Publisher 1",  R.drawable.image1, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image2, "Video 2 Title", "Publisher 2",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image3, "Video 3 Title", "Publisher 3",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image4, "Video 4 Title", "Publisher 4",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image5, "Video 5 Title", "Publisher 4",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image6, "Video 6 Title", "Publisher 4",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image7, "Video 7 Title", "Publisher 5",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image8, "Video 8 Title", "Publisher 6",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image9, "Video 9 Title", "Publisher 7",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image10, "Video 10 Title", "Publisher 7",  R.drawable.image2, "1M views", "10/10/2020"));
        videos.setValue(videoList);
    }

    public MutableLiveData<List<Video>> getAllVideos() {
        return videos;
    }
}
