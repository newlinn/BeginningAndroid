package com.beginningandroid.Services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.beginningandroid.MyApplication;
import com.beginningandroid.R;

public class MyService extends Service {
    public MyService() {
    }

    class BindInService extends Binder {

        public void doIt(){
            Toast.makeText(MyApplication.context,"Do it in BindInService", Toast.LENGTH_SHORT).show();
        }
    }

    private BindInService bindInService = new BindInService();

    @Override
    public IBinder onBind(Intent intent) {
        return bindInService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(MyApplication.context, "Service on Create", Toast.LENGTH_SHORT).show();

        Notification notification = new NotificationCompat.Builder(MyApplication.context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Notification comes")
                .setWhen(System.currentTimeMillis())
                .build();

        startForeground(1, notification);

    }

    @Override
    public void onDestroy() {
        Toast.makeText(MyApplication.context, "Service on Destroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(MyApplication.context, "Service on StartCommand", Toast.LENGTH_SHORT).show();

        return super.onStartCommand(intent, flags, startId);
    }

}
