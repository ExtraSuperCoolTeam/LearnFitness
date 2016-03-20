package com.codepath.apps.learnfitness.adapters;

import com.bumptech.glide.Glide;
import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.TrainerReply;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JaneChung on 3/6/16.
 */
public class CheckMyFormAdapter extends RecyclerView.Adapter<CheckMyFormAdapter.ViewHolder> {
    private static final String TAG = "CheckMyFormAdapter";
    private List<TrainerReply> mForms;

    public CheckMyFormAdapter(List<TrainerReply> forms) {
        mForms = forms;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ivSenderTrainer)
        ImageView mImageViewSenderTrainer;
        @Bind(R.id.tvFormLessonTitle)
        TextView lessonTitle;
        @Bind(R.id.tvSenderRepyComment)
        TextView description;

        Context mContext;

        public ViewHolder(View itemView,  Context context) {
            super(itemView);
            mContext = context;
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View formView = inflater.inflate(R.layout.item_forms, parent, false);

        ViewHolder viewHolder = new ViewHolder(formView, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder ( final ViewHolder holder, int position){
        TrainerReply trainerReply = mForms.get(position);

        //String lesson = form.getWeekTitle();
        String description = trainerReply.getFormMessageReply().getFeedback();

        Log.i(TAG, trainerReply.getTrainer().getProfileUrl());
        //holder.lessonTitle.setText(lesson);
        holder.description.setText(description);

        //TODO Need to change backend to include trainer with form reply
        Glide.with(holder.mContext).load(trainerReply.getTrainer().getProfileUrl()).placeholder(R.drawable.rock_trainer).error(R.drawable.rock_trainer)
                .into(holder.mImageViewSenderTrainer);
    }

    @Override
    public int getItemCount() {
        Log.i("CheckMyFormAdapter", Integer.toString(mForms.size()));
        return mForms.size();
    }
}
