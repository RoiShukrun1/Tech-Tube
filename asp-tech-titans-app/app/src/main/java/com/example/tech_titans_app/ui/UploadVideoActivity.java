package com.example.tech_titans_app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.mainActivity.MainActivity;
import com.example.tech_titans_app.ui.utilities.LoginValidation;

public class UploadVideoActivity extends AppCompatActivity {
    private static final int PICK_VIDEO_REQUEST_VIDEO = 1;
    private VideoView videoView;
    private Uri selectedVideoUri;

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginValidation.checkLoggedIn(this);
        setContentView(R.layout.activity_uploadpage);
        setupVideo();
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(v -> {
            if (selectedVideoUri != null) {
                Intent intent = new Intent(UploadVideoActivity.this, AddVideoActivity.class);
                intent.putExtra("videoUri", selectedVideoUri.toString());
                startActivity(intent);
            }
            else {
                showToastMessage("Please select a video");
            }
        });
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(UploadVideoActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_VIDEO_REQUEST_VIDEO);
    }

    private void setupVideo() {
        videoView = findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        findViewById(R.id.select_button).setOnClickListener(v -> openGallery());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_VIDEO_REQUEST_VIDEO && resultCode == RESULT_OK && data != null) {
            selectedVideoUri = data.getData();
            if (selectedVideoUri != null) {
                videoView.setVideoURI(selectedVideoUri);
                videoView.start();
            }
        }
    }


}
