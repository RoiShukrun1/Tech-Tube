package com.example.tech_titans_app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.tech_titans_app.ui.api.VideosAPI;
import com.example.tech_titans_app.ui.mainActivity.MainActivity;
import com.example.tech_titans_app.ui.models.account.UserData;
import com.example.tech_titans_app.ui.utilities.LoggedIn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVideoActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST_THUMBNAIL = 1;
    private static final String KEY_THUMBNAIL_URI = "thumbnail_uri";
    private Uri videoUri;
    private Uri thumbnailUri;
    private UserData loggedInUser;
    private ImageView thumbnailImage;
    private Spinner playlistSpinner;
    private VideosAPI videosAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpage);

        videosAPI = new VideosAPI(this);

        setupSpinner();
        setupThumbnail();

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

        videoUri = Uri.parse(getIntent().getStringExtra("videoUri"));

        Button uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(v -> uploadVideoData());

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddVideoActivity.this, UploadVideoActivity.class);
            startActivity(intent);
        });
    }

    private void setupSpinner() {
        playlistSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_options, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        playlistSpinner.setAdapter(adapter);
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

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_THUMBNAIL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_THUMBNAIL && resultCode == RESULT_OK && data != null && data.getData() != null) {
            thumbnailUri = data.getData();
            thumbnailImage.setImageURI(thumbnailUri);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (thumbnailUri != null) {
            outState.putParcelable(KEY_THUMBNAIL_URI, thumbnailUri);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            thumbnailUri = savedInstanceState.getParcelable(KEY_THUMBNAIL_URI);
            if (thumbnailUri != null) {
                thumbnailImage.setImageURI(thumbnailUri);
            }
        }
    }

    private File getFileFromUri(Uri uri) {
        File file = null;
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                file = new File(getCacheDir(), "temp_video.mp4");
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private void uploadVideoData() {
        EditText title = findViewById(R.id.addpagetitle);
        EditText description = findViewById(R.id.addpagedescription);
        String titleString = title.getText().toString();
        String descriptionString = description.getText().toString();
        String playlistString = playlistSpinner.getSelectedItem().toString();
        loggedInUser = LoggedIn.getInstance().getLoggedInUser();

        if (titleString.isEmpty() || descriptionString.isEmpty() || playlistString.equals("Select category:") || videoUri == null || thumbnailUri == null) {
            showToastMessage("All fields are required.");
            return;
        }

        File videoFile = getFileFromUri(videoUri);
        if (videoFile == null) {
            showToastMessage("Failed to process the video file.");
            return;
        }

        String base64Thumbnail = UriToBase64.convertUriToBase64(this, thumbnailUri);
        if (base64Thumbnail == null) {
            showToastMessage("Failed to process the thumbnail image.");
            return;
        }

        // Use some unique id here
        String videoId = String.valueOf(System.currentTimeMillis());

        Log.e("publisherimage", loggedInUser.getImage());
        Log.e("publishername", loggedInUser.getUsername());
        Log.e("videotitle", titleString);
        Log.e("videodescription", descriptionString);
        Log.e("videoplaylist", playlistString);
        Log.e("base64thumbnail", base64Thumbnail);
        videosAPI.uploadVideo(videoFile, base64Thumbnail, titleString, descriptionString, playlistString, loggedInUser.getUsername(), loggedInUser.getImage(), new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showToastMessage("Video uploaded successfully!");
                    Intent intent = new Intent(AddVideoActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Log.e("Upload", "Error: " + response.message());
                    Log.e("Upload", "Response Code: " + response.code());
                    try {
                        Log.e("Upload", "Response Body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    showToastMessage("Error uploading video");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Upload", "Failure: " + t.getMessage());
                showToastMessage("Error uploading video");
            }
        });
    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
