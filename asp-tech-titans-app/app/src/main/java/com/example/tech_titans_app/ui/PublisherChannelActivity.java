package com.example.tech_titans_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.LoginActivity;
import com.example.tech_titans_app.ui.UploadVideoActivity;
import com.example.tech_titans_app.ui.adapters.VideosListAdapter;
import com.example.tech_titans_app.ui.mainActivity.MainActivity;
import com.example.tech_titans_app.ui.mainActivity.ProfileManager;
import com.example.tech_titans_app.ui.mainActivity.SearchBarHandler;
import com.example.tech_titans_app.ui.mainActivity.SearchBarUtils;
import com.example.tech_titans_app.ui.mainActivity.DarkModeManager;
import com.example.tech_titans_app.ui.models.account.UserData;
import com.example.tech_titans_app.ui.utilities.LoggedIn;
import com.example.tech_titans_app.ui.viewmodels.MainVideoViewModel;

public class PublisherChannelActivity extends AppCompatActivity {
    private MainVideoViewModel videoViewModel;
    private VideosListAdapter adapter;
    private ProfileManager profileManager;
    private final LoggedIn loggedIn = LoggedIn.getInstance();
    private UserData publisherData;
    private boolean isSubscribed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_channel);

        // Setup search bar functionality
        new SearchBarUtils(findViewById(android.R.id.content));

        // Setup RecyclerView for displaying videos
        RecyclerView lstVideos = findViewById(R.id.lstVideos);
        lstVideos.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and set it to the RecyclerView
        adapter = new VideosListAdapter();
        lstVideos.setAdapter(adapter);

//        // Initialize ViewModel and observe changes in the video list
//        videoViewModel = new ViewModelProvider(this).get(MainVideoViewModel.class);
//        videoViewModel.getAllVideos().observe(this, videos -> adapter.setVideos(videos));

        // Setup navigation buttons
        setupNavigationButtons();

        // Setup search bar functionality
        setupSearchBar();

        // Setup profile section logic
        setupProfileSection();

        // Setup dark mode functionality
        setupDarkMode();

        // Set up publisher information
//        setupPublisherInfo();
    }

//    private void setupPublisherInfo() {
//        // Retrieve views from the layout
//        ImageView bannerImage = findViewById(R.id.banner_image);
//        ImageView publisherImage = findViewById(R.id.publisher_image);
//        TextView publisherUsername = findViewById(R.id.publisher_username);
//        TextView publisherNickname = findViewById(R.id.publisher_nickname);
//        TextView publisherSubscribesCount = findViewById(R.id.publisher_subscribes_count);
//        TextView publisherVideosCount = findViewById(R.id.publisher_videos_count);
//        Button subscribeButton = findViewById(R.id.btn_subscribe);
//
//        // Sample data for the publisher, replace with actual data retrieval
//        String bannerImageUrl = "https://example.com/banner.jpg"; // replace with actual URL
//        String publisherImageUrl = "https://example.com/publisher.jpg"; // replace with actual URL
//        String username = "Roach Killa Productions";  // replace with actual username;
//        String nickname = "RoachKillaProductions";  // replace with actual nickname
//        String subscribesCount = "1.2K" + " subscribes";  // replace with actual subscribers count
//        String videosCount = "34" + " videos";  // replace with actual videos count
//
//        // Load images (using Glide or any other image loading library)
//        Glide.with(this)
//                .load(bannerImageUrl)
//                .into(bannerImage);
//
//        Glide.with(this)
//                .load(publisherImageUrl)
//                .into(publisherImage);
//
//        // Set text for TextViews
//        publisherUsername.setText(username);
//        publisherNickname.setText(nickname);
//        publisherSubscribesCount.setText(subscribesCount);
//        publisherVideosCount.setText(videosCount);
//
//        // Set subscribe button functionality
//        subscribeButton.setOnClickListener(v -> {
//            // Handle subscribe button click
//            // Example: show a Toast message
//            Toast.makeText(PublisherChannelActivity.this, "Subscribed to " + username, Toast.LENGTH_SHORT).show();
//
//            // Change button text to "Subscribed"
//            subscribeButton.setText(R.string.subscribed);
//        });
//    }
//
//    /**
//     * Method to handle the "Subscribe" click event.
//     */
//    public void subscribeButtonClick() {
//        if (!loggedIn.isLoggedIn()) {
//            showLoginToast("You have to be logged in to subscribe");
//            return;
//        }
//
//        isSubscribed =
//                loggedIn.getLoggedInUser().getSubscriptions()
//                        .contains(publisherData);
//
//        if (isSubscribed) {
//            loggedIn.getLoggedInUser().getSubscriptions()
//                    .remove(publisherData);
//        } else {
//            loggedIn.getLoggedInUser().getSubscriptions()
//                    .add(publisherData);
//        }
//
//        setSubscribeUI();
//    }
//
//    /**
//     * Method to show a toast message.
//     *
//     * @param message The message to be shown.
//     */
//    private void showLoginToast(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
//
//    /**
//     * Method to set the subscribe UI.
//     */
//    public void setSubscribeUI() {
//        if (loggedIn.isLoggedIn()) {
//            isSubscribed =
//                    loggedIn.getLoggedInUser().getSubscriptions()
//                            .contains(publisherData);
//        } else {
//            isSubscribed = false;
//        }
//
//        Button subscribeButton = findViewById(R.id.btn_subscribe);
//        if (isSubscribed) {
//            // Subscribed state: text color black, background white, text "Subscribed"
//            subscribeButton.setTextColor(getResources().getColor(R.color.gray));
//            subscribeButton.setBackgroundResource(R.drawable.unsub_but);
//            subscribeButton.setText(R.string.subscribed);
//        } else {
//            // Not subscribed state: text color white, background black, text "Subscribe"
//            subscribeButton.setTextColor(getResources().getColor(R.color.gray));
//            subscribeButton.setBackgroundResource(R.drawable.sub_but);
//            subscribeButton.setText(R.string.subscribe);
//        }
//    }


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
