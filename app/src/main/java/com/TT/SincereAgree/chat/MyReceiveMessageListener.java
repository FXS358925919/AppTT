package com.TT.SincereAgree.chat;

import com.TT.SincereAgree.Configure;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

/**
 * Created by 冯雪松 on 2018-03-15.
 */

public class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {
    @Override
    public boolean onReceived(Message message, int i) {
        Configure.receiveCount++;
        TimerSchedule.messageId.add(message.getMessageId());
        TimerSchedule.test();

        //RongIMClient.getInstance().deleteMessages(new int[]{message.getMessageId()});
        return true;
    }
}
