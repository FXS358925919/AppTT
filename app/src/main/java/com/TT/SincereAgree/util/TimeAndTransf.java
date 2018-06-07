package com.TT.SincereAgree.util;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.squareup.picasso.Transformation;

/**
 * Created by Amei on 2018/4/26.
 */

public class TimeAndTransf {


    /**
     * beforetime为动态发布的时间
     * nowtime为当前时间
     * 返回app应该显示的时间*/
    public static String getDatePoor(String beforetime, String nowtime) {
        int yearb = Integer.valueOf(beforetime.substring(0, 4));
        int yearn = Integer.valueOf(nowtime.substring(0, 4));
        int montbb = Integer.valueOf(beforetime.substring(4, 6));
        int montbn = Integer.valueOf(nowtime.substring(4, 6));
        int dayb = Integer.valueOf(beforetime.substring(6, 8));
        int dayn = Integer.valueOf(nowtime.substring(6, 8));
        int hourb = Integer.valueOf(beforetime.substring(8, 10));
        int hourn = Integer.valueOf(nowtime.substring(8, 10));
        int secb = Integer.valueOf(beforetime.substring(10));
        int secn = Integer.valueOf(nowtime.substring(10));
        String timed = yearb + "年" + String.format("%02d", montbb) + "月"
                + String.format("%02d", dayb) + "日"
                + String.format("%02d", hourb) + ":"
                + String.format("%02d", secb);
        if (yearn - yearb > 0) {
            return timed;
        } else {
            if (montbn-montbb>0||dayn - dayb > 2) {
                return timed.substring(5);
            } else {
                if (dayn - dayb > 0) {
                    return dayn - dayb + "天前";
                }
                if((hourn*60+secn)-(hourb*60+secb)>=60){
                    return ((hourn*60+secn)-(hourb*60+secb))/60+"小时前";
                }else
                    return ((hourn*60+secn)-(hourb*60+secb))+"分钟前";
            }
        }
        //return String.format("%02d", hourb) + ":" + String.format("%02d", secb);
    }


    /**
     *重新计算listview 的高度
     * 解决嵌套的Listview显示不全的问题
     * */
    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    /**Picasso的Transformation接口，自定义转换图像*/
    public static Transformation getTransformation(final ImageView view){
        return new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = view.getWidth();
                //返回原图
                if (source.getWidth() == 0 || source.getWidth() < targetWidth) {
                    return source;
                }
                //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                if (targetHeight == 0 || targetWidth == 0) {
                    return source;
                }

                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };
    }

}
