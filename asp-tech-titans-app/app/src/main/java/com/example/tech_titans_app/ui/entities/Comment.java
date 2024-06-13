package com.example.tech_titans_app.ui.entities;

import java.util.List;

public class Comment {

    private int id;
    private int numberOfLikes;
    private int userImagePath;
    private List<Integer> usersLikeId;
    private List<Integer> usersunLikeId;
    private String comment;
    private String date;

    public Comment(int id, int numberOfLikes, String comment, String date) {
        this.id = id;
        this.numberOfLikes = numberOfLikes;
        this.comment = comment;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public void setImagePath(int imagePath) {
        this.userImagePath = imagePath;
    }

    public void setUsersLikeId(List<Integer> usersLikeId) {
        this.usersLikeId = usersLikeId;
    }

    public void setUsersunLikeId(List<Integer> usersunLikeId) {
        this.usersunLikeId = usersunLikeId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    public int getImagePath() {
        return userImagePath;
    }

    public List<Integer> getUsersLikeId() {
        return usersLikeId;
    }

    public List<Integer> getUsersunLikeId() {
        return usersunLikeId;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }
}
