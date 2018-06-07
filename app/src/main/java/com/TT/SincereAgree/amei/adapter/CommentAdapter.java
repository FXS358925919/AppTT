package com.TT.SincereAgree.amei.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.entity.Comment;

import java.util.List;

/**
 * Created by Amei on 2017/11/28.
 */

public class CommentAdapter extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private List<Comment> commentList;

    public CommentAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    //需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public CommentAdapter(Context context, List<Comment> comment) {
        layoutInflater = LayoutInflater.from(context);
        commentList = comment;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.commentitem,parent,false);
            holder = new ViewHolder();
            holder.isGoodComment = (ImageView)convertView.findViewById(R.id.comment_item_isgoodcomment);
            holder.name = (TextView)convertView.findViewById(R.id.comment_item_comname);
            holder.content = (TextView)convertView.findViewById(R.id.comment_item_comcontent);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Comment comment = commentList.get(position);

        if (comment.getCommentUserName() != null&&comment.getText()!= null)
        {
            holder.name.setText(comment.getCommentUserName());
            holder.content.setText(": "+comment.getText());
            if (position == 0)
                holder.isGoodComment.setImageResource(R.mipmap.goodcomment);
            else
                holder.isGoodComment.setImageResource(0);
        }
        return convertView;
    }
    /**
     * 添加一条评论,刷新列表
     * @param comment
     */
    public void addComment(Comment comment){
        commentList.add(comment);
        notifyDataSetChanged();
    }

        private class ViewHolder {
        ImageView isGoodComment;
        TextView name;
        TextView content;
    }

}
