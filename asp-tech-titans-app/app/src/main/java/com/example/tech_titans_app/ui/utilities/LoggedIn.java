package com.example.tech_titans_app.ui.utilities;

import com.example.tech_titans_app.ui.models.account.UserData;

public class LoggedIn {
    private static LoggedIn instance;
    private UserData loggedInUser;
    private LogoutListener logoutListener;

    private LoggedIn() {
    }

    public static synchronized LoggedIn getInstance() {
        if (instance == null) {
            instance = new LoggedIn();
        }
        return instance;
    }

    public UserData getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(UserData loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void logOut() {
        this.loggedInUser = null;
        if (logoutListener != null) {
            logoutListener.onLogout();
        }
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public void setLogoutListener(LogoutListener listener) {
        this.logoutListener = listener;
    }

    public interface LogoutListener {
        void onLogout();
    }
}
