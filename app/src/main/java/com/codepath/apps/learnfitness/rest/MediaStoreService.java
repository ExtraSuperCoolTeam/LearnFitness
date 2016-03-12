package com.codepath.apps.learnfitness.rest;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MediaStoreService {

    public static String REST_END_POINT = "https://learnxiny-mediastore.herokuapp.com";

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(REST_END_POINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    public static TrainersEndpointInterface trainersStore =
            retrofit.create(TrainersEndpointInterface.class);

    public static ContentEndpointInterface contentStore =
            retrofit.create(ContentEndpointInterface.class);

    public static FormEndpointInterface formsStore =
            retrofit.create(FormEndpointInterface.class);
}
