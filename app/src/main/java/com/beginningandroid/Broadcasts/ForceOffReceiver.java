package com.beginningandroid.Broadcasts;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

import com.beginningandroid.Activities.BaseActivity;


/**
 * Created by LingChen on 15/3/10.
 */
public class ForceOffReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("forceOff");
        alert.setMessage("force offline");
        alert.setCancelable(false);
        alert.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BaseActivity.FinishAllActivity();
                Intent intent1 = new Intent(context, LoginActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();
    }
}
