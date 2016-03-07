package com.codepath.apps.learnfitness.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.Trainer;
import com.codepath.apps.learnfitness.rest.MediaStoreService;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TrainerInfoFragment extends Fragment {

    public static String REST_END_POINT = "https://learnxiny-mediastore.herokuapp.com/trainers";

    Subscription subscription;

    @Bind(R.id.ivTrainerPhoto) ImageView mImageViewTrainerPhoto;
    @Bind(R.id.tvTrainerInfo1) TextView mTextViewTrainerInfo1;
    @Bind(R.id.tvTrainerInfo2) TextView mTextViewTrainerInfo2;
    @Bind(R.id.tvTrainerInfo3) TextView mTextViewTrainerInfo3;
    @Bind(R.id.tvTrainerInfo4) TextView mTextViewTrainerInfo4;
    @Bind(R.id.tvTrainerAddress) TextView mTextViewTrainerAddress;
    @Bind(R.id.tvTrainerCall) TextView mTextViewTrainerCall;
    public Trainer mTrainer;

    public static TrainerInfoFragment newInstance(Trainer trainer) {
        TrainerInfoFragment trainerInfoFragment = new TrainerInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("trainer", trainer);
        trainerInfoFragment.setArguments(args);

        //TODO remove this?
        //trainerInfoFragment.mTrainer = trainer;

        return trainerInfoFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
         super.onCreateView(inflater, parent, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_trainer_info, parent, false);
        ButterKnife.bind(this, view);
        //TODO This is is weird, do we already have all the trainer info here?
        Trainer trainer = getArguments().getParcelable("trainer");

        trainer = getTrainer(trainer.getId());


        return view;
    }

    private void setupTrainerDetailView(Trainer trainer) {

        mTextViewTrainerInfo1.setText(trainer.getTrainerParams().getSpeciality());
        mTextViewTrainerInfo2.setText(trainer.getTrainerParams().getYrsOfTraining());
        mTextViewTrainerInfo3.setText(trainer.getTrainerParams().getWeight());
        mTextViewTrainerInfo4.setText(trainer.getTrainerParams().getHeight());

        Glide.with(getActivity())
        .load(trainer.getProfileUrl())
                .into(mImageViewTrainerPhoto);

        mTextViewTrainerAddress.setText(trainer.getAddress());
        mTextViewTrainerCall.setText(trainer.getPhone());

    }

    public Trainer getTrainer(String trainerId) {
        Observable<Trainer> call = MediaStoreService.trainersStore.getTrainer(trainerId);
        subscription = call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Trainer>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        if (e instanceof HttpException) {
                            HttpException response = (HttpException) e;
                            int code = response.code();
                        }
                    }

                    @Override
                    public void onNext(Trainer t) {
                        Log.i("TrainerInfoFragment", t.getName());
                        mTrainer = t;
                    }
                });


        return  mTrainer;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.subscription.unsubscribe();
    }
}
