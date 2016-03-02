package com.codepath.apps.restclienttemplate.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Lesson;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JaneChung on 3/1/16.
 */
public class LessonsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Lesson> mLessons;
    private Context context;
    private int currentLesson;

    private final int CURRENT_LESSON = 0, LESSON = 1;

    public static SharedPreferences sharedPreferences;
    public static final String prefName = "MY_SHARED_PREFS";
    public static final String currentLessonNumber = "CURRENT_USER_LESSON_NUMBER";

    public LessonsAdapter(List<Lesson> lessons) {
        mLessons = lessons;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.ivImage)
        ImageView image;
        @Bind(R.id.tvTitle)
        TextView title;
        @Bind(R.id.tvDescription)
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int position = getAdapterPosition();
           // Intent i = new Intent(itemView.getContext(), LessonDetailActivity.class);

            Lesson lesson = mLessons.get(position);
//            i.putExtra("lesson", Parcels.wrap(article));

//            itemView.getContext().startActivity(i);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.tvWeek)
        TextView week;
        @Bind(R.id.tvLessonTitle)
        TextView lessonTitle;
        @Bind(R.id.ivThumbnail)
        ImageView thumbnail;

        public ViewHolder2(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int position = getAdapterPosition();
//            Intent i = new Intent(itemView.getContext(), LessonDetailActivity.class);

            Lesson article = mLessons.get(position);
//            i.putExtra("lesson", Parcels.wrap(article));
//
//            itemView.getContext().startActivity(i);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Lesson lesson = mLessons.get(position);
        int lessonNumber = lesson.getWeekNumber();
        if (currentLesson == lessonNumber) {
            return CURRENT_LESSON;
        } else {
            return LESSON;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder;
        sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        currentLesson = sharedPreferences.getInt(currentLessonNumber, 2);

        switch (viewType) {
            case CURRENT_LESSON:
                View articleView = inflater.inflate(R.layout.item_current_lesson, parent, false);
                viewHolder = new ViewHolder(articleView);
                break;
            case LESSON:
                View titleView = inflater.inflate(R.layout.item_lesson, parent, false);
                viewHolder = new ViewHolder2(titleView);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                viewHolder = new ViewHolder2(v);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Lesson lesson = mLessons.get(position);

        switch (holder.getItemViewType()) {
            case CURRENT_LESSON:
                ViewHolder v1 = (ViewHolder) holder;
                configureViewHolder(v1, lesson);
                break;
            case LESSON:
                ViewHolder2 v2 = (ViewHolder2) holder;
                configureViewHolder2(v2, lesson);
                break;
            default:
                ViewHolder2 v = (ViewHolder2) holder;
                configureViewHolder2(v, lesson);
                break;

        }

    }

    private void configureViewHolder(ViewHolder v1, Lesson lesson) {
        String imageURL = lesson.getImageUrl();
        ImageView iv = v1.image;
        iv.setImageResource(0);

        if (!TextUtils.isEmpty(imageURL)) {
            Glide.with(context).load(imageURL)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder_lesson)
                    .into(iv);
        }
        String title = lesson.getTitle();
        String number = Integer.toString(lesson.getWeekNumber());
        v1.title.setText("Week " + number + ": " + title);
        v1.description.setText(lesson.getDescription());
    }

    private void configureViewHolder2(ViewHolder2 v2, Lesson lesson) {
        String imageURL = lesson.getImageUrl();
        ImageView i = v2.thumbnail;
        i.setImageResource(0);

        if (!TextUtils.isEmpty(imageURL)) {
            Glide.with(context).load(imageURL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder_lesson)
                    .into(i);
        }

        v2.week.setText("Week " + Integer.toString(lesson.getWeekNumber()));
        v2.lessonTitle.setText(lesson.getTitle());
    }

    @Override
    public int getItemCount() {
        return mLessons.size();
    }
}
