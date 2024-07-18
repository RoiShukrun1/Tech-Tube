package com.example.tech_titans_app.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.LoginActivity;
import com.example.tech_titans_app.ui.UploadVideoActivity;
import com.example.tech_titans_app.ui.adapters.VideosListAdapter;
import com.example.tech_titans_app.ui.api.PatchReqBody;
import com.example.tech_titans_app.ui.api.UsersAPI;
import com.example.tech_titans_app.ui.api.VideosAPI;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.mainActivity.MainActivity;
import com.example.tech_titans_app.ui.mainActivity.ProfileManager;
import com.example.tech_titans_app.ui.mainActivity.SearchBarHandler;
import com.example.tech_titans_app.ui.mainActivity.SearchBarUtils;
import com.example.tech_titans_app.ui.mainActivity.DarkModeManager;
import com.example.tech_titans_app.ui.models.account.UserData;
import com.example.tech_titans_app.ui.models.account.UsersDB;
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
    private MutableLiveData<UserData> publisherDataLiveData = new MutableLiveData<>();
    private MutableLiveData<List<UserData>> publisherSubsLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> videosCountLiveData = new MutableLiveData<>();
    private boolean isSubscribed = false;
    private String base_server_url ;
    private UsersAPI usersAPI;
    private VideosAPI videosAPI;
    private String publisherId;

    private UsersDB usersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_channel);
        usersDB = UsersDB.getInstance(this);
        usersAPI = new UsersAPI(this);
        videosAPI = new VideosAPI(this);

        Context context = AppContext.getContext();
        base_server_url = context.getString(R.string.base_server_url_without_ending_slash).trim();
        // Retrieve the publisher information from the intent
        Intent intent = getIntent();
        publisherId = intent.getStringExtra("publisher");

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
        videoViewModel.getAllVideos().observe(this, videos -> adapter.setVideos(videos));
        // Setup navigation buttons
        setupNavigationButtons();

        // Setup search bar functionality
        setupSearchBar();

        // Setup profile section logic
        setupProfileSection();

        // Setup dark mode functionality
        setupDarkMode();

        setSubscribeUI();


        if (publisherId != null) {
            fetchPublisherData(publisherId);
            fetchPublisherSubsCount(publisherId);
            fetchPublisherVideosCount(publisherId);
        }

        // Observe changes in publisher data and update UI accordingly
        publisherDataLiveData.observe(this, publisherData -> {
            if (publisherData != null) {
                setupPublisherInfo(publisherData, publisherSubsLiveData.getValue(), videosCountLiveData.getValue());
            } else {
                setupPublisherInfoNull();
            }
        });

        // Observe changes in publisher subscriptions and update UI accordingly
        publisherSubsLiveData.observe(this, publisherSubs -> {
            if (publisherDataLiveData.getValue() != null) {
                setupPublisherInfo(publisherDataLiveData.getValue(), publisherSubs, videosCountLiveData.getValue());
            }
        });

        // Observe changes in videos count and update UI accordingly
        videosCountLiveData.observe(this, videosCount -> {
            if (publisherDataLiveData.getValue() != null) {
                setupPublisherInfo(publisherDataLiveData.getValue(), publisherSubsLiveData.getValue(), videosCount);
            }
        });

        // Set click listener for delete user icon
        ImageView deleteUserIcon = findViewById(R.id.delete_user_icon);
        TextView deleteText = findViewById(R.id.delete_user);
        if (loggedIn.getLoggedInUser() != null) {
            String loggedInUsername = loggedIn.getLoggedInUser().getUsername();
            if (publisherId != null && publisherId.equals(loggedInUsername)) {
                deleteUserIcon.setVisibility(View.VISIBLE);
                deleteText.setVisibility(View.VISIBLE);
                deleteUserIcon.setOnClickListener(v -> deleteUser(publisherId));
            }
        }

        // Set click listener for subscribe button
        Button subscribeButton = findViewById(R.id.btn_subscribe);
        subscribeButton.setOnClickListener(v -> subscribeButtonClick());
    }


    private void deleteUser(String publisherId) {
        usersAPI.deleteUserById(publisherId, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    deleteUserFromLocalDb(publisherId);
                    loggedIn.logOut();

                    Toast.makeText(PublisherChannelActivity.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                    navigateToMainActivity();
                    finish();
                } else {
                    Toast.makeText(PublisherChannelActivity.this, "Failed to delete user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(PublisherChannelActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(PublisherChannelActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }


    private void fetchPublisherData(String publisherId) {
        usersAPI.getUserById(publisherId, new Callback<UserData>() {
            @Override
            public void onResponse(@NonNull Call<UserData> call,
                                   @NonNull Response<UserData> response) {
                if (response.isSuccessful()) {
                    publisherDataLiveData.postValue(response.body());
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
                    publisherSubsLiveData.postValue(response.body());
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

    private void fetchPublisherVideosCount(String publisherId) {
        videosAPI.getPublisherVideosById(publisherId, new Callback<List<Video>>() {
            @Override
            public void onResponse(@NonNull Call<List<Video>> call,
                                   @NonNull Response<List<Video>> response) {
                if (response.isSuccessful()) {
                    List<Video> videos = response.body();
                    if (videos != null) {
                        videosCountLiveData.postValue(videos.size());
                    }
                } else {
                    Log.e("API_CALL", "API call failed onResponse:");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Video>> call, @NonNull Throwable t) {
                Log.e("API_CALL", "API call failed: " + t.getMessage());
            }
        });
    }

    private void setupPublisherInfo(UserData publisherData, List<UserData> publisherSubs, Integer videosCount) {
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
        int subscribesCount = publisherSubs != null ? publisherSubs.size() : 0; // replace with actual subscribers count

        // Load images (using Glide or any other image loading library)
        Glide.with(this)
                .load(base_server_url + publisherImageUrl)
                .into(publisherImage);


        Log.e("image" ,base_server_url + "/uploads/bannerPictures/" + username + ".png" );
        Glide.with(this)
                .load(base_server_url + "/uploads/bannerPictures/" + username + ".png")
                .error(R.drawable.defaultbanner) // Set your default banner image here
                .into(bannerImage);

        // Set text for TextViews
        publisherUsername.setText(username);
        publisherNickname.setText("@" + nickname);
        publisherSubscribesCount.setText(String.valueOf(subscribesCount) + " subscribers");
        publisherVideosCount.setText(String.valueOf(videosCount) + " videos");
    }

    private void setupPublisherInfoNull() {
        // Retrieve views from the layout
        ImageView bannerImage = findViewById(R.id.banner_image);
        ImageView publisherImage =  findViewById(R.id.publisher_image);
        TextView publisherUsername = findViewById(R.id.publisher_username);
        TextView publisherNickname = findViewById(R.id.publisher_nickname);
        TextView publisherSubscribesCount = findViewById(R.id.publisher_subscribes_count);
        TextView publisherVideosCount = findViewById(R.id.publisher_videos_count);
        Button subscribeButton = findViewById(R.id.btn_subscribe);


        String username = "Not Available";
        String nickname = "Not Available";
        int subscribesCount = 0; // replace with actual subscribers count

        // Load images (using Glide or any other image loading library)
        Glide.with(this)
                .load(R.drawable.no_availiable)
                .into(publisherImage);

        // Set text for TextViews
        publisherUsername.setText(username);
        publisherNickname.setText("@" + nickname);
        publisherSubscribesCount.setText(String.valueOf(subscribesCount) + " subscribers");
        publisherVideosCount.setText("videos");
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

        // Create and set OnEditorActionListener for search input
        searchInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getAction() == android.view.KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == android.view.KeyEvent.KEYCODE_ENTER)) {
                // Redirect to MainActivity and perform search
                String query = searchInput.getText().toString();
                Intent intent = new Intent(PublisherChannelActivity.this, MainActivity.class);
                intent.putExtra("SEARCH_QUERY", query);
                startActivity(intent);
                return true;
            }
            return false;
        });
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

    private void deleteUserFromLocalDb(String publisherId) {
        // Assuming username is being used as the identifier here
        UserData userToDelete = usersDB.usersDao().getUserByUsername(publisherId);
        if (userToDelete != null) {
            usersDB.usersDao().delete(userToDelete);
        }
    }

    private void setupDarkMode() {
        // Get reference to dark mode button
        ImageView darkModeButton = findViewById(R.id.dark_mode);

        // Initialize DarkModeManager
        DarkModeManager darkModeManager = new DarkModeManager(this, darkModeButton);
    }

    /**
     * Method to handle the "Subscribe" click event.
     */
    public void subscribeButtonClick() {
        if (!loggedIn.isLoggedIn()) {
            showLoginToast("You have to be logged in to subscribe");
            return;
        }

        if (loggedIn.getLoggedInUser().getUsername().equals(publisherId)) {
            showLoginToast("You can't subscribe to your own channel");
            return;
        }

        String publisher = publisherId;
        UserData loggedInUser = loggedIn.getLoggedInUser();

        isSubscribed =
                loggedInUser.getSubscriptions()
                        .contains(publisher);

        if (isSubscribed) {
            loggedInUser.getSubscriptions()
                    .remove(publisher);
        } else {
            loggedInUser.getSubscriptions()
                    .add(publisher);
        }

        updateSubscriptionsInDB();
        setSubscribeUI();
    }

    public void updateSubscriptionsInDB() {
        PatchReqBody subscriptionsArr = new PatchReqBody("subscriptions",
                loggedIn.getLoggedInUser().getSubscriptions().toString());

        usersAPI.updateUserById
                (String.valueOf(loggedIn.getLoggedInUser().getUsername()), subscriptionsArr);
    }

    /**
     * Method to set the subscribe UI.
     */
    public void setSubscribeUI() {
        String publisher = publisherId;
        if (loggedIn.isLoggedIn()) {
            isSubscribed =
                    loggedIn.getLoggedInUser().getSubscriptions()
                            .contains(publisher);
        } else {
            isSubscribed = false;
        }

        Button subscribeButton = findViewById(R.id.btn_subscribe);
        if (isSubscribed) {
            // Subscribed state: text color black, background white, text "Subscribed"
            subscribeButton.setTextColor(getResources().getColor(R.color.gray));
            subscribeButton.setBackgroundResource(R.drawable.unsub_but);
            subscribeButton.setText(R.string.subscribed);
        } else {
            // Not subscribed state: text color white, background black, text "Subscribe"
            subscribeButton.setTextColor(getResources().getColor(R.color.gray));
            subscribeButton.setBackgroundResource(R.drawable.sub_but);
            subscribeButton.setText(R.string.subscribe);
        }
    }

    /**
     * Method to show a toast message.
     *
     * @param message The message to be shown.
     */
    private void showLoginToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
