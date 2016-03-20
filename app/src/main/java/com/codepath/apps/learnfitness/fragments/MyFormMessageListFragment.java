package com.codepath.apps.learnfitness.fragments;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.adapters.MyFormMessagesAdapter;
import com.codepath.apps.learnfitness.models.MyFormMessage;
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
 * Created by spandhare on 3/19/16.
 */
public class MyFormMessageListFragment extends Fragment {

    private static final String TAG = "MyFormsListFragment";

    Subscription subscription;
    private MyFormMessagesAdapter mAdapter;
    private List<MyFormMessage> mMyFormMessages;

    @Bind(R.id.rvMessages)
    RecyclerView mRecyclerViewMessages;
    LinearLayoutManager layoutManager;

    public static MyFormMessageListFragment newInstance() {
        MyFormMessageListFragment myFormMessageListFragment = new MyFormMessageListFragment();
        return myFormMessageListFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMyFormMessages = new ArrayList<>();
        mAdapter = new MyFormMessagesAdapter(mMyFormMessages);

        mAdapter.setOnItemClickListener(new MyFormMessagesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, MyFormMessage myFormMessage) {
                mOnMyFormMessagesListener.onFormMessageSelected(itemView, myFormMessage);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_form_message_list, container, false);
        ButterKnife.bind(this, v);
        setUpViews();
        return v;
    }

    public void setUpViews() {
        mRecyclerViewMessages.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewMessages.setLayoutManager(layoutManager);

        //Get the list of forms here
        Observable<List<MyFormMessage>> call = MediaStoreService.formsMessagesStore.fetchFormMessageList();
        subscription = call
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MyFormMessage>>() {
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
                            Log.i(TAG, "Http error code: " + code);
                        }
                    }

                    @Override
                    public void onNext(List<MyFormMessage> messages) {
                        mMyFormMessages.addAll(messages);
                        Log.i(TAG, Integer.toString(mMyFormMessages.size()));
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // Define the listener of the interface type
    // listener will the activity instance containing fragment
    private OnMyFormMessagesListener mOnMyFormMessagesListener;

    // Define the events that the fragment will use to communicate
    public interface OnMyFormMessagesListener {
        // This can be any number of events to be sent to the activity
        void onCheckMyFormDialog();
        void onFormMessageSelected(View itemView, MyFormMessage myFormMessage);
    }

    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMyFormMessagesListener) {
            mOnMyFormMessagesListener = (OnMyFormMessagesListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyFormMessageListFragment.OnMyFormMessagesListener");
        }
    }

    public void showCreationDialog() {
        mOnMyFormMessagesListener.onCheckMyFormDialog();
    }
}
