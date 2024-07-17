package com.example.tech_titans_app.ui;

import android.content.Context;

public class AppContext {
    private static Context context;

    private AppContext() {
        // Private constructor to prevent instantiation
    }

    public static void init(Context ctx) {
        if (context == null) {
            context = ctx.getApplicationContext();
        }
    }

    public static Context getContext() {
        return context;
    }
}

