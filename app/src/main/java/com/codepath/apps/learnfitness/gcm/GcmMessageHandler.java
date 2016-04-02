package com.codepath.apps.learnfitness.gcm;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.activities.LessonListActivity;
import com.codepath.apps.learnfitness.models.MyFormMessage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


/**
 * Created by spandhare on 3/21/16.
 */
public class GcmMessageHandler extends GcmListenerService {
    private static final String TAG = "GcmMessageHandler";
    public static final int MESSAGE_NOTIFICATION_ID = 435345;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.i(TAG, "Message received: " + data.size());
        String message = data.getString("body");
        String title = data.getString("title");

        String formMessageString = data.getString("formMessage");
        Log.i(TAG, "formMessage str: " + formMessageString);

        Gson gson = new GsonBuilder().serializeNulls().create();
        MyFormMessage myFormMessage =  gson.fromJson(formMessageString, MyFormMessage.class);
        Log.i(TAG, "formMessage: " + myFormMessage.getMessage());
        createNotification(title, message, myFormMessage);
    }

    // Creates notification based on title and body received
    private void createNotification(String title, String body, MyFormMessage myFormMessage) {
        Context context = getBaseContext();

        Intent intent = new Intent(this, LessonListActivity.class);
        intent.putExtra("formMessage", myFormMessage);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Log.i(TAG, myFormMessage.getId());
         // Next, let's turn this into a PendingIntent using

        int requestID = (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId
        int flags = PendingIntent.FLAG_CANCEL_CURRENT; // cancel old intent and create new one
        PendingIntent pIntent = PendingIntent.getActivity(this, requestID, intent, flags);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pIntent);

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }
}
