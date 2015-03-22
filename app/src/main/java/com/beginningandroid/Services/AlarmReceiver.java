package com.beginningandroid.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by C on 2015/3/22.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent srvIntent = new Intent(context, AlarmService.class);
        context.startService(srvIntent);
    }
}
