package com.codepath.apps.learnfitness.fragments;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.adapters.CheckMyFormAdapter;
import com.codepath.apps.learnfitness.models.MyFormMessage;
import com.codepath.apps.learnfitness.models.TrainerReply;
import com.codepath.apps.learnfitness.rest.MediaStoreService;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JaneChung on 3/6/16.
 */
public class CheckMyFormFragment extends Fragment
    implements YouTubePlayer.OnInitializedListener {

    private static final String TAG = "CheckMyFormFragment";
    private static final String MY_FORM_MESSAGE = "myFormMessageInfo";
    Subscription subscription;
    private CheckMyFormAdapter mAdapter;
    private List<TrainerReply> mTrainerReplies;

    @Bind(R.id.rvCheckMyForms)
    RecyclerView rvForms;

    @Bind(R.id.flVideoMyForm)
    FrameLayout mFrameLayout;

    @Bind(R.id.tvMyFormMessageDetail)
    TextView mTextViewFormMessageDetail;

    LinearLayoutManager layoutManager;
    MyFormMessage myFormMessage;
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    public static CheckMyFormFragment newInstance(MyFormMessage myFormMessage) {
        CheckMyFormFragment checkMyFormFragment = new CheckMyFormFragment();
        Bundle args = new Bundle();
        args.putParcelable(MY_FORM_MESSAGE, myFormMessage);
        checkMyFormFragment.setArguments(args);
        return checkMyFormFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_check_my_form_details, container, false);
        ButterKnife.bind(this, v);
        setUpViews();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTrainerReplies = new ArrayList<>();
        mAdapter = new CheckMyFormAdapter(mTrainerReplies);
    }

    public void setUpViews() {
        rvForms.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getActivity());
        rvForms.setLayoutManager(layoutManager);

        myFormMessage = getArguments().getParcelable(MY_FORM_MESSAGE);
        mTextViewFormMessageDetail.setText(myFormMessage.getMessage());

        if (!TextUtils.isEmpty(myFormMessage.getVideoId())) {
            mFrameLayout.setVisibility(View.VISIBLE);

            // Switch in a new YouTubePlayerSupportFragment
            YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.flVideoMyForm, youTubePlayerFragment).commit();

            youTubePlayerFragment.initialize(getString(R.string.google_developer_key), this);
        }

        Observable<List<TrainerReply>> call =
                MediaStoreService.formsMessagesStore.
                        fetchFormMessageRepliesByMessageId(myFormMessage.getId());
        subscription = call
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<TrainerReply>>() {
                @Override
                public void onCompleted() {
                    Log.i(TAG, "Trainer reply api call success");
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
                public void onNext(List<TrainerReply> trainerReplies) {
                    mTrainerReplies.addAll(trainerReplies);
                    Log.i(TAG, Integer.toString(mTrainerReplies.size()));
                    mAdapter.notifyDataSetChanged();
                }
            });
    }

    // Define the listener of the interface type
    // listener will the activity instance containing fragment
    private OnCheckMyFormListener mOnCheckMyFormListener;

    // Define the events that the fragment will use to communicate
    public interface OnCheckMyFormListener {
        // This can be any number of events to be sent to the activity
        void onCheckMyFormDialog();
    }

    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCheckMyFormListener) {
            mOnCheckMyFormListener = (OnCheckMyFormListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement CheckMyFormFragment.OnCheckMyFormListener");
        }
    }

    public void showCreationDialog() {
        mOnCheckMyFormListener.onCheckMyFormDialog();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer,
                                        boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.cueVideo(myFormMessage.getVideoId());
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
