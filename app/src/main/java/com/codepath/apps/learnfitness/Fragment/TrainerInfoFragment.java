package com.codepath.apps.learnfitness.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.Trainer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by spandhare on 3/3/16.
 */
public class TrainerInfoFragment extends Fragment {

    public static String REST_END_POINT = "https://learnxiny-mediastore.herokuapp.com/trainers";

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
        trainerInfoFragment.mTrainer = trainer;
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

        String url = REST_END_POINT + "/1";
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        asyncHttpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {

                try {
                    if (response != null) {
                        Gson gson = new GsonBuilder().serializeNulls().create();
                        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                        if (jsonObject != null) {
                            if(jsonObject.has("trainerParams")) {
                                JsonObject jsonTrainerParamsObject =
                                        jsonObject.getAsJsonObject("trainerParams");

                                List<String> trainerInfoList = new ArrayList<String>();
                                if (jsonTrainerParamsObject != null) {
                                    for (Map.Entry<String, JsonElement> entry :
                                            jsonTrainerParamsObject.entrySet()) {

                                        trainerInfoList.add(entry.getKey() +
                                                " : " + entry.getValue());
                                        Log.i("SearchActivity", "key: " + entry.getKey() + " value: " +
                                                entry.getValue());
                                    }

                                    mTextViewTrainerInfo1.setText(trainerInfoList.get(0));
                                    mTextViewTrainerInfo2.setText(trainerInfoList.get(1));
                                    mTextViewTrainerInfo3.setText(trainerInfoList.get(2));
                                    mTextViewTrainerInfo4.setText(trainerInfoList.get(3));
                                }
                            }

                            if (jsonObject.has("profileUrl")) {
                                Glide.with(getActivity())
                                        .load(jsonObject.get("profileUrl").getAsString())
                                        .into(mImageViewTrainerPhoto);
                            }

                            if (jsonObject.has("address")) {
                                mTextViewTrainerAddress.setText(jsonObject.get("address").getAsString());
                            }

                            if (jsonObject.has("phone")) {
                                mTextViewTrainerCall.setText(jsonObject.get("phone").getAsString());
                            }
                        }
                    }
                } catch (JsonSyntaxException e) {
                    Log.w("AsyncHttpClient", "Exception while parsing json " + e.getMessage());
                    Toast.makeText(getContext(), "Opps looks like " +
                                    "some problem, try again",
                            Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String response,
                                  Throwable throwable) {
                Log.w("AsyncHttpClient", "HTTP Request failure: " + statusCode + " " +
                        throwable.getMessage());
            }
        });

        return view;
    }
}
