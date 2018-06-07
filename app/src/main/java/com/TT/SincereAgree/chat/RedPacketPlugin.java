package com.TT.SincereAgree.chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.TT.SincereAgree.App;
import com.TT.SincereAgree.R;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;

/**
 * Created by 冯雪松 on 2018-02-03.
 */

public class RedPacketPlugin implements IPluginModule {


    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.rc_ext_plugin_image_selector);
    }

    @Override
    public String obtainTitle(Context context) {
        return "红包";
    }

    @Override
    public void onClick(final Fragment fragment, RongExtension rongExtension) {
        Intent intent = new Intent(App.getContext(), HongBaoActivity.class);
        App.getContext().startActivity(intent);
        // RongRedPacketMessage rongRedPacketMessage = RongRedPacketMessage.obtain("00000001","给你一个大红包");

        /*
        RongIM.getInstance().getRongIMClient().sendMessage(Conversation.ConversationType.PRIVATE, "00000001", rongRedPacketMessage, null, null, new RongIMClient.SendMessageCallback() {
            @Override
            public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                Log.e("RongRedPacketProvider", "-----onError--" + errorCode);
            }

            @Override
            public void onSuccess(Integer integer) {
                Log.e("RongRedPacketProvider", "-----onSuccess--" + integer);
            }
        });
        */

    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}