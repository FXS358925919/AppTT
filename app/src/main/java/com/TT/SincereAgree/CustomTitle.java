package com.TT.SincereAgree;

/*
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CustomTitle {

    private static AppCompatActivity mActivity;

    public static void getCustomTitle(AppCompatActivity activity, String title) {
        mActivity = activity;
        mActivity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        mActivity.setContentView(R.layout.custom_title);
        mActivity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_title);

        TextView textView = (TextView) activity.findViewById(R.id.head_center_text);
        textView.setText(title);
        Button titleBackBtn = (Button) activity.findViewById(R.id.TitleBackBtn);
        titleBackBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Title back","key down");

                mActivity.finish();
            }
        });
    }
}
*/

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CustomTitle {

    private static Activity mActivity;

    public static void getCustomTitle(Activity activity, String title) {
        mActivity = activity;
        mActivity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        mActivity.setContentView(R.layout.custom_title);
        mActivity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_title);

        TextView textView = (TextView) activity.findViewById(R.id.head_center_text);
        textView.setText(title);
        Button titleBackBtn = (Button) activity.findViewById(R.id.TitleBackBtn);
        titleBackBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Title back","key down");

                mActivity.finish();
            }
        });
    }
}
