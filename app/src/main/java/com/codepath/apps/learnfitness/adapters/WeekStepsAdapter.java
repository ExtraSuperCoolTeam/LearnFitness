package com.codepath.apps.learnfitness.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.Step;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by spandhare on 3/5/16.
 */
public class WeekStepsAdapter extends RecyclerView.Adapter<WeekStepsAdapter.ViewHolder> {

    List<Step> mSteps;
    public WeekStepsAdapter(List<Step> steps) {
        mSteps = steps;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivWeekStepsImage) ImageView mImageViewWeekStepsImage;
        @Bind(R.id.tvWeekStepsTitle) TextView mTextViewWeekStepsTitle;
        @Bind(R.id.tvWeekStepsDescription) TextView mTextViewWeekStepsDescription;

        Context mContext;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            mContext = context;
            ButterKnife.bind(this, itemView);
        }
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public WeekStepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View weekStepView = inflater.inflate(R.layout.item_week_steps, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(weekStepView, context);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(WeekStepsAdapter.ViewHolder viewHolder, int position) {
        Step step = mSteps.get(position);

        //TODO remove hard coded strings
        viewHolder.mTextViewWeekStepsTitle.setText("Step " + step.getStepNumber() + " " +
                step.getStepTitle());
        viewHolder.mTextViewWeekStepsDescription.setText(step.getStepDescription());

        switch (position) {

            case 0:
                viewHolder.mImageViewWeekStepsImage.setImageResource(R.drawable.warrior_one);
                break;
            case 1:
                viewHolder.mImageViewWeekStepsImage.setImageResource(R.drawable.warrior_two);
                break;
            case 2:
                viewHolder.mImageViewWeekStepsImage.setImageResource(R.drawable.warrior_three);
                break;
            default:
                viewHolder.mImageViewWeekStepsImage.setImageResource(R.drawable.warrior_one);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }
}
