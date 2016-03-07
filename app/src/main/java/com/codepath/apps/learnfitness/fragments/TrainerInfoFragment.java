package com.codepath.apps.learnfitness.fragments;

import com.bumptech.glide.Glide;
import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.Trainer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import rx.Subscription;

public class TrainerInfoFragment extends Fragment {
    Subscription subscription;

    @Bind(R.id.ivTrainerPhoto) ImageView mImageViewTrainerPhoto;
    @Bind(R.id.tvTrainerSpeciality) TextView mTextViewTrainerSpeciality;
    @Bind(R.id.tvTrainerExperience) TextView mTextViewTrainerExperience;
    @Bind(R.id.tvTrainerWeight) TextView mTextViewTrainerWeight;
    @Bind(R.id.tvTrainerHeight) TextView mTextViewTrainerHeight;
    @Bind(R.id.tvTrainerAddress) TextView mTextViewTrainerAddress;
    @Bind(R.id.tvTrainerCall) TextView mTextViewTrainerCall;
    public Trainer mTrainer;

    public static TrainerInfoFragment newInstance(Trainer trainer) {
        TrainerInfoFragment trainerInfoFragment = new TrainerInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("trainer", trainer);
        trainerInfoFragment.setArguments(args);

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
        Trainer trainer = getArguments().getParcelable("trainer");

        setupTrainerDetailView(trainer);
        return view;
    }

    private void setupTrainerDetailView(Trainer trainer) {

        String speciality = mTextViewTrainerSpeciality.getText() + " " +
                trainer.getTrainerParams().getSpeciality();
        String experience = mTextViewTrainerExperience.getText() + " " +
                trainer.getTrainerParams().getYrsOfTraining();
        String weight = mTextViewTrainerWeight.getText() + " " +
                trainer.getTrainerParams().getWeight();
        String height = mTextViewTrainerHeight.getText() + " " +
                trainer.getTrainerParams().getHeight();

        mTextViewTrainerSpeciality.setText(speciality);
        mTextViewTrainerExperience.setText(experience);
        mTextViewTrainerWeight.setText(weight);
        mTextViewTrainerHeight.setText(height);

        Glide.with(getActivity())
        .load(trainer.getProfileUrl()).placeholder(R.mipmap.ic_wifi)
                .into(mImageViewTrainerPhoto);

        mTextViewTrainerAddress.setText(trainer.getAddress());
        mTextViewTrainerCall.setText(trainer.getPhone());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (subscription != null) {
            this.subscription.unsubscribe();
        }
    }
}
