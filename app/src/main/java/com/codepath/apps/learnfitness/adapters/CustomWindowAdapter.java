package com.codepath.apps.learnfitness.adapters;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import com.bumptech.glide.Glide;
import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.Trainer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JaneChung on 3/5/16.
 */
public class CustomWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private static final String TAG = "CustomWindowAdapter";
    LayoutInflater mInflater;
    HashMap<String, Trainer> mTrainers;
    Context mContext;

    @Bind(R.id.tvTrainerName) TextView mTextViewTrainerName;
    @Bind(R.id.tvAddress) TextView mTextViewTrainerAddress;
    @Bind(R.id.ivTrainerThumbnail) ImageView mImageViewTrainerThumnnail;


    public CustomWindowAdapter(LayoutInflater i, HashMap<String, Trainer> trainers, Context context){
        mInflater = i;
        mTrainers = trainers;
        mContext = context;
    }

    // This defines the contents within the info window based on the marker
    @Override
    public View getInfoContents(Marker marker) {
        // Getting view from the layout file
        View view = mInflater.inflate(R.layout.custom_info_window, null);
        ButterKnife.bind(this,view);

        String key = marker.getTitle();
        Trainer trainer = mTrainers.get(key);

        mTextViewTrainerName.setText(trainer.getName());
        mTextViewTrainerAddress.setText(trainer.getAddress());

        Log.d(TAG, "PHOTO URL: " + trainer.getProfileUrl());
        mImageViewTrainerThumnnail.setImageResource(0);
        Glide.with(mContext).load(trainer.getProfileUrl()).placeholder(R.mipmap.ic_wifi)
                .into(mImageViewTrainerThumnnail);

        // Return info window contents
        return view;
    }

    // This changes the frame of the info window; returning null uses the default frame.
    // This is just the border and arrow surrounding the contents specified above
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }
}
