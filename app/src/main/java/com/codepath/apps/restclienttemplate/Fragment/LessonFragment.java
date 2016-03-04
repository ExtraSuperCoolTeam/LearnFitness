package com.codepath.apps.restclienttemplate.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.DeveloperKey;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Lesson;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LessonFragment extends Fragment implements YouTubePlayer.OnInitializedListener {
    private static final String TAG = "LessonFragment";

    @Bind(R.id.ivImage)
    ImageView ivImage;

    @Bind(R.id.tvTextContent)
    TextView tvTextContent;

    Lesson mLesson;

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lesson_fragment, container, false);
        ButterKnife.bind(this, v);

        mLesson = Lesson.getExample();

        // Update the text content and image.
        tvTextContent.setText(mLesson.getTextContent());
        Glide.with(getActivity()).load(mLesson.getImageUrl()).into(ivImage);

        // Switch in a new YouTubePlayerSupportFragment
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.flVideo, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(DeveloperKey.DEVELOPER_KEY, this);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer,
                                        boolean wasRestored) {
        Toast.makeText(getActivity(), "OMG", Toast.LENGTH_LONG).show();
        if (!wasRestored) {
            youTubePlayer.cueVideo(mLesson.getVideoId());
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
