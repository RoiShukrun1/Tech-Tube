package com.example.tech_titans_app.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.adapters.VideosListAdapter;
import com.example.tech_titans_app.ui.adapters.commentsAdapter;
import com.example.tech_titans_app.ui.entities.Comment;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.entities.currentVideo;
import com.example.tech_titans_app.ui.mainActivity.MainActivity;
import com.example.tech_titans_app.ui.mainActivity.SearchBarUtils;
import com.example.tech_titans_app.ui.utilities.LoggedIn;
import com.example.tech_titans_app.ui.viewmodels.MainVideoViewModel;


import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class activity_watch_video_page extends AppCompatActivity {
    private MainVideoViewModel videoViewModel;
    private Video thisCurrentVideo;
    private VideosListAdapter adapter;
    private boolean isLiked;
    private boolean isUnliked;
    private boolean isSubscribed = false;
    private LoggedIn loggedIn = LoggedIn.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_watch_video_page);

        videoViewModel = new ViewModelProvider(this).get(MainVideoViewModel.class);
        thisCurrentVideo = currentVideo.getInstance().getCurrentVideo();

        addSearchBarLogic();
        addBottomBarLogic();
        initiateVideoPlayer();
        initiateRelatedVideos();
        addListeners();
        setVideoTitle();
        setVideoDetails();
        setVideoDescription();
        setPublisherInfo();
        updateLikesButtonsUI();
    }

    private void updateUI(LinearLayout profileSection, ImageView profilePicture,
                          TextView logoutText, TextView loginText) {
        if (LoggedIn.getInstance().isLoggedIn()) {
            profileSection.setVisibility(View.VISIBLE);
            loginText.setVisibility(View.GONE);
            Glide.with(this).load(LoggedIn.getInstance()
                    .getLoggedInUser().getProfilePicture()).into(profilePicture);
            logoutText.setText(R.string.logout);
        } else {
            profileSection.setVisibility(View.GONE);
            loginText.setVisibility(View.VISIBLE);
        }
    }

    private void initiateVideoPlayer() {
        VideoView videoView = findViewById(R.id.videoView);

        // Set up MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Set the URI to the VideoView
        videoView.setVideoURI(this.thisCurrentVideo.getVideoUploaded());

        // Start the video
        videoView.start();
    }

    private void initiateRelatedVideos() {
        RecyclerView lstVideos = findViewById(R.id.listVideosVWP);
        lstVideos.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VideosListAdapter();
        lstVideos.setAdapter(adapter);

        videoViewModel.getAllVideos().observe(this, videos -> adapter.setVideos(videos));
    }

    public void addListeners() {

        // Handle click event of the "Like" TextView
        TextView likeTextView = findViewById(R.id.btn_like);
        likeTextView.setText(this.thisCurrentVideo.getLikes());
        likeTextView.setOnClickListener(v -> likeButtonClick());

        // Handle click event of the "Unlike" TextView
        TextView unlikeTextView = findViewById(R.id.btn_unlike);
        unlikeTextView.setOnClickListener(v -> unlikeButtonClick());

        // Handle click event of the "Share" TextView
        TextView shareTextView = findViewById(R.id.btn_share);
        shareTextView.setOnClickListener(v -> shareButtonClick());

        // Handle click event of the "Download" TextView
        TextView downloadTextView = findViewById(R.id.btn_download);
        downloadTextView.setOnClickListener(v -> downloadButtonClick());

        // Handle click event of the like subscribe TextView
        TextView subscribeTextView = findViewById(R.id.btn_subscribe);
        subscribeTextView.setOnClickListener(v -> subscribeButtonClick());

        // Handle click event of the comment collapsed view
        TextView commentCollapsedView = findViewById(R.id.comment_collapsed_view);
        commentCollapsedView.setOnClickListener(v -> openCommentsActivity());

        // Handle click event of pencil click - edit video title
        TextView PencilTextView = findViewById(R.id.editTitle);
        PencilTextView.setOnClickListener(v -> PencilButtonClick());
    }

    public void addSearchBarLogic() {
        new SearchBarUtils(findViewById(android.R.id.content));
        EditText searchInput = findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                videoViewModel.filterVideos(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    public void addBottomBarLogic() {
        TextView homeButton = findViewById(R.id.home);
        homeButton.setOnClickListener(v -> {
            Intent intent =
                    new Intent(activity_watch_video_page.this, MainActivity.class);
            startActivity(intent);
        });

        ImageView addVideoButton = findViewById(R.id.add);
        addVideoButton.setOnClickListener(v -> {
            Intent intent =
                    new Intent(activity_watch_video_page.this,
                            UploadVideoActivity.class);
            startActivity(intent);
        });

        LinearLayout profileSection = findViewById(R.id.profile_section);
        ImageView profilePicture = findViewById(R.id.profile_picture);
        TextView logoutText = findViewById(R.id.logout_text);
        TextView loginText = findViewById(R.id.login);

        updateUI(profileSection, profilePicture, logoutText, loginText);

        profileSection.setOnClickListener(v -> {
            LoggedIn.getInstance().logOut();
            updateUI(profileSection, profilePicture, logoutText, loginText);
        });

        loginText.setOnClickListener(v -> {
            Intent intent =
                    new Intent(activity_watch_video_page.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    public void setPublisherInfo() {
        Uri publisherImageUri = currentVideo.getInstance().getCurrentVideo().getPublisherImage();
        CircleImageView publisherImage = findViewById(R.id.publisher_image_VWP);
        Glide.with(publisherImage.getContext())
                .load(publisherImageUri)
                .into(publisherImage);

        TextView publisherTextView = findViewById(R.id.publisher_name_VWP);
        publisherTextView.setText(this.thisCurrentVideo.getPublisher());
    }

    public void setVideoTitle() {
        TextView titleTextView = findViewById(R.id.video_title);
        titleTextView.setText(thisCurrentVideo.getTitle());
    }

    public void setVideoDetails() {
        TextView titleTextView = findViewById(R.id.video_details);
        String details;
        details = this.thisCurrentVideo.getViews() + " views " + this.thisCurrentVideo.getDate();
        titleTextView.setText(details);
    }

    public void setVideoDescription() {
        TextView descriptionTextView = findViewById(R.id.video_description);
        descriptionTextView.setText(thisCurrentVideo.getInfo());
    }

    public void getLikesStatus() {
        if (!loggedIn.isLoggedIn()) {
            isUnliked = false;
            isLiked = false;
        } else {
            isUnliked =
                    thisCurrentVideo.getUsersUnlikedId().contains(loggedIn.getLoggedInUser().getId());
            isLiked =
                    thisCurrentVideo.getUsersLikedId().contains(loggedIn.getLoggedInUser().getId());
        }
    }

    // Method to handle the "Like" click event
    public void likeButtonClick() {
        if (loggedIn.isLoggedIn()) {
            handleLike();
        } else {
            showLoginToast("You have to be logged in to mark as liked");
        }
    }

    public void unlikeButtonClick() {
        if (loggedIn.isLoggedIn()) {
            handleUnlike();
        } else {
            showLoginToast("You have to be logged in to mark as disliked");
        }
    }

    private void handleLike() {
        getLikesStatus();
        Integer loggedInUserId = loggedIn.getLoggedInUser().getId();

        if (isUnliked) {
            thisCurrentVideo.getUsersUnlikedId().remove(loggedInUserId);
        }
        if (isLiked) {
            thisCurrentVideo.decrementLikes();
            thisCurrentVideo.getUsersLikedId().remove(loggedInUserId);
        } else {
            thisCurrentVideo.incrementLikes();
            thisCurrentVideo.getUsersLikedId().add(loggedInUserId);
        }

        updateLikesButtonsUI();
    }

    private void handleUnlike() {
        getLikesStatus();
        Integer loggedInUserId = loggedIn.getLoggedInUser().getId();

        if (isLiked) {
            thisCurrentVideo.getUsersLikedId().remove(loggedInUserId);
            thisCurrentVideo.decrementLikes();
        }
        if (isUnliked) {
            thisCurrentVideo.getUsersUnlikedId().remove(loggedInUserId);
        } else {
            thisCurrentVideo.getUsersUnlikedId().add(loggedInUserId);
        }

        updateLikesButtonsUI();
    }

    private void showLoginToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateLikesButtonsUI() {
        getLikesStatus();
        TextView unlikeTextView = findViewById(R.id.btn_unlike);
        Drawable unlikeDrawable = ContextCompat.getDrawable
                (this, isUnliked ? R.drawable.dislike_selected : R.drawable.dislike);
        unlikeTextView.setCompoundDrawablesWithIntrinsicBounds
                (unlikeDrawable, null, null, null);

        TextView likeTextView = findViewById(R.id.btn_like);
        Drawable likeDrawable = ContextCompat.getDrawable
                (this, isLiked ? R.drawable.like_selected : R.drawable.like);
        likeTextView.setCompoundDrawablesWithIntrinsicBounds
                (likeDrawable, null, null, null);

        likeTextView.setText(String.valueOf(thisCurrentVideo.getLikes()));
    }

    // Method to handle the "Download" click event
    public void downloadButtonClick() {
        Toast.makeText(this,
                "Will be implemented after data will migrate to server side." +
                        " needs an http/s path",
                Toast.LENGTH_LONG).show();
    }

    // Method to handle the "Download" click event
    public void subscribeButtonClick() {
        if (!loggedIn.isLoggedIn()) {
            showLoginToast("You have to be logged in to subscribe");
            return;
        }
        isSubscribed =
                loggedIn.getLoggedInUser().getSubscriptions()
                        .contains(thisCurrentVideo.getPublisher());

        if (isSubscribed) {
            loggedIn.getLoggedInUser().getSubscriptions()
                    .remove(thisCurrentVideo.getPublisher());
        } else {
            loggedIn.getLoggedInUser().getSubscriptions()
                    .add(thisCurrentVideo.getPublisher());
        }

        setSubscribeUI();
    }

    public void setSubscribeUI() {
        Button subscribeButton = findViewById(R.id.btn_subscribe);
        if (isSubscribed) {
            // Subscribed state: text color black, background white, text "Subscribed"
            subscribeButton.setTextColor(Color.BLACK);
            subscribeButton.setBackgroundColor(Color.WHITE);
            subscribeButton.setText(getString(R.string.subscribed));
        } else {
            // Not subscribed state: text color white, background black, text "Subscribe"
            subscribeButton.setTextColor(Color.WHITE);
            subscribeButton.setBackgroundColor(Color.BLACK);
            subscribeButton.setText(getString(R.string.subscribe));
        }
    }

    public void shareButtonClick() {
        String s = "android.resource://"
                + getPackageName() + "/" + this.thisCurrentVideo.getVideoUploaded();
        // Create the share intent
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, s);

        // Start the chooser activity
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    public void PencilButtonClick() {
        if (loggedIn.getLoggedInUser() == null) {
            Toast.makeText(this,
                    "You have to be logged in to edit the video title",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (thisCurrentVideo.getPublisher().equals(loggedIn.getLoggedInUser().getUsername())) {
            // Inflate the dialog layout
            LayoutInflater inflater = LayoutInflater.from(this);
            View dialogView =
                    inflater.inflate(R.layout.watch_page_video_dialog_edit_title, null);

            // Get the EditText from the dialog layout
            EditText editTitleInput = dialogView.findViewById(R.id.dialog_edit_title_input);

            // Set the current title in the EditText
            String currentTitle = thisCurrentVideo.getTitle();
            editTitleInput.setText(currentTitle);

            // Build the dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView)
                    .setTitle("Edit Video Title")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Update the video title with the new value
                        String newTitle = editTitleInput.getText().toString();
                        thisCurrentVideo.setTitle(newTitle);
                        setVideoTitle();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            // Show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Toast.makeText(this,
                    "You are not the publisher of this video",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void openCommentsActivity() {
        Intent intent =
                new Intent(activity_watch_video_page.this, CommentsActivity.class);
        startActivity(intent);
    }
}