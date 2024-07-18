package com.example.tech_titans_app.ui.entities;

import android.content.Context;
import android.net.Uri;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.tech_titans_app.ui.AppContext;
import com.example.tech_titans_app.ui.api.PatchReqBody;
import com.example.tech_titans_app.ui.api.VideosAPI;
import com.example.tech_titans_app.ui.utilities.LoggedIn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Entity
public class Video {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Uri videoUploaded;
    private Uri thumbnail;
    private String title;
    private String publisher;
    private Uri publisherImage;
    private String views;
    private String date;
    private String description;
    private List<Video> relatedVideos;
    private String playlist;
    private List<Comment> comments;
    private String likes;
    private List<String> usersLikes;
    private List<String> usersUnlikes;

    @Ignore
    // Constructor for a simple Video object
    public Video(int id ,Uri thumbnail, String title, String publisher, Uri publisherImage, String views, String date) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.publisher = publisher;
        this.publisherImage = publisherImage;
        this.views = views;
        this.date = date;
        this.comments = new ArrayList<>();
        this.usersLikes = new ArrayList<>();
        this.usersUnlikes = new ArrayList<>();
    }

    @Ignore
    // Constructor with description
    public Video(Uri thumbnail, String title, String publisher, Uri publisherImage, String views, String date, String description) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.publisher = publisher;
        this.publisherImage = publisherImage;
        this.views = views;
        this.date = date;
        this.description = description;
        this.comments = new ArrayList<>();
        this.usersLikes = new ArrayList<>();
        this.usersUnlikes = new ArrayList<>();
    }

    @Ignore
    // Constructor with video upload URI and likes count
    public Video(Uri thumbnail, String title, String publisher,
                 Uri publisherImage, String views,
                 String date, String description, Uri videoUploaded, int likes) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.publisher = publisher;
        this.publisherImage = publisherImage;
        this.views = views;
        this.date = date;
        this.description = description;
        this.videoUploaded = videoUploaded;
        this.comments = new ArrayList<>();
        this.likes = String.valueOf(likes);
        this.usersLikes = new ArrayList<>();
        this.usersUnlikes = new ArrayList<>();
    }

    // Constructor for a detailed Video object
    public Video(int id, Uri videoUploaded, Uri thumbnail, String title, String publisher,
                 Uri publisherImage, String views, String date, String description,
                 List<Video> relatedVideos, String playlist,
                 List<Comment> comments, String likes) {
        this.id = id;
        this.videoUploaded = videoUploaded;
        this.thumbnail = thumbnail;
        this.title = title;
        this.publisher = publisher;
        this.publisherImage = publisherImage;
        this.views = views;
        this.date = date;
        this.description = description;
        this.relatedVideos = relatedVideos;
        this.playlist = playlist;
        this.comments = comments;
        this.likes = likes;
        this.usersLikes = new ArrayList<>();
        this.usersUnlikes = new ArrayList<>();
    }

    // Getters and setters for user liked and unliked IDs
    public List<String> getUsersLikes() {
        return usersLikes;
    }

    public void setUsersLikes(List<String> usersLikes) {
        this.usersLikes = usersLikes;
    }

    public List<String> getUsersUnlikes() {
        return usersUnlikes;
    }

    public void setUsersUnlikes(List<String> usersUnlikes) {
        this.usersUnlikes = usersUnlikes;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    // Getters and setters for Video properties
    public int getId() {
        return id;
    }

    public Uri getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public Uri getPublisherImage() {
        return publisherImage;
    }

    public String getViews() {
        return views;
    }

    public String getDate() {
        return date;
    }

    public String getInfo() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(Uri image) {
        this.thumbnail = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublisherImage(Uri publisherImage) {
        this.publisherImage = publisherImage;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setInfo(String description) {
        this.description = description;
    }

    public Uri getVideoUploaded() {
        return videoUploaded;
    }

    public void setVideoUploaded(Uri videoUploaded) {
        this.videoUploaded = videoUploaded;
    }

    // Methods for managing comments
    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(String comment) {
        LoggedIn loggedIn = LoggedIn.getInstance();
        if (loggedIn.getLoggedInUser() != null) {

            int highestID;
            if (comments.isEmpty()) {
                highestID = 0;
            } else {
                highestID = comments.get(0).getId();
            }

            Comment newComment = new Comment(highestID + 1,
                    0, loggedIn.getLoggedInUser().getUsername(),
                    comment, Video.getTodayDate(),
                    Uri.parse(loggedIn.getLoggedInUser().getImage()), this);
            comments.add(0, newComment);
            newComment.addCommentToDB();
        }
    }

    // Static method to get today's date
    public static String getTodayDate() {
        // Get a Calendar instance
        Calendar calendar = Calendar.getInstance();

        // Get current date in the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    // Methods to increment and decrement views count
    public void incrementViews() {
        int numOfViews = Integer.parseInt(this.views);
        numOfViews++;
        this.views = String.valueOf(numOfViews);

        // Update the views count in the database
        PatchReqBody newViewsParam = new PatchReqBody("views", this.views);
        Context appContext = AppContext.getContext();
        VideosAPI videosAPI = new VideosAPI(appContext);
        videosAPI.updateVideoById(String.valueOf(this.id), newViewsParam);
    }

    // Methods to increment and decrement likes count
    public void incrementLikes() {
        int numOfViews = Integer.parseInt(this.likes);
        numOfViews++;
        this.likes = String.valueOf(numOfViews);
    }

    public void decrementLikes() {
        int numOfViews = Integer.parseInt(this.likes);
        numOfViews--;
        this.likes = String.valueOf(numOfViews);
    }

    // Getters and setters for likes and other properties
    public String getLikes()
    {
        if (likes == null) {
            likes = String.valueOf(usersLikes.size());
        }
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Video> getRelatedVideos() {
        return relatedVideos;
    }

    public String getPlaylist() {
        return playlist;
    }


    public void setThumbnail(Uri thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    public void setRelatedVideos(List<Video> relatedVideos) {
        this.relatedVideos = relatedVideos;
    }
}
