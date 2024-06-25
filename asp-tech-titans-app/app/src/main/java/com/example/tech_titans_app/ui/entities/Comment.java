package com.example.tech_titans_app.ui.entities;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.utilities.LoggedIn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Comment implements Serializable {
    public Video parentVideo;
    private int id;
    private int numberOfLikes;
    private Uri userImage;
    private String publisherUsername;
    private String comment;
    private String date;
    private boolean isLiked;
    private boolean isUnliked;
    private List<Integer> usersLikedId;
    private List<Integer> usersUnlikedId;
    private final LoggedIn loggedIn = LoggedIn.getInstance();

    public Comment(int id, int numberOfLikes, String publisherUsername,
                   String comment, String date, Uri userImagePath, Video parent) {
        this.parentVideo = parent;
        this.id = id;
        this.numberOfLikes = numberOfLikes;
        this.publisherUsername = publisherUsername;
        this.comment = comment;
        this.date = date;
        this.userImage = userImagePath;
        this.usersLikedId = new ArrayList<>();
        this.usersUnlikedId = new ArrayList<>();
    }

    public void getLikesStatus() {
        if (!loggedIn.isLoggedIn()) {
            isUnliked = false;
            isLiked = false;
        } else {
            isUnliked =
                    this.getUsersUnlikedId().contains(loggedIn.getLoggedInUser().getId());
            isLiked =
                    this.getUsersLikedId().contains(loggedIn.getLoggedInUser().getId());
        }
    }

    public void likeButtonClick(TextView likeTextView, TextView unlikeTextView, Context context) {
        if (!loggedIn.isLoggedIn()) {
            Toast.makeText(context,
                    "You have to be logged in to press the like button",
                    Toast.LENGTH_LONG).show();
        } else {
            getLikesStatus();
            Integer loggedInUserId = loggedIn.getLoggedInUser().getId();
            if (isUnliked) {
                this.getUsersUnlikedId().remove(loggedInUserId);
            }
            if (isLiked) {
                this.decrementLikes();
                this.getUsersLikedId().remove(loggedInUserId);
            } else {
                this.incrementLikes();
                this.getUsersLikedId().add(loggedInUserId);
            }
            updateLikesButtonsUI(likeTextView, unlikeTextView);
        }
    }

    public void unlikeButtonClick(TextView likeTextView,
                                  TextView unlikeTextView, Context context) {
        if (!loggedIn.isLoggedIn()) {
            Toast.makeText(context,
                    "You have to be logged in to press the unlike button",
                    Toast.LENGTH_LONG).show();
        } else {
            getLikesStatus();
            Integer loggedInUserId = loggedIn.getLoggedInUser().getId();

            if (isLiked) {
                this.getUsersLikedId().remove(loggedInUserId);
                this.decrementLikes();
            }
            if (isUnliked) {
                this.getUsersUnlikedId().remove(loggedInUserId);
            } else {
                this.getUsersUnlikedId().add(loggedInUserId);
            }

            updateLikesButtonsUI(likeTextView, unlikeTextView);
        }
    }

    public void updateLikesButtonsUI(TextView likeTextView, TextView unlikeTextView) {
        getLikesStatus();
        Drawable unlikeDrawable = ContextCompat.getDrawable
                (unlikeTextView.getContext(),
                        isUnliked ? R.drawable.dislike_selected : R.drawable.dislike);
        unlikeTextView.setCompoundDrawablesWithIntrinsicBounds
                (unlikeDrawable, null, null, null);

        Drawable likeDrawable = ContextCompat.getDrawable
                (likeTextView.getContext(), isLiked ? R.drawable.like_selected : R.drawable.like);
        likeTextView.setCompoundDrawablesWithIntrinsicBounds
                (likeDrawable, null, null, null);

        likeTextView.setText(String.valueOf(this.numberOfLikes));
    }

    public void deleteComment(Context context) {
        if (!loggedIn.isLoggedIn()) {
            Toast.makeText(context,
                    "You have to be logged in to delete a comment",
                    Toast.LENGTH_LONG).show();
        } else if (this.publisherUsername.equals(loggedIn.getLoggedInUser().getUsername())) {
            this.parentVideo.getComments().remove(this);
        } else {
            Toast.makeText(context,
                    "You are not the publisher of this comment",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void pencilCommentButtonClick(Context context, TextView CommentTextView) {
        if (loggedIn.getLoggedInUser() == null) {
            Toast.makeText(context,
                    "You have to be logged in to edit the comment content",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (this.getPublisherUsername().equals(loggedIn.getLoggedInUser().getUsername())) {
            // Inflate the dialog layout
            LayoutInflater inflater = LayoutInflater.from(context);
            View dialogView =
                    inflater.inflate(R.layout.watch_page_video_dialog_edit_title, null);

            // Get the EditText from the dialog layout
            EditText editCommentInput = dialogView.findViewById(R.id.dialog_edit_title_input);

            // Set the current title in the EditText
            String currentComment = this.getComment();
            editCommentInput.setText(currentComment);

            // Build the dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(dialogView)
                    .setTitle("Edit Comment content")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Update the video title with the new value
                        String newContent = editCommentInput.getText().toString();
                        this.setComment(newContent);
                        CommentTextView.setText(this.getComment());
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            // Show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Toast.makeText(context,
                    "You are not the publisher of this comment",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void incrementLikes() {
        this.numberOfLikes += 1;
    }

    public void decrementLikes() {
        this.numberOfLikes -= 1;
    }

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

    public void setId(int id) {
        this.id = id;
    }

    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public void setImagePath(Uri imagePath) {
        this.userImage = imagePath;
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

    public Uri getImage() {
        return userImage;
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
}
