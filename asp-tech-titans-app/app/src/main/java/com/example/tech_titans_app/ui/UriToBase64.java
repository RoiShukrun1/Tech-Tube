package com.example.tech_titans_app.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class UriToBase64 {

    public static String convertUriToBase64(Context context, Uri imageUri) {
        try {
            // Get the input stream of the image from the URI
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            if (inputStream == null) {
                return null;
            }
            // Decode the input stream to a Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            // Convert the Bitmap to a byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            // Encode the byte array to Base64 without line breaks
            String base64Image = Base64.encodeToString(byteArray, Base64.NO_WRAP);
            // Prefix the Base64 string with the appropriate data URL scheme
            return "data:image/jpeg;base64," + base64Image;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
