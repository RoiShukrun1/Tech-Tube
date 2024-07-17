package com.example.tech_titans_app.ui.viewmodels;

import android.net.Uri;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.AppContext;
import com.example.tech_titans_app.ui.api.VideosAPI;
import com.example.tech_titans_app.ui.entities.CurrentVideo;
import com.example.tech_titans_app.ui.entities.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RelatedVideosRepository {

    private final MutableLiveData<List<Video>> videos;
    private List<Video> allVideos;
    private VideosAPI videosAPI = new VideosAPI(AppContext.getContext());
    private CurrentVideo currentVideo;

    // Singleton instance
    private static RelatedVideosRepository instance;

    // Private constructor to prevent instantiation
    private RelatedVideosRepository() {
        videos = new MutableLiveData<>();
        allVideos = new ArrayList<>();
        currentVideo = CurrentVideo.getInstance();
        loadVideos();

        // Observe changes to currentVideo
        currentVideo.getCurrentVideo().observeForever(video -> {
            loadVideos(); // Reload videos when the current video changes
        });
    }

    // Public method to provide access to the instance
    public static synchronized RelatedVideosRepository getInstance() {
        if (instance == null) {
            instance = new RelatedVideosRepository();
        }
        return instance;
    }

    private void loadVideos() {
        videosAPI.getRelatedVideos(String.valueOf(currentVideo.getCurrentVideo().getValue().getId())
                ,new Callback<List<Video>>() {
            @Override
            public void onResponse(@NonNull Call<List<Video>> call,
                                   @NonNull Response<List<Video>> response) {
                if (response.isSuccessful()) {
                    allVideos = response.body();
                    videos.setValue(allVideos);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Video>> call, @NonNull Throwable t) {}
        });
    }

    public MutableLiveData<List<Video>> getAllVideos() {
        return videos;
    }

    public void searchVideos(String query) {
        if (query == null || query.isEmpty()) {
            videos.setValue(allVideos);
            return;
        }

        String lowerCaseQuery = query.toLowerCase();
        List<Video> filteredVideos = allVideos.stream()
                .filter(video ->
                        video.getTitle().toLowerCase().startsWith(lowerCaseQuery) || // Prefix match
                                video.getPublisher().toLowerCase().equals(lowerCaseQuery) || // Exact publisher match
                                containsWord(video.getTitle(), lowerCaseQuery)) // One word match
                .collect(Collectors.toList());
        videos.setValue(filteredVideos);
    }

    private boolean containsWord(String title, String query) {
        String[] words = title.toLowerCase().split("\\s+");
        for (String word : words) {
            if (word.equals(query)) {
                return true;
            }
        }
        return false;
    }

    public void addVideo(Video video) {
        allVideos.add(video);
        videos.setValue(allVideos);
    }

    public void deleteVideo(Video video) {
        allVideos.remove(video);
        videos.setValue(allVideos);
    }
}
