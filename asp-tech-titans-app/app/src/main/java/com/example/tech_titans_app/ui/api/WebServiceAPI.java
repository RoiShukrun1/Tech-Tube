package com.example.tech_titans_app.ui.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import okhttp3.ResponseBody;

import com.example.tech_titans_app.ui.UserResponse;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.models.account.UserData;

import java.util.List;

public interface WebServiceAPI {

    @POST("/api/users/register")
    Call<Void> registerUser(@Body UserData user);

    @GET("/api/users/{id}")
    Call<UserData> getUserById(@Path("id") String id);

    @DELETE("/api/users/{id}")
    Call<Void> deleteUserById(@Path("id") String id);

    @POST("/api/token/")
    Call<UserResponse> loginUser(@Body UserData user);

    @GET("/uploads/profilePictures/{username}.png")
    Call<ResponseBody> getProfilePicture(@Path("username") String username);

    @GET("/api/videos")
    Call<List<Video>> getVideos(); // Add this method

}
