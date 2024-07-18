package com.example.tech_titans_app.ui.models.account;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class UsersDataArray extends Application {
    private static UsersDataArray instance;
    private List<UserData> userDataArray;

    private UsersDataArray() {
        userDataArray = new ArrayList<>();
    }

    public static synchronized UsersDataArray getInstance() {
        if (instance == null) {
            instance = new UsersDataArray();
        }
        return instance;
    }

    public List<UserData> getAccountArray() {
        return userDataArray;
    }

    public void addAccount(UserData userData) {
        userDataArray.add(userData);
    }

    public int getLength() {
        return userDataArray.size();
    }
}
