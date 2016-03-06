
package com.codepath.apps.learnfitness.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;


public class Step implements Parcelable {

    @SerializedName("stepNumber")
    @Expose
    private String stepNumber;
    @SerializedName("stepImageUrl")
    @Expose
    private String stepImageUrl;
    @SerializedName("stepTitle")
    @Expose
    private String stepTitle;
    @SerializedName("stepDescription")
    @Expose
    private String stepDescription;

    /**
     * 
     * @return
     *     The stepNumber
     */
    public String getStepNumber() {
        return stepNumber;
    }

    /**
     * 
     * @param stepNumber
     *     The stepNumber
     */
    public void setStepNumber(String stepNumber) {
        this.stepNumber = stepNumber;
    }

    /**
     * 
     * @return
     *     The stepImageUrl
     */
    public String getStepImageUrl() {
        return stepImageUrl;
    }

    /**
     * 
     * @param stepImageUrl
     *     The stepImageUrl
     */
    public void setStepImageUrl(String stepImageUrl) {
        this.stepImageUrl = stepImageUrl;
    }

    /**
     * 
     * @return
     *     The stepTitle
     */
    public String getStepTitle() {
        return stepTitle;
    }

    /**
     * 
     * @param stepTitle
     *     The stepTitle
     */
    public void setStepTitle(String stepTitle) {
        this.stepTitle = stepTitle;
    }

    /**
     * 
     * @return
     *     The stepDescription
     */
    public String getStepDescription() {
        return stepDescription;
    }

    /**
     * 
     * @param stepDescription
     *     The stepDescription
     */
    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stepNumber);
        dest.writeString(this.stepImageUrl);
        dest.writeString(this.stepTitle);
        dest.writeString(this.stepDescription);
    }

    public Step() {
    }

    protected Step(Parcel in) {
        this.stepNumber = in.readString();
        this.stepImageUrl = in.readString();
        this.stepTitle = in.readString();
        this.stepDescription = in.readString();
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
