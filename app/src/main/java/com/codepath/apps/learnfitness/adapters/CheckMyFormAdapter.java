package com.codepath.apps.learnfitness.adapters;

import com.bumptech.glide.Glide;
import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.TrainerReply;
import com.codepath.apps.learnfitness.util.TimeFormatUtility;

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
    private List<TrainerReply> mTrainerReplies;

    public CheckMyFormAdapter(List<TrainerReply> trainerReplies) {
        mTrainerReplies = trainerReplies;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvTrainerName)
        TextView mTextViewTrainerName;

        @Bind(R.id.tvTrainerHandle)
        TextView mTextViewTrainerHandle;

        @Bind(R.id.tvReplyTimeSend)
        TextView mTextViewReplyTimeSend;


        @Bind(R.id.ivTrainerProfileImage)
        ImageView mImageViewTrainerProfileImage;

        @Bind(R.id.tvSenderRepyComment)
        TextView mTextViewReplyText;

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

        View formView = inflater.inflate(R.layout.item_check_my_form_details, parent, false);

        ViewHolder viewHolder = new ViewHolder(formView, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder ( final ViewHolder holder, int position){
        Log.i(TAG, "Position is: " + position + "");
        TrainerReply trainerReply = mTrainerReplies.get(position);

        holder.mTextViewTrainerName.setText( trainerReply.getTrainer().getName());
        holder.mTextViewTrainerHandle.setText("@" + trainerReply.getTrainer().getHandle());
        holder.mTextViewReplyTimeSend.setText(
                TimeFormatUtility.getRelativeTimeFromTimesMillis(trainerReply.getFormMessageReply()
                .getTimeStamp()));

        String reply = trainerReply.getFormMessageReply().getFeedback();
        holder.mTextViewReplyText.setText(reply);

        Log.i(TAG, trainerReply.getTrainer().getProfileUrl());

        Glide.with(holder.mContext).load(trainerReply.getTrainer().getProfileUrl())
                .placeholder(R.drawable.rock_trainer)
                .error(R.drawable.rock_trainer)
                .into(holder.mImageViewTrainerProfileImage);
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, Integer.toString(mTrainerReplies.size()));
        return mTrainerReplies.size();
    }
}
