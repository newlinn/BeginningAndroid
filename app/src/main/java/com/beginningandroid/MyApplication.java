package com.beginningandroid;

import android.app.Application;
import android.content.Context;

/**
 * Created by C on 2015/3/22.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context context = null;
}
