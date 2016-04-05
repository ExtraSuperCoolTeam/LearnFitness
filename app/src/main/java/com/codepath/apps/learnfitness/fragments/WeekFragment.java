package com.codepath.apps.learnfitness.fragments;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.activities.LessonListActivity;
import com.codepath.apps.learnfitness.adapters.WeekStepsAdapter;
import com.codepath.apps.learnfitness.models.Step;
import com.codepath.apps.learnfitness.models.Week;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//import com.codepath.apps.learnfitness.DeveloperKey;

public class WeekFragment extends Fragment implements YouTubePlayer.OnInitializedListener {
    private static final String TAG = "WeekFragment";

    @Bind(R.id.rvWeekStepsList)
    RecyclerView mRecyclerViewWeekSteps;

    Week mWeek;
    private WeekStepsAdapter mAdapterWeekSteps;
    private List<Step> mSteps;
    LinearLayoutManager layoutManager;

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
        View v = inflater.inflate(R.layout.fragment_week, container, false);
        ButterKnife.bind(this, v);

       ((LessonListActivity)getActivity()).checkLoginWeekDetails();

        mWeek = getArguments().getParcelable("weekInfo");
        mSteps = new ArrayList<>();
        mAdapterWeekSteps = new WeekStepsAdapter(mSteps);
        mSteps.addAll(mWeek.getSteps());
        mRecyclerViewWeekSteps.setAdapter(mAdapterWeekSteps);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewWeekSteps.setLayoutManager(layoutManager);

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

    @Override
    public void onResume() {
        super.onResume();
//        FloatingActionButton mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
//        mFab.setVisibility(View.GONE);
        ((LessonListActivity)getActivity()).checkLoginWeekDetails();
        FabSetup();
        Log.i(TAG, "In Resume");
        ((LessonListActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_activity_lesson_list);
    }

    void FabSetup() {
        FloatingActionButton mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setVisibility(View.VISIBLE);


        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) mFab.getLayoutParams();
        p.setBehavior(new FABScrollBehavior(getContext(), null));
        mFab.setLayoutParams(p);
    }
}
