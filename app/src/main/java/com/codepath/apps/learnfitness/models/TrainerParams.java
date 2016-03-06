
package com.codepath.apps.learnfitness.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

public class TrainerParams implements Parcelable {

    @SerializedName("Speciality")
    @Expose
    private String Speciality;
    @SerializedName("Height")
    @Expose
    private String Height;
    @SerializedName("Yrs of Training")
    @Expose
    private String YrsOfTraining;
    @SerializedName("Weight")
    @Expose
    private String Weight;

    /**
     * 
     * @return
     *     The Speciality
     */
    public String getSpeciality() {
        return Speciality;
    }

    /**
     * 
     * @param Speciality
     *     The Speciality
     */
    public void setSpeciality(String Speciality) {
        this.Speciality = Speciality;
    }

    /**
     * 
     * @return
     *     The Height
     */
    public String getHeight() {
        return Height;
    }

    /**
     * 
     * @param Height
     *     The Height
     */
    public void setHeight(String Height) {
        this.Height = Height;
    }

    /**
     * 
     * @return
     *     The YrsOfTraining
     */
    public String getYrsOfTraining() {
        return YrsOfTraining;
    }

    /**
     * 
     * @param YrsOfTraining
     *     The Yrs of Training
     */
    public void setYrsOfTraining(String YrsOfTraining) {
        this.YrsOfTraining = YrsOfTraining;
    }

    /**
     * 
     * @return
     *     The Weight
     */
    public String getWeight() {
        return Weight;
    }

    /**
     * 
     * @param Weight
     *     The Weight
     */
    public void setWeight(String Weight) {
        this.Weight = Weight;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Speciality);
        dest.writeString(this.Height);
        dest.writeString(this.YrsOfTraining);
        dest.writeString(this.Weight);
    }

    public TrainerParams() {
    }

    protected TrainerParams(Parcel in) {
        this.Speciality = in.readString();
        this.Height = in.readString();
        this.YrsOfTraining = in.readString();
        this.Weight = in.readString();
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
