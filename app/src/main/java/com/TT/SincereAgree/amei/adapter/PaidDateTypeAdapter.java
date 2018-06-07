package com.TT.SincereAgree.amei.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.TT.SincereAgree.R;

import java.util.List;

/**
 * Created by Amei on 2017/12/11.
 */

public class PaidDateTypeAdapter extends BaseAdapter {
    private Context context;
    private List<String> typelist;
    private int selected = -1;
    public PaidDateTypeAdapter(Context context,List<String> typelist){
        this.context = context;
        this.typelist = typelist;
    }
    public void setSelected(int position){
        selected = position;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return typelist.size();
    }

    @Override
    public String getItem(int position) {
        return typelist.get(position);
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
            convertView= LayoutInflater.from(context).inflate(R.layout.paiddatatype_item, null);
            viewHolder.textView=(TextView) convertView.findViewById(R.id.paiddatetyItemTe);
            viewHolder.layout = (LinearLayout) convertView
                    .findViewById(R.id.layout);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        String text = getItem(position);
        viewHolder.textView.setText(text);
        // 点击改变选中listItem的背景色
        if (selected == position) {
           viewHolder.layout.setBackgroundResource(R.drawable.typeshapeselected);
            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.paiddate_selected));
        } else {
           viewHolder.layout.setBackgroundResource(R.drawable.datatypeshape);
            viewHolder.textView.setTextColor(0xff000000);
        }
        return convertView;
    }
    static class ViewHolder{

        private TextView textView;
        public LinearLayout layout;
    }
}
