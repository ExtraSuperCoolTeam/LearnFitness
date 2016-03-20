package com.codepath.apps.learnfitness.rest;

import com.codepath.apps.learnfitness.models.MyFormMessage;
import com.codepath.apps.learnfitness.models.TrainerReply;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by spandhare on 3/19/16.
 */
public interface FormMessagesEndpointInterface {

    @GET("messages")
    Observable<List<MyFormMessage>> fetchFormMessageList();

    @POST("messages")
    Observable<MyFormMessage> postMyFormMessage(@Header("Content-Type") String applicationJson,
                                      @Body MyFormMessage myFormMessage);


    @GET("messages/{messageId}/replies")
    Observable<List<TrainerReply>>
            fetchFormMessageRepliesByMessageId(@Path("messageId") String messageId);
}
