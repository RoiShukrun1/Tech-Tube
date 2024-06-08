package com.example.tech_titans_app.ui.mainActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tech_titans_app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FilterUtils().setupFilterClickListeners(findViewById(android.R.id.content));
        new SearchBarUtils(findViewById(android.R.id.content));
    }
}
