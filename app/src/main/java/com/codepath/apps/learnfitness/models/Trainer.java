package com.codepath.apps.learnfitness.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;

public class Trainer implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("profileUrl")
    @Expose
    private String profileUrl;
    @SerializedName("trainerParams")
    @Expose
    private TrainerParams trainerParams;
    @SerializedName("location")
    @Expose
    private Location location;


    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */

    //Todo add trainderParams from Retrofit
    //Todo add longitude and Latitude for trainers

    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone
     *     The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 
     * @return
     *     The profileUrl
     */
    public String getProfileUrl() {
        return profileUrl;
    }

    /**
     * 
     * @param profileUrl
     *     The profileUrl
     */
    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    /**
     * 
     * @return
     *     The trainerParams
     */
    public TrainerParams getTrainerParams() {
        return trainerParams;
    }

    /**
     * 
     * @param trainerParams
     *     The trainerParams
     */
    public void setTrainerParams(TrainerParams trainerParams) {
        this.trainerParams = trainerParams;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.phone);
        dest.writeString(this.profileUrl);
        dest.writeParcelable(this.trainerParams, flags);
        dest.writeParcelable(this.location, flags);
    }

    public Trainer() {
    }

    protected Trainer(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
        this.profileUrl = in.readString();
        this.trainerParams = in.readParcelable(TrainerParams.class.getClassLoader());
        this.location = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Parcelable.Creator<Trainer> CREATOR = new Parcelable.Creator<Trainer>() {
        public Trainer createFromParcel(Parcel source) {
            return new Trainer(source);
        }

        public Trainer[] newArray(int size) {
            return new Trainer[size];
        }
    };

    public static Trainer fromJSONObject(JSONObject object) {
        Trainer trainer = new Trainer();
        try {
            trainer.id = object.getString("id");
            trainer.name = object.getString("name");
            trainer.address = object.getString("address");
            trainer.profileUrl = object.getString("profileUrl");
            trainer.phone = object.getString("phone");

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

    public static HashMap<String, Trainer> mapTrainerIdToTrainer(List<Trainer> trainers) {
        HashMap<String, Trainer> trainerMap = new HashMap<>();

        int i = 0;
        for (Trainer trainer:trainers) {
            trainerMap.put(trainer.getId(), trainer);

            break;
        }
        return trainerMap;
    }
}
