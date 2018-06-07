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


public class GiftPlugin implements IPluginModule {


    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.rc_ext_plugin_image_selector);
    }

    @Override
    public String obtainTitle(Context context) {
        return "礼物";
    }

    @Override
    public void onClick(final Fragment fragment, RongExtension rongExtension) {
        Intent intent = new Intent(App.getContext(), GiftActivity.class);
        App.getContext().startActivity(intent);
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }

}