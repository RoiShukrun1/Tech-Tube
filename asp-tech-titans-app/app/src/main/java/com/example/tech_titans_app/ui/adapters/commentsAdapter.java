package com.example.tech_titans_app.ui.adapters;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.entities.Comment;
import com.example.tech_titans_app.ui.utilities.LoggedIn;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class commentsAdapter extends RecyclerView.Adapter<commentsAdapter.ViewHolder> {

    private List<Comment> commentList;
    private final LoggedIn loggedIn = LoggedIn.getInstance();
    private final Context context;

    public commentsAdapter(Context context) {
        this.context = context;
    }

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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.comment.setText(comment.getComment());
        holder.date.setText(comment.getDate());
        holder.publisher.setText(comment.getPublisherUsername());

        Glide.with(holder.itemView.getContext())
                .load(comment.getImage())
                .into(holder.publisherImage);

        // Set click listeners for like and unlike buttons
        holder.btnLike.setOnClickListener(v -> {
            comment.likeButtonClick(holder.btnLike, holder.btnUnlike, context);
        });

        holder.btnUnlike.setOnClickListener(v -> {
            comment.unlikeButtonClick(holder.btnLike, holder.btnUnlike, context);
        });

        // Set click listener for delete button
        holder.deleteComment.setOnClickListener(v -> {
            comment.deleteComment(context);
            notifyDataSetChanged();
        });

        // Set click listener for edit button
        holder.editComment.setOnClickListener(v -> {
            comment.pencilCommentButtonClick(context, holder.comment);
            notifyDataSetChanged();
        });



        comment.updateLikesButtonsUI(holder.btnLike, holder.btnUnlike);

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView comment, publisher, date, editComment;
        public CircleImageView publisherImage;
        public TextView btnLike, btnUnlike;
        public ImageButton deleteComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.Comment_content);
            publisher = itemView.findViewById(R.id.comment_publisher);
            date = itemView.findViewById(R.id.comment_date);
            publisherImage = itemView.findViewById(R.id.comment_publisher_image);
            btnLike = itemView.findViewById(R.id.comment_btn_like);
            btnUnlike = itemView.findViewById(R.id.comment_btn_unlike);
            deleteComment = itemView.findViewById(R.id.comments_close_button);
            editComment = itemView.findViewById(R.id.edit_comment);
        }
    }
}
