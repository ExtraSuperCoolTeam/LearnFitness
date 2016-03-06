package com.codepath.apps.learnfitness.rest;

import com.codepath.apps.learnfitness.models.Trainer;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface TrainersEndpointInterface {

    @GET("trainers/{trainerId}")
    Observable<Trainer> getTrainer(@Path("trainerId") String trainerId);

    @GET("trainers")
    Observable<List<Trainer>> fetchTrainers();
}
