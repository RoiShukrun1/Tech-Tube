package com.example.tech_titans_app.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tech_titans_app.ui.entities.Comment;

import java.util.List;

public class commentsViewModel extends ViewModel {
    private final MutableLiveData<List<Comment>> comments;
    private final commentsRepository repository;

    public commentsViewModel() {
        this.repository = new commentsRepository();
        this.comments = repository.getAllcomments();
    }

    public LiveData<List<Comment>> getAllComments() {
        return this.comments;
    }

    public void setComments(List<Comment> videoList) {
        comments.setValue(videoList);
    }
}
