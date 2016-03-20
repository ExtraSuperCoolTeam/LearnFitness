package com.codepath.apps.learnfitness.util;

import com.codepath.apps.learnfitness.FitnessApplication;
import com.codepath.apps.learnfitness.activities.LessonListActivity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by spandhare on 3/20/16.
 */
public class LessonUtility {

    public static int getCurrentWeek() {
        int weekNumber =  1;
        SharedPreferences sharedPreferences = null;
        if (FitnessApplication.getAppContext() != null) {
            sharedPreferences =
                    FitnessApplication.getAppContext().getSharedPreferences(LessonListActivity.MY_SHARED_PREFS,
                            Context.MODE_PRIVATE);
        }

        if (sharedPreferences != null) {
            weekNumber = Integer.parseInt(sharedPreferences.
                            getString(LessonListActivity.CURRENT_WEEK_NUMBER, "1"));
        }
        return weekNumber;
    }

    public static int fromStringToInt(String input) {
        return Integer.parseInt(input);
    }
}
