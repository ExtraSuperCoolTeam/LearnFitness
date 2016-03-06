
package com.codepath.apps.learnfitness.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Lesson implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    @SerializedName("numberOfWeeks")
    @Expose
    private String numberOfWeeks;

    @SerializedName("weeks")
    @Expose
    private List<Week> weeks = new ArrayList<Week>();

    /**
     *
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     *     The numberOfWeeks
     */
    public String getNumberOfWeeks() {
        return numberOfWeeks;
    }

    /**
     *
     * @param numberOfWeeks
     *     The numberOfWeeks
     */
    public void setNumberOfWeeks(String numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }

    /**
     *
     * @return
     *     The weeks
     */
    public List<Week> getWeeks() {
        return weeks;
    }

    /**
     *
     * @param weeks
     *     The weeks
     */
    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.imageUrl);
        dest.writeString(this.numberOfWeeks);
        dest.writeList(this.weeks);
    }

    public Lesson() {
    }

    protected Lesson(Parcel in) {
        this.title = in.readString();
        this.imageUrl = in.readString();
        this.numberOfWeeks = in.readString();
        this.weeks = new ArrayList<Week>();
        in.readList(this.weeks, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<Lesson> CREATOR = new Parcelable.Creator<Lesson>() {
        public Lesson createFromParcel(Parcel source) {
            return new Lesson(source);
        }

        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };
}
