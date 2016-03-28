package com.codepath.apps.learnfitness.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

/**
 * Created by spandhare on 3/12/16.
 */
@SuppressLint("ParcelCreator")
public class LessonListActivityReceiver extends ResultReceiver {
    private static final String TAG = "LessonListReceiver";
    private Receiver receiver;

    // Constructor takes a handler
    public LessonListActivityReceiver(Handler handler) {
        super(handler);
    }

    // Setter for assigning the receiver
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    // Defines our event interface for communication
    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
        void onProgressUpdate(int resultCode, Bundle resultData);
    }

    // Delegate method which passes the result to the receiver if the receiver has been assigned
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver != null) {
            Log.i(TAG, "Update progress to " + resultCode);
            if (resultCode == Activity.RESULT_OK) {
                receiver.onReceiveResult(resultCode, resultData);
            } else {
                receiver.onProgressUpdate(resultCode, resultData);
            }
        }
    }


}
