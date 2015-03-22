package com.beginningandroid.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

import com.beginningandroid.MyApplication;

/**
 * Created by C on 2015/3/22.
 */
public class AlarmService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.context, "AlarmService", Toast.LENGTH_SHORT).show();
                stopSelf();
            }
        }).start();

        AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent receiveIntent = new Intent(AlarmService.this, AlarmReceiver.class);
        PendingIntent operation = PendingIntent.getBroadcast(AlarmService.this, 0, receiveIntent, 0);
        alarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 60*60*1000, operation);
        return super.onStartCommand(intent, flags, startId);
    }
}
