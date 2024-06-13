package com.example.tech_titans_app.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.entities.Comment;
import com.example.tech_titans_app.ui.entities.Video;

import java.util.ArrayList;
import java.util.List;

public class commentsRepository {

    private final MutableLiveData<List<Comment>> comments;
    private List<Comment> allComments;

    public commentsRepository() {
        comments = new MutableLiveData<>();
        allComments = new ArrayList<>();
        comments.setValue(allComments);
    }

    public void loadComments(Video currentVideo) {
        comments.setValue(currentVideo.getComments());
        allComments = currentVideo.getComments();
    }

    public MutableLiveData<List<Comment>> getAllComments() {
        return comments;
    }

    public MutableLiveData<List<Comment>> getAllcomments() {
        return comments;
    }

    public void addCommentToVideo(Video video, String commentText) {
        video.addComment(commentText);
        // Notify observers of the change in comments
        comments.setValue(video.getComments());
        allComments = video.getComments();
    }

}
