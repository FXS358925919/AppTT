package com.TT.SincereAgree.amei.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.TT.SincereAgree.R;

/**
 * 创建人: Fang.Mr
 * 创建时间:2018-01-23
 * 功能描述:
 */

public class SimpleItemLayout extends LinearLayout {
    private ImageView imageView;//item的图标
    private TextView textView;//item的文字
    private ImageView bottomview;
    private boolean isbootom=true;//是否显示底部的下划线
    public SimpleItemLayout(Context context) {
        this(context,null);
    }

    public SimpleItemLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public SimpleItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       LayoutInflater.from(getContext()).inflate(R.layout.item_view,this);
       /* LayoutInflater mInflater = LayoutInflater.from(context);
        View myView = mInflater.inflate(R.layout.SimpleItemLayout, null);
        addView(myView);*/
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.SimpleItemLayout);
        isbootom=ta.getBoolean(R.styleable.SimpleItemLayout_show_bottomline,true);
       bottomview= (ImageView) findViewById(R.id.item_bottom);
        imageView= (ImageView) findViewById(R.id.item_img);
        textView= (TextView) findViewById(R.id.item_text);

        textView.setText(ta.getString(R.styleable.SimpleItemLayout_show_text));
        imageView.setBackgroundResource(ta.getResourceId(R.styleable.SimpleItemLayout_show_leftimg,R.drawable.setting));

        ta.recycle();
        initview();
    }

    private void initview() {
        if(isbootom){
            bottomview.setVisibility(View.VISIBLE);
        }else{
            bottomview.setVisibility(View.GONE);
        }
    }


}
