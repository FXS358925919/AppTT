package com.TT.SincereAgree.chat;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by 冯雪松 on 2017-10-16.
 */

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.ViewHolder> {
    private String baseGiftUrl = Configure.rootUrl + "chat/gift/getPic";
    private Context mContext;
    private List<ChatGift> list;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView GiftImage;
        TextView GiftName;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            GiftImage = (ImageView) itemView.findViewById(R.id.chat_gift_image);
            GiftName = (TextView) itemView.findViewById(R.id.chat_gift_name);
        }
    }

    public GiftAdapter(List<ChatGift> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.gift_select_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                ChatGift gift = list.get(position);
                GiftMessage giftMessage = GiftMessage.obtain(Configure.accountId, Configure.chatToId,String.valueOf(gift.getInNum()),false,gift.getUrl());


                RongIM.getInstance().getRongIMClient().sendMessage(Conversation.ConversationType.PRIVATE, Configure.chatToId, giftMessage, null, null, new RongIMClient.SendMessageCallback() {
                    @Override
                    public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                        Log.e("RongRedPacketProvider", "-----onError--" + errorCode);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.e("RongRedPacketProvider", "-----onSuccess--" + integer);
                    }
                });
                /*
                ChatUtil.sendRequestWithHttpURLConnectionForToken(Configure.accountId,String.valueOf("-"+gift.getNum()),false);
                ChatUtil.sendRequestWithHttpURLConnectionForToken(Configure.chatToId,String.valueOf(gift.getNum()),false);
                */
                //-1表示这是普通礼物，不是动态礼物
                ChatUtil.addGiftForUser(Configure.chatToId,gift.getId(),"-1", Configure.accountId);
                ChatUtil.deleteGiftForUser(Configure.chatToId,gift.getId(),"-1", Configure.accountId);
                ((Activity)mContext).finish();

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatGift gift = list.get(position);
        holder.GiftName.setText(gift.getName()+String.valueOf("\n")+gift.getCount()+String.valueOf("个"));
        new DownloadImageTask(holder.GiftImage).execute(baseGiftUrl+"?url="+gift.getUrl());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

}
