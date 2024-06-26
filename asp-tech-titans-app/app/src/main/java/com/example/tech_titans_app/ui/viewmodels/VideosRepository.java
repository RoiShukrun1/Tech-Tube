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
        Uri publisherImage1 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.drawable.rick_astely);
        Uri publisherImage2 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.drawable.rotem);
        Uri publisherImage3 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.drawable.shahar);
        Uri publisherImage4 = Uri.parse("android.resource://com.example.tech_titans_app/"
                + R.drawable.omer_adam);

        videoList.add(new Video(1, video1, image1, "Digitalization; Where to start",
                "Rick Astley", publisherImage1, "500", "2021-10-01",
                "An introductory guide to digitalization, outlining the essential steps to begin your digital transformation journey effectively.",
                null,
                "sport", new ArrayList<>(), "10"));
        videoList.add(new Video(2, video2, image2, "Technology Background Video Loop For Website",
                "רותם שפירו", publisherImage2, "20", "2021-10-01",
                "A seamless technology-themed background video loop perfect for enhancing the visual appeal of your website.",
                null,
                "sport", new ArrayList<>(), "20"));
        videoList.add(new Video(3, video3, image3, "10 MINUTE DINNER! The Best Honey Garlic Chicken Recipe",
                "Omer Adam", publisherImage3, "999", "2021-10-01",
                "A quick and delicious honey garlic chicken recipe that can be prepared in just 10 minutes, perfect for busy evenings.",
                null,
                "sport", new ArrayList<>(), "30"));
        videoList.add(new Video(4, video4, image4, "Road Nature video clip 4k",
                "Shahar Saul", publisherImage4, "3", "2021-10-01",
                "A stunning 4K video clip showcasing the serene beauty of nature along a scenic road, ideal for relaxation and inspiration.",
                null,
                "sport", new ArrayList<>(), "3"));
        videoList.add(new Video(5, video5, image5, "How to make PERFECT CHURROS with Hot Chocolate",
                "Omer Adam", publisherImage4, "78", "2024-03-13",
                "A step-by-step tutorial on making perfect churros, accompanied by a rich and indulgent hot chocolate dip",
                null,
                "sport", new ArrayList<>(), "50"));
        videoList.add(new Video(6, video6, image6, "Futuristic interface - HUD sound effects",
                "Omer Adam", publisherImage4, "80", "2024-03-13",
                "A collection of high-quality sound effects designed for futuristic interfaces and heads-up displays (HUDs).",
                null,
                "sport", new ArrayList<>(), "60"));
        videoList.add(new Video(7, video7, image7, "Sci-fi Hologram UI Sound Design",
                "Omer Adam", publisherImage4, "1", "2024-03-13",
                "An immersive sound design collection featuring sci-fi hologram user interface (UI) effects, perfect for enhancing futuristic projects.",
                null,
                "sport", new ArrayList<>(), "1"));
        videoList.add(new Video(8, video8, image8, "TASTY CHICKEN CURRY - Easy food recipes for dinner to make at home",
                "Omer Adam", publisherImage4, "90", "2024-03-13",
                "A simple yet flavorful chicken curry recipe that's easy to make at home, offering a delicious dinner option.",
                null,
                "sport", new ArrayList<>(), "80"));
        videoList.add(new Video(9, video9, image9, "short Sunset Video Clip (4K) Philippines",
                "Omer Adam", publisherImage4, "25", "10/10/2020",
                "A breathtaking short video clip capturing the vibrant hues of a sunset in the Philippines, presented in stunning 4K resolution.",
                null,
                "sport", new ArrayList<>(), "20"));
        videoList.add(new Video(10, video10, image10, "Nature Beautiful short video",
                "Omer Adam", publisherImage4, "200", "2024-03-13",
                "A brief yet captivating video showcasing the serene beauty of nature, perfect for relaxation and mindfulness.",
                null,
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
