package com.example.tech_titans_app.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.models.add_video.VideoData;
import com.example.tech_titans_app.ui.models.add_video.VideoDataArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddVideoActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST_THUMBNAIL = 1;
    private Uri videoUri;

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setupSpinner() {
        // Find the Spinner in the layout
        Spinner spinner = findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_options, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_THUMBNAIL);
    }

    private void setupThumbnail() {
        ImageView thumbnailImage = findViewById(R.id.thumbnailimage);
        thumbnailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpage);

        videoUri = Uri.parse(getIntent().getStringExtra("videoUri"));

        setupSpinner();
        setupThumbnail();

        Button uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(v -> uploadVideoData());

        VideoView videoView = findViewById(R.id.videoView3);
        videoView.setVideoURI(videoUri);
        videoView.start();
    }

    private void uploadVideoData() {
        EditText title = findViewById(R.id.addpagetitle);
        EditText description = findViewById(R.id.addpagedescription);
        ImageView thumbnail = findViewById(R.id.thumbnailimage);
        String titleString = title.getText().toString();
        String descriptionString = description.getText().toString();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(currentDate);

        if (videoUri != null) {
            VideoData currentVideoData = new VideoData(
                    VideoDataArray.getInstance().getLength() + 1,
                    videoUri,
                    thumbnail,
                    titleString,
                    "",
                    null,
                    0,
                    formattedDate,
                    descriptionString,
                    new ArrayList<>(),
                    "",
                    new ArrayList<>()
            );
            VideoDataArray.getInstance().addVideo(currentVideoData);
            showToastMessage("Video data uploaded successfully!");
            Intent intent = new Intent(AddVideoActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            showToastMessage("Please select a video");
        }
    }
}
