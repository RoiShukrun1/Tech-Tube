package com.example.tech_titans_app.ui.entities;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.CommentsActivity;
import com.example.tech_titans_app.ui.viewmodels.commentsViewModel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Entity
public class Video implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int image;
    private String title;
    private String publisher;
    private int publisherImage;
    private String views;
    private CharSequence date;
    private String info;
    private int videoUrl;
    private List<Comment> comments;

    public Video() {
        this.image = R.drawable.image1;
    }


    public Video(int image, String title, String publisher, int publisherImage, String views, CharSequence date) {
        this.image = image;
        this.title = title;
        this.publisher = publisher;
        this.publisherImage = publisherImage;
        this.views = views;
        this.date = date;
        this.comments = new ArrayList<>();
    }

    public Video(int image, String title, String publisher, int publisherImage, String views, CharSequence date, String info) {
        this.image = image;
        this.title = title;
        this.publisher = publisher;
        this.publisherImage = publisherImage;
        this.views = views;
        this.date = date;
        this.info = info;
        this.comments = new ArrayList<>();
    }

    public Video(int image, String title, String publisher,
                 int publisherImage, String views,
                 CharSequence date, String info, int videoUrl) {
        this.image = image;
        this.title = title;
        this.publisher = publisher;
        this.publisherImage = publisherImage;
        this.views = views;
        this.date = date;
        this.info = info;
        this.videoUrl = videoUrl;
        this.comments = new ArrayList<>();
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getPublisherImage() {
        return publisherImage;
    }

    public String getViews() {
        return views;
    }

    public CharSequence getDate() {
        return date;
    }

    public String getInfo() {
        return info;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublisherImage(int publisherImage) {
        this.publisherImage = publisherImage;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public void setDate(CharSequence date) {
        this.date = date;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(int videoUrl) {
        this.videoUrl = videoUrl;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public void addComment(String comment) {
        int numberOfComments = comments.size();
        Comment newComment = new Comment(numberOfComments + 1,
                0, "Aviel Segev",
                Video.getTodayDate(), comment, R.drawable.image2, this);
        comments.add(newComment);
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
}
