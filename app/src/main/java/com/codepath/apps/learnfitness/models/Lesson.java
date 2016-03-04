package com.codepath.apps.learnfitness.models;

import java.util.ArrayList;

public class Lesson {
    String title;
    String description;
    String imageUrl;
    String videoUrl;
    String videoId;
    String textContent;
    int weekNumber;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTextContent() {
        return textContent;
    }

    public int getWeekNumber() {
        return weekNumber;
    }
    
    public static Lesson getExample() {
        Lesson newLesson =  new Lesson();
        newLesson.title = "Sample Lesson";
        newLesson.videoUrl = "asdf.com";
        newLesson.videoId = "D9BC884dIDg";
        newLesson.imageUrl = "http://gumbofitness.com/wp-content/uploads/2014/11/header-photo1.jpg";
//        newLesson.weekNumber = 1;
        newLesson.textContent = "Super awesome lesson content!. Step 1\n\nStep 2" +
                "fasdfjadsklfjaldkjf  lkasdjfj lkjsdlkfj jksldfjw lksjdf fjkdls" +
                "fasdfjadsklfjaldkjf  lkasdjfj lkjsdlkfj jksldfjw lksjdf fjkdls" +
                "fasdfjadsklfjaldkjf  lkasdjfj lkjsdlkfj jksldfjw lksjdf fjkdls" +
                "fasdfjadsklfjaldkjf  lkasdjfj lkjsdlkfj jksldfjw lksjdf fjkdls" +
                "fasdfjadsklfjaldkjf  lkasdjfj lkjsdlkfj jksldfjw lksjdf fjkdls" +
                "fasdfjadsklfjaldkjf  lkasdjfj lkjsdlkfj jksldfjw lksjdf fjkdls" +
                "fasdfjadsklfjaldkjf  lkasdjfj lkjsdlkfj jksldfjw lksjdf fjkdls" +
                "fasdfjadsklfjaldkjf  lkasdjfj lkjsdlkfj jksldfjw lksjdf fjkdls";
        newLesson.description = "This is a short description of the lesson!";

        return newLesson;
    }

    public static ArrayList<Lesson> getExampleList() {
        ArrayList<Lesson> lessons = new ArrayList<>();

        for (int i = 0 ; i < 8; i++) {
            Lesson l = getExample();
            l.weekNumber = i + 1;
            lessons.add(l);
        }

        return lessons;
    }

}
