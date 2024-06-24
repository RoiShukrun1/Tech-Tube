package com.example.tech_titans_app.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.activity_watch_video_page;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.utilities.LoggedIn;
import com.example.tech_titans_app.ui.viewmodels.VideosRepository;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;
import com.example.tech_titans_app.ui.entities.currentVideo;

public class VideosListAdapter extends RecyclerView.Adapter<VideosListAdapter.ViewHolder> {

    private List<Video> videoList;
    private final currentVideo currentVideo = com.example.tech_titans_app.ui.entities.currentVideo.getInstance();

    @SuppressLint("NotifyDataSetChanged")
    public void setVideos(List<Video> videos) {
        this.videoList = videos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = videoList.get(position);
        holder.videoTitle.setText(video.getTitle());
        holder.publisher.setText(video.getPublisher());
        holder.views.setText(video.getViews());
        holder.date.setText(video.getDate());
        // Load the thumbnail image
        Uri thumbnailUri = video.getThumbnail();
        Glide.with(holder.itemView.getContext())
                .load(thumbnailUri)
                .into(holder.videoImage);

        // Load the publisher image
        Uri publisherImageUri = video.getPublisherImage();
        Glide.with(holder.itemView.getContext())
                .load(publisherImageUri)
                .into(holder.publisherImage);

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            currentVideo.setCurrentVideo(video);
            Intent intent = new Intent(context, activity_watch_video_page.class);
            context.startActivity(intent);
        });

        if (LoggedIn.getInstance().isLoggedIn()) {
            String loggedInUsername = LoggedIn.getInstance().getLoggedInUser().getUsername();
                String videoPublisher = video.getPublisher();
                if (loggedInUsername.equals(videoPublisher)) {
                    holder.removeIcon.setVisibility(View.VISIBLE);
                } else {
                    holder.removeIcon.setVisibility(View.GONE);
                }
        }
        holder.removeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideosRepository.getInstance().deleteVideo(video);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView videoTitle, publisher, views, date;
        public ImageView videoImage;
        public CircleImageView publisherImage;
        public ImageView removeIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.video_title);
            publisher = itemView.findViewById(R.id.publisher);
            views = itemView.findViewById(R.id.views);
            date = itemView.findViewById(R.id.date);
            videoImage = itemView.findViewById(R.id.video_image);
            publisherImage = itemView.findViewById(R.id.publisher_image);
            removeIcon = itemView.findViewById(R.id.remove_icon);
        }
    }
}
