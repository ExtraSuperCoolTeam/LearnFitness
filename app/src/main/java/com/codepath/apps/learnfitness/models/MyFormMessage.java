package com.codepath.apps.learnfitness.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by spandhare on 3/19/16.
 */
public class MyFormMessage implements Parcelable {

    String id;
    String weekTitle;
    String weekNumber;
    String message = "";
    String videoId;
    String timeStamp;
    String numberOfReplies;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeekTitle() {
        return weekTitle;
    }

    public void setWeekTitle(String weekTitle) {
        this.weekTitle = weekTitle;
    }

    public String getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(String weekNumber) {
        this.weekNumber = weekNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNumberOfReplies() {
        return numberOfReplies;
    }

    public void setNumberOfReplies(String numberOfReplies) {
        this.numberOfReplies = numberOfReplies;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.weekTitle);
        dest.writeString(this.weekNumber);
        dest.writeString(this.message);
        dest.writeString(this.videoId);
        dest.writeString(this.timeStamp);
        dest.writeString(this.numberOfReplies);
    }

    public MyFormMessage() {
    }

    protected MyFormMessage(Parcel in) {
        this.id = in.readString();
        this.weekTitle = in.readString();
        this.weekNumber = in.readString();
        this.message = in.readString();
        this.videoId = in.readString();
        this.timeStamp = in.readString();
        this.numberOfReplies = in.readString();
    }

    public static final Parcelable.Creator<MyFormMessage> CREATOR = new Parcelable.Creator<MyFormMessage>() {
        public MyFormMessage createFromParcel(Parcel source) {
            return new MyFormMessage(source);
        }

        public MyFormMessage[] newArray(int size) {
            return new MyFormMessage[size];
        }
    };
}
