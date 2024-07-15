package com.example.tech_titans_app.ui.api;

import android.content.Context;
import com.example.tech_titans_app.ui.models.account.UserData;
import com.example.tech_titans_app.ui.models.account.UsersDB;
import com.example.tech_titans_app.ui.models.account.UsersDataDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.Executors;
import com.example.tech_titans_app.ui.TokenManager;


public class UsersAPI {

    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private UsersDataDao usersDataDao;
    private TokenManager tokenManager;


    public UsersAPI(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:80/") // Use the correct port your server is running on
                .addConverterFactory(GsonConverterFactory.create())
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
                    externalCallback.onFailure(call, new Throwable("Registration failed: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                externalCallback.onFailure(call, t);
            }
        });
    }


    public void loginUser(UserData user, Callback<Void> externalCallback) {
        Call<Void> call = webServiceAPI.loginUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
//                    // Save the user to Room database
//                    Executors.newSingleThreadExecutor().execute(() -> {
//                        usersDataDao.insert(user);
//                    });
                    externalCallback.onResponse(call, response);
                } else {
                    externalCallback.onFailure(call, new Throwable("Registration failed: " + response.message()));
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                externalCallback.onFailure(call, t);
            }
        });
    }


}
