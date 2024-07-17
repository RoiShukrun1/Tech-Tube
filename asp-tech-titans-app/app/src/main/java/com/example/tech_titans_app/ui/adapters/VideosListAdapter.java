package com.example.tech_titans_app.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.AppContext;
import com.example.tech_titans_app.ui.WatchVideoPageActivity;
import com.example.tech_titans_app.ui.PublisherChannelActivity;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.utilities.LoggedIn;
import com.example.tech_titans_app.ui.viewmodels.VideosRepository;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import com.example.tech_titans_app.ui.entities.CurrentVideo;

public class VideosListAdapter extends RecyclerView.Adapter<VideosListAdapter.ViewHolder> {
    private List<Video> videoList;
    private final CurrentVideo currentVideo = CurrentVideo.getInstance();
    private final String serverBaseUrl = "http://10.0.2.2/";
    private final String serverBaseUrl2 = "http://10.0.2.2";

    /**
     * Constructor for VideosListAdapter.
     * Sets the logout listener to refresh the list when the user logs out.
     */
    public VideosListAdapter() {
        // Set the logout listener to refresh the list when user logs out
        LoggedIn.getInstance().setLogoutListener(new LoggedIn.LogoutListener() {
            @Override
            public void onLogout() {
                notifyDataSetChanged();
            }
        });
    }

    /**
     * Sets the video list and notifies the adapter of the data change.
     *
     * @param videos The list of videos to display.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setVideos(List<Video> videos) {
        this.videoList = videos;
        notifyDataSetChanged();
    }

    /**
     * Creates and returns a ViewHolder object for a video item view.
     *
     * @param parent The parent view group.
     * @param viewType The view type.
     * @return A new ViewHolder object.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_layout, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds the data to the ViewHolder for the specified position.
     *
     * @param holder The ViewHolder to bind data to.
     * @param position The position in the adapter.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Video video = videoList.get(position);

        // Set video details to the holder views
        holder.videoTitle.setText(video.getTitle());
        holder.publisher.setText(video.getPublisher());
        holder.views.setText(video.getViews() + " views");
        holder.date.setText(video.getDate());

        // Construct the URI for the video, thumbnail, and publisher image
        Uri videoUri = Uri.parse(serverBaseUrl + video.getVideoUploaded());
        Uri thumbnailUri = Uri.parse(serverBaseUrl + video.getThumbnail());
        Uri publisherImageUri = Uri.parse(serverBaseUrl2 + video.getPublisherImage());

        // Load the thumbnail image using Glide
        Glide.with(holder.itemView.getContext())
                .load(thumbnailUri)
                .into(holder.videoImage);

        // Load the publisher image using Glide
        Glide.with(holder.itemView.getContext())
                .load(publisherImageUri)
                .into(holder.publisherImage);

        // Set click listener for the video thumbnail to navigate to the watch video page
        holder.videoImage.setOnClickListener(v -> {
            Context context = v.getContext();
            currentVideo.setCurrentVideo(video);
            currentVideo.getCurrentVideo().incrementViews();
            Intent intent = new Intent(context, WatchVideoPageActivity.class);
            intent.setData(videoUri); // Pass the video URI to the WatchVideoPageActivity
            context.startActivity(intent);
        });

        // Set click listener for the publisher image to navigate to the publisher channel page
        holder.publisherImage.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, PublisherChannelActivity.class);
            intent.putExtra("publisher", video.getPublisher()); // Pass the publisher information to the PublisherChannelActivity
            context.startActivity(intent);
        });

        // Handle visibility of remove icon based on logged-in status and publisher
        if (LoggedIn.getInstance().isLoggedIn()) {
            String loggedInUsername = LoggedIn.getInstance().getLoggedInUser().getUsername();
            String videoPublisher = video.getPublisher();
            if (loggedInUsername.equals(videoPublisher)) {
                holder.removeIcon.setVisibility(View.VISIBLE);
            } else {
                holder.removeIcon.setVisibility(View.GONE);
            }
        } else {
            holder.removeIcon.setVisibility(View.GONE);
        }

        // Set click listener for remove icon to delete the video
        holder.removeIcon.setOnClickListener(v -> {
            Log.e("check pah", "here");
            VideosRepository.getInstance().deleteVideoById(String.valueOf(video.getId()), new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    // Refresh the video list
                    videoList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, videoList.size());
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    // Handle failure (optional)
                    Log.e("API_CALL", "Failed to delete video: " + t.getMessage());
                }
            });
        });
    }

    /**
     * Returns the number of items in the adapter.
     *
     * @return The item count.
     */
    @Override
    public int getItemCount() {
        return videoList != null ? videoList.size() : 0;
    }

    /**
     * ViewHolder class to represent a video item view.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView videoTitle, publisher, views, date;
        public ImageView videoImage;
        public CircleImageView publisherImage;
        public ImageView removeIcon;

        /**
         * Constructor for ViewHolder.
         *
         * @param itemView The item view.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize UI components
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
