package com.codepath.apps.restclienttemplate.models;

import java.util.ArrayList;
import java.util.List;

public class Lesson {
    String title;
    String description;
    String imageUrl;
    String videoUrl;
    String textContent;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getTextContent() {
        return textContent;
    }
    
    public static Lesson getExample() {
        Lesson newLesson =  new Lesson();
        newLesson.title = "Sample Lesson";
        newLesson.videoUrl = "asdf.com";
        newLesson.imageUrl = "http://gumbofitness.com/wp-content/uploads/2014/11/header-photo1.jpg";
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
            lessons.add(getExample());
        }

        return lessons;
    }

}
