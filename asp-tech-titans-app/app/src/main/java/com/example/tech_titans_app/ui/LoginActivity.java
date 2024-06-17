package com.example.tech_titans_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.mainActivity.MainActivity;
import com.example.tech_titans_app.ui.models.account.AccountData;
import com.example.tech_titans_app.ui.models.account.AccountDataArray;
import com.example.tech_titans_app.ui.utilities.LoggedIn;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerRedirectText = findViewById(R.id.registerRedirectText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                checkLogin(username, password);
            }
        });

        registerRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkLogin(String username, String password) {
        AccountDataArray accountDataArray = AccountDataArray.getInstance();
        List<AccountData> accounts = accountDataArray.getAccountArray();
        for (AccountData account : accounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                LoggedIn.getInstance().setLoggedInUser(account);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                return; // Exit the method if login is successful
            }
        }
        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
    }
}
