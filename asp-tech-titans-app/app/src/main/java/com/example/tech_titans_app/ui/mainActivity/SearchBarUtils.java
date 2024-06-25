package com.example.tech_titans_app.ui.mainActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.tech_titans_app.R;

public class SearchBarUtils {

    // UI elements for the search bar functionality
    private final ImageView searchIcon;
    private final ImageView backButton;
    private final RelativeLayout searchLayout;
    private final EditText searchInput;
    private final ImageView darkMode;
    private final ImageView logo;

    /**
     * Constructor to initialize UI elements.
     *
     * @param rootView The root view containing the search bar elements.
     */
    public SearchBarUtils(View rootView) {
        // Initialize UI elements
        searchIcon = rootView.findViewById(R.id.search);
        backButton = rootView.findViewById(R.id.back_button);
        searchLayout = rootView.findViewById(R.id.search_layout);
        searchInput = rootView.findViewById(R.id.search_input);
        darkMode = rootView.findViewById(R.id.dark_mode);
        logo = rootView.findViewById(R.id.logo);

        // Setup search bar functionality
        setupSearchBar();
    }

    /**
     * Sets up the search bar's visibility and interaction logic.
     */
    private void setupSearchBar() {
        // When the search icon is clicked, show the search layout and hide other elements
        searchIcon.setOnClickListener(v -> {
            searchLayout.setVisibility(View.VISIBLE);
            searchIcon.setVisibility(View.GONE);
            darkMode.setVisibility(View.GONE);
            logo.setVisibility(View.GONE);
            searchInput.requestFocus(); // Focus on the search input field
        });

        // When the back button is clicked, hide the search layout and show other elements
        backButton.setOnClickListener(v -> {
            searchLayout.setVisibility(View.GONE);
            searchIcon.setVisibility(View.VISIBLE);
            darkMode.setVisibility(View.VISIBLE);
            logo.setVisibility(View.VISIBLE);
        });
    }
}
