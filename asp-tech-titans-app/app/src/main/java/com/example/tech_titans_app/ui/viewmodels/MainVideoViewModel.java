package com.example.tech_titans_app.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.viewmodels.VideosRepository;
import java.util.List;

public class MainVideoViewModel extends ViewModel {

    private MutableLiveData<List<Video>> videos;
    private VideosRepository repository;

    public MainVideoViewModel() {
        repository = new VideosRepository();
        videos = repository.getAllVideos();
    }

    public LiveData<List<Video>> getAllVideos() {
        return videos;
    }
}
