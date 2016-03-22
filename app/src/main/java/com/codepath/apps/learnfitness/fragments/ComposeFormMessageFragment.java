package com.codepath.apps.learnfitness.fragments;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.activities.LessonListActivity;
import com.codepath.apps.learnfitness.models.Lesson;
import com.codepath.apps.learnfitness.models.MyFormMessage;
import com.codepath.apps.learnfitness.models.Week;
import com.codepath.apps.learnfitness.rest.MediaStoreService;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Spinner;
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
public class ComposeFormMessageFragment extends Fragment {

    Subscription subscription;
    public static final String TAG = "ComposeFormMessage";

    private static final int RESULT_VIDEO_CAP = 5;

    private String recordedVideoUrl;
    private List<Week> mWeeks;
    private ArrayAdapter<Week> mWeekArrayAdapter;

    @Bind(R.id.btnComposeFormMessageCancel)
    Button mButtonComposeFormMessageCancel;

    @Bind(R.id.btnComposeFormMessageRecord)
    ImageView mButtonComposeFormMessageRecord;

    @Bind(R.id.btnComposeMessageSend)
    Button mButtonComposeFormMessageSend;

    @Bind(R.id.vvComposeFormVideo)
    VideoView mVideoViewComposeFormVideo;

    @Bind(R.id.spWeeksTitleList)
    Spinner mSpinnerWeeksTitleList;

    @Bind(R.id.etComposeMessageText)
    EditText mEditTextMessageText;

//    @Bind(R.id.vMessageVideoPlaceHolder)
//    View mViewVideoPlaceHolder;
//
//    @Bind(R.id.vMessageItemsSeperator2)
//    View mViewSeapratorBelowVideo;

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
                android.R.layout.simple_spinner_item, mWeeks);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.compose_message, container, false);
        ButterKnife.bind(this, rootView);

        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditTextMessageText.requestFocus();
        mEditTextMessageText.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(mEditTextMessageText, 0);
            }
        }, 200);

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

                    //TODO mSpinnerWeeksTitleList set the default from preferences
                    mSpinnerWeeksTitleList.setAdapter(mWeekArrayAdapter);

                }
            });

        return rootView;
    }

    @OnClick(R.id.btnComposeFormMessageRecord)
    public void recordVideo(View view) {
        dismissSoftKeyboard();
        Log.i(TAG, "In record video");

        if (mOnFormMessageListener == null) {
            mOnFormMessageListener = (OnFormMessageListener) getActivity();
        }
        mOnFormMessageListener.onRecordVideo(view);
    }

    @OnClick(R.id.btnComposeMessageSend)
    public void sendMessage(View view) {

        dismissSoftKeyboard();
        //TODO check if empty fields and ignore post
        Week selectedWeek = (Week) mSpinnerWeeksTitleList.getSelectedItem();
//        Form form = new Form();
//        form.setWeekTitle(selectedWeek.getWeekTitle());
//        form.setWeekNumber(selectedWeek.getWeekNumber());
//        form.setMessage(mEditTextMessageText.getText().toString());

        MyFormMessage myFormMessage = new MyFormMessage();
        myFormMessage.setWeekTitle(selectedWeek.getWeekTitle());
        myFormMessage.setWeekNumber(selectedWeek.getWeekNumber());
        myFormMessage.setMessage(mEditTextMessageText.getText().toString());
        myFormMessage.setTimeStamp(Long.toString(System.currentTimeMillis()));

        if (!TextUtils.isEmpty(recordedVideoUrl)) {
            Log.i(TAG, "Received path:" + recordedVideoUrl);
            if (mOnFormMessageListener == null) {
                mOnFormMessageListener = (OnFormMessageListener) getActivity();
            }

            mOnFormMessageListener.startUpload(myFormMessage);
        } else {
            Log.i(TAG, "Didn't get video path :(");
            mOnFormMessageListener.startPostWithoutVideo(myFormMessage);
        }
    }

    // Whenever we leave this fragment, show the fab again.
    @Override
    public void onPause() {
        super.onPause();
        ((LessonListActivity)getActivity()).showFab(true);
    }

    @OnClick(R.id.btnComposeFormMessageCancel)
    public void cancelMessage() {
        dismissSoftKeyboard();
        if (mOnFormMessageListener == null) {
            mOnFormMessageListener = (OnFormMessageListener) getActivity();
        }
        mOnFormMessageListener.composeMessageCancel();
    }

    @OnClick(R.id.btnComposeFormMessageGallery)
    public void lookupVideoGallery() {
        dismissSoftKeyboard();
        if (mOnFormMessageListener == null) {
            mOnFormMessageListener = (OnFormMessageListener) getActivity();
        }
        mOnFormMessageListener.launchVideoGallery();
    }

    private OnFormMessageListener mOnFormMessageListener;

    // Define the events that the fragment will use to communicate
    public interface OnFormMessageListener {
        // This can be any number of events to be sent to the activity
        void onRecordVideo(View view);
        void startUpload(MyFormMessage myFormMessage);
        void startPostWithoutVideo(MyFormMessage myFormMessage);
        void composeMessageCancel();
        void launchVideoGallery();
    }

    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFormMessageListener) {
            mOnFormMessageListener = (OnFormMessageListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ComposeFormMessageFragment.OnFormMessageListener");
        }
    }

    public String getRecordedVideoUrl() {
        return recordedVideoUrl;
    }

    public void setRecordedVideoUrl(String recordedVideoUrl) {
        this.recordedVideoUrl = recordedVideoUrl;
    }

    public void showVideo(Uri localVideoUri) {

        mEditTextMessageText.setMaxLines(3);
        mVideoViewComposeFormVideo.setVisibility(View.VISIBLE);
        mVideoViewComposeFormVideo.setVideoURI(localVideoUri);
        mVideoViewComposeFormVideo.setMediaController(new MediaController(getActivity()));
        mVideoViewComposeFormVideo.requestFocus();
        mVideoViewComposeFormVideo.start();
    }

    private void dismissSoftKeyboard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
