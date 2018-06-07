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
 * Created by Amei on 2017/11/20.
 */

public class GiftPictureAdapter extends BaseAdapter{
    private Context context;

    private List<Picture> pictures=new ArrayList<Picture>();

    public GiftPictureAdapter(String[] titles,String[] prices, Integer[] images, Context context) {
        super();
        this.context = context;
        for (int i = 0; i < images.length; i++) {
            Picture picture = new Picture(titles[i],prices[i],images[i]);
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
            convertView = LayoutInflater.from(this.context).inflate(R.layout.presentitem, null);

            // 初始化组件
            viewHolder.image = (ImageView) convertView.findViewById(R.id.presentItem_giftitem);
            viewHolder.title = (TextView) convertView.findViewById(R.id.presentItem_textView16);
            viewHolder.price = (TextView) convertView.findViewById(R.id.presentItem_textView17);
            // 给converHolder附加一个对象
            convertView.setTag(viewHolder);
        } else {
            // 取得converHolder附加的对象
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 给组件设置资源
        Picture picture = pictures.get(position);
        viewHolder.image.setImageResource(picture.getImageId());
        viewHolder.title.setText(picture.getTitle());
        viewHolder.price.setText(picture.getPrice());

        return convertView;
    }

    class ViewHolder {
        public TextView title;
        public TextView price;
        public ImageView image;
    }

    class Picture {
        private String title;
        private String price;
        private int imageId;

        public Picture(String title,String price, Integer imageId) {
            this.imageId = imageId;
            this.title = title;
            this.price = price;
        }

        public String getTitle() {
            return title;
        }
        public String getPrice() {
            return price;
        }

        public int getImageId() {
            return imageId;
        }

    }
}
