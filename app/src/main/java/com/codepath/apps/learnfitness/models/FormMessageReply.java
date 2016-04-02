package com.codepath.apps.learnfitness.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by spandhare on 3/20/16.
 */
public class FormMessageReply implements Parcelable {
    String id;
    String messageId;
    String feedback = "";
    String timeStamp;
    String trainerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.messageId);
        dest.writeString(this.feedback);
        dest.writeString(this.timeStamp);
        dest.writeString(this.trainerId);
    }

    public FormMessageReply() {
    }

    protected FormMessageReply(Parcel in) {
        this.id = in.readString();
        this.messageId = in.readString();
        this.feedback = in.readString();
        this.timeStamp = in.readString();
        this.trainerId = in.readString();
    }

    public static final Parcelable.Creator<FormMessageReply> CREATOR = new Parcelable.Creator<FormMessageReply>() {
        public FormMessageReply createFromParcel(Parcel source) {
            return new FormMessageReply(source);
        }

        public FormMessageReply[] newArray(int size) {
            return new FormMessageReply[size];
        }
    };
}
