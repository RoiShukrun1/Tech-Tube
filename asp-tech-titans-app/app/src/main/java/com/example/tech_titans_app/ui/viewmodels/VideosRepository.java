package com.example.tech_titans_app.ui.viewmodels;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.entities.Video;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VideosRepository {

    private final MutableLiveData<List<Video>> videos;
    private List<Video> allVideos;


    public VideosRepository() {
        videos = new MutableLiveData<>();
        allVideos = new ArrayList<>();
        loadVideos();
    }

    private void loadVideos() {
        List<Video> videoList = new ArrayList<>();
        Uri image1 = Uri.parse("android.resource://com.example.tech_titans_app/" + R.drawable.image1);
        Uri image2 = Uri.parse("android.resource://com.example.tech_titans_app/" + R.drawable.image2);
        Uri image3 = Uri.parse("android.resource://com.example.tech_titans_app/" + R.drawable.image3);
        Uri image4 = Uri.parse("android.resource://com.example.tech_titans_app/" + R.drawable.image4);
        Uri image5 = Uri.parse("android.resource://com.example.tech_titans_app/" + R.drawable.image5);
        Uri image6 = Uri.parse("android.resource://com.example.tech_titans_app/" + R.drawable.image6);
        Uri image7 = Uri.parse("android.resource://com.example.tech_titans_app/" + R.drawable.image7);
        Uri image8 = Uri.parse("android.resource://com.example.tech_titans_app/" + R.drawable.image8);
        Uri image9 = Uri.parse("android.resource://com.example.tech_titans_app/" + R.drawable.image9);
        Uri image10 = Uri.parse("android.resource://com.example.tech_titans_app/" + R.drawable.image10);
        videoList.add(new Video(image1, "Video 1 Title", "Publisher 1",  image1, "50", "10/10/2020"));
        videoList.add(new Video(image2, "Video 2 Title", "Publisher 2",  image2, "60", "10/10/2020"));
        videoList.add(new Video(image3, "Video 3 Title", "Publisher 3",  image3, "70", "10/10/2020"));
        videoList.add(new Video(image4, "Video 4 Title", "Publisher 4",  image4, "80", "10/10/2020"));
        videoList.add(new Video(image5, "Video 5 Title", "Publisher 4",  image2, "3", "10/10/2020"));
        videoList.add(new Video(image6, "Video 6 Title", "Publisher 4",  image2, "9", "10/10/2020"));
        videoList.add(new Video(image7, "Video 7 Title", "Publisher 5",  image2, "17", "10/10/2020"));
        videoList.add(new Video(image8, "Video 8 Title", "Publisher 6",  image2, "22", "10/10/2020"));
        videoList.add(new Video(image9, "Video 9 Title", "Publisher 7",  image2, "25", "10/10/2020"));
        videoList.add(new Video(image10, "Video 10 Title", "Publisher 7",  image2, "77", "10/10/2020"));
        allVideos = videoList;
        videos.setValue(allVideos);
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
}
