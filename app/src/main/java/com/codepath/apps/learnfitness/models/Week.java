
package com.codepath.apps.learnfitness.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class Week implements Parcelable {

    @SerializedName("weekTitle")
    @Expose
    private String weekTitle;
    @SerializedName("weekNumber")
    @Expose
    private String weekNumber;
    @SerializedName("photoUrl")
    @Expose
    private String photoUrl;
    @SerializedName("videoUrl")
    @Expose
    private String videoUrl;
    @SerializedName("videoId")
    @Expose
    private String videoId;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("longDescription")
    @Expose
    private String longDescription;
    @SerializedName("steps")
    @Expose
    private List<Step> steps;
    @SerializedName("duration")
    @Expose
    private String videoDuration;

    @SerializedName("isCurrent")
    @Expose
    private boolean isCurrent;

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getVideoDuration() {
        if (videoDuration == null) {
            return "1:54";
        }
        return videoDuration;
    }

    /**
     *
     * @return
     *     The weekTitle
     */
    public String getWeekTitle() {
        return weekTitle;
    }

    /**
     *
     * @param weekTitle
     *     The weekTitle
     */
    public void setWeekTitle(String weekTitle) {
        this.weekTitle = weekTitle;
    }

    /**
     *
     * @return
     *     The weekNumber
     */
    public String getWeekNumber() {
        return weekNumber;
    }

    /**
     *
     * @param weekNumber
     *     The weekNumber
     */
    public void setWeekNumber(String weekNumber) {
        this.weekNumber = weekNumber;
    }

    /**
     *
     * @return
     *     The photoUrl
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     *
     * @param photoUrl
     *     The photoUrl
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     *
     * @return
     *     The videoUrl
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     *
     * @param videoUrl
     *     The videoUrl
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     *
     * @return
     *     The videoId
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     *
     * @param videoId
     *     The videoId
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     *
     * @return
     *     The shortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     *
     * @param shortDescription
     *     The shortDescription
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     *
     * @return
     *     The longDescription
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     *
     * @param longDescription
     *     The longDescription
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    /**
     *
     * @return
     *     The steps
     */
    public List<Step> getSteps() {
        return steps;
    }

    /**
     *
     * @param steps
     *     The steps
     */
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.weekTitle);
        dest.writeString(this.weekNumber);
        dest.writeString(this.photoUrl);
        dest.writeString(this.videoUrl);
        dest.writeString(this.videoId);
        dest.writeString(this.shortDescription);
        dest.writeString(this.longDescription);
        dest.writeTypedList(this.steps);
        dest.writeByte(isCurrent ? (byte) 1 : (byte) 0);
    }

    public Week() {
    }

    protected Week(Parcel in) {
        this.weekTitle = in.readString();
        this.weekNumber = in.readString();
        this.photoUrl = in.readString();
        this.videoUrl = in.readString();
        this.videoId = in.readString();
        this.shortDescription = in.readString();
        this.longDescription = in.readString();
        in.readList(this.steps, List.class.getClassLoader());
        this.isCurrent = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Week> CREATOR = new Parcelable.Creator<Week>() {
        public Week createFromParcel(Parcel source) {
            return new Week(source);
        }

        public Week[] newArray(int size) {
            return new Week[size];
        }
    };

    @Override
    public String toString() {
        return "Week: " + weekTitle;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.weekTitle.equals(((Week) obj).weekTitle)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(this.getWeekNumber()) * (this.weekTitle.hashCode());
    }
}
