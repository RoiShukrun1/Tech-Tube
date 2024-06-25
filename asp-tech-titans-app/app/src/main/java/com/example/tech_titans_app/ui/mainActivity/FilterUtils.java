package com.example.tech_titans_app.ui.mainActivity;

import android.view.View;
import android.widget.TextView;

import com.example.tech_titans_app.R;

public class FilterUtils {
    // Store the last selected filter TextView
    private TextView lastSelectedFilter;

    /**
     * Sets up click listeners for filter buttons.
     * When a filter button is clicked, it updates the selection state.
     *
     * @param rootView The root view containing all the filter buttons.
     */
    public void setupFilterClickListeners(View rootView) {
        View.OnClickListener filterClickListener = view -> {
            if (lastSelectedFilter != null) {
                lastSelectedFilter.setSelected(false);
            }
            view.setSelected(true);
            lastSelectedFilter = (TextView) view;
        };

        rootView.findViewById(R.id.all_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.computer_programming_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.music_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.gaming_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.restaurants_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.mixes_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.barbells_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.sitcoms_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.satire_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.brawl_stars_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.functions_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.playlists_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.uefa_champions_league_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.cdj_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.news_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.live_filter).setOnClickListener(filterClickListener);
        rootView.findViewById(R.id.sports_filter).setOnClickListener(filterClickListener);
    }
}
