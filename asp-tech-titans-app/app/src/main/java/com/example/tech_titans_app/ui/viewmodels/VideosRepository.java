//package com.example.tech_titans_app.ui.viewmodels;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.tech_titans_app.R;
//import com.example.tech_titans_app.ui.entities.Video;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class VideosRepository {
//    private final MutableLiveData<List<Video>> videoList = new MutableLiveData<>();
//    private final List<Video> videos = new ArrayList<>();
//
//    public VideosRepository() {
//        // Initialize with some sample data
//        videos.add(new Video(R.drawable.image1, "Video 1 Title", "Publisher 1",  R.drawable.image1, "1M views", "10/10/2020"));
//        videos.add(new Video(R.drawable.image1, "Video 2 Title", "Publisher 2",  R.drawable.image2, "1M views", "10/10/2020"));
//        videoList.setValue(videos);
//    }
//
//    public LiveData<List<Video>> getAll() {
//        return videoList;
//    }
//
//    public void add(Video video) {
//        videos.add(video);
//        videoList.setValue(videos);
//    }
//
//    public void delete(Video video) {
//        videos.remove(video);
//        videoList.setValue(videos);
//    }
//
//    public void reload() {
//        videoList.setValue(videos);
//    }
//}
