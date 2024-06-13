package com.example.tech_titans_app.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.entities.Comment;
import java.util.ArrayList;
import java.util.List;

public class commentsRepository {

    private final MutableLiveData<List<Comment>> comments;
    private List<Comment> allComments;

    public commentsRepository() {
        comments = new MutableLiveData<>();
        allComments = new ArrayList<>();
        loadComments();
    }

    private void loadComments() {
        List<Comment> commentsList = new ArrayList<>();
        commentsList.add((new Comment(1, 0,
                "Aviel Segev","this is first comment",
                "13-06-24", R.drawable.image2)));
        allComments = commentsList;
        comments.setValue(allComments);
    }

    public MutableLiveData<List<Comment>> getAllcomments() {
        return comments;
    }

}
