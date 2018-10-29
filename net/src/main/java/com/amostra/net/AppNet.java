package com.amostra.net;

import android.app.Application;

public class AppNet extends Application {
    private static AppNet ourInstance;

    public static AppNet getInstance() {
        if (ourInstance == null) new AppNet();
        return ourInstance;
    }
}
