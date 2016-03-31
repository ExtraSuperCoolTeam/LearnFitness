package com.codepath.apps.learnfitness.fragments;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.activities.LessonListActivity;
import com.codepath.apps.learnfitness.adapters.MyFormMessagesAdapter;
import com.codepath.apps.learnfitness.models.MyFormMessage;
import com.codepath.apps.learnfitness.rest.MediaStoreService;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
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

//    @Bind(R.id.fab)
    FloatingActionButton mFab;

    LinearLayoutManager layoutManager;

    public static MyFormMessageListFragment newInstance() {
        MyFormMessageListFragment myFormMessageListFragment = new MyFormMessageListFragment();
        return myFormMessageListFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ((LessonListActivity)getActivity()).checkLogin();

        mMyFormMessages = new ArrayList<>();
        mAdapter = new MyFormMessagesAdapter(mMyFormMessages);

        mAdapter.setOnItemClickListener(new MyFormMessagesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, MyFormMessage myFormMessage) {
                mOnMyFormMessagesListener.onFormMessageSelected(itemView, myFormMessage);
            }
        });

        mEnterTransitionListener = new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                enterReveal();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        };
        getActivity().getWindow().getEnterTransition().addListener(mEnterTransitionListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_form_message_list, container, false);
        ButterKnife.bind(this, v);

        ((LessonListActivity)getActivity()).checkLogin();

        setUpViews();
//        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
//        mFab.setVisibility(View.VISIBLE);
//        mFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showCreationDialog();
//
//            }
//        });

        return v;
    }

    public void showData() {
        mRecyclerViewMessages.setVisibility(View.VISIBLE);
    }

    public void setUpViews() {
        mRecyclerViewMessages.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewMessages.setLayoutManager(layoutManager);

        final LessonListActivity activity = (LessonListActivity)getActivity();
        activity.showProgressBar(true);

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

                        activity.showProgressBar(false);

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
        //showFab(false);
        mOnMyFormMessagesListener.onCheckMyFormDialog();
    }

//    public void showFab(Boolean show) {
//        if (show) {
//            mFab.setVisibility(View.VISIBLE);
//        } else {
//            mFab.setVisibility(View.GONE);
//        }
//    }

    void enterReveal() {
        // previously invisible view
        final View myView =  getActivity().findViewById(R.id.fab);

        if (myView == null)
            return;

        // get the center for the clipping circle
        int cx = myView.getMeasuredWidth() / 2;
        int cy = myView.getMeasuredHeight() / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(myView.getWidth(), myView.getHeight()) / 2;
        Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
        myView.setVisibility(View.VISIBLE);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getActivity().getWindow().getEnterTransition().removeListener(mEnterTransitionListener);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }
    public void exitReveal() {
        // previously visible view
        final View myView = getActivity().findViewById(R.id.fab);

        if (myView == null)
            return;

        // get the center for the clipping circle
        int cx = myView.getMeasuredWidth() / 2;
        int cy = myView.getMeasuredHeight() / 2;

        // get the initial radius for the clipping circle
        int initialRadius = myView.getWidth() / 2;

        // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
            }
        });

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);

                // Finish the activity after the exit transition completes.
                //supportFinishAfterTransition();
            }
        });

        // start the animation
        anim.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "In Resume");
        //showFab(true);
        FabSetup();
        enterReveal();
    }

    @Override
    public void onPause() {
        super.onPause();
        exitReveal();
    }

    private Transition.TransitionListener mEnterTransitionListener;

    void FabSetup() {
        FloatingActionButton mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setVisibility(View.VISIBLE);

        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) mFab.getLayoutParams();
        p.setBehavior(new FABScrollBehavior(getContext(), null));
        mFab.setLayoutParams(p);
    }

}
