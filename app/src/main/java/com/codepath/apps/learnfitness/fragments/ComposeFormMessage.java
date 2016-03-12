package com.codepath.apps.learnfitness.fragments;

import com.codepath.apps.learnfitness.R;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;



/**
 * Created by spandhare on 3/11/16.
 */
public class ComposeFormMessage extends DialogFragment {

    private static final int RESULT_VIDEO_CAP = 5;

    @Bind(R.id.btnComposeFormMessageRecord)
    Button mButtonComposeFormMessageRecord;

    @Bind(R.id.btnComposeMessageSend)
    Button mButtonComposeFormMessageSend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.compose_message, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


    }

    @OnClick(R.id.btnComposeFormMessageRecord)
    public void recordVideo(View view) {
        Log.i("CheckMyFormAdapter", "In record video");

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        // Workaround for Nexus 7 Android 4.3 Intent Returning Null problem
        // create a file to save the video in specific folder (this works for
        // video only)
        // mFileURI = getOutputMediaFile(MEDIA_TYPE_VIDEO);
        // intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileURI);

        // set the video image quality to high
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        // start the Video Capture Intent
        startActivityForResult(intent, RESULT_VIDEO_CAP);

    }

    @OnClick(R.id.btnComposeMessageSend)
    public void sendMessage(View view) {
        dismiss();
    }
}
