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
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Uri.class, new UriTypeAdapter())
                .create();


        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/")
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
                }
            }

            @Override
            public void onFailure(@NonNull Call<Video> call, @NonNull Throwable t) {
                Log.i("Video title", t.getMessage());
                callback.onFailure(call, t);
            }
        });
    }
}
