package com.example.tech_titans_app.ui.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

import com.example.tech_titans_app.ui.entities.Comment;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.models.account.UserData;

import java.util.Map;

public interface WebServiceAPI {

    /**
     * Sets api fot users operation.
     */

    @POST("/api/users/register")
    Call<Void> registerUser(@Body UserData user);

    /**
     * Sets api fot videos operation.
     */
    @GET("/api/users/user/videos/{id}")
    Call<Video> getVideoById(@Path("id") String id);
    @POST("/api/users/user/videos")
    Call<Void> uploadVideo(@Body Video video);
    @DELETE("/api/users/user/videos/{id}")
    Call<Void> deleteVideoById(@Path("id") String id);
    @PATCH("/api/users/user/videos/{videoId}")
    Call<Void> updateVideoById(@Path("videoId") String videoId,
                               @Body Map<String, String> updateParams);

    /**
     * Sets api fot comments operation.
     */

    @GET("/api/users/user/videos/{videoId}/comments/{commentId}")
    Call<Comment> getCommentById(@Path("videoId") String videoId,
                                 @Path("commentId") String commentId);

}