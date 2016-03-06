package com.codepath.apps.learnfitness.fragments;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

//import com.codepath.apps.learnfitness.DeveloperKey;
import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.adapters.WeekStepsAdapter;
import com.codepath.apps.learnfitness.models.Step;
import com.codepath.apps.learnfitness.models.Week;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeekFragment extends Fragment implements YouTubePlayer.OnInitializedListener {
    private static final String TAG = "WeekFragment";

    private WeekStepsAdapter mAdapterWeekSteps;
    private List<Step> mSteps;
    LinearLayoutManager layoutManager;

    //@Bind(R.id.ivImage) ImageView ivImage;
    @Bind(R.id.tvTextContent) TextView tvTextContent;
    @Bind(R.id.rvWeekStepsList) RecyclerView mRecyclerViewWeekSteps;

    Week mWeek;

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    public static WeekFragment newInstance(Week week) {
        WeekFragment weekFragment = new WeekFragment();
        Bundle args = new Bundle();
        args.putParcelable("weekInfo", week);

        weekFragment.setArguments(args);
        return weekFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lesson_fragment, container, false);
        ButterKnife.bind(this, v);

        //mWeek = LessonDepre.getExample();

        mWeek = getArguments().getParcelable("weekInfo");
        mSteps = new ArrayList<>();
        mAdapterWeekSteps = new WeekStepsAdapter(mSteps);
        mSteps.addAll(mWeek.getSteps());
        mRecyclerViewWeekSteps.setAdapter(mAdapterWeekSteps);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewWeekSteps.setLayoutManager(layoutManager);

        // Update the text content and image.
        tvTextContent.setText(mWeek.getLongDescription());
        //Glide.with(getActivity()).load(mWeek.getPhotoUrl()).into(ivImage);

        // Switch in a new YouTubePlayerSupportFragment
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.flVideo, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(getString(R.string.google_developer_key), this);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer,
                                        boolean wasRestored) {
        Toast.makeText(getActivity(), "OMG", Toast.LENGTH_LONG).show();
        if (!wasRestored) {
            youTubePlayer.cueVideo(mWeek.getVideoId());
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
