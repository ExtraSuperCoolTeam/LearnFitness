package com.codepath.apps.learnfitness.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.adapters.CheckMyFormAdapter;
import com.codepath.apps.learnfitness.models.Form;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JaneChung on 3/6/16.
 */
public class CheckMyFormFragment extends Fragment {

    private CheckMyFormAdapter mAdapter;
    private List<Form> mForms;

    @Bind(R.id.rvCheckMyForms)
    RecyclerView rvForms;
    LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_check_my_form, container, false);
        ButterKnife.bind(this, v);
        setUpViews();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       mAdapter = new CheckMyFormAdapter(mForms);

        //Todo onclick listener

    }

    public void setUpViews() {
        rvForms.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getActivity());
        rvForms.setLayoutManager(layoutManager);

        //Get the list of forms here
    }
}
