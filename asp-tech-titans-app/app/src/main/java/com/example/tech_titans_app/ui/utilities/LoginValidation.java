package com.example.tech_titans_app.ui.utilities;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tech_titans_app.ui.LoginActivity;

public class LoginValidation {
    public static void checkLoggedIn(Context context) {
        if (LoggedIn.getInstance().getLoggedInUser() == null) {
            Toast.makeText(context, "You must be logged in to access this page", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            if (context instanceof AppCompatActivity) {
                ((AppCompatActivity) context).finish();
            }
        }
    }
}
