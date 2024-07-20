package com.example.tech_titans_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.TokenManager;
import com.example.tech_titans_app.ui.api.UsersAPI;
import com.example.tech_titans_app.ui.UserResponse;
import com.example.tech_titans_app.ui.mainActivity.MainActivity;
import com.example.tech_titans_app.ui.models.account.UserData;
import com.example.tech_titans_app.ui.models.account.UsersDB;
import com.example.tech_titans_app.ui.models.account.UsersDataDao;
import com.example.tech_titans_app.ui.utilities.LoggedIn;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerRedirectText;
    private TextView guestRedirectText;
    private UsersAPI usersAPI;
    private UsersDataDao usersDataDao;
    private UsersDB usersDB;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerRedirectText = findViewById(R.id.registerRedirectText);
        guestRedirectText = findViewById(R.id.guestRedirectText);

        // Initialize UsersAPI and TokenManager
        usersAPI = new UsersAPI(this);
        tokenManager = new TokenManager(this);

        // Open UsersDB instance
        usersDB = UsersDB.getInstance(this);
        usersDataDao = usersDB.usersDao();

        // Set click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                checkLogin(username, password);
            }
        });

        // Set click listener for the redirect to registration
        registerRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for the redirect to main page
        guestRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "Continuing as guest", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (usersDB.isOpen()) {
            usersDB.close();
        }
    }

    // Method to check login credentials
    private void checkLogin(String username, String password) {
        UserData userData = new UserData(0, username, "", password, new ArrayList<>(), "");
        usersAPI.loginUser(userData, new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse != null) {
                        // Save the token
                        String token = userResponse.getToken();
                        tokenManager.saveToken(token);

                        fetchProfilePicturePath(userData, username);

                        usersAPI.getUserById(username, new Callback<UserData>() {
                            @Override
                            public void onResponse(@NonNull Call<UserData> call,
                                                   @NonNull Response<UserData> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    userData.setSubscriptions(response.body().getSubscriptions());
                                }

                            }

                            @Override
                            public void onFailure(Call<UserData> call, Throwable t) {
                                Toast.makeText(LoginActivity.this,
                                        "Failed to get subscription",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                        // Set logged in user
                        LoggedIn.getInstance().setLoggedInUser(userData);

                        // Navigate to MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        // Show success toast
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed: Invalid response", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    checkLoginLocal(username, password);
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                checkLoginLocal(username, password);
            }
        });
    }

    // Method to check login credentials locally
    private void checkLoginLocal(String username, String password) {
        UserData localUser = usersDataDao.getUserByUsername(username);
        if (localUser != null && localUser.getPassword().equals(password)) {
            LoggedIn.getInstance().setLoggedInUser(localUser);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(LoginActivity.this, "Server is not responding , searching for local user... Login successful (local)", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "username/password does not exist / server offline , searching for local user... no local user found", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchProfilePicturePath(UserData userData, String username) {
        usersAPI.getProfilePicture(username, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Set the profile picture path
                    String imagePath = "/uploads/profilePictures/" + username + ".png";
                    userData.setImage(imagePath);

                    // Set logged in user
                    LoggedIn.getInstance().setLoggedInUser(userData);

                    // Navigate to MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                    // Show success toast
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to load profile picture path", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed to load profile picture path", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
