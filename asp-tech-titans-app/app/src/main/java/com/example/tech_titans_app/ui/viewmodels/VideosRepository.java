package com.example.tech_titans_app.ui.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.tech_titans_app.ui.api.VideosAPI;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.AppContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideosRepository {

    private final MutableLiveData<List<Video>> videos;
    private List<Video> allVideos;
    private final VideosAPI videosAPI;
    // Singleton instance
    private static VideosRepository instance;

    // Private constructor to prevent instantiation
    private VideosRepository() {
        videos = new MutableLiveData<>();
        allVideos = new ArrayList<>();
        videosAPI = new VideosAPI(AppContext.getContext());
        load20VideosFromAPI();
    }

    // Public method to provide access to the instance
    public static synchronized VideosRepository getInstance() {
        if (instance == null) {
            instance = new VideosRepository();
        }
        return instance;
    }

    private void loadVideosFromAPI() {
        videosAPI.getAllVideos(new Callback<List<Video>>() {
            @Override
            public void onResponse(@NonNull Call<List<Video>> call,
                                   @NonNull Response<List<Video>> response) {
                if (response.isSuccessful()) {
                    allVideos = response.body();
                    videos.setValue(allVideos);
                } else {
                    Log.e("API_CALL", "API call failed onResponse:");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Video>> call, @NonNull Throwable t) {
                Log.e("API_CALL", "API call failed: " + t.getMessage());
            }
        });
    }

    public MutableLiveData<List<Video>> getPublisherVideos(String publisher) {
        MutableLiveData<List<Video>> publisherVideos = new MutableLiveData<>();
        videosAPI.getPublisherVideosById(publisher, new Callback<List<Video>>() {
            @Override
            public void onResponse(@NonNull Call<List<Video>> call, @NonNull Response<List<Video>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    publisherVideos.setValue(response.body());
                } else {
                    Log.e("API_CALL", "API call failed getPublisherVideosById onResponse:");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Video>> call, @NonNull Throwable t) {
                Log.e("API_CALL", "API call failed getPublisherVideosById: " + t.getMessage());
            }
        });
        return publisherVideos;
    }

    private void load20VideosFromAPI() {
        videosAPI.get20Videos(new Callback<List<Video>>() {
            @Override
            public void onResponse(@NonNull Call<List<Video>> call,
                                   @NonNull Response<List<Video>> response) {
                if (response.isSuccessful()) {
                    allVideos = response.body();
                    videos.setValue(allVideos);
                } else {
                    Log.e("API_CALL", "API call failed onResponse:");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Video>> call, @NonNull Throwable t) {
                Log.e("API_CALL", "API call failed: " + t.getMessage());
            }
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
