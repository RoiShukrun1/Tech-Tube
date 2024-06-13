package com.example.tech_titans_app.ui.entities;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.tech_titans_app.R;

import java.util.List;

public class Comment {
    private int id;
    private int numberOfLikes;
    private int userImagePath;
    private List<Integer> usersLikeId;
    private List<Integer> usersUnlikeId;
    private String publisherUsername;
    private String comment;
    private String date;
    private boolean isLiked = false;
    private boolean isUnliked = false;

    public Comment(int id, int numberOfLikes, String publisherUsername,
                   String comment, String date, int userImagePath) {
        this.id = id;
        this.numberOfLikes = numberOfLikes;
        this.publisherUsername = publisherUsername;
        this.comment = comment;
        this.date = date;
        this.userImagePath = userImagePath;
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
        this.usersUnlikeId = usersunLikeId;
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
        return usersUnlikeId;
    }

    public String getPublisherUsername() {
        return publisherUsername;
    }

    public void setPublisherUsername(String publisherUsername) {
        this.publisherUsername = publisherUsername;
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

    // Method to handle the "Like" click event
    public void likeButtonClick(TextView likeTextView, TextView unlikeTextView) {
        isLiked = !isLiked;
        if (isLiked) {
            Drawable likeDrawable = ContextCompat.getDrawable(unlikeTextView.getContext(),
                    R.drawable.like_selected);
            likeTextView.setCompoundDrawablesWithIntrinsicBounds
                    (likeDrawable, null, null, null);
            incrementLikes();
        } else {
            Drawable likeDrawable = ContextCompat.getDrawable(unlikeTextView.getContext(),
                    R.drawable.like);
            likeTextView.setCompoundDrawablesWithIntrinsicBounds
                    (likeDrawable, null, null, null);
            decrementLikes();
        }
        if (isUnliked) {
            isUnliked = false;
            Drawable unlikeDrawable = ContextCompat.getDrawable(unlikeTextView.getContext(),
                    R.drawable.dislike);
            unlikeTextView.setCompoundDrawablesWithIntrinsicBounds
                    (unlikeDrawable, null, null, null);
        }
    }

    // Method to handle the "Unlike" click event
    public void unlikeButtonClick(TextView unlikeTextView, TextView likeTextView) {
        isUnliked = !isUnliked;
        Drawable unlikeDrawable;
        if (isUnliked) {
            unlikeDrawable = ContextCompat.getDrawable(unlikeTextView.getContext(),
                    R.drawable.dislike_selected);
        } else {
            unlikeDrawable = ContextCompat.getDrawable(unlikeTextView.getContext(),
                    R.drawable.dislike);
        }
        unlikeTextView.setCompoundDrawablesWithIntrinsicBounds
                (unlikeDrawable, null, null, null);
        if (isLiked) {
            isLiked = false;
            Drawable likeDrawable = ContextCompat.getDrawable(unlikeTextView.getContext(),
                    R.drawable.like);
            likeTextView.setCompoundDrawablesWithIntrinsicBounds
                    (likeDrawable, null, null, null);
            decrementLikes();
        }
    }


    public void incrementLikes() {
        this.numberOfLikes += 1;
    }

    public void decrementLikes() {
        this.numberOfLikes -= 1;
    }
}
