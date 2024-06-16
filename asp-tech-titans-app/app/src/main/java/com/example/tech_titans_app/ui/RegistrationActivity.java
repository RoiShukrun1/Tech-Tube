package com.example.tech_titans_app.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.models.account.AccountData;
import com.example.tech_titans_app.ui.models.account.AccountDataArray;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {
    private EditText nicknameText, usernameText, passwordText, confirmPasswordText;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageButton uploadPhotoButton;
    private Button signInButton;
    private TextView loginRedirectText;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        uploadPhotoButton = findViewById(R.id.imageButton);
        // Initialize EditText fields and Button
        nicknameText = findViewById(R.id.nicknameText);
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordregistrationText);
        confirmPasswordText = findViewById(R.id.confirmPasswordText);
        signInButton = findViewById(R.id.button2);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        // Add TextWatcher for each EditText field to perform validation
        nicknameText.addTextChangedListener(textWatcher);
        usernameText.addTextChangedListener(textWatcher);
        passwordText.addTextChangedListener(textWatcher);
        confirmPasswordText.addTextChangedListener(textWatcher);

        // Set onClickListener for the Sign-in Button
        signInButton.setOnClickListener(v -> {
            // Perform registration logic here
            register();
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        uploadPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    // TextWatcher to monitor changes in EditText fields and perform validation
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            validateFields();
        }
    };

    // Method to validate all EditText fields
    private boolean validateFields() {
        boolean flag = true; // Start with true as initial flag value

        String nickname = nicknameText.getText().toString().trim();
        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String confirmPassword = confirmPasswordText.getText().toString().trim();

        if (nickname.isEmpty() || !isValidName(nickname)) {
            nicknameText.setError("Nickname must contain only letters and cannot be empty");
            flag = false; // Set flag to false if nickname is invalid or empty
        } else {
            nicknameText.setError(null);
        }

        if (username.isEmpty() || !isValidName(username)) {
            usernameText.setError("Username must contain only letters and cannot be empty");
            flag = false; // Set flag to false if username is invalid or empty
        } else {
            usernameText.setError(null);
        }

        if (!isValidPassword(password)) {
            passwordText.setError("Password must contain at least 8 characters, including uppercase, lowercase, numbers, and special characters.");
            flag = false; // Set flag to false if password is invalid
        } else {
            passwordText.setError(null);
        }

        if (!password.equals(confirmPassword) || confirmPassword.isEmpty()) {
            confirmPasswordText.setError("Password and Confirm Password must match and cannot be empty");
            flag = false; // Set flag to false if passwords don't match or confirm password is empty
        } else {
            confirmPasswordText.setError(null);
        }

        return flag; // Return the final flag value
    }

    private void register() {
        if (validateFields()) {
            // Perform registration logic here
            String nickname = nicknameText.getText().toString();
            String username = usernameText.getText().toString();
            String password = passwordText.getText().toString();
            AccountDataArray accountDataArray = AccountDataArray.getInstance();
            AccountData newAccount = new AccountData(accountDataArray.getLength() + 1, username, nickname, password, new ArrayList<>(), selectedImageUri);
            AccountDataArray.getInstance().addAccount(newAccount);
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean isValidName(String name) {
        Pattern namePattern = Pattern.compile("^[a-zA-Z]+$");
        return namePattern.matcher(name).matches();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri));
                int targetWidth = uploadPhotoButton.getWidth();
                int targetHeight = uploadPhotoButton.getHeight();
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);
                // Set the scaled bitmap as the source of the ImageButton
                uploadPhotoButton.setImageBitmap(scaledBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
