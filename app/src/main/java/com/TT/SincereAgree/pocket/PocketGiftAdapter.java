package com.TT.SincereAgree.pocket;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.chat.ChatGift;
import com.TT.SincereAgree.util.DownloadImageTask;

import org.json.JSONObject;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.TT.SincereAgree.Configure.rootUrl;


public class PocketGiftAdapter extends RecyclerView.Adapter<PocketGiftAdapter.ViewHolder> {
    private String baseGiftUrl = Configure.rootUrl + "chat/gift/getPic";
    private Context mContext;
    private List<ChatGift> list;
    private String[] exchanges = new String[]{"积分", "诚意分"};
    private ButtonOnClick buttonOnClick = new ButtonOnClick(0);
    private Handler mhandler;
    private class ButtonOnClick implements DialogInterface.OnClickListener {
        private boolean b = false;
        private int index; // 表示选项的索引
        private int giftid;
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
                        sendRequestWithHttpURLConnectionForToken(giftid,indexB);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    final String s = b? "兑换成功！":"您的分数不够";
                    final AlertDialog ad = new AlertDialog.Builder(mContext).setMessage(s).show();
                    //显示用户选择的是第几个列表项。
                    //3秒钟后自动关闭。
                    index = 0;
                    Message message = Message.obtain(mhandler,0);
                    message.sendToTarget();

                    Handler hander = new Handler();
                    Runnable runnable = new Runnable() {

                        @Override
                        public void run() {
                            ad.dismiss();

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
        TextView GiftValue;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            GiftImage = (ImageView) itemView.findViewById(R.id.exchange_gift_image);
            GiftName = (TextView) itemView.findViewById(R.id.exchange_gift_name);
            GiftValue = (TextView) itemView.findViewById(R.id.exchange_gift_value);
        }
    }

    public PocketGiftAdapter(List<ChatGift> list, Handler handler) {
        this.list = list;
        this.mhandler = handler;
    }

    @Override
    public PocketGiftAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.exchange_gift_item,parent,false);
        final PocketGiftAdapter.ViewHolder viewHolder = new PocketGiftAdapter.ViewHolder(view);
        viewHolder.GiftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("请选择兑换方式");
                buttonOnClick.giftid = list.get(viewHolder.getAdapterPosition()).getId();
                builder.setSingleChoiceItems(exchanges, 0, buttonOnClick);
                builder.setPositiveButton("确定", buttonOnClick);
                builder.setNegativeButton("取消", buttonOnClick);
                builder.show();

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PocketGiftAdapter.ViewHolder holder, int position) {
        ChatGift gift = list.get(position);
        holder.GiftName.setText(gift.getName());
        holder.GiftValue.setText(gift.getInNum()+"积分/"+gift.getSinNum()+"诚意分");
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
