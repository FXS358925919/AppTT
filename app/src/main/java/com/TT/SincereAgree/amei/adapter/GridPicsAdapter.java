package com.TT.SincereAgree.amei.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.TT.SincereAgree.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class GridPicsAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<String> imageUrls;

    public GridPicsAdapter(Context context, int currentLayout,List<String> imageUrls) {
        super(context, currentLayout , imageUrls);
        this.context = context;
        this.imageUrls = imageUrls;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
           View view = inflater.inflate(R.layout.activity_blur_grid_item,null);
           convertView = view.findViewById(R.id.iv_grid_item);
        }
        Glide.with(context).load(imageUrls.get(position)).into((ImageView) convertView);
        return convertView;
    }

}
