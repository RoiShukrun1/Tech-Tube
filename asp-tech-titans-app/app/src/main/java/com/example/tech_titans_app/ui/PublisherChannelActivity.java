package com.example.tech_titans_app.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.LoginActivity;
import com.example.tech_titans_app.ui.UploadVideoActivity;
import com.example.tech_titans_app.ui.adapters.VideosListAdapter;
import com.example.tech_titans_app.ui.api.UsersAPI;
import com.example.tech_titans_app.ui.api.VideosAPI;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.mainActivity.MainActivity;
import com.example.tech_titans_app.ui.mainActivity.ProfileManager;
import com.example.tech_titans_app.ui.mainActivity.SearchBarHandler;
import com.example.tech_titans_app.ui.mainActivity.SearchBarUtils;
import com.example.tech_titans_app.ui.mainActivity.DarkModeManager;
import com.example.tech_titans_app.ui.models.account.UserData;
import com.example.tech_titans_app.ui.utilities.LoggedIn;
import com.example.tech_titans_app.ui.viewmodels.MainVideoViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublisherChannelActivity extends AppCompatActivity {
    private MainVideoViewModel videoViewModel;
    private VideosListAdapter adapter;
    private ProfileManager profileManager;
    private final LoggedIn loggedIn = LoggedIn.getInstance();
    private UserData publisherData;
    private List<UserData> publisherSubs;
    private int videosCount;
    private boolean isSubscribed = false;
    private String base_server_url = "http://10.0.2.2";
    private UsersAPI usersAPI;
    private VideosAPI videosAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_channel);

        usersAPI = new UsersAPI(this);
        videosAPI = new VideosAPI(this);

        // Retrieve the publisher information from the intent
        Intent intent = getIntent();
        String publisherId = intent.getStringExtra("publisher");

        // Setup search bar functionality
        new SearchBarUtils(findViewById(android.R.id.content));

        // Setup RecyclerView for displaying videos
        RecyclerView lstVideos = findViewById(R.id.lstVideos);
        lstVideos.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and set it to the RecyclerView
        adapter = new VideosListAdapter();
        lstVideos.setAdapter(adapter);

        // Initialize ViewModel and observe changes in the video list
        videoViewModel = new ViewModelProvider(this).get(MainVideoViewModel.class);
        videoViewModel.getPublisherVideos(publisherId).observe(this, videos -> adapter.setVideos(videos));

        // Setup navigation buttons
        setupNavigationButtons();

        // Setup search bar functionality
        setupSearchBar();

        // Setup profile section logic
        setupProfileSection();

        // Setup dark mode functionality
        setupDarkMode();

        if (publisherId != null) {
            fetchPublisherData(publisherId);
            fetchPublisherSubsCount(publisherId);
            videosCount = fetchPublisherVideosCount(publisherId);
            setupPublisherInfo(publisherData, publisherSubs, videosCount);
        }
    }

    private void fetchPublisherData(String publisherId) {
        usersAPI.getUserById(publisherId, new Callback<UserData>() {
            @Override
            public void onResponse(@NonNull Call<UserData> call,
                                   @NonNull Response<UserData> response) {
                if (response.isSuccessful()) {
                    publisherData = response.body();
                    Log.e("API_CALL", "API call succeeded but user data is null");
                } else {
                    Log.e("API_CALL", "API call failed onResponse:");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserData> call, @NonNull Throwable t) {
                Log.e("API_CALL", "API call failed: " + t.getMessage());
            }
        });
    }

    private void fetchPublisherSubsCount(String publisherId) {
        usersAPI.getUserSubsById(publisherId, new Callback<List<UserData>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserData>> call,
                                   @NonNull Response<List<UserData>> response) {
                if (response.isSuccessful()) {
                    publisherSubs = response.body();
                    Log.e("API_CALL", "API call succeeded but user data is null");
                } else {
                    Log.e("API_CALL", "API call failed onResponse:");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserData>> call, @NonNull Throwable t) {
                Log.e("API_CALL", "API call failed: " + t.getMessage());
            }
        });
    }

    private int fetchPublisherVideosCount(String publisherId) {
        videosAPI.getPublisherVideosById(publisherId, new Callback<List<Video>>() {
            @Override
            public void onResponse(@NonNull Call<List<Video>> call,
                                   @NonNull Response<List<Video>> response) {
                if (response.isSuccessful()) {
                    List<Video> videos = response.body();
                    videosCount = videos.size();
                    Log.e("API_CALL", "API call succeeded but user data is null");
                } else {
                    Log.e("API_CALL", "API call failed onResponse:");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Video>> call, @NonNull Throwable t) {
                Log.e("API_CALL", "API call failed: " + t.getMessage());
            }
        });
        return videosCount;
    }


    private void setupPublisherInfo(UserData publisherData, List<UserData> publisherSubs, int videosCount) {
        // Retrieve views from the layout
        ImageView bannerImage = findViewById(R.id.banner_image);
        ImageView publisherImage = findViewById(R.id.publisher_image);
        TextView publisherUsername = findViewById(R.id.publisher_username);
        TextView publisherNickname = findViewById(R.id.publisher_nickname);
        TextView publisherSubscribesCount = findViewById(R.id.publisher_subscribes_count);
        TextView publisherVideosCount = findViewById(R.id.publisher_videos_count);
        Button subscribeButton = findViewById(R.id.btn_subscribe);

        // Sample data for the publisher, replace with actual data retrieval
        String publisherImageUrl = publisherData.getImage();
        String username = publisherData.getUsername();
        String nickname = publisherData.getNickname();
        int subscribesCount = publisherSubs.size(); // replace with actual subscribers count

        // Load images (using Glide or any other image loading library)
        Glide.with(this)
                .load(base_server_url + publisherImageUrl)
                .into(publisherImage);

        // Set text for TextViews
        publisherUsername.setText(username);
        publisherNickname.setText("@" + nickname);
        publisherSubscribesCount.setText(subscribesCount);
        publisherVideosCount.setText(videosCount);

        // Set subscribe button functionality
        subscribeButton.setOnClickListener(v -> {
            // Handle subscribe button click
            // Example: show a Toast message
            Toast.makeText(PublisherChannelActivity.this, "Subscribed to " + username, Toast.LENGTH_SHORT).show();

            // Change button text to "Subscribed"
            subscribeButton.setText(R.string.subscribed);
        });
    }

    private void setupNavigationButtons() {
        // Home button to reload the MainActivity
        TextView homeButton = findViewById(R.id.home);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(PublisherChannelActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Add video button to navigate to the UploadVideoActivity
        ImageView addVideoButton = findViewById(R.id.add);
        addVideoButton.setOnClickListener(v -> {
            Intent intent = new Intent(PublisherChannelActivity.this, UploadVideoActivity.class);
            startActivity(intent);
        });
    }

    private void setupSearchBar() {
        // Initialize search input field
        EditText searchInput = findViewById(R.id.search_input);

        // Create and set TextWatcher for search input
        SearchBarHandler searchBarHandler = new SearchBarHandler(videoViewModel);
        searchInput.addTextChangedListener(searchBarHandler);
    }

    private void setupProfileSection() {
        // Get references to profile section views
        LinearLayout profileSection = findViewById(R.id.profile_section);
        ImageView profilePicture = findViewById(R.id.profile_picture);
        TextView logoutText = findViewById(R.id.logout_text);
        TextView loginText = findViewById(R.id.login);

        // Initialize ProfileManager and update UI
        profileManager = new ProfileManager(this, profileSection, profilePicture, logoutText, loginText);
        profileManager.updateUI();

        // Set click listener for profile section to handle logout
        profileSection.setOnClickListener(v -> {
            profileManager.handleLogout();
            profileManager.updateUI();
        });

        // Set click listener for login text to navigate to LoginActivity
        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(PublisherChannelActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void setupDarkMode() {
        // Get reference to dark mode button
        ImageView darkModeButton = findViewById(R.id.dark_mode);

        // Initialize DarkModeManager
        DarkModeManager darkModeManager = new DarkModeManager(this, darkModeButton);
    }
}

