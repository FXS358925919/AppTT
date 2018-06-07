package com.TT.SincereAgree.mainpage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.util.DownloadImageTask;

import java.util.List;

/**
 * Created by 冯雪松 on 2018-06-04.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<InformationComment> list;

    public CommentAdapter(List<InformationComment> list){
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.information_comment,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InformationComment comment = list.get(position);
        new DownloadImageTask(holder.userProfile).execute(Configure.userUrl+comment.getUserProfile());
        holder.userName.setText(comment.getUserName());
        holder.dynamicReleaseTime.setText(comment.getDynamicReleaseTime());
        new DownloadImageTask(holder.dynamicPicture).execute(Configure.dynamicpic+comment.getDynamicPicUrl());
        holder.commentText.setText(comment.getCommentText());
        holder.dynamicText.setText(comment.getDynaminText());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView userProfile;
        TextView userName;
        TextView dynamicReleaseTime;
        ImageView dynamicPicture;
        TextView commentText;
        TextView dynamicText;
        public ViewHolder(View itemView) {
            super(itemView);
            userProfile = (ImageView) itemView.findViewById(R.id.comment_iv_leftlogo_square);
            userName = (TextView) itemView.findViewById(R.id.comment_date_userName);
            dynamicReleaseTime = (TextView) itemView.findViewById(R.id.comment_date_tvAge);
            dynamicPicture = (ImageView) itemView.findViewById(R.id.comment_dynamic_pic);
            commentText = (TextView) itemView.findViewById(R.id.comment_comment_maintext);
            dynamicText = (TextView) itemView.findViewById(R.id.comment_dynamic_text);
        }
    }
}
