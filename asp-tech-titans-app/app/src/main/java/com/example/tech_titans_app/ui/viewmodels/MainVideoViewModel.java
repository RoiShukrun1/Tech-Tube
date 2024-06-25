package com.example.tech_titans_app.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.tech_titans_app.ui.entities.Video;

import java.util.List;

public class MainVideoViewModel extends ViewModel {

    private final MutableLiveData<List<Video>> videos;
    private final VideosRepository repository;

    /**
     * Constructor for MainVideoViewModel.
     * Initializes the repository and loads the videos.
     */
    public MainVideoViewModel() {
        repository = VideosRepository.getInstance();
        videos = repository.getAllVideos();
    }

    /**
     * Returns a LiveData object containing the list of videos.
     *
     * @return LiveData containing the list of videos.
     */
    public LiveData<List<Video>> getAllVideos() {
        return videos;
    }

    /**
     * Sets the list of videos and updates the LiveData.
     *
     * @param videoList The list of videos to set.
     */
    public void setVideos(List<Video> videoList) {
        videos.setValue(videoList);
    }

    /**
     * Filters the videos based on the search query.
     *
     * @param query The search query to filter videos.
     */
    public void filterVideos(String query) {
        repository.searchVideos(query);
    }
}
