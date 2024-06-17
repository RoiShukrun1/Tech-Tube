package com.example.tech_titans_app.ui.models.account;

import android.app.Application;
import android.net.Uri;
import android.view.View;

import java.util.List;

public class AccountData extends Application {
    private int id;
    private String username;
    private String nickname;
    private String password;
    private List<String> subscriptions;
    private Uri profilePicture;
        // Constructor
  public AccountData(int id, String username, String nickname, String password, List<String> subscriptions, Uri profilePicture) {
      this.id = id;
      this.username = username;
      this.nickname = nickname;
      this.password = password;
      this.subscriptions = subscriptions;
      this.profilePicture = profilePicture;
  }
  // Getter and Setter methods


    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public List<String> getSubscriptions() {
        return subscriptions;
        }
    public Uri getProfilePicture() {
        return profilePicture;
    }
}
