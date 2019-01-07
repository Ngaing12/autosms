package com.example.sonic.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class SmsDeliveredReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent arg1) {
        switch (getResultCode()) {
            case Activity.RESULT_OK:
                Toast.makeText(context, "SMS delivered", Toast.LENGTH_SHORT).show();
                Log.d("sms","SMS terkirm");
                Intent intent = new Intent("smsaja");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                break;
            case Activity.RESULT_CANCELED:
                Toast.makeText(context, "SMS not delivered", Toast.LENGTH_SHORT).show();
                Log.d("sms"," SMS tak terkirim");
                break;
        }
    };
}
