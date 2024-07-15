package com.example.tech_titans_app.ui.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import com.example.tech_titans_app.ui.entities.Comment;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.models.account.UserData;

public interface WebServiceAPI {

    @POST("/api/users/register")
    Call<Void> registerUser(@Body UserData user);

    @GET("/api/users/{id}")
    Call<UserData> getUserById(@Path("id") String id);

    @DELETE("/api/users/{id}")
    Call<Void> deleteUserById(@Path("id") String id);

    @GET("/api/users/user/videos/{id}")
    Call<Video> getVideoById(@Path("id") String id);

    @GET("/api/users/user/videos/{videoId}/comments/{commentId}")
    Call<Comment> getCommentById(@Path("videoId") String videoId,
                                 @Path("commentId") String commentId);
}