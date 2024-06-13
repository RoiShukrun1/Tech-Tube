package com.example.tech_titans_app.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.entities.Comment;
import com.example.tech_titans_app.ui.entities.Video;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class commentsAdapter extends RecyclerView.Adapter<commentsAdapter.ViewHolder> {

    private List<Comment> commentList;

    @SuppressLint("NotifyDataSetChanged")
    public void setComments(List<Comment> comments) {
        this.commentList = comments;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.comment_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.comment.setText(comment.getComment());
        holder.date.setText(comment.getDate());
        holder.likes.setText(String.valueOf(comment.getNumberOfLikes()));
        holder.publisher.setText(comment.getPublisherUsername());
        holder.publisherImage.setImageResource(comment.getImagePath());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView comment, publisher, likes, date;
        public CircleImageView publisherImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.Comment_content);
            publisher = itemView.findViewById(R.id.comment_publisher);
            date = itemView.findViewById(R.id.comment_date);
            publisherImage = itemView.findViewById(R.id.comment_publisher_image);
            likes = itemView.findViewById(R.id.Comment_likes);
        }
    }
}
