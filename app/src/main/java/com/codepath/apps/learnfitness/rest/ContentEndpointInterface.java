package com.codepath.apps.learnfitness.rest;

import com.codepath.apps.learnfitness.models.Lesson;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by spandhare on 3/5/16.
 */
public interface ContentEndpointInterface {
    @GET("contents")
    Observable<Lesson> fetchContent();
}
