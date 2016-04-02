package com.codepath.apps.learnfitness.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by spandhare on 3/20/16.
 */
public class TrainerReply implements Parcelable {
    FormMessageReply formMessageReply;
    Trainer trainer;

    public FormMessageReply getFormMessageReply() {
        return formMessageReply;
    }

    public void setFormMessageReply(FormMessageReply formMessageReply) {
        this.formMessageReply = formMessageReply;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.formMessageReply, flags);
        dest.writeParcelable(this.trainer, 0);
    }

    public TrainerReply() {
    }

    protected TrainerReply(Parcel in) {
        this.formMessageReply = in.readParcelable(FormMessageReply.class.getClassLoader());
        this.trainer = in.readParcelable(Trainer.class.getClassLoader());
    }

    public static final Parcelable.Creator<TrainerReply> CREATOR = new Parcelable.Creator<TrainerReply>() {
        public TrainerReply createFromParcel(Parcel source) {
            return new TrainerReply(source);
        }

        public TrainerReply[] newArray(int size) {
            return new TrainerReply[size];
        }
    };
}
