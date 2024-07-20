package com.example.tech_titans_app.ui.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Header;
import okhttp3.ResponseBody;

import com.example.tech_titans_app.ui.CheckAuthResponse;
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

    @PATCH("/api/users/{id}")
    Call<Void> updateUserById(@Path("id") String id,
                              @Body Map<String, String> updateParams);

    @GET("/api/users/{id}/subscribers")
    Call<List<UserData>> getUserSubsById(@Path("id") String id);

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

    @Multipart
    @POST("/api/users/:id/videos")
    Call<Void> uploadVideo(
            @Part("id") RequestBody id,
            @Part MultipartBody.Part videoUploaded,
            @Part("thumbnail") RequestBody thumbnail,
            @Part("title") RequestBody title,
            @Part("description") RequestBody description,
            @Part("playlist") RequestBody playlist,
            @Part("publisher") RequestBody publisher,
            @Part("publisherImage") RequestBody publisherImage,
            @Part("views") RequestBody views,
            @Part("date") RequestBody date,
            @Part("usersLikes") RequestBody usersLikes,
            @Part("usersUnlikes") RequestBody usersUnlikes,
            @Part("comments") RequestBody comments
    );


    @GET("/api/users/user/videos/{videoId}/relatedvideos")
    Call<List<Video>> getRelatedVideos(@Path("videoId") String videoId);

    @GET("/api/users/{id}/videos")
    Call<List<Video>> getPublisherVideosById(@Path("id") String id);

    @POST("/api/users/user/videos")
    Call<Void> uploadVideo(@Body Video video);

    @DELETE("/api/users/user/videos/{id}")
    Call<Void> deleteVideoById(@Path("id") String id);

    @PATCH("/api/users/user/videos/{videoId}")
    Call<Void> updateVideoById(@Path("videoId") String videoId,
                               @Body Map<String, String> updateParams);

    @GET("api/videos")
    Call<List<Video>> get20Videos();

    @GET("/api/videos/all")
    Call<List<Video>> getAllVideos();


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

    @GET("/api/token/checkAuth")
    Call<CheckAuthResponse> checkAuth(@Header("Authorization") String token);

}
