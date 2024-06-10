package com.example.tech_titans_app.ui;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_watch_video_page);

        initiateVideoPlayer();
        addButtonsListeners();
        setVideoTitle();
        setvideoDescription();
    }

    private void initiateVideoPlayer() {
        VideoView videoView = findViewById(R.id.videoView);

        // Set the video URI (you can also use a URL or a file path)
        this.currentVideo = new Video(R.drawable.image1, "Video 1 Title",
                "Publisher 1",  R.drawable.image1,
                "1M views", "10/10/2020", "The world is changing.",
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
    // Method to handle the "Download" click event
    public void downloadButtonClick() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
//        } else {
//            startDownload();
//        }
    }

    private void startDownload() {
//        String videoUrl = Integer.toString(currentVideo.getVideoUrl()); // Ensure this is a valid URL
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(videoUrl));
//        request.setTitle("Downloading Video")
//                .setDescription("Downloading " + currentVideo.getTitle())
//                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, currentVideo.getTitle() + ".mp4");
//
//        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//        if (downloadManager != null) {
//            downloadManager.enqueue(request);
//            Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Failed to start download", Toast.LENGTH_SHORT).show();
//        }
    }

    // Method to handle the "Download" click event
    public void subscribeButtonClick() {
        System.out.println("this is subscribe");
    }

    public void setCurrentVideo(Video currentVideo) {
        this.currentVideo = currentVideo;
    }

    public Video getCurrentVideo() {
        return currentVideo;
    }

}