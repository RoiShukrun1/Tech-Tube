package com.example.tech_titans_app.ui.api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.TokenManager;
import com.example.tech_titans_app.ui.adapters.UriTypeAdapter;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.entities.VideoDB;
import com.example.tech_titans_app.ui.entities.VideoDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.example.tech_titans_app.ui.Converters.usernameDeserializer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideosAPI {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private VideoDao videoDao;
    private TokenManager tokenManager;



    public VideosAPI(Context context) {
        tokenManager = new TokenManager(context);

        // Define the type for the custom deserializer
        Type listType = new TypeToken<List<String>>(){}.getType();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Uri.class, new UriTypeAdapter())
                .registerTypeAdapter(listType, new usernameDeserializer())
                .create();

        String baseUrl = context.getString(R.string.base_server_url).trim();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        String token = tokenManager.getToken();
                        Log.e ("token", token);
                        Request request = chain.request().newBuilder()
                                .addHeader("authorization", token)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson)).client(client)
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        videoDao = VideoDB.getInstance(context).videoDao();
    }

    public void getVideoById(String id, Callback<Video> callback) {
        Call<Video> call = webServiceAPI.getVideoById(id);
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(@NonNull Call<Video> call, @NonNull Response<Video> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Response not successful"));
                    Log.e("API_CALL", "API call failed getvideobyid");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Video> call, @NonNull Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void uploadVideo(File videoFile, String base64Thumbnail, String title, String description, String playlist, String publisher, String publisherImage, Callback<Void> callback) {
        getHighestID(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String newIdStr = response.body();

                    // Prepare the parts for the upload request
                    RequestBody videoRequestBody = RequestBody.create(MediaType.parse("video/*"), videoFile);
                    MultipartBody.Part videoPart = MultipartBody.Part.createFormData("videoUploaded", videoFile.getName(), videoRequestBody);
                    RequestBody thumbnailPart = RequestBody.create(MediaType.parse("text/plain"), base64Thumbnail);
                    RequestBody titlePart = RequestBody.create(MediaType.parse("text/plain"), title);
                    RequestBody descriptionPart = RequestBody.create(MediaType.parse("text/plain"), description);
                    RequestBody playlistPart = RequestBody.create(MediaType.parse("text/plain"), playlist);
                    RequestBody publisherPart = RequestBody.create(MediaType.parse("text/plain"), publisher);
                    RequestBody publisherImagePart = RequestBody.create(MediaType.parse("text/plain"), publisherImage);
                    RequestBody viewsPart = RequestBody.create(MediaType.parse("text/plain"), "0");
                    RequestBody datePart = RequestBody.create(MediaType.parse("text/plain"), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    RequestBody usersLikesPart = RequestBody.create(MediaType.parse("application/json"), "[]");
                    RequestBody usersUnlikesPart = RequestBody.create(MediaType.parse("application/json"), "[]");
                    RequestBody commentsPart = RequestBody.create(MediaType.parse("application/json"), "[]");

                    // Include the new ID field
                    RequestBody idPart = RequestBody.create(MediaType.parse("text/plain"), newIdStr);

                    // Make the upload video call
                    Call<Void> uploadCall = webServiceAPI.uploadVideo(
                            idPart,
                            videoPart,
                            thumbnailPart,
                            titlePart,
                            descriptionPart,
                            playlistPart,
                            publisherPart,
                            publisherImagePart,
                            viewsPart,
                            datePart,
                            usersLikesPart,
                            usersUnlikesPart,
                            commentsPart
                    );

                    uploadCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                            if (response.isSuccessful()) {
                                callback.onResponse(call, response);
                            } else {
                                callback.onFailure(call, new Throwable("Response not successful"));
                                Log.e("API_CALL", "API call failed uploadvideo");
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                            callback.onFailure(call, t);
                        }
                    });
                } else {
                    callback.onFailure(null, new Throwable("Failed to get new ID"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                callback.onFailure(null, t);
            }
        });
    }

    public void deleteVideoById(String id) {
        Call<Void> call = webServiceAPI.deleteVideoById(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {}

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {}
        });
    }

    public void updateVideoById(String id, PatchReqBody newParams) {
        Call<Void> call = webServiceAPI.updateVideoById(id, newParams.getUpdateParams());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {}

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {}
        });
    }
    public void getAllVideos(Callback<List<Video>> callback) {
        Call<List<Video>> call = webServiceAPI.getAllVideos();
        call.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(@NonNull Call<List<Video>> call,
                                   @NonNull Response<List<Video>> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Response not successful"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Video>> call, @NonNull Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void getHighestID(Callback<String> callback) {
        Call<List<Video>> call = webServiceAPI.getAllVideos();
        call.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(@NonNull Call<List<Video>> call, @NonNull Response<List<Video>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int highestID = -1;
                    List<Video> videoList = response.body();
                    // Find the highest ID
                    for (Video video : videoList) {
                        if (video.getId() > highestID) {
                            highestID = video.getId();
                        }
                    }
                    String newIdStr = String.valueOf(highestID + 1);
                    callback.onResponse(null, Response.success(newIdStr)); // Pass null for the call parameter
                } else {
                    callback.onFailure(null, new Throwable("Failed to get video list or video list is empty")); // Pass null for the call parameter
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Video>> call, @NonNull Throwable t) {
                callback.onFailure(null, t); // Pass null for the call parameter
            }
        });
    }
}
