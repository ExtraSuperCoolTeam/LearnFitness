package com.codepath.apps.restclienttemplate.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.Adapter.LessonsAdapter;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Lesson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LessonsListFragment extends Fragment {

    @Bind(R.id.rvLessonsList)
    RecyclerView rvLessons;

    private LessonsAdapter mAdapter;
    private ArrayList<Lesson> mLessons;

    LinearLayoutManager layoutManager;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lesson_list_fragment, container, false);
        ButterKnife.bind(this, v);
        setUpViews();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpProgressDialogForLoading();
        //TODO: Change this to api call
        mLessons = Lesson.getExampleList();
        mAdapter = new LessonsAdapter(mLessons);
    }


    //Stub for later
    public void populateLessonsList() {

        //Get lessons from api
        //show and unshow pd

    }
    public void setUpViews() {

        rvLessons.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getActivity());
        rvLessons.setLayoutManager(layoutManager);
    }

    public void setUpProgressDialogForLoading() {
        pd = new ProgressDialog(getActivity());
        pd.setTitle("Loading...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
    }
}
