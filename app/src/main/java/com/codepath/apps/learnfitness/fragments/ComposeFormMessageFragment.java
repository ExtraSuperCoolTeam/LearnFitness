package com.codepath.apps.learnfitness.fragments;

import com.codepath.apps.learnfitness.R;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;



/**
 * Created by spandhare on 3/11/16.
 */
public class ComposeFormMessageFragment extends DialogFragment {

    public static final String TAG = "ComposeFormMessageFragment";

    private static final int RESULT_VIDEO_CAP = 5;

    private String recordedVideoUrl;

    @Bind(R.id.btnComposeFormMessageRecord)
    Button mButtonComposeFormMessageRecord;

    @Bind(R.id.btnComposeMessageSend)
    Button mButtonComposeFormMessageSend;

    @Bind(R.id.vvComposeFormVideo)
    VideoView mVideoViewComposeFormVideo;

    public static ComposeFormMessageFragment newInstance() {
        ComposeFormMessageFragment composeFormMessageFragment = new ComposeFormMessageFragment();

        Bundle args = new Bundle();

        return composeFormMessageFragment;
    }

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
        Log.i("ComposeFormMessage", "In record video");

        if (mOnRecordVideoListener == null) {
            mOnRecordVideoListener = (OnRecordVideoListener) getActivity();
        }
        mOnRecordVideoListener.onRecordVideo(view);
    }

    @OnClick(R.id.btnComposeMessageSend)
    public void sendMessage(View view) {
        if (!TextUtils.isEmpty(recordedVideoUrl)) {
            Log.i("ComposeFormMessage", "Received path:" + recordedVideoUrl);
        } else {
            Log.i("ComposeFormMessage", "Didnt get video path :(");
        }

        if (mOnRecordVideoListener == null) {
            mOnRecordVideoListener = (OnRecordVideoListener) getActivity();
        }
        mOnRecordVideoListener.startUpload();
        dismiss();
    }

    private OnRecordVideoListener mOnRecordVideoListener;

    // Define the events that the fragment will use to communicate
    public interface OnRecordVideoListener {
        // This can be any number of events to be sent to the activity
        void onRecordVideo(View view);
        void startUpload();
    }

    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecordVideoListener) {
            mOnRecordVideoListener = (OnRecordVideoListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ComposeFormMessageFragment.OnRecordVideoListener");
        }
    }

    public String getRecordedVideoUrl() {
        return recordedVideoUrl;
    }

    public void setRecordedVideoUrl(String recordedVideoUrl) {
        this.recordedVideoUrl = recordedVideoUrl;
    }

    public void showVideo(Uri localVideoUri) {
        mVideoViewComposeFormVideo.setVisibility(View.VISIBLE);
        mVideoViewComposeFormVideo.setVideoURI(localVideoUri);
        mVideoViewComposeFormVideo.setMediaController(new MediaController(getActivity()));
        mVideoViewComposeFormVideo.requestFocus();
        mVideoViewComposeFormVideo.start();
    }
}
