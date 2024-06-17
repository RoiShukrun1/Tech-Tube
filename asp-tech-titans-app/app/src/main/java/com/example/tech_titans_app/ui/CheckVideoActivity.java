package com.example.tech_titans_app.ui;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.models.add_video.VideoData;
import com.example.tech_titans_app.ui.models.add_video.VideoDataArray;

import java.util.List;

public class CheckVideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private ImageView thumbnailImageView;
    private TextView titleTextView;
    private TextView publisherTextView;
    private ImageView publisherImageView;
    private TextView viewsTextView;
    private TextView dateTextView;
    private TextView descriptionTextView;
    private TextView relatedVideosTextView;
    private TextView playlistTextView;
    private TextView commentsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkvideo);
        videoView = findViewById(R.id.videoView);
        thumbnailImageView = findViewById(R.id.thumbnailImageView);
        titleTextView = findViewById(R.id.titleTextView);
        publisherTextView = findViewById(R.id.publisherTextView);
        publisherImageView = findViewById(R.id.publisherImageView);
        viewsTextView = findViewById(R.id.viewsTextView);
        dateTextView = findViewById(R.id.dateTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        relatedVideosTextView = findViewById(R.id.relatedVideosTextView);
        playlistTextView = findViewById(R.id.playlistTextView);
        commentsTextView = findViewById(R.id.commentsTextView);

        VideoDataArray videoDataArray = VideoDataArray.getInstance();
        List<VideoData> videoDataList = videoDataArray.getVideoArray();

        // Example: Display the first video's data
        if (!videoDataList.isEmpty()) {
            VideoData videoData = videoDataList.get(0);
            displayVideoData(videoData);
        }
    }

    private void displayVideoData(VideoData videoData) {
        videoView.setVideoURI(videoData.getVideoUri());
        videoView.start();
        thumbnailImageView.setImageURI(videoData.getThumbnail());
        titleTextView.setText(videoData.getTitle());
        publisherTextView.setText(videoData.getPublisher());
        publisherImageView.setImageURI(videoData.getPublisherImage());
        viewsTextView.setText(String.valueOf(videoData.getViews()));
        dateTextView.setText(videoData.getDate());
        descriptionTextView.setText(videoData.getInfo());
        relatedVideosTextView.setText(videoData.getRelatedVideos().toString());
        playlistTextView.setText(videoData.getPlaylist());
        commentsTextView.setText(videoData.getComments().toString());
    }
}
