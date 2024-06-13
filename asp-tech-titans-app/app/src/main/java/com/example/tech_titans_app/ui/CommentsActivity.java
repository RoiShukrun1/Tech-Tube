package com.example.tech_titans_app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.adapters.commentsAdapter;
import com.example.tech_titans_app.ui.viewmodels.commentsViewModel;

public class CommentsActivity extends AppCompatActivity {

    private commentsViewModel commentsViewModel;
    private commentsAdapter commentsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_comments);

        ImageButton button = findViewById(R.id.comments_close_button);

        initiateCommentsSection();

        // Set OnClickListener for the ImageButton
        button.setOnClickListener(v -> navigateBackToMain());
    }

    private void initiateCommentsSection() {
        RecyclerView listComments = findViewById(R.id.comments_scrolling);
        listComments.setLayoutManager(new LinearLayoutManager(this));

        commentsAdapter = new commentsAdapter();
        listComments.setAdapter(commentsAdapter);

        commentsViewModel = new ViewModelProvider(this).get(commentsViewModel.class);
        commentsViewModel.getAllComments().observe(this, comments -> commentsAdapter.setComments(comments));
    }

    private void navigateBackToMain() {
        Intent intent = new Intent(this, activity_watch_video_page.class);
        setResult(Activity.RESULT_OK, intent);
        finish(); // Optional: Close this activity if not needed anymore
    }
}
