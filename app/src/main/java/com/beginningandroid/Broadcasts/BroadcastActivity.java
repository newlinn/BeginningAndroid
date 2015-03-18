package com.beginningandroid.Broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beginningandroid.Activities.BaseActivity;

import com.beginningandroid.R;

public class BroadcastActivity extends BaseActivity implements NetworkChangeReceiver.OnChanged {

    LocalBroadcastManager localBroadcastMgr;
    BroadcastReceiver localReceiver;
    ForceOffReceiver forceOffReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        findViewById(R.id.btnSendMyBroadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("cn.com2.testinone.MyBroadcast");
                //sendBroadcast(intent);
                sendOrderedBroadcast(intent, null);
            }
        });

        localBroadcastMgr = LocalBroadcastManager.getInstance(BroadcastActivity.this);
        findViewById(R.id.btnForceOff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("cn.com2.testinone.FORCE_OFF");
                //sendBroadcast(intent);
                if (forceOffReceiver == null) {
                    forceOffReceiver = new ForceOffReceiver();
                    IntentFilter intentFilter1 = new IntentFilter();
                    intentFilter1.addAction("cn.com2.testinone.FORCE_OFF");
                    localBroadcastMgr.registerReceiver(forceOffReceiver, intentFilter1);
                }
                localBroadcastMgr.sendBroadcast(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkChangeReceiver != null)
            unregisterReceiver(networkChangeReceiver);
        if (localReceiver != null)
            localBroadcastMgr.unregisterReceiver(localReceiver);
        if (forceOffReceiver != null)
            localBroadcastMgr.unregisterReceiver(forceOffReceiver);
    }

    private NetworkChangeReceiver networkChangeReceiver;
    private IntentFilter intentFilter;

    public void broadCast(View v) {
        //全局广播
        if (networkChangeReceiver == null) {
            intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            networkChangeReceiver = new NetworkChangeReceiver(this);
            registerReceiver(networkChangeReceiver, intentFilter);
        }
        //本地广播
        if (localBroadcastMgr == null)
            localBroadcastMgr = LocalBroadcastManager.getInstance(this);
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("cn.com2.testinone.LocalBroadcast");
        localReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(BroadcastActivity.this, "local broadcast received!", Toast.LENGTH_SHORT).show();
            }
        };
        localBroadcastMgr.registerReceiver(localReceiver, localIntentFilter);
        //send
        localBroadcastMgr.sendBroadcast(new Intent("cn.com2.testinone.LocalBroadcast"));
    }

    @Override
    public void doOnChanged() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        Button btn = (Button) findViewById(R.id.btnTestBroadcast);
        if (networkInfo != null && networkInfo.isAvailable())
            btn.setText("networkInfo.isAvailable");
        else
            btn.setText("networkInfo.isUnavailable");

    }
}
