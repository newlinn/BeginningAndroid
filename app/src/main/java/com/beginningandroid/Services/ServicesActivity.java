package com.beginningandroid.Services;


import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beginningandroid.Activities.BaseActivity;

import com.beginningandroid.R;

public class ServicesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        btnSendMsg = (Button)findViewById(R.id.btnSendMsg);
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = SEND_MESSAGE;
                        msg.obj = "SEND_MESSAGE";
                        msgHandler.sendMessage(msg);
                    }
                }).start();

                Message msg = new Message();
                msg.what = SEND_MESSAGE;
                msgHandler.sendMessage(msg);
            }
        });

        btnAsyncTask = (Button)findViewById(R.id.btnAsyncTask);
        btnAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    Button btnAsyncTask;

    private void handleMessage(View v) {
        Toast.makeText(ServicesActivity.this, "handle message", Toast.LENGTH_SHORT).show();
    }

    static final int SEND_MESSAGE = 1001;
    Button btnSendMsg;

    private Handler msgHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEND_MESSAGE:
                    btnSendMsg.setText(String.valueOf(msg.obj));
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };



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

    class MyAsyncTask extends AsyncTask<Void, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPreExecute() {

            //super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }
}
