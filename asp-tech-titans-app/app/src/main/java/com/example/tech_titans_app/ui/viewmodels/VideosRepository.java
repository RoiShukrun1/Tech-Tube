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

    // Singleton instance
    private static VideosRepository instance;

    // Private constructor to prevent instantiation
    private VideosRepository() {
        videos = new MutableLiveData<>();
        allVideos = new ArrayList<>();
        loadVideos();
    }

    // Public method to provide access to the instance
    public static synchronized VideosRepository getInstance() {
        if (instance == null) {
            instance = new VideosRepository();
        }
        return instance;
    }

    private void loadVideos() {
        List<Video> videoList = new ArrayList<>();
        Uri image1 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.drawable.image1);
        Uri image2 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.drawable.image2);
        Uri image3 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.drawable.image3);
        Uri image4 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.drawable.image4);
        Uri image5 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.drawable.image5);
        Uri image6 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.drawable.image6);
        Uri image7 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.drawable.image7);
        Uri image8 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.drawable.image8);
        Uri image9 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.drawable.image9);
        Uri image10 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.drawable.image10);
        Uri video1 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.raw.video1);
        Uri video2 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.raw.video2);
        Uri video3 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.raw.video3);
        Uri video4 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.raw.video4);
        Uri video5 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.raw.video5);
        Uri video6 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.raw.video6);
        Uri video7 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.raw.video7);
        Uri video8 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.raw.video8);
        Uri video9 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.raw.video9);
        Uri video10 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.raw.video10);

        videoList.add(new Video(1, video1, image1, "Video 1 Title",
                "Publisher 1", image1, "50", "10/10/2020",
                "This is a video description1.", null,
                "sport", new ArrayList<>(), "10"));
        videoList.add(new Video(2, video2, image2, "Video 2 Title",
                "Publisher 2", image2, "60", "10/10/2020",
                "This is a video description2.", null,
                "sport", new ArrayList<>(), "20"));
        videoList.add(new Video(3, video3, image3, "Video 3 Title",
                "Publisher 3", image3, "70", "10/10/2020",
                "This is a video description3.", null,
                "sport", new ArrayList<>(), "30"));
        videoList.add(new Video(4, video4, image4, "Video 4 Title",
                "Publisher 4", image4, "80", "10/10/2020",
                "This is a video description4.", null,
                "sport", new ArrayList<>(), "40"));
        videoList.add(new Video(5, video5, image5, "Video 5 Title",
<<<<<<< feature-branch-video-watch-page-android
                "Publisher 5", image2, "3", "10/10/2020",
                "This is a video description5.", null,
                "sport", new ArrayList<>(), "50"));
        videoList.add(new Video(6, video6, image6, "Video 6 Title",
                "Publisher 6", image2, "9", "10/10/2020",
                "This is a video description6.", null,
                "sport", new ArrayList<>(), "60"));
        videoList.add(new Video(7, video7, image7, "Video 7 Title",
                "Publisher 7", image2, "17", "10/10/2020",
                "This is a video description7.", null,
                "sport", new ArrayList<>(), "70"));
        videoList.add(new Video(8, video8, image8, "Video 8 Title",
                "Publisher 8", image2, "22", "10/10/2020",
                "This is a video description8.", null,
                "sport", new ArrayList<>(), "80"));
        videoList.add(new Video(9, video9, image9, "Video 9 Title",
                "Publisher 9", image2, "25", "10/10/2020",
                "This is a video description9.", null,
                "sport", new ArrayList<>(), "90"));
        videoList.add(new Video(10, video10, image10, "Video 10 Title",
                "Publisher 10", image2, "77", "10/10/2020",
=======
                "Publisher 4", image2, "3", "10/10/2020",
                "This is a video description5.", null,
                "sport", new ArrayList<>(), "50"));
        videoList.add(new Video(6, video6, image6, "Video 6 Title",
                "Publisher 4", image2, "9", "10/10/2020",
                "This is a video description6.", null,
                "sport", new ArrayList<>(), "60"));
        videoList.add(new Video(7, video7, image7, "Video 7 Title",
                "Publisher 5", image2, "17", "10/10/2020",
                "This is a video description7.", null,
                "sport", new ArrayList<>(), "70"));
        videoList.add(new Video(8, video8, image8, "Video 8 Title",
                "Publisher 6", image2, "22", "10/10/2020",
                "This is a video description8.", null,
                "sport", new ArrayList<>(), "80"));
        videoList.add(new Video(9, video9, image9, "Video 9 Title",
                "Publisher 7", image2, "25", "10/10/2020",
                "This is a video description9.", null,
                "sport", new ArrayList<>(), "90"));
        videoList.add(new Video(10, video10, image10, "Video 10 Title",
                "Publisher 7", image2, "77", "10/10/2020",
>>>>>>> main
                "This is a video description10.", null,
                "sport", new ArrayList<>(), "100"));

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

    public void addVideo(Video video) {
        allVideos.add(video);
        videos.setValue(allVideos);
    }

    public void deleteVideo(Video video) {
        allVideos.remove(video);
        videos.setValue(allVideos);
    }
}
