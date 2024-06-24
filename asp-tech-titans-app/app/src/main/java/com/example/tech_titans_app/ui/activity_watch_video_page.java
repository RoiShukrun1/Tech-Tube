package com.example.tech_titans_app.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.adapters.VideosListAdapter;
import com.example.tech_titans_app.ui.adapters.commentsAdapter;
import com.example.tech_titans_app.ui.entities.Comment;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.entities.currentVideo;
import com.example.tech_titans_app.ui.utilities.LoggedIn;
import com.example.tech_titans_app.ui.viewmodels.MainVideoViewModel;


import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;


public class activity_watch_video_page extends AppCompatActivity {

    private Video thisCurrentVideo;
    private VideosListAdapter adapter;
    private boolean isLiked = false;
    private boolean isUnliked = false;
    private boolean isSubscribed = false;
    private LoggedIn loggedIn = LoggedIn.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_watch_video_page);

        initiateVideoPlayer();
        initiateRelatedVideos();
        addListeners();
        setVideoTitle();
        setVideoDetails();
        setVideoDescription();
    }

    private void initiateVideoPlayer() {
        VideoView videoView = findViewById(R.id.videoView);

        Uri image1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.image1);
        Uri video4 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video4);
        thisCurrentVideo = new Video(image1, "Video 1 Title",
                "Publisher 1", image1,
                "100", "10/10/2020", "The world is changing.",
                video4, 7);

        currentVideo.getInstance().setCurrentVideo(thisCurrentVideo);

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

        MainVideoViewModel videoViewModel = new ViewModelProvider(this).get(MainVideoViewModel.class);
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

    // Method to handle the "Like" click event
    public void likeButtonClick() {
        if (loggedIn.getLoggedInUser() != null) {
            TextView likeTextView = findViewById(R.id.btn_like);
            Drawable likeDrawable =
                    ContextCompat.getDrawable(this, isLiked ? R.drawable.like : R.drawable.like_selected);
            likeTextView.setCompoundDrawablesWithIntrinsicBounds(likeDrawable, null, null, null);
            if (isUnliked) {
                this.unlikeButtonClick();
            }
            if (isLiked) {
                this.thisCurrentVideo.decrementLikes();
            } else {
                this.thisCurrentVideo.incrementLikes();
            }
            likeTextView.setText(this.thisCurrentVideo.getLikes());
            isLiked = !isLiked;  // Toggle the state
        } else {
            Toast.makeText(this,
                    "You have to be logged in to mark as liked",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Method to handle the "Unlike" click event
    public void unlikeButtonClick() {
        if (loggedIn.getLoggedInUser() != null) {
            TextView unlikeTextView = findViewById(R.id.btn_unlike);
            Drawable unlikeDrawable =
                    ContextCompat.getDrawable(this, isUnliked ? R.drawable.dislike : R.drawable.dislike_selected);
            if (unlikeDrawable != null) {
                unlikeTextView.setCompoundDrawablesWithIntrinsicBounds(unlikeDrawable, null, null, null);
            }
            if (isLiked) {
                this.likeButtonClick();
            }
            isUnliked = !isUnliked;  // Toggle the state
        } else {
            Toast.makeText(this,
                    "You have to be logged in to mark dis-like",
                    Toast.LENGTH_SHORT).show();
        }
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
        Button subscribeButton = findViewById(R.id.btn_subscribe);
        isSubscribed = !isSubscribed;
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

    public void PencilButtonClick(){
        if (loggedIn.getLoggedInUser() == null) {
            Toast.makeText(this,
                    "You have to be logged in to edit the video title",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (thisCurrentVideo.getPublisher().equals(loggedIn.getLoggedInUser().getUsername())){
            Toast.makeText(this,
                    "OK",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,
                    "You are not the publisher of this video",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void openCommentsActivity() {
        Intent intent =
                new Intent(activity_watch_video_page.this, CommentsActivity.class);
//        intent.putExtra("video", this.currentVideo);
        startActivity(intent);
    }
}