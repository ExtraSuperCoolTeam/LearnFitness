package com.codepath.apps.learnfitness.fragments;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.Trainer;
import com.codepath.apps.learnfitness.rest.MediaStoreService;
import com.loopj.android.http.AsyncHttpClient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        Observable<Trainer> call = MediaStoreService.trainersStore.getTrainer("1");
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
                public void onNext(Trainer trainer) {
                    Log.i("TrainerInfoFragment", trainer.getName());
                }
            });

//        Observable<List<Trainer>> call2 = MediaStoreService.trainersStore.fetchTrainers();
//        subscription = call2
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Trainer>>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        // cast to retrofit.HttpException to get the response code
//                        if (e instanceof HttpException) {
//                            HttpException response = (HttpException) e;
//                            int code = response.code();
//                        }
//                    }
//
//                    @Override
//                    public void onNext(List<Trainer> trainers) {
//                        Log.i("TrainerInfoFragment",  "" + trainers.size());
//                    }
//                });


        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

//        asyncHttpClient.get(url, new TextHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String response) {
//
//                try {
//                    if (response != null) {
//                        Gson gson = new GsonBuilder().serializeNulls().create();
//                        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
//                        if (jsonObject != null) {
//                            if(jsonObject.has("trainerParams")) {
//                                JsonObject jsonTrainerParamsObject =
//                                        jsonObject.getAsJsonObject("trainerParams");
//
//                                List<String> trainerInfoList = new ArrayList<String>();
//                                if (jsonTrainerParamsObject != null) {
//                                    for (Map.Entry<String, JsonElement> entry :
//                                            jsonTrainerParamsObject.entrySet()) {
//
//                                        trainerInfoList.add(entry.getKey() +
//                                                " : " + entry.getValue());
//                                        Log.i("SearchActivity", "key: " + entry.getKey() + " value: " +
//                                                entry.getValue());
//                                    }
//
//                                    mTextViewTrainerInfo1.setText(trainerInfoList.get(0));
//                                    mTextViewTrainerInfo2.setText(trainerInfoList.get(1));
//                                    mTextViewTrainerInfo3.setText(trainerInfoList.get(2));
//                                    mTextViewTrainerInfo4.setText(trainerInfoList.get(3));
//                                }
//                            }
//
//                            if (jsonObject.has("profileUrl")) {
//                                Glide.with(getActivity())
//                                        .load(jsonObject.get("profileUrl").getAsString())
//                                        .into(mImageViewTrainerPhoto);
//                            }
//
//                            if (jsonObject.has("address")) {
//                                mTextViewTrainerAddress.setText(jsonObject.get("address").getAsString());
//                            }
//
//                            if (jsonObject.has("phone")) {
//                                mTextViewTrainerCall.setText(jsonObject.get("phone").getAsString());
//                            }
//                        }
//                    }
//                } catch (JsonSyntaxException e) {
//                    Log.w("AsyncHttpClient", "Exception while parsing json " + e.getMessage());
//                    Toast.makeText(getContext(), "Opps looks like " +
//                                    "some problem, try again",
//                            Toast.LENGTH_SHORT).show();
//
//                }
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String response,
//                                  Throwable throwable) {
//                Log.w("AsyncHttpClient", "HTTP Request failure: " + statusCode + " " +
//                        throwable.getMessage());
//            }
//        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.subscription.unsubscribe();
    }
}
