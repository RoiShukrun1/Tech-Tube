package com.example.tech_titans_app.ui.mainActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.tech_titans_app.R;

public class SearchBarUtils {

    private final ImageView searchIcon;
    private final ImageView backButton;
    private final RelativeLayout searchLayout;
    private final EditText searchInput;
    private final ImageView darkMode;
    private final ImageView logo;



    public SearchBarUtils(View rootView) {
        searchIcon = rootView.findViewById(R.id.search);
        backButton = rootView.findViewById(R.id.back_button);
        searchLayout = rootView.findViewById(R.id.search_layout);
        searchInput = rootView.findViewById(R.id.search_input);
        darkMode = rootView.findViewById(R.id.dark_mode);
        logo = rootView.findViewById(R.id.logo);

        setupSearchBar();
    }

    private void setupSearchBar() {
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLayout.setVisibility(View.VISIBLE);
                searchIcon.setVisibility(View.GONE);
                darkMode.setVisibility(View.GONE);
                logo.setVisibility(View.GONE);
                searchInput.requestFocus();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLayout.setVisibility(View.GONE);
                searchIcon.setVisibility(View.VISIBLE);
                darkMode.setVisibility(View.VISIBLE);
                logo.setVisibility(View.VISIBLE);
            }
        });
    }
}
