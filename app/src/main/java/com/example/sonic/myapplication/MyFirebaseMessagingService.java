package com.example.sonic.myapplication;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.M;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            Intent intent = new Intent("firebase");
            intent.putExtra("isi",remoteMessage.getData().get("hp").toString());
            sendSMS(remoteMessage.getData().get("hp").toString(),remoteMessage.getData().get("nama").toString(),remoteMessage.getData().get("pesan").toString());
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    private void sendSMS(String phoneNumber,String nama, String message) {
        DataBaseHelper db;
        Context mContext = this;
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();
        PendingIntent sentPI = PendingIntent.getBroadcast(mContext, 0,
                new Intent(mContext, SmsSentReceiver.class).putExtra("hp",phoneNumber).putExtra("pesan",message).putExtra("nama",nama), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(mContext, 0,
                new Intent(mContext, SmsDeliveredReceiver.class).putExtra("hp",phoneNumber).putExtra("pesan",message).putExtra("nama",nama), 0);
        try {
            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> mSMSMessage = sms.divideMessage(message);
            for (int i = 0; i < mSMSMessage.size(); i++) {
                sentPendingIntents.add(i, sentPI);
                deliveredPendingIntents.add(i, deliveredPI);
            }
            Log.d("sms","mencoba mengirim sms");
            sms.sendMultipartTextMessage(phoneNumber, null, mSMSMessage,
                    sentPendingIntents, deliveredPendingIntents);
            db=new DataBaseHelper(this);
            Boolean res= db.insertData(phoneNumber,nama,message);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "SMS sending failed...",Toast.LENGTH_SHORT).show();
            Log.d("sms"," gagal");
        }

    }
}
