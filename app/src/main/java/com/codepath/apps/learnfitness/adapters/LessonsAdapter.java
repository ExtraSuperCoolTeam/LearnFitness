package com.codepath.apps.learnfitness.adapters;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.Week;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by JaneChung on 3/1/16.
 */
public class LessonsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Week> mWeeks;
    private Context context;
    private final int CURRENT_LESSON = 0, LESSON = 1;

    public LessonsAdapter(List<Week> weeks) {
        mWeeks = weeks;
    }

//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        @Bind(R.id.ivImage) ImageView image;
//        @Bind(R.id.tvTitle) TextView title;
//        @Bind(R.id.tvDescription) TextView description;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            ButterKnife.bind(this, itemView);
//            itemView.setOnClickListener(this);
//        }
//
//        public void onClick(View v) {
//            int position = getAdapterPosition();
//           // Intent i = new Intent(itemView.getContext(), LessonDetailActivity.class);
//
//            Week week = mWeeks.get(position);
////            i.putExtra("lesson", Parcels.wrap(article));
//
////            itemView.getContext().startActivity(i);
//
//            WeekFragment fragment = WeekFragment.newInstance(week);
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.flContent, fragment);
//            ft.commit();
//
//        }
//    }

//    public class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        @Bind(R.id.tvWeek) TextView week;
//        @Bind(R.id.tvLessonTitle) TextView lessonTitle;
//        @Bind(R.id.ivThumbnail) ImageView thumbnail;
//
//        public ViewHolder2(View itemView) {
//            super(itemView);
//
//            ButterKnife.bind(this, itemView);
//            itemView.setOnClickListener(this);
//        }
//
//        public void onClick(View v) {
//            int position = getAdapterPosition();
////            Intent i = new Intent(itemView.getContext(), LessonDetailActivity.class);
//
//            Week week = mWeeks.get(position);
////            i.putExtra("lesson", Parcels.wrap(article));
////
////            itemView.getContext().startActivity(i);
//        }
//    }

    @Override
    public int getItemViewType(int position) {
        Week week = mWeeks.get(position);
        //String weekNumber = week.getWeekNumber();
        //if (currentLesson == Integer.parseInt(weekNumber)) {
        if (week.isCurrent()) {
            return CURRENT_LESSON;
        } else {
            return LESSON;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;
//        sharedPreferences = context.getSharedPreferences(LessonListActivity.MY_SHARED_PREFS,
//                Context.MODE_PRIVATE);
//        currentLesson = sharedPreferences.getInt(LessonListActivity.CURRENT_WEEK_NUMBER, 1);

        switch (viewType) {
            case CURRENT_LESSON:
                View expandedView = inflater.inflate(R.layout.item_current_lesson, parent, false);
                viewHolder = new ExpandedWeekViewHolder(context, listener, expandedView, mWeeks);
                break;
            case LESSON:
                View collapsedView = inflater.inflate(R.layout.item_lesson, parent, false);
                viewHolder = new CollapsedWeekViewHolder(context, listener, collapsedView, mWeeks);
                break;
            default:
                View view = inflater.inflate(R.layout.item_lesson, parent, false);
                viewHolder = new CollapsedWeekViewHolder(context, listener, view, mWeeks);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Week week = mWeeks.get(position);

        switch (holder.getItemViewType()) {
            case CURRENT_LESSON:
                ExpandedWeekViewHolder expandedWeekViewHolder = (ExpandedWeekViewHolder) holder;
                configureExpandedWeekViewHolder(expandedWeekViewHolder, week);
                break;
            case LESSON:
                CollapsedWeekViewHolder collapsedWeekViewHolder = (CollapsedWeekViewHolder) holder;
                configureCollapsedWeekViewHolder(collapsedWeekViewHolder, week);
                break;
            default:
                CollapsedWeekViewHolder defaultViewHolder = (CollapsedWeekViewHolder) holder;
                configureCollapsedWeekViewHolder(defaultViewHolder, week);
                break;

        }
    }

    private void configureExpandedWeekViewHolder(ExpandedWeekViewHolder expandedWeekViewHolder,
                                                 Week week) {
        String imageURL = week.getPhotoUrl();
        ImageView iv = expandedWeekViewHolder.image;
        iv.setImageResource(0);

        if (!TextUtils.isEmpty(imageURL)) {
            Glide.with(context).load(imageURL)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder_lesson)
                    .into(iv);
        }
        String title = week.getWeekTitle();
        String number = week.getWeekNumber();
        expandedWeekViewHolder.title.setText("Week " + number + ": " + title);
        expandedWeekViewHolder.description.setText(week.getShortDescription());
    }

    private void configureCollapsedWeekViewHolder(CollapsedWeekViewHolder collapsedWeekViewHolder,
                                                  Week week) {
        String imageURL = week.getPhotoUrl();
        ImageView i = collapsedWeekViewHolder.thumbnail;
        i.setImageResource(0);

        if (!TextUtils.isEmpty(imageURL)) {
            Glide.with(context).load(imageURL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder_lesson)
                    .into(i);
        }

        collapsedWeekViewHolder.week.setText("Week " + week.getWeekNumber());
        collapsedWeekViewHolder.lessonTitle.setText(week.getWeekTitle());
    }

    @Override
    public int getItemCount() {
        return mWeeks.size();
    }

    /***** Creating OnItemClickListener *****/

    // Define listener member variable
    private static OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, Week week);
        void onItemExpand(View itemView, Week week);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
