package com.codepath.apps.learnfitness.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.activities.LessonListActivity;
import com.codepath.apps.learnfitness.adapters.LessonsAdapter;
import com.codepath.apps.learnfitness.models.Week;

import java.util.List;

public class WeeksListFragment extends Fragment {
    private static final String TAG = "WeeksListFragment";

    private LessonsAdapter mAdapter;

    LinearLayoutManager layoutManager;
    ProgressDialog pd;
    RecyclerView rvLessons;

    LessonListActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
//        View v = inflater.inflate(R.layout.fragment_weeks_list, container, false);
        //ButterKnife.bind(this, v);


        //rvLessons = (RecyclerView)v.findViewById(R.id.rvLessonsList);
        rvLessons = (RecyclerView)inflater.inflate(R.layout.fragment_weeks_list, container, false);

        rvLessons.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getActivity());

        rvLessons.setLayoutManager(layoutManager);

        mAdapter.notifyDataSetChanged();


        return rvLessons;
    }

    public void notifyWeeksChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        //TODO setup progressbar
        //setUpProgressDialogForLoading();
        mActivity = (LessonListActivity) getActivity();


        mAdapter = new LessonsAdapter(mActivity.getWeeks());

        mAdapter.setOnItemClickListener(new LessonsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, Week week) {
                Log.i("WeeksListFragment", "back to fragment");
                //Toast.makeText(getActivity(), "In fragment clicklistener", Toast.LENGTH_SHORT).show();
                mOnItemSelectedListener.onWeekSelected(itemView, week);
            }

            @Override
            public void onItemExpand(View itemView, Week week) {
                SharedPreferences sharedPreferences =
                        getActivity().getSharedPreferences(LessonListActivity.MY_SHARED_PREFS,
                                Context.MODE_PRIVATE);

                int previousWeekNumber =
                        Integer.parseInt(sharedPreferences.
                                getString(LessonListActivity.CURRENT_WEEK_NUMBER, "2")) - 1;

                List<Week> weeks = mActivity.getWeeks();

                Log.i("WeeksListFragment", "prev weeknumber: " + previousWeekNumber);
                if (previousWeekNumber >= 0 && previousWeekNumber < weeks.size()) {
                    Week previous = weeks.get(previousWeekNumber);
                    previous.setIsCurrent(false);

                    mAdapter.notifyItemChanged(previousWeekNumber);
                }

                int newlySelectedWeekNumber = Integer.parseInt(week.getWeekNumber()) - 1;
                Week newlySelectedWeek = weeks.get(newlySelectedWeekNumber);
                newlySelectedWeek.setIsCurrent(true);

                mAdapter.notifyItemChanged(newlySelectedWeekNumber);

                if (newlySelectedWeekNumber >= 0 && newlySelectedWeekNumber <= weeks.size()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(LessonListActivity.CURRENT_WEEK_NUMBER,
                            Integer.toString(newlySelectedWeekNumber + 1));

                    editor.commit();

                }
            }
        });
    }

    public void setUpProgressDialogForLoading() {
        pd = new ProgressDialog(getActivity());
        pd.setTitle("Loading...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
    }

    // Define the listener of the interface type
    // listener will the activity instance containing fragment
    private OnItemSelectedListener mOnItemSelectedListener;

    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
        void onWeekSelected(View itemView, Week week);
    }

    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            mOnItemSelectedListener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement WeeksListFragment.OnItemSelectedListener");
        }
    }
}
