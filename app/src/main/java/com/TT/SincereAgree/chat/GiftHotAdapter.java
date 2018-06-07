package com.TT.SincereAgree.chat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.util.DownloadImageTask;

import org.json.JSONObject;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;

/**
 * Created by 冯雪松 on 2018-05-20.
 */

public class GiftHotAdapter extends RecyclerView.Adapter<GiftHotAdapter.ViewHolder> {
    private String baseGiftUrl = Configure.rootUrl + "chat/gift/getPic";
    private Context mContext;
    private List<ChatGift> list;
    private String[] exchanges = new String[]{"积分", "诚意分"};
    private ButtonOnClick buttonOnClick = new ButtonOnClick(0);
    private class ButtonOnClick implements DialogInterface.OnClickListener {
        private boolean b = false;
        private int index; // 表示选项的索引
        private ChatGift gift;
        public ButtonOnClick(int index) {
            this.index = index;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

            if (which >= 0) {
                //如果单击的是列表项，将当前列表项的索引保存在index中。
                //如果想单击列表项后关闭对话框，可在此处调用dialog.cancel()
                //或是用dialog.dismiss()方法。
                index = which;
            } else {
                //用户单击的是【确定】按钮
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    boolean indexB = index == 0 ? true:false;
                    try{
                        sendRequestWithHttpURLConnectionForToken(gift.getId(),indexB);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    final String s = b? "兑换成功！":"您的分数不够";
                    final AlertDialog ad = new AlertDialog.Builder(mContext).setMessage(s).show();
                    //显示用户选择的是第几个列表项。
                    //3秒钟后自动关闭。
                    index = 0;
                    Handler hander = new Handler();
                    Runnable runnable = new Runnable() {

                        @Override
                        public void run() {
                            ad.dismiss();
                            if(b){
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
                                //-1表示这是普通礼物，不是动态礼物
                                ChatUtil.addGiftForUser(Configure.chatToId,gift.getId(),"-1", Configure.accountId);
                                ChatUtil.deleteGiftForUser(Configure.chatToId,gift.getId(),"-1", Configure.accountId);
                                ((Activity)mContext).finish();
                            }
                        }
                    };

                    hander.postDelayed(runnable, 1 * 1000);

                }
                //用户单击的是【取消】按钮
                else if (which == DialogInterface.BUTTON_NEGATIVE) {
                    Toast.makeText(mContext, "你没有选择任何东西",
                            Toast.LENGTH_LONG);
                }
            }

        }
    }

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

    public GiftHotAdapter(List<ChatGift> list) {
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
                /*
                int position = viewHolder.getAdapterPosition();
                ChatGift gift = list.get(position);
                GiftMessage giftMessage = GiftMessage.obtain(Configure.accountId,Configure.chatToId,String.valueOf(gift.getInNum()),false,gift.getUrl());


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
                //-1表示这是普通礼物，不是动态礼物
                ChatUtil.addGiftForUser(Configure.chatToId,gift.getId(),"-1",Configure.accountId);
                ChatUtil.deleteGiftForUser(Configure.chatToId,gift.getId(),"-1",Configure.accountId);
                ((Activity)mContext).finish();*/
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("请选择兑换方式");
                buttonOnClick.gift = list.get(viewHolder.getAdapterPosition());
                builder.setSingleChoiceItems(exchanges, 0, buttonOnClick);
                builder.setPositiveButton("确定", buttonOnClick);
                builder.setNegativeButton("取消", buttonOnClick);
                builder.show();

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

    public void sendRequestWithHttpURLConnectionForToken(final int giftid,final boolean isintegeral) throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("accountid", Configure.accountId)
                            .add("giftid",giftid+"").add("isintegeral",String.valueOf(isintegeral)).build();
                    Request request = new Request.Builder().url(rootUrl+"pocket/gift/exchange").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jSONObject = new JSONObject(responseData);
                    //JSONArray jsonArray = new JSONArray(responseData);
                    if(jSONObject.getInt("code") == 100)
                        buttonOnClick.b = true;
                    else
                        buttonOnClick.b = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        thread.join();
    }

}
