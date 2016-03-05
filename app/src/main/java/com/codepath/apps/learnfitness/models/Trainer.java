package com.codepath.apps.learnfitness.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by JaneChung on 3/5/16.
 */
public class Trainer {

    String name;
    String address;
    String photoUrl;
    String phoneNumber;
    String id;
    //Todo add trainderParams from Retrofit
    //Todo add longitude and Latitude for trainers

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getId() {
        return id;
    }


    public static Trainer fromJSONObject(JSONObject object) {
        Trainer trainer = new Trainer();
        try {
            trainer.id = object.getString("id");
            trainer.name = object.getString("name");
            trainer.address = object.getString("address");
            trainer.photoUrl = object.getString("profileUrl");
            trainer.phoneNumber = object.getString("phone");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trainer;
    }

    //TODO Fix the object to array

    public static HashMap<String, Trainer> fromJSONArray(JSONObject object) {
        HashMap<String, Trainer> trainers = new HashMap<>();

        //todo for loop
        Trainer trainer = fromJSONObject(object);
        trainers.put(trainer.getId(), trainer);

        return trainers;
    }





}
