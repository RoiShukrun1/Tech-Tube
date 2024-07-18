package com.example.tech_titans_app.ui;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.adapters.VideosListAdapter;

import com.example.tech_titans_app.ui.api.CommentsAPI;
import com.example.tech_titans_app.ui.api.PatchReqBody;
import com.example.tech_titans_app.ui.api.UsersAPI;
import com.example.tech_titans_app.ui.api.VideosAPI;
import com.example.tech_titans_app.ui.entities.Comment;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.entities.CurrentVideo;
import com.example.tech_titans_app.ui.entities.VideoDB;
import com.example.tech_titans_app.ui.entities.VideoDao;
import com.example.tech_titans_app.ui.mainActivity.MainActivity;
import com.example.tech_titans_app.ui.mainActivity.ProfileManager;
import com.example.tech_titans_app.ui.mainActivity.SearchBarHandler;
import com.example.tech_titans_app.ui.mainActivity.SearchBarUtils;
import com.example.tech_titans_app.ui.models.account.UserData;
import com.example.tech_titans_app.ui.utilities.LoggedIn;
import com.example.tech_titans_app.ui.utilities.LoginValidation;
import com.example.tech_titans_app.ui.viewmodels.MainVideoViewModel;
import com.example.tech_titans_app.ui.viewmodels.VideoViewModelVWP;


import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WatchVideoPageActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;

    private VideoViewModelVWP videoViewModel;
    private Video thisCurrentVideo;
    private VideosListAdapter adapter;
    private boolean isLiked;
    private boolean isUnliked;
    private boolean isSubscribed = false;
    private final LoggedIn loggedIn = LoggedIn.getInstance();
    private SharedPreferences.Editor editor;
    private View header;
    private View menuBar;
    private View videoInfo;
    private View comments;
    private RecyclerView recyclerView;
    private VideosAPI videosAPI;
    private UsersAPI usersAPI;
