
package com.codepath.apps.learnfitness.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

public class TrainerParams implements Parcelable {

    @SerializedName("speciality")
    @Expose
    private String speciality;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("yrsOfTraining")
    @Expose
    private String yrsOfTraining;
    @SerializedName("weight")
    @Expose
    private String weight;

    /**
     * 
     * @return
     *     The speciality
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * 
     * @param Speciality
     *     The speciality
     */
    public void setSpeciality(String Speciality) {
        this.speciality = Speciality;
    }

    /**
     * 
     * @return
     *     The height
     */
    public String getHeight() {
        return height;
    }

    /**
     * 
     * @param Height
     *     The height
     */
    public void setHeight(String Height) {
        this.height = Height;
    }

    /**
     * 
     * @return
     *     The yrsOfTraining
     */
    public String getYrsOfTraining() {
        return yrsOfTraining;
    }

    /**
     * 
     * @param YrsOfTraining
     *     The Yrs of Training
     */
    public void setYrsOfTraining(String YrsOfTraining) {
        this.yrsOfTraining = YrsOfTraining;
    }

    /**
     * 
     * @return
     *     The weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * 
     * @param Weight
     *     The weight
     */
    public void setWeight(String Weight) {
        this.weight = Weight;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.speciality);
        dest.writeString(this.height);
        dest.writeString(this.yrsOfTraining);
        dest.writeString(this.weight);
    }

    public TrainerParams() {
    }

    protected TrainerParams(Parcel in) {
        this.speciality = in.readString();
        this.height = in.readString();
        this.yrsOfTraining = in.readString();
        this.weight = in.readString();
    }

    public static final Parcelable.Creator<TrainerParams> CREATOR = new Parcelable.Creator<TrainerParams>() {
        public TrainerParams createFromParcel(Parcel source) {
            return new TrainerParams(source);
        }

        public TrainerParams[] newArray(int size) {
            return new TrainerParams[size];
        }
    };
}
