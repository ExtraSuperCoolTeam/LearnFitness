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

    @Override
    public int getItemViewType(int position) {
        Week week = mWeeks.get(position);
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
