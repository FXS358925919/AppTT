package com.TT.SincereAgree.mainpage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.activity.UsermainpageActivity;
import com.TT.SincereAgree.util.DownloadImageTask;

import java.util.List;

import static com.TT.SincereAgree.Configure.rootUrl;


public class NineAdapter extends RecyclerView.Adapter<NineAdapter.ViewHolder> {
    private Context mContext;
    private List<NineBean> list;
    public NineAdapter(List<NineBean> list){
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_page_left_item,parent,false);

        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NineBean bean = list.get(position);
        new DownloadImageTask(holder.img1).execute(rootUrl+"/mine/home/profile?url="+bean.getImageUrl1());
        new DownloadImageTask(holder.img2).execute(rootUrl+"/mine/home/profile?url="+bean.getImageUrl2());
        new DownloadImageTask(holder.img3).execute(rootUrl+"/mine/home/profile?url="+bean.getImageUrl3());
        new DownloadImageTask(holder.img4).execute(rootUrl+"/mine/home/profile?url="+bean.getImageUrl4());
        new DownloadImageTask(holder.img5).execute(rootUrl+"/mine/home/profile?url="+bean.getImageUrl5());
        new DownloadImageTask(holder.img6).execute(rootUrl+"/mine/home/profile?url="+bean.getImageUrl6());
        holder.name1.setText(bean.getName1());
        holder.name2.setText(bean.getName2());
        holder.name3.setText(bean.getName3());
        holder.name4.setText(bean.getName4());
        holder.name5.setText(bean.getName5());
        holder.name6.setText(bean.getName6());
        holder.city1.setText(bean.getCity1());
        holder.city2.setText(bean.getCity2());
        holder.city3.setText(bean.getCity3());
        holder.city4.setText(bean.getCity4());
        holder.city5.setText(bean.getCity5());
        holder.city6.setText(bean.getCity6());
        holder.relation1.setText(bean.getRelation1());
        holder.relation2.setText(bean.getRelation2());
        holder.relation3.setText(bean.getRelation3());
        holder.relation4.setText(bean.getRelation4());
        holder.relation5.setText(bean.getRelation5());
        holder.relation6.setText(bean.getRelation6());
        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountId = bean.getAccountid1();
                Toast.makeText(mContext, accountId + bean.getName1(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext,UsermainpageActivity.class);
                intent.putExtra("ACCOUNTID",accountId);
                mContext.startActivity(intent);
            }
        });
        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountId = bean.getAccountid2();
                //写事件
                Toast.makeText(mContext, accountId + bean.getName2(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext,UsermainpageActivity.class);
                intent.putExtra("ACCOUNTID",accountId);
                mContext.startActivity(intent);
            }
        });
        holder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountId = bean.getAccountid3();
                //写事件
                Toast.makeText(mContext, accountId + bean.getName3(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext,UsermainpageActivity.class);
                intent.putExtra("ACCOUNTID",accountId);
                mContext.startActivity(intent);

            }
        });
        holder.img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountId = bean.getAccountid4();
                //写事件
                Toast.makeText(mContext, accountId + bean.getName4(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext,UsermainpageActivity.class);
                intent.putExtra("ACCOUNTID",accountId);
                mContext.startActivity(intent);
            }
        });
        holder.img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountId = bean.getAccountid5();
                //写事件
                Toast.makeText(mContext, accountId + bean.getName5(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext,UsermainpageActivity.class);
                intent.putExtra("ACCOUNTID",accountId);
                mContext.startActivity(intent);
            }
        });
        holder.img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountId = bean.getAccountid6();
                //写事件
                Toast.makeText(mContext, accountId + bean.getName6(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext,UsermainpageActivity.class);
                intent.putExtra("ACCOUNTID",accountId);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img1;
        ImageView img2;
        ImageView img3;
        ImageView img4;
        ImageView img5;
        ImageView img6;
        TextView name1;
        TextView name2;
        TextView name3;
        TextView name4;
        TextView name5;
        TextView name6;
        TextView city1;
        TextView city2;
        TextView city3;
        TextView city4;
        TextView city5;
        TextView city6;
        TextView relation1;
        TextView relation2;
        TextView relation3;
        TextView relation4;
        TextView relation5;
        TextView relation6;


        public ViewHolder(View itemView) {
            super(itemView);
            img1 = (ImageView) itemView.findViewById(R.id.nine_img1);
            img2 = (ImageView) itemView.findViewById(R.id.nine_img2);
            img3 = (ImageView) itemView.findViewById(R.id.nine_img3);
            img4 = (ImageView) itemView.findViewById(R.id.nine_img4);
            img5 = (ImageView) itemView.findViewById(R.id.nine_img5);
            img6 = (ImageView) itemView.findViewById(R.id.nine_img6);
            name1 = (TextView) itemView.findViewById(R.id.nine_name1);
            name2 = (TextView) itemView.findViewById(R.id.nine_name2);
            name3 = (TextView) itemView.findViewById(R.id.nine_name3);
            name4 = (TextView) itemView.findViewById(R.id.nine_name4);
            name5 = (TextView) itemView.findViewById(R.id.nine_name5);
            name6 = (TextView) itemView.findViewById(R.id.nine_name6);
            city1 = (TextView) itemView.findViewById(R.id.nine_city1);
            city2 = (TextView) itemView.findViewById(R.id.nine_city2);
            city3 = (TextView) itemView.findViewById(R.id.nine_city3);
            city4 = (TextView) itemView.findViewById(R.id.nine_city4);
            city5 = (TextView) itemView.findViewById(R.id.nine_city5);
            city6 = (TextView) itemView.findViewById(R.id.nine_city6);
            relation1 = (TextView) itemView.findViewById(R.id.nine_relationship1);
            relation2 = (TextView) itemView.findViewById(R.id.nine_relationship2);
            relation3 = (TextView) itemView.findViewById(R.id.nine_relationship3);
            relation4 = (TextView) itemView.findViewById(R.id.nine_relationship4);
            relation5 = (TextView) itemView.findViewById(R.id.nine_relationship5);
            relation6 = (TextView) itemView.findViewById(R.id.nine_relationship6);
        }
    }
}
