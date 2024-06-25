package com.example.tech_titans_app.ui.adapters;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.entities.Comment;

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

        Glide.with(holder.itemView.getContext())
                .load(comment.getImage())
                .into(holder.publisherImage);

        // Set click listeners for like and unlike buttons
        holder.btnLike.setOnClickListener(v -> {
            comment.likeButtonClick(holder.btnLike, holder.btnUnlike);
            // Update likes count after click
            holder.likes.setText(String.valueOf(comment.getNumberOfLikes()));
        });

        holder.btnUnlike.setOnClickListener(v -> {
            comment.unlikeButtonClick(holder.btnLike, holder.btnUnlike);
            // Update likes count after click
            holder.likes.setText(String.valueOf(comment.getNumberOfLikes()));
        });

        comment.updateLikesButtonsUI(holder.btnLike, holder.btnUnlike);

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView comment, publisher, likes, date;
        public CircleImageView publisherImage;
        public TextView btnLike, btnUnlike, commentSubmit, commentCancel;
        public EditText commentEditText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.Comment_content);
            publisher = itemView.findViewById(R.id.comment_publisher);
            date = itemView.findViewById(R.id.comment_date);
            publisherImage = itemView.findViewById(R.id.comment_publisher_image);
            likes = itemView.findViewById(R.id.Comment_likes);
            btnLike = itemView.findViewById(R.id.comment_btn_like);
            btnUnlike = itemView.findViewById(R.id.comment_btn_unlike);
        }
    }
}
