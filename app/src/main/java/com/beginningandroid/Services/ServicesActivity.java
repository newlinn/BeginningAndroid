package com.beginningandroid.Services;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.beginningandroid.Activities.BaseActivity;

import com.beginningandroid.R;

public class ServicesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

    }

    private void handleMessage(View v) {
        Toast.makeText(ServicesActivity.this, "handle message", Toast.LENGTH_SHORT).show();
    }

    private void doInThread() {
        Thread t = new Thread(new MyRunnable());
        t.start();
    }


    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
        }
    }

    class MyRunnable implements Runnable {
        @Override
        public void run() {

        }
    }

}
