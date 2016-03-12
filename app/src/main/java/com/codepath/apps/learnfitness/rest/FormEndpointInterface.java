package com.codepath.apps.learnfitness.rest;

import com.codepath.apps.learnfitness.models.Form;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by spandhare on 3/11/16.
 */
public interface FormEndpointInterface {
    @GET("forms")
    Observable<List<Form>> fetchFormMessages();
}
