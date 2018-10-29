package com.amostra.comanda;

import android.app.Application;
import android.content.Context;

import com.amostra.comanda.util.TypefaceUtil;
import com.amostra.net.AppNet;
import com.amostra.net.api.API;

public class App extends Application {
    private static Context context;
    private static App instance;
    private API api;
    private static boolean connected = true;

    public static App getInstance() {
        if (instance == null)  initial();
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    private static void initial() {
        instance = new App();
    }

    public static void setStatusConnection(boolean _connected){
        connected = _connected;
    }

    public static boolean isConnected() {
        return connected;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        instance = this;
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Oswald-Light.ttf");
    }

    public API getApi() {
        return api;
    }

    public void setApi(API api) {
        this.api = api;
    }
}
