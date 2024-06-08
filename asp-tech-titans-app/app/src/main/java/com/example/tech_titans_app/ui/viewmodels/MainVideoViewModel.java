//package com.example.tech_titans_app.ui.viewmodels;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.example.tech_titans_app.ui.viewmodels.VideosRepository;
//import com.example.tech_titans_app.ui.entities.Video;
//import java.util.List;
//public class MainVideoViewModel extends ViewModel{
//    private final VideosRepository videosRepository;
//    private final LiveData<List<Video>> videos;
//
//    public MainVideoViewModel(VideosRepository videosRepository, LiveData<List<Video>> videos) {
//        this.videosRepository = new VideosRepository();
//        this.videos = videosRepository.getAll();
//    }
//
//    public LiveData<List<Video>> getVideos() {
//        return videos;
//    }
//
//    public void add(Video video) {
//        videosRepository.add(video);
//    }
//
//    public void delete(Video video) {
//        videosRepository.delete(video);
//    }
//    public void reload(Video video) {
//        videosRepository.reload();
//    }
//}
