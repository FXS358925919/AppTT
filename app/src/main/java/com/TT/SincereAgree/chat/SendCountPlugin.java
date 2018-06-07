package com.TT.SincereAgree.chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.TT.SincereAgree.App;
import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;

/**
 * Created by 冯雪松 on 2018-05-22.
 */

public class SendCountPlugin implements IPluginModule {


    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.rc_ext_plugin_image_selector);
    }

    @Override
    public String obtainTitle(Context context) {
        return "收发统计";
    }

    @Override
    public void onClick(final Fragment fragment, RongExtension rongExtension) {
        Toast.makeText(App.getContext(),"发送消息:"+ Configure.sendCount + "条\n收到消息:"+ Configure.receiveCount+"条"+"\n"+ Configure.reduceIntegral,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}