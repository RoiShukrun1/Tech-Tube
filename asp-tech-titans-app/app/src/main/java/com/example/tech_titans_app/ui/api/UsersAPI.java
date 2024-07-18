package com.example.tech_titans_app.ui.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tech_titans_app.ui.CheckAuthResponse;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.TokenManager;
import com.example.tech_titans_app.ui.UserResponse;
import com.example.tech_titans_app.ui.entities.VideoDB;
import com.example.tech_titans_app.ui.models.account.UserData;
import com.example.tech_titans_app.ui.models.account.UsersDB;
import com.example.tech_titans_app.ui.models.account.UsersDataDao;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.ResponseBody;
import retrofit2.http.Path;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

public class UsersAPI {

    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private UsersDataDao usersDataDao;
    private TokenManager tokenManager;
    private String token;

    public UsersAPI(Context context) {
        tokenManager = new TokenManager(context);
        String baseUrl = context.getString(R.string.base_server_url).trim();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        String token = tokenManager.getToken();
                        if (token != null) {
                            Request request = chain.request().newBuilder()
                                    .addHeader("authorization", token)
                                    .build();
                            return chain.proceed(request);
                        }
                        return chain.proceed(chain.request());
                    }
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) // Use the correct port your server is running on
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        usersDataDao = UsersDB.getInstance(context).usersDao();
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
                        externalCallback.onFailure(call, new Throwable("Registration failed: " + response.message()));
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

    public void getUserById(String id, Callback<UserData> callback){
        Call<UserData> call = webServiceAPI.getUserById(id);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(@NonNull Call<UserData> call, @NonNull Response<UserData> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call,
                            new Throwable("Failed to load profile picture: " + response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserData> call, @NonNull Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void getUserSubsById(String id, Callback<List<UserData>> callback){
        Call<List<UserData>> call = webServiceAPI.getUserSubsById(id);
        call.enqueue(new Callback<List<UserData>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserData>> call, @NonNull Response<List<UserData>> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Failed to load profile picture: " + response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserData>> call, @NonNull Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void deleteUserById(String id, Callback<Void> callback) {
        Call<Void> call = webServiceAPI.deleteUserById(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(call, response);
                } else {
                    callback.onFailure(call, new Throwable("Failed to delete user: " + response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void checkAuth(Callback<CheckAuthResponse> callback) {
        String token = tokenManager.getToken();
        Log.d("UsersAPI", "Retrieved token: " + token); // Log the token

        if (token == null || token.isEmpty()) {
            callback.onFailure(null, new Throwable("Token not available"));
            return;
        }

        Call<CheckAuthResponse> call = webServiceAPI.checkAuth(token);
        call.enqueue(new Callback<CheckAuthResponse>() {
            @Override
            public void onResponse(Call<CheckAuthResponse> call, Response<CheckAuthResponse> response) {
                Log.d("UsersAPI", "Response code: " + response.code()); // Log the response code
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<CheckAuthResponse> call, Throwable t) {
                Log.e("UsersAPI", "Request failed: " + t.getMessage()); // Log the error
                callback.onFailure(call, t);
            }
        });
    }

    public void updateUserById(String id, PatchReqBody newParams) {
        Call<Void> call = webServiceAPI.updateUserById(id, newParams.getUpdateParams());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {}

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {}
        });
    }


}
