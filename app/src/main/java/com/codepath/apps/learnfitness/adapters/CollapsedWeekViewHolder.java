package com.codepath.apps.learnfitness.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.Week;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by spandhare on 3/5/16.
 */
public class CollapsedWeekViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    private List<Week> mWeeks;
    private Context mContext;
    private LessonsAdapter.OnItemClickListener mOnItemClickListener;

    @Bind(R.id.tvWeek) TextView week;
    @Bind(R.id.tvLessonTitle) TextView lessonTitle;
    @Bind(R.id.ivThumbnail) ImageView thumbnail;
    @Bind(R.id.ivCheckMark)
    ImageView mCheckMark;
    @Bind(R.id.rlLesson)
    RelativeLayout mLesson;

    public CollapsedWeekViewHolder(Context context, LessonsAdapter.OnItemClickListener listener,
                                   View itemView, List<Week> weeks) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        mContext = context;
        mOnItemClickListener = listener;
        mWeeks = weeks;
        itemView.setOnClickListener(this);
    }

    public void onClick(View v) {
        int position = getAdapterPosition();
        Week week = mWeeks.get(position);

        if (mOnItemClickListener != null)
            mOnItemClickListener.onItemExpand(v, week);
    }

}
