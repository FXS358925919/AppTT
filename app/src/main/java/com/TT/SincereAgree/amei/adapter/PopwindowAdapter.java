package com.TT.SincereAgree.amei.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.TT.SincereAgree.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amei on 2017/12/4.
 */

public class PopwindowAdapter extends BaseAdapter {
    private Context context;

    private List<Picturep> pictures=new ArrayList<Picturep>();

    public PopwindowAdapter(String[] titles,Integer[] images, Context context) {
        super();
        this.context = context;
        for (int i = 0; i < images.length; i++) {
            Picturep picture = new Picturep(titles[i],images[i]);
            pictures.add(picture);
        }

    }

    @Override
    public int getCount() {
        if (null != pictures) {
            return pictures.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return pictures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            // 获得容器
            convertView = LayoutInflater.from(this.context).inflate(R.layout.popwindowitem, null);

            // 初始化组件
            viewHolder.image = (ImageView) convertView.findViewById(R.id.popimaitem);
            viewHolder.title = (TextView) convertView.findViewById(R.id.poptextitem);
            // 给converHolder附加一个对象
            convertView.setTag(viewHolder);
        } else {
            // 取得converHolder附加的对象
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 给组件设置资源
        Picturep picture = pictures.get(position);
        viewHolder.image.setImageResource(picture.getImageId());
        viewHolder.title.setText(picture.getTitle());


        return convertView;
    }

    class ViewHolder {
        public TextView title;
        public ImageView image;
    }

    class Picturep {
        private String title;
        private int imageId;

        public Picturep(String title,Integer imageId) {
            this.imageId = imageId;
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
        public int getImageId() {
            return imageId;
        }

    }
}
