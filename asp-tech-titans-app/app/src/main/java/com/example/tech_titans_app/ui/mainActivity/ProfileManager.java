package com.example.tech_titans_app.ui.mainActivity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.utilities.LoggedIn;

public class ProfileManager {
    private Context context;
    private LinearLayout profileSection;
    private ImageView profilePicture;
    private TextView logoutText;
    private TextView loginText;

    public ProfileManager(Context context, LinearLayout profileSection, ImageView profilePicture, TextView logoutText, TextView loginText) {
        this.context = context;
        this.profileSection = profileSection;
        this.profilePicture = profilePicture;
        this.logoutText = logoutText;
        this.loginText = loginText;
    }

    // Update UI based on login status
    public void updateUI() {
        if (LoggedIn.getInstance().isLoggedIn()) {
            profileSection.setVisibility(View.VISIBLE);
            loginText.setVisibility(View.GONE);
            Glide.with(context).load(LoggedIn.getInstance().getLoggedInUser().getImage()).into(profilePicture);
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
}
