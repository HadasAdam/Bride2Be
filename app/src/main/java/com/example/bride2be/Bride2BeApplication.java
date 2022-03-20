package com.example.bride2be;

import android.app.Application;
import android.content.Context;

public class Bride2BeApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
