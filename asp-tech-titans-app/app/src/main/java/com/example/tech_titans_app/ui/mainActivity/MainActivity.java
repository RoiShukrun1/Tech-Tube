package com.example.tech_titans_app.ui.mainActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tech_titans_app.R;
import com.example.tech_titans_app.ui.entities.Video;
import com.example.tech_titans_app.ui.adapters.VideosListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView lstVideos;
    private VideosListAdapter adapter;
    private List<Video> videoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FilterUtils().setupFilterClickListeners(findViewById(android.R.id.content));
        new SearchBarUtils(findViewById(android.R.id.content));

        lstVideos = findViewById(R.id.lstVideos);
        lstVideos.setLayoutManager(new LinearLayoutManager(this));

        videoList = new ArrayList<>();
        // Add some sample videos
        videoList.add(new Video(R.drawable.image1, "Video 1 Title", "Publisher 1",  R.drawable.image1, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image2, "Video 2 Title", "Publisher 2",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image3, "Video 3 Title", "Publisher 3",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image4, "Video 4 Title", "Publisher 4",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image5, "Video 5 Title", "Publisher 4",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image6, "Video 6 Title", "Publisher 4",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image7, "Video 7 Title", "Publisher 5",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image8, "Video 8 Title", "Publisher 6",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image9, "Video 9 Title", "Publisher 7",  R.drawable.image2, "1M views", "10/10/2020"));
        videoList.add(new Video(R.drawable.image10, "Video 10 Title", "Publisher 7",  R.drawable.image2, "1M views", "10/10/2020"));

        adapter = new VideosListAdapter(videoList);
        lstVideos.setAdapter(adapter);
    }
}
