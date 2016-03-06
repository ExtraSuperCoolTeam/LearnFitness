package com.codepath.apps.learnfitness.activities;

import com.codepath.apps.learnfitness.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    @Bind(R.id.lbLogin)
    LoginButton lbLogin;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        callbackManager = CallbackManager.Factory.create();

        checkIfAuthenticated();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.d(TAG, "Successful login");
                        Toast.makeText(LoginActivity.this, "Successful login", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LoginActivity.this, LessonListActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.d(TAG, "Cancelled login");
                        Toast.makeText(LoginActivity.this, "Cancelled login", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d(TAG, "Login error");

                        Toast.makeText(LoginActivity.this, "Error login", Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
        checkIfAuthenticated();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    private void checkIfAuthenticated() {
        // If the user is already logged in, skip the Login page and go directly to the LessonDepre List.
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token != null) {
            Intent i = new Intent(this, LessonListActivity.class);
            startActivity(i);
        }
    }
}
