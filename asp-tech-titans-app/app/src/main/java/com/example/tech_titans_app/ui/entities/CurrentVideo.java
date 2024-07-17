package com.example.tech_titans_app.ui.entities;

import androidx.lifecycle.MutableLiveData;

import com.example.tech_titans_app.ui.viewmodels.VideosRepository;

/**
 * Singleton class to manage the current video being viewed.
 */
public class CurrentVideo {
    private static CurrentVideo instance;
    private final MutableLiveData<Video> currentVideo = new MutableLiveData<>();

    /**
     * Private constructor to initialize the current video.
     * If there are no videos in the repository, the current video remains null.
     */
    private CurrentVideo() {
        if (VideosRepository.getInstance().getAllVideos().getValue() == null) {
            return;
        }
        currentVideo.setValue(VideosRepository.getInstance().getAllVideos().getValue().get(0));
    }

    /**
     * Gets the single instance of the currentVideo class.
     *
     * @return The singleton instance of the currentVideo class.
     */
    public static synchronized CurrentVideo getInstance() {
        if (instance == null) {
            instance = new CurrentVideo();
        }
        return instance;
    }

    /**
     * Gets the current video being viewed.
     *
     * @return The current video.
     */
    public MutableLiveData<Video> getCurrentVideo() {
        return currentVideo;
    }

    /**
     * Sets the current video being viewed.
     *
     * @param currentVideo The video to set as the current video.
     */
    public void setCurrentVideo(Video currentVideo) {
        this.currentVideo.setValue(currentVideo);
    }
}
