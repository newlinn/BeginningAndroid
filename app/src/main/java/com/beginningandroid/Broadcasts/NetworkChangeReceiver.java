package com.beginningandroid.Broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public interface OnChanged{
        void doOnChanged();
    };

    public NetworkChangeReceiver(){}

    OnChanged onChanged;
    public NetworkChangeReceiver(OnChanged onChanged) {
        this.onChanged = onChanged;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (this.onChanged != null)
            this.onChanged.doOnChanged();
    }


}
