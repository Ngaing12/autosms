package com.example.sonic.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SmsSentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent i) {
        Log.d("sms","status sms "+getResultCode());
        switch (getResultCode()) {

            case Activity.RESULT_OK:
                Toast.makeText(context, "SMS Sent KE "+i.getStringExtra("hp"), Toast.LENGTH_SHORT).show();
                Log.d("SMS","telah terkirim ke "+i.getStringExtra("hp"));
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                Toast.makeText(context, "SMS generic failure", Toast.LENGTH_SHORT)
                        .show();
                Log.d("SMS","telah gagal");
                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE:
                Toast.makeText(context, "SMS no service", Toast.LENGTH_SHORT)
                        .show();

                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
                Toast.makeText(context, "SMS null PDU", Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                Toast.makeText(context, "SMS radio off", Toast.LENGTH_SHORT).show();
                break;
        }
    };
}

