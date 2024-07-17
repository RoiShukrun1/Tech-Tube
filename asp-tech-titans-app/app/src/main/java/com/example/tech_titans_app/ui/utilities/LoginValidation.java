package com.example.tech_titans_app.ui.utilities;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tech_titans_app.ui.LoginActivity;
import com.example.tech_titans_app.ui.TokenManager;
import com.example.tech_titans_app.ui.api.UsersAPI;
import com.example.tech_titans_app.ui.CheckAuthResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginValidation {

    public static void checkLoggedIn(Context context) {
        if (LoggedIn.getInstance().getLoggedInUser() == null) {
            redirectToLogin(context, "You must be logged in to access this page");
        } else {
            TokenManager tokenManager = new TokenManager(context);
            String token = tokenManager.getToken();

            if (token == null || token.isEmpty()) {
                redirectToLogin(context, "Invalid token. Please log in again.");
            } else {
                UsersAPI usersAPI = new UsersAPI(context);
                usersAPI.checkAuth(new Callback<CheckAuthResponse>() {
                    @Override
                    public void onResponse(Call<CheckAuthResponse> call, Response<CheckAuthResponse> response) {
                        if (!response.isSuccessful() || response.body() == null || !response.body().isAuthenticated()) {
                            redirectToLogin(context, "Session expired. Please log in again.");
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckAuthResponse> call, Throwable t) {
                        redirectToLogin(context, "Unable to validate session. Please log in again.");
                    }
                });
            }
        }
    }

    private static void redirectToLogin(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).finish();
        }
    }
}
