package com.codepath.apps.learnfitness.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by JaneChung on 3/22/16.
 */
public class Utils {

    public static void hideKeyboard(Activity activity) {
        if (activity == null)
            return;

        View view = activity.getCurrentFocus();
        if (view == null)
            view = new View(activity);

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


    }
}
