package com.example.tech_titans_app.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.tech_titans_app.ui.entities.Video;

import java.util.List;

public class MainVideoViewModel extends ViewModel {

    private MutableLiveData<List<Video>> videos;
    private List<Video> allVideos;
    private VideosRepository repository;

    public MainVideoViewModel() {
        repository = new VideosRepository();
        videos = repository.getAllVideos();
    }

    public LiveData<List<Video>> getAllVideos() {
        return videos;
    }

    public void setVideos(List<Video> videoList) {
        allVideos = videoList;
        videos.setValue(videoList);
    }

    public void filterVideos(String query) {
        repository.searchVideos(query);
    }
}
