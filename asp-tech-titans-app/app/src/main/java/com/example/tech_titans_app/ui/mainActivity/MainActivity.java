package com.example.tech_titans_app.ui.mainActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.AppContext;
import com.example.tech_titans_app.ui.LoginActivity;
import com.example.tech_titans_app.ui.PublisherChannelActivity;
import com.example.tech_titans_app.ui.UploadVideoActivity;
import com.example.tech_titans_app.ui.adapters.VideosListAdapter;
import com.example.tech_titans_app.ui.models.account.UsersDB;
import com.example.tech_titans_app.ui.models.account.UsersDataDao;
import com.example.tech_titans_app.ui.utilities.LoggedIn;
import com.example.tech_titans_app.ui.utilities.LoginValidation;
import com.example.tech_titans_app.ui.viewmodels.MainVideoViewModel;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private MainVideoViewModel videoViewModel;
    private VideosListAdapter adapter;
    private ProfileManager profileManager;
    private UsersDB usersDB;
    private UsersDataDao usersDataDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the context manager
        AppContext.init(this);

        // Setup filter click listeners
        new FilterUtils().setupFilterClickListeners(findViewById(android.R.id.content));

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
        videoViewModel.getAllVideos().observe(this, videos -> adapter.setVideos(videos));

        // Setup navigation buttons
        setupNavigationButtons();

        // Setup search bar functionality
        setupSearchBar();

        // Setup profile section logic
        setupProfileSection();

        // Setup dark mode functionality
        setupDarkMode();

        // Handle search query from Intent
        handleSearchQueryFromIntent();

        // Get the database instance
        UsersDB db = UsersDB.getInstance(this);
        usersDataDao = db.usersDao();
        AppContext.init(this);
    }

    private void handleSearchQueryFromIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("SEARCH_QUERY")) {
            String searchQuery = intent.getStringExtra("SEARCH_QUERY");
            if (searchQuery != null) {
                videoViewModel.filterVideos(searchQuery);
            } else {
                videoViewModel.filterVideos(""); // Handle the case where the search query is empty
            }
        }
    }

    private void setupNavigationButtons() {
        // Home button to reload the MainActivity
        TextView homeButton = findViewById(R.id.home);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra("SEARCH_QUERY", ""); // Ensure the search query is reset
            startActivity(intent);
            finish(); // Finish the current activity to ensure it is properly reset
        });

        // Add video button to navigate to the UploadVideoActivity
        ImageView addVideoButton = findViewById(R.id.add);
        addVideoButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UploadVideoActivity.class);
            startActivity(intent);
        });

        TextView myChannelButton = findViewById(R.id.mychannel);
        myChannelButton.setOnClickListener(v -> {
            LoginValidation.checkLoggedIn(this);
            if (LoggedIn.getInstance().isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, PublisherChannelActivity.class);
            intent.putExtra("publisher", LoggedIn.getInstance().getLoggedInUser().getUsername()); // Pass the publisher information to the PublisherChannelActivity
            startActivity(intent);
            }
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
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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
