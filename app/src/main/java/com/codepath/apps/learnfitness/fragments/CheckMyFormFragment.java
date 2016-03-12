package com.codepath.apps.learnfitness.fragments;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.adapters.CheckMyFormAdapter;
import com.codepath.apps.learnfitness.models.Form;
import com.codepath.apps.learnfitness.rest.MediaStoreService;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class CheckMyFormFragment extends Fragment {

    Subscription subscription;
    private CheckMyFormAdapter mAdapter;
    private List<Form> mForms;

    @Bind(R.id.rvCheckMyForms)
    RecyclerView rvForms;
    LinearLayoutManager layoutManager;

    public static CheckMyFormFragment newInstance() {
        CheckMyFormFragment checkMyFormFragment = new CheckMyFormFragment();
        return checkMyFormFragment;
    }

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

        mForms = new ArrayList<>();
        mAdapter = new CheckMyFormAdapter(mForms);
        //Todo onclick listener

    }

    public void setUpViews() {
        rvForms.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getActivity());
        rvForms.setLayoutManager(layoutManager);

        //Get the list of forms here
        Observable<List<Form>> call = MediaStoreService.formsStore.fetchFormMessages();
        subscription = call
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Form>>() {
                    @Override
                    public void onCompleted() {
                        Log.i("CheckMyFormFragment", "Api call success");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        Log.i("CheckMyFormFragment", "in error");
                        Log.i("CheckMyFormFragment", e.toString());

                        if (e instanceof HttpException) {
                            HttpException response = (HttpException) e;
                            int code = response.code();
                            Log.i("CheckMyFormFragment", "Http error code: " + code);
                        }
                    }

                    @Override
                    public void onNext(List<Form> forms) {
                        mForms.addAll(forms);
                        Log.i("CheckMyFormFragment", Integer.toString(mForms.size()));
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
}
