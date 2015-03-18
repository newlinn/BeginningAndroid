package com.beginningandroid.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.beginningandroid.R;

public class HandlerActivity extends ActionBarActivity {

    private Handler uiHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //截获，优先级高
            if (1 == msg.what) {
                tvHandler.setText("handler " + msg.obj);
                return true;
            } else
                return false;
        }
    }){
        @Override
        public void handleMessage(Message msg) {
            tvHandler.setText((String)msg.obj);
        }
    };

    private TextView tvHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        tvHandler = (TextView)findViewById(R.id.tvHandler);

        findViewById(R.id.btnHandler1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread("thread handler"){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        uiHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvHandler.setText("runnable " + Thread.currentThread().getName());
                            }
                        }, 1000);
                    }
                };

                thread.start();

            }
        });

        findViewById(R.id.btnSendMsg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new Thread("sendMsgThread"){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message msg = new Message();
                        msg.obj = "sendMessage " + Thread.currentThread().getName();
                        uiHandler.sendMessage(msg);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message msg1 = uiHandler.obtainMessage();
                        msg1.what = 1;
                        msg1.obj = "obtainMessage " + Thread.currentThread().getName();
                        msg1.sendToTarget();
                    }
                }).start();
            }
        });

        findViewById(R.id.btnHandlerThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandlerThread handlerThread = new HandlerThread("HandlerThread");
                handlerThread.start();

                Handler handler = new Handler(handlerThread.getLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        //tvHandler.setText("handlerThread" + Thread.currentThread().getName());
                        Toast.makeText(HandlerActivity.this, "handler thread", Toast.LENGTH_SHORT).show();
                    }
                };
                handler.sendEmptyMessage(1);
            }
        });

        findViewById(R.id.btnThreads).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                 handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        Message msg12 = new Message();
                        msg12.arg1 = 200;
                        threadHandler.sendMessageDelayed(msg12, 1000);
                    }
                };

                HandlerThread thread = new HandlerThread("HandlerThread");
                thread.start();

                threadHandler = new Handler(thread.getLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        Message msg1 = new Message();
                        msg.arg1 = 100;
                        handler.sendMessageDelayed(msg1, 1000);
                    }
                };

                threadHandler.sendEmptyMessage(1);
            }
        });
    }

     Handler handler;
     Handler threadHandler;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_handler, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
