package com.example.tech_titans_app.ui.entities;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    private List<Integer> usersLikedId;
    private List<Integer> usersUnlikedId;

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
        this.usersLikedId = new ArrayList<>();
        this.usersUnlikedId = new ArrayList<>();
    }

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
        this.usersLikedId = new ArrayList<>();
        this.usersUnlikedId = new ArrayList<>();
    }

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
        this.usersLikedId = new ArrayList<>();
        this.usersUnlikedId = new ArrayList<>();
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
        this.usersLikedId = new ArrayList<>();
        this.usersUnlikedId = new ArrayList<>();
    }

    // Getters and setters for user liked and unliked IDs
    public List<Integer> getUsersLikedId() {
        return usersLikedId;
    }

    public void setUsersLikedId(List<Integer> usersLikedId) {
        this.usersLikedId = usersLikedId;
    }

    public List<Integer> getUsersUnlikedId() {
        return usersUnlikedId;
    }

    public void setUsersUnlikedId(List<Integer> usersUnlikedId) {
        this.usersUnlikedId = usersUnlikedId;
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

    public CharSequence getDate() {
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
            int numberOfComments = comments.size();
            Comment newComment = new Comment(numberOfComments + 1,
                    0, loggedIn.getLoggedInUser().getUsername(),
                    Video.getTodayDate(), comment,
                    loggedIn.getLoggedInUser().getProfilePicture(), this);
            comments.add(newComment);
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
    }

    public void decrementViews() {
        int numOfViews = Integer.parseInt(this.views);
        numOfViews--;
        this.views = String.valueOf(numOfViews);
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
    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDescription() {
        return description;
    }

    public List<Video> getRelatedVideos() {
        return relatedVideos;
    }

    public String getPlaylist() {
        return playlist;
    }
}
