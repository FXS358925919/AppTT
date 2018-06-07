package com.TT.SincereAgree.amei.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * ViewPager的适配器
 * Created by Amei on 2018/5/13.
 */

public class ImageSlipAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<View> viewLists;

    public  ImageSlipAdapter(Context context,ArrayList<View> viewLists){
        this.context = context;
        this.viewLists = viewLists;
    }
    @Override
    public int getCount() {
        return viewLists.size();
    }
    @Override
    public int getItemPosition (Object object) { return POSITION_NONE; }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //((ViewGroup)container).removeView(mImageViews[position % mImageViews.length]);
    }

    /**
     * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
     * instantiateItem该方法的功能是创建指定位置的页面视图。finishUpdate(ViewGroup)返回前，页面应该保证被构造好
     * 返回值：返回一个对应该页面的object，这个不一定必须是View，但是应该是对应页面的一些其他容器
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try {
            container.addView(viewLists.get(position));
        }catch(Exception e){
            //handler something
        }
        return viewLists.get(position);
    }
}
