package com.TT.SincereAgree.pocket;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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

/**
 * Created by 冯雪松 on 2018-03-28.
 */

public class MyGiftAdapter extends RecyclerView.Adapter<MyGiftAdapter.ViewHolder> {
    private boolean success;
    private String baseGiftUrl = Configure.rootUrl + "chat/gift/getPic";
    private Context mContext;
    private List<ChatGift> list;
    private EditText edit;
    private int count;
    private String[] exchanges = new String[]{"积分", "诚意分"};
    private MyGiftAdapter.ButtonOnClick buttonOnClick = new MyGiftAdapter.ButtonOnClick(0);
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
                        Toast.makeText(mContext, "你输入的是: " + edit.getText().toString(), Toast.LENGTH_SHORT).show();
                        //sendRequestWithHttpURLConnectionForToken(giftid,indexB);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    final String s = b? "兑换成功！":"您的分数不够";
                    final AlertDialog ad = new AlertDialog.Builder(mContext).setMessage(s).show();
                    //显示用户选择的是第几个列表项。
                    //3秒钟后自动关闭。
                    index = 0;
                    //Message message = Message.obtain(mhandler,0);
                    //message.sendToTarget();

                    Handler hander = new Handler();
                    Runnable runnable = new Runnable() {

                        @Override
                        public void run() {
                            ad.dismiss();

                        }
                    };

                    hander.postDelayed(runnable, 3 * 1000);
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

    public MyGiftAdapter(List<ChatGift> list, Context context) {
        this.list = list;

    }

    @Override
    public MyGiftAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.gift_select_item,parent,false);
        final MyGiftAdapter.ViewHolder viewHolder = new MyGiftAdapter.ViewHolder(view);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChatGift gift = list.get(viewHolder.getAdapterPosition());
                final int count = gift.getCount();
                final int giftId = gift.getId();
                View view = LayoutInflater.from(mContext).inflate(R.layout.mygiftadapter, null);
                final AlertDialog dialog = new AlertDialog.Builder(mContext).setTitle("兑换积分").setView(view)
                        .create();
                final EditText editText = (EditText) view.findViewById(R.id.mygift_exchange_edit);
                final RadioButton radio = (RadioButton) view.findViewById(R.id.mygift_exchange_integral);
                Button button = (Button) view.findViewById(R.id.mygift_exchange_button);
                Button quit = (Button) view.findViewById(R.id.mygift_exchange_button_quit);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isNum = false;
                        int exchangecount = 0;
                        try{
                            exchangecount = Integer.parseInt(editText.getText().toString());
                            isNum = true;
                        }catch (Exception e){
                            Toast.makeText(mContext,"请输入数字",Toast.LENGTH_SHORT).show();
                        }
                        if(isNum && exchangecount > count)
                            Toast.makeText(mContext,"输入数量大于礼物数量",Toast.LENGTH_SHORT).show();
                        else if(isNum){
                            boolean isIntegral = radio.isChecked();
                            try{
                                if(isIntegral){
                                    sendRequestWithHttpURLConnectionForToken(giftId,exchangecount,true);
                                }else{
                                    sendRequestWithHttpURLConnectionForToken(giftId,exchangecount,false);
                                }
                                final String s = success? "兑换成功！":"兑换失败";
                                final AlertDialog ad = new AlertDialog.Builder(mContext).setMessage(s).show();
                                //刷新礼物个数
                                viewHolder.GiftName.setText(gift.getName()+String.valueOf("\n")+(gift.getCount()-exchangecount)+String.valueOf("个"));
                                Handler hander = new Handler();
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        ad.dismiss();
                                        dialog.dismiss();
                                    }
                                };
                                hander.postDelayed(runnable, 1 * 1000);
                            }catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(mContext,"兑换失败，请重新兑换",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                quit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyGiftAdapter.ViewHolder holder, int position) {
        ChatGift gift = list.get(position);
        holder.GiftName.setText(gift.getName()+String.valueOf("\n")+gift.getCount()+String.valueOf("个"));
        new DownloadImageTask(holder.GiftImage).execute(baseGiftUrl+"?url="+gift.getUrl());



    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public void sendRequestWithHttpURLConnectionForToken(final int giftid,final int num,final boolean isintegeral) throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("accountid", Configure.accountId)
                            .add("giftid",giftid+"").add("num",num+"").add("isintegeral",String.valueOf(isintegeral)).build();
                    Request request = new Request.Builder().url(rootUrl+"pocket/gift/exchangeforintegral").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jSONObject = new JSONObject(responseData);
                    //JSONArray jsonArray = new JSONArray(responseData);
                    if(jSONObject.getInt("code") == 100)
                        success = true;
                    else
                        success = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        thread.join();
    }
}
