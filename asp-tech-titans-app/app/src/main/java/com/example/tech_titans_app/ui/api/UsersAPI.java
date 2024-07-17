package com.example.tech_titans_app.ui.api;

import android.content.Context;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.TokenManager;
import com.example.tech_titans_app.ui.UserResponse;
import com.example.tech_titans_app.ui.models.account.UserData;
import com.example.tech_titans_app.ui.models.account.UsersDB;
import com.example.tech_titans_app.ui.models.account.UsersDataDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.ResponseBody;

import java.io.InputStream;
import java.util.concurrent.Executors;

public class UsersAPI {

    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private UsersDataDao usersDataDao;
    private TokenManager tokenManager;

    public UsersAPI(Context context) {

        String baseUrl = context.getString(R.string.base_server_url).trim();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) // Use the correct port your server is running on
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        usersDataDao = UsersDB.getInstance(context).usersDao();
        tokenManager = new TokenManager(context); // Initialize TokenManager
    }

    public void registerUser(UserData user, Callback<Void> externalCallback) {
        Call<Void> call = webServiceAPI.registerUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Save the user to Room database
                    Executors.newSingleThreadExecutor().execute(() -> {
                        usersDataDao.insert(user);
                    });
                    externalCallback.onResponse(call, response);
                } else {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        String errorMessage = errorBody != null ? errorBody.string() : "Error occurred";
                        externalCallback.onFailure(call, new Throwable(errorMessage));
                    } catch (Exception e) {
                        externalCallback.onFailure(call, new Throwable("Registration failed5: " + response.message()));
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                externalCallback.onFailure(call, t);
            }
        });
    }

    public void loginUser(UserData user, Callback<UserResponse> externalCallback) {
        Call<UserResponse> call = webServiceAPI.loginUser(user);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse != null) {
                        tokenManager.saveToken(userResponse.getToken());
                        externalCallback.onResponse(call, response);
                    } else {
                        externalCallback.onFailure(call, new Throwable("Invalid response from server"));
                    }
                } else {
                    try {
                        ResponseBody errorBody = response.errorBody();
                        String errorMessage = errorBody != null ? errorBody.string() : "Unknown error";
                        externalCallback.onFailure(call, new Throwable(errorMessage));
                    } catch (Exception e) {
                        externalCallback.onFailure(call, new Throwable("Login failed: " + response.message()));
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                externalCallback.onFailure(call, t);
            }
        });
    }

    public void getProfilePicture(String username, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = webServiceAPI.getProfilePicture(username);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Failed to load profile picture: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }
}