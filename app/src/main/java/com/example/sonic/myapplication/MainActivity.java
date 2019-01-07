package com.example.sonic.myapplication;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper db;
EditText text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("tes", "token: " + refreshedToken);
        text1=(EditText)findViewById(R.id.EditText1);
        text1.setText(refreshedToken);
        db = new DataBaseHelper(this);
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(broadcastReceiver, new IntentFilter("smsaja"));


    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver (){

        @Override
        public void onReceive(Context context, Intent intent) {

            Toast.makeText(context, "sukses", Toast.LENGTH_SHORT).show();
            recreate();
            //findViewById(R.id.fragment1).invalidate();
        }
    };

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

}

