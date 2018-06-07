package com.TT.SincereAgree.amei.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.TT.SincereAgree.R;

/**
 * Created by Amei on 2017/11/29.
 */

public class TitleBar extends RelativeLayout{
    private View titleBarView;
    private LayoutInflater layoutInflater;
    private TextView leftStr;
    private TextView centerTitle;
    private TextView rightStr;
    private RelativeLayout allView;

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        InitTitleBarView(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        InitTitleBarView(context);
    }

    public TitleBar(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        InitTitleBarView(context);
    }
    public void InitTitleBarView(Context context)
    {
        layoutInflater=LayoutInflater.from(context);
        titleBarView=layoutInflater.inflate(R.layout.titlebar, this);
        leftStr=(TextView)titleBarView.findViewById(R.id.titleBarLeftStr);
        centerTitle=(TextView)titleBarView.findViewById(R.id.title);
        allView=(RelativeLayout)titleBarView.findViewById(R.id.titleBarView);
        rightStr=(TextView)titleBarView.findViewById(R.id.titleBarRightStr);
    }

    /**
     * 左右都显示
     */
    public void showLeftAndRight(String title,String left,OnClickListener leftClicki,OnClickListener rightClick){
        centerTitle.setText(title);
        centerTitle.setVisibility(View.VISIBLE);
        leftStr.setText(left);
        leftStr.setVisibility(View.VISIBLE);
        leftStr.setOnClickListener(leftClicki);
    }

    /**
     * 左边+右边文字
     */
    public void showLeftImageAndRightStr(String title,String rightStrs,String left){
        centerTitle.setVisibility(View.VISIBLE);
        centerTitle.setText(title);
        leftStr.setText(left);
        leftStr.setVisibility(View.VISIBLE);
        rightStr.setText(rightStrs);
        rightStr.setVisibility(View.VISIBLE);
        rightStr.setTextSize(14);
        leftStr.setTextSize(14);
    }

    public void clickleft(OnClickListener left)
    {
        leftStr.setOnClickListener(left);
    }
    public void clickright(OnClickListener right)
    {
        rightStr.setOnClickListener(right);
    }

    /**
     * 设置背景颜色
     * @param color
     */
    public void setBgColor(int color){
        allView.setBackgroundColor(color);
    }

    /**
     * 只显示标题
     * @param title
     */
    public void showCenterTitle(String title){
        centerTitle.setText(title);
        centerTitle.setVisibility(View.VISIBLE);
    }
}
