package com.codepath.apps.learnfitness.fragments;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.activities.LessonListActivity;
import com.codepath.apps.learnfitness.adapters.LessonsAdapter;
import com.codepath.apps.learnfitness.models.Lesson;
import com.codepath.apps.learnfitness.models.Week;
import com.codepath.apps.learnfitness.rest.MediaStoreService;

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

import java.util.LinkedList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeeksListFragment extends Fragment {
    private static final String TAG = "WeeksListFragment";

    Subscription subscription;
    private LessonsAdapter mAdapter;
    private List<Week> mWeeks;

    LinearLayoutManager layoutManager;
    ProgressDialog pd;
    //@Bind(R.id.rvLessonsList)
    RecyclerView rvLessons;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View v = inflater.inflate(R.layout.weeks_list_fragment, container, false);
        //ButterKnife.bind(this, v);

        rvLessons = (RecyclerView)v.findViewById(R.id.rvLessonsList);
        rvLessons.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getActivity());

        rvLessons.setLayoutManager(layoutManager);

        mWeeks.clear();
        mAdapter.notifyDataSetChanged();

       // RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        //rvLessons.addItemDecoration(itemDecoration);

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

                    SharedPreferences sharedPreferences =
                            getActivity().getSharedPreferences(LessonListActivity.MY_SHARED_PREFS,
                                    Context.MODE_PRIVATE);

                    int currentWeekNumber =
                            Integer.parseInt(sharedPreferences.getString(
                                    LessonListActivity.CURRENT_WEEK_NUMBER, "2")) - 1;
                    if (currentWeekNumber >= 0 && currentWeekNumber < mWeeks.size()) {
                        Week currentWeek = mWeeks.get(currentWeekNumber);
                        currentWeek.setIsCurrent(true);
                    } else {
                        Week currentWeek = mWeeks.get(0);
                        currentWeek.setIsCurrent(true);
                    }

                    mAdapter.notifyDataSetChanged();
                }
            });

        //setUpViews(v);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        //TODO setup progressbar
        //setUpProgressDialogForLoading();

        mWeeks = new LinkedList<>();
        mAdapter = new LessonsAdapter(mWeeks);

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

                Log.i("WeeksListFragment", "prev weeknumber: " + previousWeekNumber);
                if (previousWeekNumber >= 0 && previousWeekNumber < mWeeks.size()) {
                    Week previous = mWeeks.get(previousWeekNumber);
                    previous.setIsCurrent(false);

                    mAdapter.notifyItemChanged(previousWeekNumber);
                }


                int newlySelectedWeekNumber = Integer.parseInt(week.getWeekNumber()) - 1;
                Week newlySelectedWeek = mWeeks.get(newlySelectedWeekNumber);
                newlySelectedWeek.setIsCurrent(true);

                mAdapter.notifyItemChanged(newlySelectedWeekNumber);

                if (newlySelectedWeekNumber >= 0 && newlySelectedWeekNumber <= mWeeks.size()) {
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
