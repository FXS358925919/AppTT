package com.TT.SincereAgree.amei.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.TT.SincereAgree.R;

import java.util.List;

public class PersonLabelAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<String> dataList;

    public PersonLabelAdapter(Context context, int currentLayout,List<String> dataList) {
        super(context, currentLayout , dataList);
        this.context = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.simple_edit_text_item,null);
        TextView textView = (TextView) view.findViewById(R.id.tv_grid_item);
        textView.setText(dataList.get(position));
        return textView;
    }
}
