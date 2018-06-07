package com.TT.SincereAgree.pocket;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.common.PostUpdateData;
import com.TT.SincereAgree.chat.ChatGift;
import com.TT.SincereAgree.chat.ChatUtil;
import com.TT.SincereAgree.util.DownloadImageTask;

import java.util.HashMap;
import java.util.List;

import static com.TT.SincereAgree.Configure.rootUrl;


public class SquareGiftAdapter extends RecyclerView.Adapter<SquareGiftAdapter.ViewHolder> {
    private String baseGiftUrl = rootUrl + "chat/gift/getPic";
    private Context mContext;
    private List<ChatGift> list;
    private String toid;
    private String dyId;
    private String newGiftNum;
    private String type;
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

    public SquareGiftAdapter(List<ChatGift> list, String toid, String dyId, String newGiftNum, String type) {
        this.list = list;
        this.toid = toid;
        this.dyId = dyId;
        this.newGiftNum = newGiftNum;
        this.type = type;
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

                //-1表示这是普通礼物，不是动态礼物
                ChatUtil.addGiftForUser(toid,gift.getId(),"-1", Configure.accountId);
                ChatUtil.deleteGiftForUser(toid,gift.getId(),"-1", Configure.accountId);
                final HashMap<String,String> mapnum = new HashMap<String, String>();
                final String url1 = rootUrl+"dynamic/updateDynamic";
                final String url2 = rootUrl+"voice/updateVoicedy";
                if(type.equals("1")){
                    mapnum.put("dId",dyId);
                    mapnum.put("gift",newGiftNum);
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            PostUpdateData.postUpdate(mapnum,url1);
                        }
                    });
                    t.start();
                }else{
                    mapnum.put("vId",dyId);
                    mapnum.put("gift",newGiftNum);
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            PostUpdateData.postUpdate(mapnum,url2);
                        }
                    });
                    t.start();
                }


                ((Activity)mContext).finish();

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatGift gift = list.get(position);
        holder.GiftName.setText(gift.getName());
        new DownloadImageTask(holder.GiftImage).execute(baseGiftUrl+"?url="+gift.getUrl());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

}
