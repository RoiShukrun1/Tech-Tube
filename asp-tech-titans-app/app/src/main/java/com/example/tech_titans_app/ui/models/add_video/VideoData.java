package com.example.tech_titans_app.ui.models.add_video;

import android.app.Application;
import android.net.Uri;
import android.view.View;

import java.util.List;

    public class VideoData extends Application {
        private int id;
        private Uri videoUploaded;
        private View thumbnail;
        private String title;
        private String publisher;
        private View publisherImage;
        private int views;
        private String date;
        private String description;
        private List<Integer> relatedVideos;
        private String playlist;
        private List<String> comments;
        // Constructor
        public VideoData(int id, Uri videoUri, View imgUrl, String videoTitle, String publisher,
                         View publisherImg, int views, String date, String info,
                              List<Integer> relatedVideos, String playlist, List<String> comments) {
            this.id = id;
            this.videoUploaded = videoUri;
            this.thumbnail = imgUrl;
            this.title = videoTitle;
            this.publisher = publisher;
            this.publisherImage = publisherImg;
            this.views = views;
            this.date = date;
            this.description = info;
            this.relatedVideos = relatedVideos;
            this.playlist = playlist;
            this.comments = comments;
        }

        public String getTitle() {
            return title;
        }

        public Uri getVideoUri() {
            return videoUploaded;
        }

        public View getImgUrl() {
            return thumbnail;
        }

        public View getPublisherImg() {
            return publisherImage;
        }

        public String getPublisher() {
            return publisher;
        }

        public int getViews() {
            return views;
        }

        public String getDate() {
            return date;
        }

        public String getInfo() {
            return description;
        }

        public List<Integer> getRelatedVideos() {
            return relatedVideos;
        }

        public String getPlaylist() {
            return playlist;
        }

        public List<String> getComments() {
            return comments;
        }

        public int getId() {
            return id;
        }

    }
