package com.TT.SincereAgree.amei.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.TT.SincereAgree.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Amei on 2017/11/29.
 */

public class GridViewAdapter extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private List<Bitmap> imageData;
    private ArrayList<String> listUrls;
    private  Context context;

    public GridViewAdapter(Context context, List<Bitmap> allImages) {
        // TODO Auto-generated constructor stub
        layoutInflater=LayoutInflater.from(context);
        this.imageData=allImages;
    }

    public GridViewAdapter(Context context,ArrayList<String> listUrls){
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
        this.listUrls = listUrls;
        if(listUrls.size() == 11){
            listUrls.remove(listUrls.size()-1);
        }

    }

    @Override
    public int getCount() {
        return listUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return listUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.gridviewitem, null);
            viewHolder.imageView=(ImageView)convertView.findViewById(R.id.addima_gridItemView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        //viewHolder.imageView.setImageBitmap(listUrls.get(position));
        final String path=listUrls.get(position);
        if (path.equals("addima")){
            viewHolder.imageView.setImageResource(R.drawable.addima);
        }else {
            Glide.with(context)
                    .load(path)
                    .placeholder(R.mipmap.default_error)
                    .error(R.mipmap.default_error)
                    .centerCrop()
                    .crossFade()
                    .into(viewHolder.imageView);
        }
        return convertView;
    }

    static class ViewHolder{

        private ImageView imageView;
    }
}
