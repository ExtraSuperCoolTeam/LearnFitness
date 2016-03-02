package com.codepath.apps.restclienttemplate.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.codepath.apps.restclienttemplate.DeveloperKey;
import com.codepath.apps.restclienttemplate.R;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class LessonActivity extends YoutubeFailureRecoveryActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.pvYoutube);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo("D9BC884dIDg");
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.pvYoutube);
    }

}
