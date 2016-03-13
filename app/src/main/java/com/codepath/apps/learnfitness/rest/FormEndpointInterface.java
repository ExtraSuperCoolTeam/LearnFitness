package com.codepath.apps.learnfitness.rest;

import com.codepath.apps.learnfitness.models.Form;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by spandhare on 3/11/16.
 */
public interface FormEndpointInterface {
    @GET("forms")
    Observable<List<Form>> fetchFormMessages();

    @POST("forms")
    Observable<Form> postFormMessages(@Header("Content-Type") String applicationJson,
                                      @Body Form form);
}
