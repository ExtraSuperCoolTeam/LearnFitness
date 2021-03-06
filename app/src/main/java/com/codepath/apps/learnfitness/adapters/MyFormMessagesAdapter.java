package com.codepath.apps.learnfitness.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.MyFormMessage;
import com.codepath.apps.learnfitness.util.TimeFormatUtility;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by spandhare on 3/19/16.
 */
public class MyFormMessagesAdapter extends RecyclerView.Adapter<MyFormMessagesAdapter.ViewHolder> {

    private static final String TAG = "MyFormMessagesAdapter";
    private static final String YOUTUBE_VIDEO_THUMBNAIL_URL = "http://img.youtube.com/vi/";
    private List<MyFormMessage> mMyFormMessages;

    public MyFormMessagesAdapter(List<MyFormMessage> formMessages) {
        mMyFormMessages = formMessages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        @Bind(R.id.tvAboutWeekInfo)
        TextView mTextViewAboutWeekInfo;

        @Bind(R.id.tvTimeSend)
        TextView mTextViewTimeSend;

        @Bind(R.id.tvMyMessage)
        TextView mTextViewMyMessageText;

        @Bind(R.id.ivMyMessageImage)
        ImageView mImageViewMyMessageImage;

        @Bind(R.id.tvMessageReplies)
        TextView mTextViewMessageReplies;

        Context mContext;
        List<MyFormMessage> mMyFormMessages;


        private MyFormMessagesAdapter.OnItemClickListener mOnItemClickListener;

        public ViewHolder(View itemView, Context context,
                          MyFormMessagesAdapter.OnItemClickListener listener,
                          List<MyFormMessage> messages) {
            super(itemView);
            mContext = context;
            ButterKnife.bind(this, itemView);

            mMyFormMessages = messages;
            mOnItemClickListener = listener;

            itemView.setOnClickListener(this);

//            RelativeLayout rlCard = (RelativeLayout) itemView.findViewById(R.id.rlMessageItem);
//
//            int[] attrs = new int[]{R.attr.selectableItemBackground, R.color.white};
//            TypedArray typedArray = context.obtainStyledAttributes(attrs);
//            int backgroundResource = typedArray.getResourceId(0, 0);
//
//            rlCard.setBackgroundResource(backgroundResource);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            MyFormMessage myFormMessage = mMyFormMessages.get(position);
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, myFormMessage);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View formView = inflater.inflate(R.layout.item_form_my_message, parent, false);

        ViewHolder viewHolder = new ViewHolder(formView, context, listener, mMyFormMessages);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyFormMessage myFormMessage = mMyFormMessages.get(position);

        String weekTitle = myFormMessage.getWeekTitle();
        String myFormMessageText = myFormMessage.getMessage();
        String myFormVideoId = myFormMessage.getVideoId();
        String myFormTimeSend = myFormMessage.getTimeStamp();
        String myFormMessageReplies = myFormMessage.getNumberOfReplies();

        Log.i(TAG, myFormMessageText);

        holder.mTextViewAboutWeekInfo.setText(weekTitle);
        holder.mTextViewMyMessageText.setText(myFormMessageText);
        holder.mTextViewTimeSend.setText(TimeFormatUtility.getRelativeTimeFromTimesMillis(myFormTimeSend));

        holder.mTextViewMessageReplies.setText(myFormMessageReplies);

        if (!TextUtils.isEmpty(myFormVideoId)) {
            String url = YOUTUBE_VIDEO_THUMBNAIL_URL + myFormVideoId + "/0.jpg";
            holder.mImageViewMyMessageImage.setVisibility(View.VISIBLE);

            Glide.with(holder.mContext).load(url)
                    .placeholder(R.drawable.fitness_placeholder)
                    .error(R.mipmap.ic_wifi)
                    .fitCenter()
                    .into(holder.mImageViewMyMessageImage);
        }
    }

    @Override
    public int getItemCount() {
        return mMyFormMessages.size();
    }

    // Define listener member variable
    private static OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, MyFormMessage myFormMessage);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
