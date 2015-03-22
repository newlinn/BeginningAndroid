package com.beginningandroid.Services;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.beginningandroid.MyApplication;

/**
 * Created by C on 2015/3/22.
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Toast.makeText(MyApplication.context, "onHandleIntent", Toast.LENGTH_SHORT).show();
    }
}
