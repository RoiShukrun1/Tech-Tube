package com.example.tech_titans_app.ui.entities;

import com.example.tech_titans_app.ui.viewmodels.VideosRepository;

/**
 * Singleton class to manage the current video being viewed.
 */
public class currentVideo {
    private static currentVideo instance;
    private Video currentVideo;

    /**
     * Private constructor to initialize the current video.
     * If there are no videos in the repository, the current video remains null.
     */
    private currentVideo() {
        if (VideosRepository.getInstance().getAllVideos().getValue() == null) {
            return;
        }
        currentVideo = VideosRepository.getInstance().getAllVideos().getValue().get(0);
    }

    /**
     * Gets the single instance of the currentVideo class.
     *
     * @return The singleton instance of the currentVideo class.
     */
    public static synchronized currentVideo getInstance() {
        if (instance == null) {
            instance = new currentVideo();
        }
        return instance;
    }

    /**
     * Gets the current video being viewed.
     *
     * @return The current video.
     */
    public Video getCurrentVideo() {
        return currentVideo;
    }

    /**
     * Sets the current video being viewed.
     *
     * @param currentVideo The video to set as the current video.
     */
    public void setCurrentVideo(Video currentVideo) {
        this.currentVideo = currentVideo;
    }
}
