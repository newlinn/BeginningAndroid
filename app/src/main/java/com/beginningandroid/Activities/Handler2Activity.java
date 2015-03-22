package com.beginningandroid.Activities;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beginningandroid.R;

import java.util.ArrayList;

public class Handler2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler2);

        tvMsg = (TextView) findViewById(R.id.tvMsg);
        ivFruit = (ImageView) findViewById(R.id.ivFruit);

        findViewById(R.id.btnHandlerInThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HandlerInThread thread = new HandlerInThread();
                thread.start();
                try {
                    Thread.sleep(500);
                } catch (Exception ex) {
                }
                thread.handler.sendEmptyMessage(flagDoHandlerThread);

            }
        });
    }

    static int flagRemove = 1001;
    static int flagPostRunnable = 1002;
    static int flagDoHandlerThread = 1003;
    static int flagMyHandlerThread = 1004;

    Handler uiHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (flagRemove == msg.what)
            {
                uiHandler.removeCallbacks(fruitRunnable);
                tvMsg.setText("removeCallbacks fruitRunnable");
                return true;
            }

            return false;
        }
    }) {
        @Override
        public void handleMessage(Message msg) {
            if (flagPostRunnable == msg.what)
                tvMsg.setText("the message from " + String.valueOf(msg.obj) + " sendToTarget");
            else if (flagDoHandlerThread == msg.what)
                tvMsg.setText("this message is from doHandlerThread");
            else if (flagMyHandlerThread == msg.what)
                tvMsg.setText("this message is from myHandlerThread");
        }

    };
    TextView tvMsg;
    ImageView ivFruit;

    ////  postRunnable
    public void removeRunnable(View view) {
        Message msg = uiHandler.obtainMessage(flagRemove);
        msg.sendToTarget();
    }


    int idx = 0;

    class FruitRunnable implements Runnable {
        @Override
        public void run() {
            idx++;
            if (fruits.size() == idx) idx = 0;
            ivFruit.setImageResource((int) (fruits.get(idx)));

            uiHandler.postDelayed(fruitRunnable, 1000);
        }
    }

    ArrayList fruits = new ArrayList();
    FruitRunnable fruitRunnable = new FruitRunnable();

    public void postRunnable(View view) {
        fruits.add(R.drawable.apple_pic);
        fruits.add(R.drawable.banana_pic);
        fruits.add(R.drawable.orange_pic);
        fruits.add(R.drawable.watermelon_pic);
        fruits.add(R.drawable.pear_pic);
        fruits.add(R.drawable.grape_pic);
        fruits.add(R.drawable.pineapple_pic);
        fruits.add(R.drawable.strawberry_pic);
        fruits.add(R.drawable.cherry_pic);
        fruits.add(R.drawable.mango_pic);

        new Thread() {
            @Override
            public void run() {
                uiHandler.postDelayed(fruitRunnable, 1000);
                Message msg = uiHandler.obtainMessage(flagPostRunnable);
                msg.obj = String.valueOf("postRunnable");
                msg.sendToTarget();
            }
        }.start();

    }

    ////  postRunnable  end


    class HandlerInThread extends Thread{
        public Handler handler;

        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    //线程中的handler也不能不处理UI，转发给UI线程中的handler来处理
                    uiHandler.sendEmptyMessage(flagDoHandlerThread);
                }
            };
            Looper.loop();
        }
    }


    public void MyHandlerThread(View view){
        MyHandlerThread myHandlerThread = new MyHandlerThread("MyHandlerThread");
        myHandlerThread.start();
        Handler handler = new Handler(myHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                //使用了myHandlerThread.getLooper()，不在ui线程上
                uiHandler.sendEmptyMessage(msg.what);
            }
        };
        handler.sendEmptyMessage(flagMyHandlerThread);
    }

    class MyHandlerThread extends HandlerThread{

        MyHandlerThread(String name) {
            super(name);
        }
    }
}
