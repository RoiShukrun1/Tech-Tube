package com.example.tech_titans_app.ui.utilities;

import com.example.tech_titans_app.ui.models.account.AccountData;

public class LoggedIn {
    private static LoggedIn instance;
    private AccountData loggedInUser;

    private LoggedIn() {}

    public static synchronized LoggedIn getInstance() {
        if (instance == null) {
            instance = new LoggedIn();
        }
        return instance;
    }

    public AccountData getLoggedInUser() {
        return loggedInUser;
    }
    public void setLoggedInUser(AccountData loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void logOut() {
        this.loggedInUser = null;
    }

}
