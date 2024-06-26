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

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a comment on a video.
 */
public class Comment {
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

    /**
     * Constructor for creating a Comment object.
     *
     * @param id The ID of the comment.
     * @param numberOfLikes The number of likes the comment has.
     * @param publisherUsername The username of the publisher.
     * @param comment The content of the comment.
     * @param date The date the comment was posted.
     * @param userImagePath The URI of the user's image.
     * @param parent The parent video of the comment.
     */
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

    /**
     * Retrieves the like and unlike status of the comment for the logged-in user.
     */
    public void getLikesStatus() {
        if (!loggedIn.isLoggedIn()) {
            isUnliked = false;
            isLiked = false;
        } else {
            isUnliked = this.getUsersUnlikedId().contains(loggedIn.getLoggedInUser().getId());
            isLiked = this.getUsersLikedId().contains(loggedIn.getLoggedInUser().getId());
        }
    }

    /**
     * Handles the like button click event.
     *
     * @param likeTextView The TextView for the like button.
     * @param unlikeTextView The TextView for the unlike button.
     * @param context The context of the application.
     */
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

    /**
     * Handles the unlike button click event.
     *
     * @param likeTextView The TextView for the like button.
     * @param unlikeTextView The TextView for the unlike button.
     * @param context The context of the application.
     */
    public void unlikeButtonClick(TextView likeTextView, TextView unlikeTextView, Context context) {
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

    /**
     * Updates the UI of the like and unlike buttons.
     *
     * @param likeTextView The TextView for the like button.
     * @param unlikeTextView The TextView for the unlike button.
     */
    public void updateLikesButtonsUI(TextView likeTextView, TextView unlikeTextView) {
        getLikesStatus();
        Drawable unlikeDrawable = ContextCompat.getDrawable(unlikeTextView.getContext(),
                isUnliked ? R.drawable.dislike_selected : R.drawable.dislike);
        unlikeTextView.setCompoundDrawablesWithIntrinsicBounds(unlikeDrawable, null, null, null);

        Drawable likeDrawable = ContextCompat.getDrawable(likeTextView.getContext(),
                isLiked ? R.drawable.like_selected : R.drawable.like);
        likeTextView.setCompoundDrawablesWithIntrinsicBounds(likeDrawable, null, null, null);

        likeTextView.setText(String.valueOf(this.numberOfLikes));
    }

    /**
     * Deletes the comment if the logged-in user is the publisher.
     *
     * @param context The context of the application.
     */
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

    /**
     * Handles the pencil button click event for editing the comment content.
     *
     * @param context The context of the application.
     * @param CommentTextView The TextView of the comment content.
     */
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
            View dialogView = inflater.inflate(R.layout.watch_page_video_dialog_edit_title, null);

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

    /**
     * Increments the number of likes for the comment.
     */
    public void incrementLikes() {
        this.numberOfLikes += 1;
    }

    /**
     * Decrements the number of likes for the comment.
     */
    public void decrementLikes() {
        this.numberOfLikes -= 1;
    }

    /**
     * Gets the list of user IDs who liked the comment.
     *
     * @return The list of user IDs who liked the comment.
     */
    public List<Integer> getUsersLikedId() {
        return usersLikedId;
    }

    /**
     * Sets the list of user IDs who liked the comment.
     *
     * @param usersLikedId The list of user IDs who liked the comment.
     */
    public void setUsersLikedId(List<Integer> usersLikedId) {
        this.usersLikedId = usersLikedId;
    }

    /**
     * Gets the list of user IDs who unliked the comment.
     *
     * @return The list of user IDs who unliked the comment.
     */
    public List<Integer> getUsersUnlikedId() {
        return usersUnlikedId;
    }

    /**
     * Sets the list of user IDs who unliked the comment.
     *
     * @param usersUnlikedId The list of user IDs who unliked the comment.
     */
    public void setUsersUnlikedId(List<Integer> usersUnlikedId) {
        this.usersUnlikedId = usersUnlikedId;
    }

    /**
     * Sets the ID of the comment.
     *
     * @param id The ID of the comment.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the number of likes for the comment.
     *
     * @param numberOfLikes The number of likes.
     */
    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    /**
     * Sets the image path of the user's image.
     *
     * @param imagePath The URI of the user's image.
     */
    public void setImagePath(Uri imagePath) {
        this.userImage = imagePath;
    }

    /**
     * Sets the content of the comment.
     *
     * @param comment The content of the comment.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Sets the date the comment was posted.
     *
     * @param date The date the comment was posted.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the number of likes for the comment.
     *
     * @return The number of likes.
     */
    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    /**
     * Gets the URI of the user's image.
     *
     * @return The URI of the user's image.
     */
    public Uri getImage() {
        return userImage;
    }

    /**
     * Gets the username of the publisher.
     *
     * @return The username of the publisher.
     */
    public String getPublisherUsername() {
        return publisherUsername;
    }

    /**
     * Sets the username of the publisher.
     *
     * @param publisherUsername The username of the publisher.
     */
    public void setPublisherUsername(String publisherUsername) {
        this.publisherUsername = publisherUsername;
    }

    /**
     * Gets the content of the comment.
     *
     * @return The content of the comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Gets the date the comment was posted.
     *
     * @return The date the comment was posted.
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the ID of the comment.
     *
     * @return The ID of the comment.
     */
    public int getId() {
        return id;
    }
}
