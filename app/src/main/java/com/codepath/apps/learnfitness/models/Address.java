package com.codepath.apps.learnfitness.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dmccoy on 3/20/16.
 */
public class Address implements Parcelable {

    public String getFirstAddressLine() {
        return firstAddressLine;
    }

    public void setFirstAddressLine(String firstAddressLine) {
        this.firstAddressLine = firstAddressLine;
    }

    public String getSecondAddressLine() {
        return secondAddressLine;
    }

    public void setSecondAddressLine(String secondAddressLine) {
        this.secondAddressLine = secondAddressLine;
    }

    @SerializedName("firstAddressLine")
    @Expose
    private String firstAddressLine;

    @SerializedName("secondAddressLine")
    @Expose
    private String secondAddressLine;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstAddressLine);
        dest.writeString(this.secondAddressLine);
    }

    public Address() {
    }
}
