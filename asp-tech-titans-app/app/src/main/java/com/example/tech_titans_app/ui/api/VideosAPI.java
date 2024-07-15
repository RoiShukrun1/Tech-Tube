package com.example.tech_titans_app.ui.api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.adapters.UriTypeAdapter;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.entities.VideoDB;
import com.example.tech_titans_app.ui.entities.VideoDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.example.tech_titans_app.ui.Converters.usernameDeserializer;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideosAPI {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private VideoDao videoDao;

    public VideosAPI(Context context) {

        // Define the type for the custom deserializer
        Type listType = new TypeToken<List<String>>(){}.getType();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Uri.class, new UriTypeAdapter())
                .registerTypeAdapter(listType, new usernameDeserializer())
                .create();

        String baseUrl = context.getString(R.string.base_server_url).trim();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
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

    public void uploadVideo(Video video, Callback<Void> callback) {
        Call<Void> call = webServiceAPI.uploadVideo(video);
        call.enqueue(new Callback<Void>() {
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



}
