package com.example.tech_titans_app.ui.models.account;

import android.app.Application;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity()
public class UserData extends Application {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String nickname;
    private String password;
    private List<String> subscriptions;
    private String image;
    // Constructor
  public UserData(int id, String username, String nickname,
                  String password, List<String> subscriptions, String image) {
      this.id = id;
      this.username = username;
      this.nickname = nickname;
      this.password = password;
      this.subscriptions = subscriptions;
      this.image = image;
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
    public String getImage() {
        return image;
    }
    public void  setImage(String newImage){
        this.image = newImage;
    }
}
