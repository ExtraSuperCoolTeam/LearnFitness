package com.codepath.apps.learnfitness.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JaneChung on 3/6/16.
 */
public class Form implements Parcelable {

    String id;
    String trainerId;
    String weekTitle;
    String weekNumber;
    String feedback = "";
    String message = "";

    String videoId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getWeekTitle() {
        return weekTitle;
    }

    public String getWeekNumber() {
        return weekNumber;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setWeekTitle(String weekTitle) {
        this.weekTitle = weekTitle;
    }

    public void setWeekNumber(String weekNumber) {
        this.weekNumber = weekNumber;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    //Todo get form from api

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.trainerId);
        dest.writeString(this.weekTitle);
        dest.writeString(this.weekNumber);
        dest.writeString(this.feedback);
        dest.writeString(this.message);
        dest.writeString(this.videoId);
    }

    public Form() {
    }

    protected Form(Parcel in) {
        this.id = in.readString();
        this.trainerId = in.readString();
        this.weekTitle = in.readString();
        this.weekNumber = in.readString();
        this.feedback = in.readString();
        this.message = in.readString();
        this.videoId = in.readString();
    }

    public static final Parcelable.Creator<Form> CREATOR = new Parcelable.Creator<Form>() {
        public Form createFromParcel(Parcel source) {
            return new Form(source);
        }

        public Form[] newArray(int size) {
            return new Form[size];
        }
    };
}
