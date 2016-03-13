package com.codepath.apps.learnfitness.fragments;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.Form;
import com.codepath.apps.learnfitness.models.Lesson;
import com.codepath.apps.learnfitness.models.Week;
import com.codepath.apps.learnfitness.rest.MediaStoreService;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by spandhare on 3/11/16.
 */
public class ComposeFormMessageFragment extends DialogFragment {

    Subscription subscription;
    public static final String TAG = "ComposeFormMessage";

    private static final int RESULT_VIDEO_CAP = 5;

    private String recordedVideoUrl;
    private List<Week> mWeeks;
    private ArrayAdapter<Week> mWeekArrayAdapter;

    @Bind(R.id.btnComposeFormMessageRecord)
    Button mButtonComposeFormMessageRecord;

    @Bind(R.id.btnComposeMessageSend)
    Button mButtonComposeFormMessageSend;

    @Bind(R.id.vvComposeFormVideo)
    VideoView mVideoViewComposeFormVideo;

    @Bind(R.id.spWeeksTitleList)
    Spinner mSpinnerWeeksTitleList;

    @Bind(R.id.etComposeMessageText)
    EditText mEditTextMessageText;

    @Bind(R.id.rlComposeMessageVideoView)
    RelativeLayout mRelativeLayoutVideoView;

    public static ComposeFormMessageFragment newInstance() {
        ComposeFormMessageFragment composeFormMessageFragment = new ComposeFormMessageFragment();

        Bundle args = new Bundle();
        return composeFormMessageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWeeks = new ArrayList<>();
        mWeekArrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, mWeeks);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.compose_message, container, false);
        ButterKnife.bind(this, rootView);

        final Observable<Lesson> call = MediaStoreService.contentStore.fetchContent();
        subscription = call
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Lesson>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "Api call success");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        Log.i(TAG, "in error");
                        Log.i(TAG, e.toString());

                        if (e instanceof HttpException) {
                            HttpException response = (HttpException) e;
                            int code = response.code();
                        }
                        call.retry();
                    }

                    @Override
                    public void onNext(Lesson lesson) {
                        mWeeks.addAll(lesson.getWeeks());
                        Log.i(TAG, lesson.getTitle());

                        //mSpinnerWeeksTitleList set the default from preferences
                        mSpinnerWeeksTitleList.setAdapter(mWeekArrayAdapter);

                    }
                });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    @OnClick(R.id.btnComposeFormMessageRecord)
    public void recordVideo(View view) {
        Log.i(TAG, "In record video");

        if (mOnRecordVideoListener == null) {
            mOnRecordVideoListener = (OnRecordVideoListener) getActivity();
        }
        mOnRecordVideoListener.onRecordVideo(view);
    }

    @OnClick(R.id.btnComposeMessageSend)
    public void sendMessage(View view) {
        if (!TextUtils.isEmpty(recordedVideoUrl)) {
            Log.i(TAG, "Received path:" + recordedVideoUrl);
            if (mOnRecordVideoListener == null) {
                mOnRecordVideoListener = (OnRecordVideoListener) getActivity();
            }

            Week selectedWeek = (Week) mSpinnerWeeksTitleList.getSelectedItem();
            Form form = new Form();
            form.setWeekTitle(selectedWeek.getWeekTitle());
            form.setWeekNumber(selectedWeek.getWeekNumber());
            form.setMessage(mEditTextMessageText.getText().toString());

            mOnRecordVideoListener.startUpload(form);

            Toast.makeText(getActivity(), R.string.toast_form_message_submitted,
                    Toast.LENGTH_SHORT).show();
        } else {
            Log.i(TAG, "Didn't get video path :(");
            Toast.makeText(getActivity(), R.string.toast_form_record_video_failure,
                    Toast.LENGTH_SHORT).show();
        }
        dismiss();
    }

    private OnRecordVideoListener mOnRecordVideoListener;

    // Define the events that the fragment will use to communicate
    public interface OnRecordVideoListener {
        // This can be any number of events to be sent to the activity
        void onRecordVideo(View view);
        void startUpload(Form form);
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
        mRelativeLayoutVideoView.setVisibility(View.VISIBLE);
        mVideoViewComposeFormVideo.setVideoURI(localVideoUri);
        mVideoViewComposeFormVideo.setMediaController(new MediaController(getActivity()));
        mVideoViewComposeFormVideo.requestFocus();
        mVideoViewComposeFormVideo.start();
    }
}
