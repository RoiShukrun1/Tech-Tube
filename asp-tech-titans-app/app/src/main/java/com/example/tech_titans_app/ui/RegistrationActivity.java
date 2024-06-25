package com.example.tech_titans_app.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.mainActivity.MainActivity;
import com.example.tech_titans_app.ui.models.account.AccountData;
import com.example.tech_titans_app.ui.models.account.AccountDataArray;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {
    private EditText nicknameText, usernameText, passwordText, confirmPasswordText;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String KEY_IMAGE_URI = "image_uri";
    private ImageButton uploadPhotoButton;
    private Button signInButton;
    private TextView loginRedirectText;
    private TextView guestRedirectText;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Initialize the views
        uploadPhotoButton = findViewById(R.id.imageButton);
        nicknameText = findViewById(R.id.nicknameText);
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordregistrationText);
        confirmPasswordText = findViewById(R.id.confirmPasswordText);
        signInButton = findViewById(R.id.button2);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        guestRedirectText = findViewById(R.id.guestRedirectText);

        // Set default image
        uploadPhotoButton.setImageResource(R.drawable.profile);
        // Restore the image URI
        if (savedInstanceState != null) {
            selectedImageUri = savedInstanceState.getParcelable(KEY_IMAGE_URI);
            if (selectedImageUri != null) {
                loadImageButton();
            }
        }

        // Add TextWatcher for each EditText field to perform validation
        nicknameText.addTextChangedListener(textWatcher);
        usernameText.addTextChangedListener(textWatcher);
        passwordText.addTextChangedListener(textWatcher);
        confirmPasswordText.addTextChangedListener(textWatcher);

        // Set onClickListener for the Sign-in Button
        signInButton.setOnClickListener(v -> {
            register();
        });
        //Set redirect to login page
        loginRedirectText.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        //Set redirect to main page
        guestRedirectText.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(RegistrationActivity.this, "Continuing as guest", Toast.LENGTH_SHORT).show();
        });

        uploadPhotoButton.setOnClickListener(v -> openGallery());
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
            nicknameText.setError("Nickname must contain only letters and cannot be less then 2 characters");
            flag = false;
        } else {
            nicknameText.setError(null);
        }

        if (username.isEmpty() || !isValidName(username)) {
            usernameText.setError("Username must contain only letters and cannot be less then 2 characters");
            flag = false;
        } else {
            usernameText.setError(null);
        }

        if (!isValidPassword(password)) {
            passwordText.setError("Password must contain at least 8 characters, including uppercase, lowercase, numbers, and special characters.");
            flag = false;
        } else {
            passwordText.setError(null);
        }

        if (!password.equals(confirmPassword) || confirmPassword.isEmpty()) {
            confirmPasswordText.setError("Password and Confirm Password must match and cannot be empty");
            flag = false;
        } else {
            confirmPasswordText.setError(null);
        }

        return flag;
    }

    private void register() {
        if (validateFields()) {
            String nickname = nicknameText.getText().toString();
            String username = usernameText.getText().toString();
            String password = passwordText.getText().toString();
            AccountDataArray accountDataArray = AccountDataArray.getInstance();
            // Use default image if no image selected
            if (selectedImageUri == null) {
                selectedImageUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.profile);
            }
            AccountData newAccount = new AccountData(accountDataArray.getLength() + 1, username, nickname, password, new ArrayList<>(), selectedImageUri);
            AccountDataArray.getInstance().addAccount(newAccount);
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }

    // Regular expression for password validation
    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    // Regular expression for name validation
    private boolean isValidName(String name) {
        Pattern namePattern = Pattern.compile("^[a-zA-Z]{2,}$");
        return namePattern.matcher(name).matches();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
    // Handle the result of the gallery intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            loadImageButton();
        }
    }
    // Load the selected image into the ImageButton
    private void loadImageButton() {
        ViewTreeObserver observer = uploadPhotoButton.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (uploadPhotoButton.getViewTreeObserver().isAlive()) {
                    uploadPhotoButton.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
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
        });
    }
    // Save the image URI
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the image URI
        if (selectedImageUri != null) {
            outState.putParcelable(KEY_IMAGE_URI, selectedImageUri);
        }
    }
    // Restore the image URI
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            selectedImageUri = savedInstanceState.getParcelable(KEY_IMAGE_URI);
            if (selectedImageUri != null) {
                loadImageButton();
            }
        }
    }
}
