package com.example.tech_titans_app.ui;

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
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.viewmodels.MainVideoViewModel;

import androidx.core.content.ContextCompat;
import android.graphics.drawable.Drawable;


public class activity_watch_video_page extends AppCompatActivity {

    private Video currentVideo;
    private VideosListAdapter adapter;
    private MainVideoViewModel videoViewModel;
    private boolean isLiked = false;
    private boolean isUnliked = false;
    private boolean isSubscribed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_watch_video_page);

        initiateVideoPlayer();
        initiateRelatedVideos();
        addButtonsListeners();
        setVideoTitle();
        setVideoDetails();
        setvideoDescription();
    }

    private void initiateVideoPlayer() {
        VideoView videoView = findViewById(R.id.videoView);

        // Set the video URI (you can also use a URL or a file path)
        this.currentVideo = new Video(R.drawable.image1, "Video 1 Title",
                "Publisher 1",  R.drawable.image1,
                "1000000", "10/10/2020", "The world is changing.",
                R.raw.video4);

        String videoPath = "android.resource://"
                + getPackageName() + "/" + this.currentVideo.getVideoUrl();

        Uri videoUri = Uri.parse(videoPath);

        // Set up MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Set the URI to the VideoView
        videoView.setVideoURI(videoUri);

        // Start the video
        videoView.start();
    }

    private void initiateRelatedVideos() {
        RecyclerView lstVideos = findViewById(R.id.listVideosVWP);
        lstVideos.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VideosListAdapter();
        lstVideos.setAdapter(adapter);

        videoViewModel = new ViewModelProvider(this).get(MainVideoViewModel.class);
        videoViewModel.getAllVideos().observe(this, videos -> adapter.setVideos(videos));
    }

    public void addButtonsListeners() {

        // Handle click event of the "Like" TextView
        TextView likeTextView = findViewById(R.id.btn_like);
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

    }

    public void setVideoTitle() {
        TextView titleTextView = findViewById(R.id.video_title);
        titleTextView.setText(currentVideo.getTitle());
    }

    public void setVideoDetails() {
        TextView titleTextView = findViewById(R.id.video_details);
        String details;
        details = this.currentVideo.getViews() + " views " + this.currentVideo.getDate();
        titleTextView.setText(details);
    }

    public void setvideoDescription() {
        TextView descriptionTextView = findViewById(R.id.video_description);
        descriptionTextView.setText(currentVideo.getInfo());
    }

    // Method to handle the "Like" click event
    public void likeButtonClick() {
        TextView likeTextView = findViewById(R.id.btn_like);
        Drawable likeDrawable =
                ContextCompat.getDrawable(this, isLiked ? R.drawable.like : R.drawable.like_selected);
        likeTextView.setCompoundDrawablesWithIntrinsicBounds(likeDrawable, null, null, null);
        if(isUnliked) {
            this.unlikeButtonClick();
        }
        isLiked = !isLiked;  // Toggle the state
    }

    // Method to handle the "Unlike" click event
    public void unlikeButtonClick() {
        TextView unlikeTextView = findViewById(R.id.btn_unlike);
        Drawable unlikeDrawable =
                ContextCompat.getDrawable(this, isUnliked ? R.drawable.dislike : R.drawable.dislike_selected);
        if (unlikeDrawable != null) {
            unlikeTextView.setCompoundDrawablesWithIntrinsicBounds(unlikeDrawable, null, null, null);
        }
        if(isLiked) {
            this.likeButtonClick();
        }
        isUnliked = !isUnliked;  // Toggle the state
    }

    // Method to handle the "Share" click event
    public void shareButtonClick() {
        System.out.println("this is share");
    }

    // Method to handle the "Download" click event
    public void downloadButtonClick() {
        Toast.makeText(this,
                "Will be implemented after data will migrate to server side",
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

    public void setCurrentVideo(Video currentVideo) {
        this.currentVideo = currentVideo;
    }

    public Video getCurrentVideo() {
        return currentVideo;
    }

}