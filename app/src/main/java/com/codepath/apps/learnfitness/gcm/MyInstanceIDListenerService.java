package com.codepath.apps.learnfitness.gcm;

import com.google.android.gms.iid.InstanceIDListenerService;

import android.content.Intent;

/**
 * Created by spandhare on 3/21/16.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify of changes
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
