package com.example.tech_titans_app.ui.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.tech_titans_app.R;

@Entity
public class Video {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int image;
    private String title;
    private String publisher;
    private int publisherImage;
    private String views;
    private CharSequence date;
    private String info;

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
    }

    public Video(int image, String title, String publisher, int publisherImage, String views, CharSequence date, String info) {
        this.image = image;
        this.title = title;
        this.publisher = publisher;
        this.publisherImage = publisherImage;
        this.views = views;
        this.date = date;
        this.info = info;
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
}
