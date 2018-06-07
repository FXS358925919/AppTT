package com.TT.SincereAgree.mine;

import android.content.Context;
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

import io.rong.imkit.RongIM;

import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * Created by 冯雪松 on 2017-09-11.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private Context mContext;
    private List<Friend> list;
    public FriendsAdapter(List<Friend> list){
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item,parent,false);

        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Friend friend = list.get(position);
                if (RongIM.getInstance() != null) {
                    //每次开启聊天窗口就初始化sendCount和receiveCount
                    Configure.sendCount = 0;
                    Configure.receiveCount = 0;
                    //Configure.sendValueCount = 0;
                    //把聊天对象的Id保存到静态变量中
                    Configure.chatToId = friend.getAccountid();
                    if(Configure.sex.equals("女")  && friend.getSex().equals("男"))
                        Configure.reduceIntegral = false;
                    RongIM.getInstance().startPrivateChat(mContext,friend.getAccountid(),"与"+friend.getName()+"聊天中");
                }
            }
        });
        /*viewHolder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Friend Friend = list.get(position);
                Toast.makeText(v.getContext(),"you click image"+ Friend.getName(),Toast.LENGTH_SHORT).show();
            }
        });*/
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Friend friends = list.get(position);

        new DownloadImageTask(holder.fruitImage).execute(rootUrl+"/mine/home/profile?url="+friends.getImageUrl());
        holder.textView.setText(friends.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View fruitView;
        ImageView fruitImage;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            fruitImage = (ImageView) itemView.findViewById(R.id.mine_friend_image);
            textView = (TextView) itemView.findViewById(R.id.mine_friend_name);
            fruitView = itemView;
        }
    }
}
