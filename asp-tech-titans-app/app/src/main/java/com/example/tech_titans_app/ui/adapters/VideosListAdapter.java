package com.example.tech_titans_app.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.entities.Video;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

public class VideosListAdapter extends RecyclerView.Adapter<VideosListAdapter.ViewHolder> {

    private List<Video> videoList;

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
        holder.videoImage.setImageResource(video.getImage());
        holder.publisherImage.setImageResource(video.getPublisherImage());
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView videoTitle, publisher, views, date;
        public ImageView videoImage;
        public CircleImageView publisherImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.video_title);
            publisher = itemView.findViewById(R.id.publisher);
            views = itemView.findViewById(R.id.views);
            date = itemView.findViewById(R.id.date);
            videoImage = itemView.findViewById(R.id.video_image);
            publisherImage = itemView.findViewById(R.id.publisher_image);
        }
    }
}
