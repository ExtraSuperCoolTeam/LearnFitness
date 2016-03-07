package com.codepath.apps.learnfitness.adapters;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.Week;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by spandhare on 3/5/16.
 */
public class ExpandedWeekViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {


    private List<Week> mWeeks;
    private Context mContext;
    private LessonsAdapter.OnItemClickListener mOnItemClickListener;

    @Bind(R.id.ivImage) ImageView image;
    @Bind(R.id.tvTitle) TextView title;
    @Bind(R.id.tvDescription) TextView description;

    public ExpandedWeekViewHolder(Context context, LessonsAdapter.OnItemClickListener listener,
                                  View itemView, List<Week> weeks) {
        super(itemView);

        mContext = context;
        mOnItemClickListener = listener;
        mWeeks = weeks;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onClick(View v) {
        int position = getAdapterPosition();
        Week week = mWeeks.get(position);
        if (mOnItemClickListener != null)
            mOnItemClickListener.onItemClick(v, week);
    }
}
