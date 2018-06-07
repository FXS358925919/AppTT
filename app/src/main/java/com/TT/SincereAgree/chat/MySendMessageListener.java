package com.TT.SincereAgree.chat;

import android.widget.Toast;

import com.TT.SincereAgree.App;
import com.TT.SincereAgree.Configure;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Message;

import static com.TT.SincereAgree.Configure.lackIntegeral;

/**
 * Created by 冯雪松 on 2018-03-15.
 */

public class MySendMessageListener implements RongIM.OnSendMessageListener {

    @Override
    public Message onSend(Message message) {
        if(lackIntegeral){
            Toast.makeText(App.getContext(),"您的积分已经不足，无法发送消息，请充值",Toast.LENGTH_LONG).show();
            return null;
        }
        return message;
    }

    @Override
    public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
        Configure.sendCount++;
        if(Configure.reduceIntegral)
            Configure.sendValueCount++;
        TimerSchedule.messageId.add(message.getMessageId());
        TimerSchedule.test();
        return false;
    }
}
