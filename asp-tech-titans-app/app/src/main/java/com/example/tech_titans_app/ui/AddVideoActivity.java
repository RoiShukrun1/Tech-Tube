package com.example.tech_titans_app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.mainActivity.MainActivity;
import com.example.tech_titans_app.ui.models.account.AccountData;
import com.example.tech_titans_app.ui.models.add_video.VideoDataArray;
import com.example.tech_titans_app.ui.utilities.LoggedIn;
import com.example.tech_titans_app.ui.viewmodels.VideosRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddVideoActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST_THUMBNAIL = 1;
    private static final String KEY_THUMBNAIL_URI = "thumbnail_uri";
    private Uri videoUri;
    private Uri thumbnailUri;
    private AccountData loggedInUser;
    private ImageView thumbnailImage;
    private Spinner playlistSpinner;

    // Method to show a toast message
    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Method to set up the spinner
    private void setupSpinner() {
        playlistSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_options, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        playlistSpinner.setAdapter(adapter);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_THUMBNAIL);
    }

    private void setupThumbnail() {
        thumbnailImage = findViewById(R.id.thumbnailimage);
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
        // Restore the thumbnail URI from savedInstanceState
        if (savedInstanceState != null) {
            thumbnailUri = savedInstanceState.getParcelable(KEY_THUMBNAIL_URI);
            if (thumbnailUri != null) {
                thumbnailImage.setImageURI(thumbnailUri);
            } else {
                thumbnailImage.setImageResource(R.drawable.default_thumbnail);
            }
        } else {
            thumbnailImage.setImageResource(R.drawable.default_thumbnail);
        }
        // Set up the upload button
        Button uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(v -> uploadVideoData());
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddVideoActivity.this, UploadVideoActivity.class);
            startActivity(intent);
        });
    }
    // Handle the result of the gallery intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_THUMBNAIL && resultCode == RESULT_OK && data != null && data.getData() != null) {
            thumbnailUri = data.getData();
            thumbnailImage.setImageURI(thumbnailUri);  // Set the selected image URI to the ImageView
        }
    }
    // Save the thumbnail URI in onSaveInstanceState
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the thumbnail URI
        if (thumbnailUri != null) {
            outState.putParcelable(KEY_THUMBNAIL_URI, thumbnailUri);
        }
    }
    // Restore the thumbnail URI from onRestoreInstanceState
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore the thumbnail URI
        if (savedInstanceState != null) {
            thumbnailUri = savedInstanceState.getParcelable(KEY_THUMBNAIL_URI);
            if (thumbnailUri != null) {
                thumbnailImage.setImageURI(thumbnailUri);
            }
        }
    }
    // Upload video data to the Singlton
    private void uploadVideoData() {
        EditText title = findViewById(R.id.addpagetitle);
        EditText description = findViewById(R.id.addpagedescription);
        String titleString = title.getText().toString();
        String descriptionString = description.getText().toString();
        String playlistString = playlistSpinner.getSelectedItem().toString();  // Get the selected playlist
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(currentDate);
        loggedInUser = LoggedIn.getInstance().getLoggedInUser();

        // Validation checks
        if (titleString.isEmpty() || descriptionString.isEmpty() || playlistString.equals("Add to playlist:") || videoUri == null || thumbnailUri == null) {
            showToastMessage("All fields are required.");
            return;
        }

        if (videoUri != null && thumbnailUri != null) {
            Video currentVideoData = new Video(
                    VideosRepository.getInstance().getAllVideos().getValue().size() + 1,
                    videoUri,
                    thumbnailUri,  // Use the selected or default thumbnail URI
                    titleString,
                    loggedInUser.getNickname(),
                    loggedInUser.getProfilePicture(),
                    "0",
                    formattedDate,
                    descriptionString,
                    new ArrayList<>(),
                    playlistString,  // Set the selected playlist
                    new ArrayList<>(),
                    "0"
            );
            VideosRepository.getInstance().addVideo(currentVideoData);
            showToastMessage("Video data uploaded successfully!");
            Intent intent = new Intent(AddVideoActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            showToastMessage("Please select a video and a thumbnail");
        }
    }
}
