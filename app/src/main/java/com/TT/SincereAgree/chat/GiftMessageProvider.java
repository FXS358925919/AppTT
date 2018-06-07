package com.TT.SincereAgree.chat;

import android.content.Context;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.util.DownloadImageTask;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

/**
 * Created by 冯雪松 on 2018-03-28.
 */

@ProviderTag(messageContent = GiftMessage.class, showPortrait = true, showProgress = true, centerInHorizontal = false)
// 会话界面自定义UI注解
public class GiftMessageProvider extends IContainerItemProvider.MessageProvider<GiftMessage> {
    private Context context;
    private String baseGiftUrl = Configure.rootUrl + "chat/gift/getPic";
    @Override
    public void bindView(View view, int i, GiftMessage giftMessage, UIMessage message) {
        ViewHolder holder = (ViewHolder) view.getTag();

        // 更改气泡样式
        if (message.getMessageDirection() == Message.MessageDirection.SEND) {
            // 消息方向，自己发送的
            holder.view.setBackgroundResource(R.drawable.de_ic_bubble_right);
        } else {
            // 消息方向，别人发送的
            holder.view.setBackgroundResource(R.drawable.de_ic_bubble_left);
        }
        new DownloadImageTask(holder.imageView).execute(baseGiftUrl+"?url="+giftMessage.getUrl());
    }

    @Override
    public Spannable getContentSummary(GiftMessage giftMessage) {
        return null;
    }

    @Override
    public void onItemClick(View view, int i, GiftMessage message, UIMessage uiMessage) {
        Toast.makeText(context, "收到礼物!", Toast.LENGTH_SHORT).show();
    }

    class ViewHolder {
        View view;
        ImageView imageView;
    }
    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(
                R.layout.de_customize_message_gift, null);
        ViewHolder holder = new ViewHolder();
        holder.view = (View) view.findViewById(R.id.rc_img);
        holder.imageView = (ImageView) view.findViewById(R.id.chat_gift_image);
        view.setTag(holder);
        return view;
    }
    /**
     * 初始化View
     */

}

