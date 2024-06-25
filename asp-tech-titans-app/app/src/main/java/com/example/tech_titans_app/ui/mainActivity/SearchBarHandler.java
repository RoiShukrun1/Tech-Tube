package com.example.tech_titans_app.ui.mainActivity;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.tech_titans_app.ui.viewmodels.MainVideoViewModel;

public class SearchBarHandler implements TextWatcher {
    private MainVideoViewModel videoViewModel;

    public SearchBarHandler(MainVideoViewModel videoViewModel) {
        this.videoViewModel = videoViewModel;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Filter videos based on search input
        videoViewModel.filterVideos(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {}
}
