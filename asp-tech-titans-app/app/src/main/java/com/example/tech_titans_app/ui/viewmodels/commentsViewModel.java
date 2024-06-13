package com.example.tech_titans_app.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tech_titans_app.ui.entities.Comment;
import com.example.tech_titans_app.ui.entities.Video;

import java.util.List;

public class commentsViewModel extends ViewModel {
    private final MutableLiveData<List<Comment>> comments;

    private final commentsRepository repository;


    public commentsRepository getRepository() {
        return repository;
    }

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

    public void addCommentToVideo(Video video, String commentText) {
        repository.addCommentToVideo(video, commentText);
    }
}
