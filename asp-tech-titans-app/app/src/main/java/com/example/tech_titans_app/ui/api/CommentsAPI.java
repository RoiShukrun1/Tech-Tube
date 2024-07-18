package com.example.tech_titans_app.ui.api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.TokenManager;
import com.example.tech_titans_app.ui.Converters.UriTypeAdapter;
import com.example.tech_titans_app.ui.entities.Comment;
import com.example.tech_titans_app.ui.entities.VideoDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.example.tech_titans_app.ui.Converters.usernameDeserializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentsAPI {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private TokenManager tokenManager;
    private String token;

    public CommentsAPI(Context context) {
        tokenManager = new TokenManager(context);
        token = tokenManager.getToken();

        Type listType = new TypeToken<List<String>>(){}.getType();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Uri.class, new UriTypeAdapter())
                .registerTypeAdapter(listType, new usernameDeserializer())
                .create();

        String baseUrl = context.getString(R.string.base_server_url).trim();

        if (token != null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            String token = tokenManager.getToken();
                            Log.e("token", token);
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
        }
        else {

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            webServiceAPI = retrofit.create(WebServiceAPI.class);

        }
    }

    public void getCommentById(String videoId, String commentId, Callback<Comment> callback) {
        Call<Comment> call = webServiceAPI.getCommentById(videoId, commentId);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(@NonNull Call<Comment> call,
                                   @NonNull Response<Comment> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Response not successful"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Comment> call, @NonNull Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void getAllComments(String videoId, Callback<List<Comment>> callback) {
        Call<List<Comment>> call = webServiceAPI.getAllComments(videoId);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(@NonNull Call<List<Comment>> call,
                                   @NonNull Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Response not successful"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Comment>> call, @NonNull Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void deleteCommentById(String videoId, String commentId) {
        Call<Void> call = webServiceAPI.deleteCommentById(videoId, commentId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {}

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {}
        });
    }

    public void updateCommentById(String videoId, String prevCommentId, Comment newComment) {
        Call<Void> call = webServiceAPI.updateCommentById(videoId, prevCommentId, newComment);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {}

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {}
        });
    }

    public void createNewComment(String videoId, Comment newComment) {
        Call<Void> call = webServiceAPI.createNewComment(videoId, newComment);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {}

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {}
        });
    }




}
