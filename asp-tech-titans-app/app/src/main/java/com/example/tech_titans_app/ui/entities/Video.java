package com.example.tech_titans_app.ui.entities;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.content.ContentProviderCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.CommentsActivity;
import com.example.tech_titans_app.ui.utilities.LoggedIn;
import com.example.tech_titans_app.ui.viewmodels.commentsViewModel;

import java.io.Serializable;
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

    public Video(int id ,Uri thumbnail, String title, String publisher, Uri publisherImage, String views, String date) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.publisher = publisher;
        this.publisherImage = publisherImage;
        this.views = views;
        this.date = date;
        this.comments = new ArrayList<>();
    }

    public Video(Uri thumbnail, String title, String publisher, Uri publisherImage, String views, String date, String description) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.publisher = publisher;
        this.publisherImage = publisherImage;
        this.views = views;
        this.date = date;
        this.description = description;
        this.comments = new ArrayList<>();
    }

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
    }

    public Video(int id, Uri videoUploaded, Uri thumbnail, String title, String publisher, Uri publisherImage, String views, String date, String description, List<Video> relatedVideos, String playlist, List<Comment> comments, String likes) {
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
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


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

    public static String getTodayDate() {
        // Get a Calendar instance
        Calendar calendar = Calendar.getInstance();

        // Get current date in the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
    public void incrementViews(){
        int numOfViews = Integer.parseInt(this.views);
        numOfViews++;
        this.views = String.valueOf(numOfViews);
    }
    public void decrementViews(){
        int numOfViews = Integer.parseInt(this.views);
        numOfViews--;
        this.views = String.valueOf(numOfViews);
    }

    public void incrementLikes(){
        int numOfViews = Integer.parseInt(this.likes);
        numOfViews++;
        this.likes = String.valueOf(numOfViews);
    }
    public void decrementLikes(){
        int numOfViews = Integer.parseInt(this.likes);
        numOfViews--;
        this.likes = String.valueOf(numOfViews);
    }

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
