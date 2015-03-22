package com.beginningandroid.Services;


import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beginningandroid.Activities.BaseActivity;

import com.beginningandroid.MyApplication;
import com.beginningandroid.R;

public class ServicesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        btnSendMsg = (Button) findViewById(R.id.btnSendMsg);
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

        btnAsyncTask = (Button) findViewById(R.id.btnAsyncTask);
        btnAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAsyncTask(ServicesActivity.this).execute();
            }
        });
    }

    Button btnAsyncTask;

    private void handleMessage(View v) {
        Toast.makeText(ServicesActivity.this, "handle message", Toast.LENGTH_SHORT).show();
    }

    static final int SEND_MESSAGE = 1001;
    Button btnSendMsg;

    private Handler msgHandler = new Handler() {
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

    class MyAsyncTask extends AsyncTask<Void, Integer, Boolean> {

        Context context;

        ProgressDialog progressDialog;

        public MyAsyncTask(Context context) {
            this.context = context;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                int idx = 0;
                while (true) {
                    if (100 == idx) break;
                    publishProgress(idx);
                    idx++;
                    Thread.sleep(100);
                }
                return true;
            } catch (Exception ex) {
            }
            return false;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("");
            progressDialog.setMessage("请稍等...");
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setMessage(String.valueOf(values[0]) + "% done");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            if (aBoolean) {
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //// service
    public void StartService(View view) {
        Intent intent = new Intent(ServicesActivity.this, MyService.class);
        startService(intent);
    }

    public void StopService(View view) {
        Intent intent = new Intent(ServicesActivity.this, MyService.class);
        stopService(intent);
    }


    MyService.BindInService bindInService;

    class MyServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bindInService = (MyService.BindInService) service;
            bindInService.doIt();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    private MyServiceConn myServiceConn = new MyServiceConn();

    public void btnBindService(View view) {
        Intent service = new Intent(ServicesActivity.this, MyService.class);
        myServiceConn = new MyServiceConn();
        int flags = BIND_AUTO_CREATE;
        bindService(service, myServiceConn, flags);
    }

    public void btnUnBindService(View view) {
        unbindService(myServiceConn);
    }

    //// service end

    public void btnIntentService(View view) {
        Intent intent = new Intent(ServicesActivity.this, MyIntentService.class);
        startService(intent);
    }
}