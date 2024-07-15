package com.example.tech_titans_app.ui.mainActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.api.UsersAPI;
import com.example.tech_titans_app.ui.models.account.UsersDB;
import com.example.tech_titans_app.ui.models.account.UsersDataDao;
import com.example.tech_titans_app.ui.utilities.LoggedIn;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileManager {
    private Context context;
    private LinearLayout profileSection;
    private ImageView profilePicture;
    private TextView logoutText;
    private TextView loginText;
    private UsersAPI usersAPI;
    private UsersDataDao usersDataDao;

    public ProfileManager(Context context, LinearLayout profileSection, ImageView profilePicture, TextView logoutText, TextView loginText) {
        this.context = context;
        this.profileSection = profileSection;
        this.profilePicture = profilePicture;
        this.logoutText = logoutText;
        this.loginText = loginText;
        this.usersAPI = new UsersAPI(context); // Initialize UsersAPI
        this.usersDataDao = UsersDB.getInstance(context).usersDao(); // Initialize UsersDataDao
    }

    // Update UI based on login status
    public void updateUI() {
        if (LoggedIn.getInstance().isLoggedIn()) {
            profileSection.setVisibility(View.VISIBLE);
            loginText.setVisibility(View.GONE);
            fetchAndLoadProfilePicture(LoggedIn.getInstance().getLoggedInUser().getUsername()); // Fetch profile picture from server
            logoutText.setText(R.string.logout);
        } else {
            profileSection.setVisibility(View.GONE);
            loginText.setVisibility(View.VISIBLE);
        }
    }

    // Handle user logout
    public void handleLogout() {
        LoggedIn.getInstance().logOut();
    }

    // Fetch profile picture from server and load it into ImageView
    private void fetchAndLoadProfilePicture(String username) {
        usersAPI.getProfilePicture(username, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    InputStream inputStream = response.body().byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    profilePicture.post(() -> Glide.with(context).load(bitmap).into(profilePicture)); // Load bitmap into ImageView using Glide
                } else {
                    loadProfilePictureFromLocal(username);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loadProfilePictureFromLocal(username);
            }
        });
    }

    // Load profile picture from local Room database if server is not responding
    private void loadProfilePictureFromLocal(String username) {
        AsyncTask.execute(() -> {
            String imagePath = usersDataDao.getUserProfilePicture(username);
            if (imagePath != null) {
                profilePicture.post(() -> Glide.with(context).load(imagePath).into(profilePicture));
            }
        });
    }
}
