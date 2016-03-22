package com.codepath.apps.learnfitness.gcm;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.ClientRegistration;
import com.codepath.apps.learnfitness.models.ClientToken;
import com.codepath.apps.learnfitness.rest.MediaStoreService;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by spandhare on 3/21/16.
 */
public class RegistrationIntentService extends IntentService {

    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String GCM_TOKEN = "gcmToken";

    // abbreviated tag name
    private static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Make a call to Instance API
        InstanceID instanceID = InstanceID.getInstance(this);
        String senderId = getResources().getString(R.string.gcm_defaultSenderId);
        //String senderId = "823310351743";
        String token ="";
        try {
            // request token that will be used by the server to send push notifications
            token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.d(TAG, "GCM Registration Token: " + token);

            // pass along this data
            sendRegistrationToServer(token);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Fetch token here
        try {
            // save token
            sharedPreferences.edit().putString(GCM_TOKEN, token).apply();
            // pass along this data
            sendRegistrationToServer(token);
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();
        }
    }

    private void sendRegistrationToServer(String token) {
        Log.i(TAG, "Client ID: " + getResources().getString(R.string.clientId));
        ClientToken clientToken = new ClientToken(getResources().getString(R.string.clientId), token);
        final Context context = getApplicationContext();
        // Add custom implementation, as needed.
        Observable<ClientRegistration> call =
                MediaStoreService.formsMessagesStore.postToken(clientToken);
            call
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<ClientRegistration>() {
                @Override
                public void onCompleted() {
                    Log.i(TAG, "POST token call success");
                }

                @Override
                public void onError(Throwable e) {
                    // cast to retrofit.HttpException to get the response code
                    Log.i(TAG, "in error");
                    Log.i(TAG, e.toString());

                    if (e instanceof HttpException) {
                        HttpException response = (HttpException) e;
                        int code = response.code();
                        Log.i(TAG, "Http error code: " + code);
                    }
                }

                @Override
                public void onNext(ClientRegistration clientRegistration) {
                    Log.i(TAG, clientRegistration.getStatus());

                    if (clientRegistration.getStatus().equals("success")) {
                        // if registration sent was successful, store a boolean that
                        // indicates whether the generated token has been sent to server
                        SharedPreferences sharedPreferences =
                                PreferenceManager.getDefaultSharedPreferences(context);
                        sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();
                    }
                }
            });
    }
}
