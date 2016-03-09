package com.codepath.apps.learnfitness.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.apps.learnfitness.R;
import com.codepath.apps.learnfitness.models.Form;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JaneChung on 3/6/16.
 */
public class CheckMyFormAdapter extends RecyclerView.Adapter<CheckMyFormAdapter.ViewHolder> {

    private List<Form> mForms;
    private Context context;

    public CheckMyFormAdapter(List<Form> forms) {
        mForms = forms;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvFormLessonTitle)
        TextView lessonTitle;
        @Bind(R.id.tvDescription)
        TextView description;

        static final int REQUEST_VIDEO_CAPTURE = 1;


        public ViewHolder(View itemView) {
            super(itemView);

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
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View formView = inflater.inflate(R.layout.item_forms, parent, false);

        ViewHolder viewHolder = new ViewHolder(formView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder ( final ViewHolder holder, int position){
        Form form = mForms.get(position);

        String lesson = form.getLessonTitle();
        String description = form.getFeedback();

        holder.lessonTitle.setText(lesson);
        holder.description.setText(description);
    }

    @Override
    public int getItemCount() {
        return mForms.size();
    }
}
