package com.TT.SincereAgree.chat;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

import com.TT.SincereAgree.R;

public class ConversitionActivity extends FragmentActivity {

    private TextView mName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversition);
        //禁止截屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        mName = (TextView) findViewById(R.id.name);
        String id = getIntent().getData().getQueryParameter("targetId");
        String name = getIntent().getData().getQueryParameter("title");
        if (!TextUtils.isEmpty(name)) {
            mName.setText(name);
        }else{
            mName.setText("去自己的服务器找");
        }
    }
}
