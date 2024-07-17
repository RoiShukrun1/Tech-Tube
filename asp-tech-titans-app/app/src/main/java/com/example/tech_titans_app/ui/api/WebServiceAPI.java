package com.example.tech_titans_app.ui.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.Path;
import okhttp3.ResponseBody;

import com.example.tech_titans_app.ui.entities.Comment;
import com.example.tech_titans_app.ui.UserResponse;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.models.account.UserData;

import java.util.List;
import java.util.Map;

public interface WebServiceAPI {

    /**
     * Sets api fot users operation.
     */

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
    
    /**
     * Sets api fot videos operation.
     */
     
    @GET("/api/users/user/videos/{id}")
    Call<Video> getVideoById(@Path("id") String id);

    @GET("api/videos")
    Call<List<Video>> get20Videos();

    @GET("/api/videos/all")
    Call<List<Video>> getAllVideos();

    @GET("/api/users/user/videos/{videoId}/relatedvideos")
    Call<List<Video>> getRelatedVideos(@Path("videoId") String videoId);
    
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

    @GET("/api/users/user/videos/{videoId}/comments")
    Call<List<Comment>> getAllComments(@Path("videoId") String videoId);

    @PUT("/api/users/user/videos/{videoId}/comments/{commentId}")
    Call<Void> updateCommentById(@Path("videoId") String videoId,
                                 @Path("commentId") String commentId, @Body Comment newComment);
    @DELETE("/api/users/user/videos/{videoId}/comments/{commentId}")
    Call<Void> deleteCommentById(@Path("videoId") String videoId,
                                 @Path("commentId") String commentId);
    @POST("/api/users/user/videos/{videoId}/comments")
    Call<Void> createNewComment(@Path("videoId") String videoId, @Body Comment newComment);

}
