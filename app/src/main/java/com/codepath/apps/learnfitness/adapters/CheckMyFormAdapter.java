package com.codepath.apps.learnfitness.adapters;

import com.bumptech.glide.Glide;
import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.Form;

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

    private List<Form> mForms;

    public CheckMyFormAdapter(List<Form> forms) {
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

        static final int REQUEST_VIDEO_CAPTURE = 1;


        public ViewHolder(View itemView,  Context context) {
            super(itemView);
            mContext = context;
            ButterKnife.bind(this, itemView);
        }

//        @Override
//        public void onClick(View v) {
//            int position = getAdapterPosition();
////            Form form = mForms.get(position);
////
////            //Show the video camera
////
////            if (v.getId() != R.id.btnSubmitForm) {
////                return;
////            }
////
////            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
////            if (takeVideoIntent.resolveActivity(itemView.getContext().getPackageManager()) != null) {
////                ((Activity) itemView.getContext())
////                        .startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
////            }
//        }

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
        Form form = mForms.get(position);

        String lesson = form.getWeekTitle();
        String description = form.getFeedback();

        Log.i("CheckMyFormAdapter", description);
        holder.lessonTitle.setText(lesson);
        holder.description.setText(description);

        //TODO Need to change backend to include trainer with form reply
        Glide.with(holder.mContext).load(R.drawable.rock_trainer).placeholder(R.drawable.rock_trainer).error(R.drawable.rock_trainer)
                .into(holder.mImageViewSenderTrainer);
    }

    @Override
    public int getItemCount() {
        Log.i("CheckMyFormAdapter", Integer.toString(mForms.size()));
        return mForms.size();
    }
}
