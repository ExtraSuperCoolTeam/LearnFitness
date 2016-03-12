package com.codepath.apps.learnfitness.models;

/**
 * Created by JaneChung on 3/6/16.
 */
public class Form {

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
}