//    private ProfileManager profileManager;

    /**
     * Method to handle the creation of the activity.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_watch_video_page);

        videoViewModel = new ViewModelProvider(this).get(VideoViewModelVWP.class);
        thisCurrentVideo = CurrentVideo.getInstance().getCurrentVideo().getValue();
        videosAPI = new VideosAPI(this);
        usersAPI = new UsersAPI(this);

        addSearchBarLogic();
        addBottomBarLogic();
        initiateVideoPlayer();
        initiateRelatedVideos();
        addListeners();
        setVideoTitle();
        setVideoDetails();
        setVideoDescription();
        setPublisherInfo();
        updateLikesButtonsUI();
        listenToDarkModeSwitch();
        orientationConfigurationChangeListener();
        setSubscribeUI();
        handleEditIconsVisibility();



//        // Setup navigation buttons
//        setupNavigationButtons();
//
//        // Setup search bar functionality
//        setupSearchBar();
//
//        // Setup profile section logic
//        setupProfileSection();
    }

//    private void setupNavigationButtons() {
//        // Home button to reload the MainActivity
//        TextView homeButton = findViewById(R.id.home);
//        homeButton.setOnClickListener(v -> {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        });
//
//        // Add video button to navigate to the UploadVideoActivity
//        ImageView addVideoButton = findViewById(R.id.add);
//        addVideoButton.setOnClickListener(v -> {
//            Intent intent = new Intent(this, UploadVideoActivity.class);
//            startActivity(intent);
//        });
//
//        TextView myChannelButton = findViewById(R.id.mychannel);
//        myChannelButton.setOnClickListener(v -> {
//            LoginValidation.checkLoggedIn(this);
//            if (LoggedIn.getInstance().isLoggedIn()) {
//                Intent intent = new Intent(this, PublisherChannelActivity.class);
//                intent.putExtra("publisher", LoggedIn.getInstance().getLoggedInUser().getUsername()); // Pass the publisher information to the PublisherChannelActivity
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void setupSearchBar() {
//        // Initialize search input field
//        EditText searchInput = findViewById(R.id.search_input);
//
//        // Create and set TextWatcher for search input
//        SearchBarHandler searchBarHandler = new SearchBarHandler(videoViewModel);
//        searchInput.addTextChangedListener(searchBarHandler);
//    }
//
//    private void setupProfileSection() {
//        // Get references to profile section views
//        LinearLayout profileSection = findViewById(R.id.profile_section);
//        ImageView profilePicture = findViewById(R.id.profile_picture);
//        TextView logoutText = findViewById(R.id.logout_text);
//        TextView loginText = findViewById(R.id.login);
//
//        // Initialize ProfileManager and update UI
//        profileManager = new ProfileManager(this, profileSection, profilePicture, logoutText, loginText);
//        profileManager.updateUI();
//
//        // Set click listener for profile section to handle logout
//        profileSection.setOnClickListener(v -> {
//            profileManager.handleLogout();
//            profileManager.updateUI();
//        });
//
//        // Set click listener for login text to navigate to LoginActivity
//        loginText.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intent);
//        });
//    }

    /**
     * Method to handle the configuration change of the device.
     */
    private void orientationConfigurationChangeListener() {
        header = findViewById(R.id.header);
        menuBar = findViewById(R.id.menu_bar);
        videoInfo = findViewById(R.id.watch_video_page_video_info);
        comments = findViewById(R.id.watch_video_page_comments);
        recyclerView = findViewById(R.id.listVideosVWP);

        View rootView = getWindow().getDecorView().getRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // Handle landscape orientation
                handleOrientationChange(true);
            } else {
                // Handle portrait orientation
                handleOrientationChange(false);
            }
        });
    }

    /**
     * Method to handle the orientation change of the device.
     *
     * @param isLandscape The boolean value to check if the orientation is landscape.
     */
    private void handleOrientationChange(boolean isLandscape) {
        if (isLandscape) {
            // Execute code for landscape orientation
            enterFullscreenMode();
        } else {
            // Execute code for portrait orientation
            exitFullscreenMode();
        }
    }

    /**
     * Method to enter fullscreen mode.
     */
    private void enterFullscreenMode() {
        FrameLayout frameLayout = findViewById(R.id.frame_Layout_video_Player);
        ViewGroup.LayoutParams params = frameLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        frameLayout.setLayoutParams(params);

        // Hide header and menu bar for fullscreen video
        header.setVisibility(View.GONE);
        menuBar.setVisibility(View.GONE);
        videoInfo.setVisibility(View.GONE);
        comments.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    /**
     * Method to exit fullscreen mode.
     */
    private void exitFullscreenMode() {

        // Show header and menu bar when exiting fullscreen
        header.setVisibility(View.VISIBLE);
        menuBar.setVisibility(View.VISIBLE);
        videoInfo.setVisibility(View.VISIBLE);
        comments.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Method to update the UI based on the user's login status.
     *
     * @param profileSection The profile section of the activity.
     * @param profilePicture The profile picture of the user.
     * @param logoutText The logout text view.
     * @param loginText The login text view.
     */
    private void updateUI(LinearLayout profileSection, ImageView profilePicture,
                          TextView logoutText, TextView loginText) {
        if (LoggedIn.getInstance().isLoggedIn()) {
            profileSection.setVisibility(View.VISIBLE);
            loginText.setVisibility(View.GONE);
            Glide.with(this).load(LoggedIn.getInstance()
                    .getLoggedInUser().getImage()).into(profilePicture);
            logoutText.setText(R.string.logout);
        } else {
            profileSection.setVisibility(View.GONE);
            loginText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Method to initiate the video player.
     */
    private void initiateVideoPlayer() {
        VideoView videoView = findViewById(R.id.videoView);

        // Set up MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        String baseUrl = this.getString(R.string.base_server_url).trim();
        // Ensure the base URL ends with a "/"
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }

        // Assuming thisCurrentVideo.getVideoUploaded() returns a relative path
        String videoPath = thisCurrentVideo.getVideoUploaded().toString();
        // Ensure there is no leading "/" in videoPath to prevent double slashes
        if (videoPath.startsWith("/")) {
            videoPath = videoPath.substring(1);
        }

        // Combine the base URL with the video path
        Uri videoUri = Uri.parse(baseUrl + videoPath);

        // Set the combined URI to the VideoView
        videoView.setVideoURI(videoUri);

        // Start the video
        videoView.start();
    }

    /**
     * Method to initiate the related videos.
     */
    private void initiateRelatedVideos() {
        RecyclerView lstVideos = findViewById(R.id.listVideosVWP);
        lstVideos.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VideosListAdapter();
        lstVideos.setAdapter(adapter);

        videoViewModel.getAllVideos().observe(this, videos -> adapter.setVideos(videos));
    }

    /**
     * Method to add listeners to the activity.
     */
    public void addListeners() {

        // Handle click event of the "Like" TextView
        TextView likeTextView = findViewById(R.id.btn_like);
        likeTextView.setText(this.thisCurrentVideo.getLikes());
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

        // Handle click event of the comment collapsed view
        TextView commentCollapsedView = findViewById(R.id.comment_collapsed_view);
        commentCollapsedView.setOnClickListener(v -> openCommentsActivity());

        // Handle click event of pencil click - edit video title
        TextView PencilTextView = findViewById(R.id.editTitle);
        PencilTextView.setOnClickListener(v -> PencilButtonClick());

        // Handle click event of pencil description click - edit video description
        TextView PencilDescriptionTextView = findViewById(R.id.editDescription);
        PencilDescriptionTextView.setOnClickListener(v -> pencilDescriptionButtonClick());

    }

    /**
     * Method to add search bar logic to the activity.
     */
    public void addSearchBarLogic() {
        new SearchBarUtils(findViewById(android.R.id.content));
        EditText searchInput = findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                videoViewModel.filterVideos(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    /**
     * Method to add bottom bar logic to the activity.
     */
    public void addBottomBarLogic() {
        TextView homeButton = findViewById(R.id.home);
        homeButton.setOnClickListener(v -> {
            Intent intent =
                    new Intent(WatchVideoPageActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ImageView addVideoButton = findViewById(R.id.add);
        addVideoButton.setOnClickListener(v -> {
            Intent intent =
                    new Intent(WatchVideoPageActivity.this,
                            UploadVideoActivity.class);
            startActivity(intent);
        });

        LinearLayout profileSection = findViewById(R.id.profile_section);
        ImageView profilePicture = findViewById(R.id.profile_picture);
        TextView logoutText = findViewById(R.id.logout_text);
        TextView loginText = findViewById(R.id.login);

        updateUI(profileSection, profilePicture, logoutText, loginText);

        profileSection.setOnClickListener(v -> {
            LoggedIn.getInstance().logOut();
            updateUI(profileSection, profilePicture, logoutText, loginText);
        });

        loginText.setOnClickListener(v -> {
            Intent intent =
                    new Intent(WatchVideoPageActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Method to listen to the dark mode switch.
     */
    public void listenToDarkModeSwitch() {
        ImageView darkModeButton = findViewById(R.id.dark_mode);
        SharedPreferences sharedPreferences = getSharedPreferences("themeSharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        darkModeButton.setOnClickListener(v -> {
            if (isDarkModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("isDarkModeOn", false);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("isDarkModeOn", true);
            }
            editor.apply();
            recreate();
        });
    }

    /**
     * Method to set the publisher info.
     */
    public void setPublisherInfo() {
        String baseUrl = this.getString(R.string.base_server_url).trim();
        // Ensure the base URL ends with a "/"
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }

        // Get the relative path of the publisher's image from the current video
        String imageRelativePath =
                CurrentVideo.getInstance().getCurrentVideo().getValue().getPublisherImage()
                        .toString().trim();
        // Ensure there is no leading "/" in imageRelativePath to prevent double slashes
        if (imageRelativePath.startsWith("/")) {
            imageRelativePath = imageRelativePath.substring(1);
        }

        // Combine the base URL with the image path and parse it into a Uri
        Uri publisherImageUri = Uri.parse(baseUrl + imageRelativePath);

        // Set the publisher image using Glide
        CircleImageView publisherImage = findViewById(R.id.publisher_image_VWP);
        Glide.with(publisherImage.getContext())
                .load(publisherImageUri)
                .into(publisherImage);

        // Set the publisher name
        TextView publisherTextView = findViewById(R.id.publisher_name_VWP);
        publisherTextView.setText(this.thisCurrentVideo.getPublisher());
    }


    /**
     * Method to set the video title.
     */
    public void setVideoTitle() {
        TextView titleTextView = findViewById(R.id.video_title);
        titleTextView.setText(thisCurrentVideo.getTitle());
    }

    /**
     * Method to set the video details.
     */
    public void setVideoDetails() {
        TextView titleTextView = findViewById(R.id.video_details);
        String details;
        details = this.thisCurrentVideo.getViews() + " views " + this.thisCurrentVideo.getDate();
        titleTextView.setText(details);
    }

    /**
     * Method to set the video description.
     */
    public void setVideoDescription() {
        TextView descriptionTextView = findViewById(R.id.video_description);
        descriptionTextView.setText(thisCurrentVideo.getInfo());
    }

    /**
     * Method to get the likes status.
     */
    public void getLikesStatus() {
        if (!loggedIn.isLoggedIn()) {
            isUnliked = false;
            isLiked = false;
        } else {
            isUnliked =
                    thisCurrentVideo.getUsersUnlikes()
                            .contains(loggedIn.getLoggedInUser().getUsername());
            isLiked =
                    thisCurrentVideo.getUsersLikes()
                            .contains(loggedIn.getLoggedInUser().getUsername());
        }
    }

    /**
     * Method to handle the "Like" click event.
     */
    public void likeButtonClick() {
        if (loggedIn.isLoggedIn()) {
            handleLike();
        } else {
            showLoginToast("You have to be logged in to mark as liked");
        }
    }

    /**
     * Method to handle the "Unlike" click event.
     */
    public void unlikeButtonClick() {
        if (loggedIn.isLoggedIn()) {
            handleUnlike();
        } else {
            showLoginToast("You have to be logged in to mark as disliked");
        }
    }

    /**
     * Method to handle the like and unlike actions.
     */
    private void handleLike() {
        getLikesStatus();
        String loggedInUserId = loggedIn.getLoggedInUser().getUsername();

        if (isUnliked) {
            thisCurrentVideo.getUsersUnlikes().remove(loggedInUserId);
            updateUnlikesInDB();
        }
        if (isLiked) {
            thisCurrentVideo.decrementLikes();
            thisCurrentVideo.getUsersLikes().remove(loggedInUserId);
        } else {
            thisCurrentVideo.incrementLikes();
            thisCurrentVideo.getUsersLikes().add(loggedInUserId);
        }
        updateLikesInDB();
        updateLikesButtonsUI();
    }

    /**
     * Method to handle the unlike action.
     */
    private void handleUnlike() {
        getLikesStatus();
        String loggedInUserId = loggedIn.getLoggedInUser().getUsername();

        if (isLiked) {
            thisCurrentVideo.getUsersLikes().remove(loggedInUserId);
            thisCurrentVideo.decrementLikes();
            updateLikesInDB();
        }
        if (isUnliked) {
            thisCurrentVideo.getUsersUnlikes().remove(loggedInUserId);
        } else {
            thisCurrentVideo.getUsersUnlikes().add(loggedInUserId);
        }

        updateUnlikesInDB();
        updateLikesButtonsUI();
    }

    public void updateLikesInDB() {
        PatchReqBody likesArr = new PatchReqBody("usersLikes",
                thisCurrentVideo.getUsersLikes().toString());
        videosAPI.updateVideoById(String.valueOf(thisCurrentVideo.getId()), likesArr);
    }

    public void updateUnlikesInDB() {
        PatchReqBody unlikesArr = new PatchReqBody("usersUnlikes",
                thisCurrentVideo.getUsersUnlikes().toString());
        videosAPI.updateVideoById(String.valueOf(thisCurrentVideo.getId()), unlikesArr);
    }

    /**
     * Method to show a toast message.
     *
     * @param message The message to be shown.
     */
    private void showLoginToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to update the likes buttons UI.
     */
    private void updateLikesButtonsUI() {
        getLikesStatus();
        TextView unlikeTextView = findViewById(R.id.btn_unlike);
        Drawable unlikeDrawable = ContextCompat.getDrawable
                (this, isUnliked ? R.drawable.dislike_selected : R.drawable.dislike);
        unlikeTextView.setCompoundDrawablesWithIntrinsicBounds
                (unlikeDrawable, null, null, null);

        TextView likeTextView = findViewById(R.id.btn_like);
        Drawable likeDrawable = ContextCompat.getDrawable
                (this, isLiked ? R.drawable.like_selected : R.drawable.like);
        likeTextView.setCompoundDrawablesWithIntrinsicBounds
                (likeDrawable, null, null, null);

        likeTextView.setText(String.valueOf(thisCurrentVideo.getLikes()));
    }

    /**
     * Method to handle the "Download" click event.
     */
    public void downloadButtonClick() {
        if (checkPermission()) {
            startDownload();
        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownload();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void startDownload() {

        String videoUrl = "http://10.0.2.2/uploads/uploadedVideos/" + thisCurrentVideo.getId() + ".mp4";
        Uri uri = Uri.parse(videoUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("Downloading Video");
        request.setDescription("Downloading video file...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "video.mp4");

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = downloadManager.enqueue(request);
    }
    /**
     * Method to handle the "Subscribe" click event.
     */
    public void subscribeButtonClick() {
        if (!loggedIn.isLoggedIn()) {
            showLoginToast("You have to be logged in to subscribe");
            return;
        }
        if (loggedIn.getLoggedInUser().getUsername().equals(thisCurrentVideo.getPublisher())) {
            showLoginToast("You can't subscribe to your own channel");
            return;
        }

        String publisher = thisCurrentVideo.getPublisher();
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
        String publisher = thisCurrentVideo.getPublisher();
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
     * Method to handle the "Share" click event.
     */
    public void shareButtonClick() {
        String s = "android.resource://"
                + getPackageName() + "/" + this.thisCurrentVideo.getVideoUploaded();
        // Create the share intent
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, s);

        // Start the chooser activity
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    /**
     * Method to handle the "Pencil" click event.
     */
    public void pencilDescriptionButtonClick() {
        if (loggedIn.getLoggedInUser() == null) {
            Toast.makeText(this,
                    "You have to be logged in to edit the video description",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (thisCurrentVideo.getPublisher().equals(loggedIn.getLoggedInUser().getUsername())) {
            // Inflate the dialog layout
            LayoutInflater inflater = LayoutInflater.from(this);
            View dialogView =
                    inflater.inflate(R.layout.watch_page_video_dialog_edit_title, null);

            // Get the EditText from the dialog layout
            EditText editDescriptionInput = dialogView.findViewById(R.id.dialog_edit_title_input);

            // Set the current title in the EditText
            String currentDescription = thisCurrentVideo.getDescription();
            editDescriptionInput.setText(currentDescription);

            // Build the dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView)
                    .setTitle("Edit Video Description")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Update the video title with the new value
                        String newDescription = editDescriptionInput.getText().toString();
                        thisCurrentVideo.setDescription(newDescription);
                        TextView DescriptionTextView = findViewById(R.id.video_description);
                        DescriptionTextView.setText(thisCurrentVideo.getDescription());
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            // Show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Toast.makeText(this,
                    "You are not the publisher of this video",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method to handle the "Pencil" click event.
     */
    public void PencilButtonClick() {
        if (loggedIn.getLoggedInUser() == null) {
            Toast.makeText(this,
                    "You have to be logged in to edit the video title",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (thisCurrentVideo.getPublisher().equals(loggedIn.getLoggedInUser().getUsername())) {
            // Inflate the dialog layout
            LayoutInflater inflater = LayoutInflater.from(this);
            View dialogView =
                    inflater.inflate(R.layout.watch_page_video_dialog_edit_title, null);

            // Get the EditText from the dialog layout
            EditText editTitleInput = dialogView.findViewById(R.id.dialog_edit_title_input);

            // Set the current title in the EditText
            String currentTitle = thisCurrentVideo.getTitle();
            editTitleInput.setText(currentTitle);

            // Build the dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView)
                    .setTitle("Edit Video Title")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Update the video title with the new value
                        String newTitle = editTitleInput.getText().toString();
                        thisCurrentVideo.setTitle(newTitle);
                        setVideoTitle();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            // Show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Toast.makeText(this,
                    "You are not the publisher of this video",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method to open the comments activity.
     */
    public void openCommentsActivity() {
        Intent intent =
                new Intent(WatchVideoPageActivity.this, CommentsActivity.class);
        startActivity(intent);
    }

    private void handleEditIconsVisibility() {
        TextView pencilTitleTextView = findViewById(R.id.editTitle);
        TextView pencilDescriptionTextView = findViewById(R.id.editDescription);

        if (loggedIn.isLoggedIn() && thisCurrentVideo.getPublisher().equals(loggedIn.getLoggedInUser().getUsername())) {
            pencilTitleTextView.setVisibility(View.VISIBLE);
            pencilDescriptionTextView.setVisibility(View.VISIBLE);
        } else {
            pencilTitleTextView.setVisibility(View.GONE);
            pencilDescriptionTextView.setVisibility(View.GONE);
        }
    }
}