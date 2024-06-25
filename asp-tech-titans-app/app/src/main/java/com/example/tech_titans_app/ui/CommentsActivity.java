package com.example.tech_titans_app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.adapters.commentsAdapter;
import com.example.tech_titans_app.ui.entities.Comment;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.entities.currentVideo;
import com.example.tech_titans_app.ui.utilities.LoggedIn;
import com.example.tech_titans_app.ui.viewmodels.commentsViewModel;

public class CommentsActivity extends AppCompatActivity {

    private commentsViewModel commentsViewModel;
    private commentsAdapter commentsAdapter;
    private Video thisCurrentVideo;
    private LoggedIn loggedIn = LoggedIn.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_comments);

        thisCurrentVideo = currentVideo.getInstance().getCurrentVideo();

        initiateCommentsSection();

        initListeners();

    }

    private void initListeners() {

        ImageButton button = findViewById(R.id.comments_close_button);
        button.setOnClickListener(v -> navigateBackToMain());

        TextView commentSubmit = findViewById(R.id.comment_submit);
        commentSubmit.setOnClickListener(v -> handleSubmitClick());

        TextView commentCancel = findViewById(R.id.comment_cancel);
        commentCancel.setOnClickListener(v -> handleCancelClick());

    }

    private void initiateCommentsSection() {
        RecyclerView listComments = findViewById(R.id.comments_scrolling);
        listComments.setLayoutManager(new LinearLayoutManager(this));

        commentsAdapter = new commentsAdapter();
        listComments.setAdapter(commentsAdapter);

        commentsViewModel = new ViewModelProvider(this).get(commentsViewModel.class);

        commentsViewModel.getRepository().loadComments(this.thisCurrentVideo);

        commentsViewModel.getAllComments()
                .observe(this, comments -> commentsAdapter.setComments(comments));

        commentsViewModel.getAllComments().observe(this, comments -> {
        });
    }

    private void navigateBackToMain() {
        Intent intent = new Intent(this, activity_watch_video_page.class);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void handleSubmitClick() {

        EditText input = findViewById(R.id.input_edit_Text_Comment);
        String commentText = input.getText().toString().trim();

        if (!commentText.isEmpty() && thisCurrentVideo != null) {
            if (loggedIn.getLoggedInUser() != null) {
                this.commentsViewModel.addCommentToVideo(thisCurrentVideo, commentText);
            } else {
                Toast.makeText(this,
                        "You have to be logged in to add a comment",
                        Toast.LENGTH_SHORT).show();
            }
            input.setText(""); // Clear the input field
        }
    }

    public void handleCancelClick() {
        EditText input = findViewById(R.id.input_edit_Text_Comment);
        input.setText(""); // Clear the input field
    }

}
