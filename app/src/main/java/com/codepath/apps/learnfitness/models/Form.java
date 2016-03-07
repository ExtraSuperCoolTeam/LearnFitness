package com.codepath.apps.learnfitness.models;

/**
 * Created by JaneChung on 3/6/16.
 */
public class Form {

    String lessonTitle;
    int lessonNumber;
    String feedback;
    String videoUrl;

    public String getLessonTitle() {
        return lessonTitle;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getVideoUrl() {
        return videoUrl;
    }


    //Todo get form from api
}
