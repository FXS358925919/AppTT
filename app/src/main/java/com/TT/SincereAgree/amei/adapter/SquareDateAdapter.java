package com.TT.SincereAgree.amei.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.TT.SincereAgree.Configure;
import com.TT.SincereAgree.R;
import com.TT.SincereAgree.amei.entity.SquareDate;
import com.TT.SincereAgree.util.DownloadImageTask;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 冯雪松 on 2017-12-13.
 */

public class SquareDateAdapter extends RecyclerView.Adapter<SquareDateAdapter.ViewHolder> {
    private List<SquareDate> list;
    public SquareDateAdapter(List<SquareDate> list) {
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dyuserinftop_square,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SquareDate squareDate = list.get(position);
        //holder.userProfile.setImageResource(squareDate.getUserProfileId());
        new DownloadImageTask(holder.userProfile).execute(Configure.userUrl+squareDate.getUserProfile());
        //holder.vipLevel.setImageResource(squareDate.getVipLevel());
        /*if(squareDate.isCertifacated()){
            holder.certifacate.setVisibility(View.VISIBLE);
        }*/
        holder.userName.setText(squareDate.getUserName());
        holder.dynamicReleaseTime.setText(squareDate.getDynamicReleaseTime());
        //holder.releasePicture.setImageResource(squareDate.getReleasePicture());
        new DownloadImageTask(holder.releasePicture).execute(Configure.userUrl+squareDate.getReleasePicture());
        holder.sexAndage.setText(squareDate.getSex() + " " + squareDate.getAge());
        //holder.constellation.setText(squareDate.getConstellation());
        holder.userweight.setText(squareDate.getUserweight());
        holder.userheight.setText(squareDate.getUserheight());
        holder.mainText.setText(squareDate.getMainText());
        holder.classification0.setText(squareDate.getClassification0());
        holder.classification1.setText(squareDate.getClassification1());
        holder.classification2.setText(squareDate.getClassification2());
        holder.classification3.setText(squareDate.getClassification3());
        holder.location.setText(squareDate.getLocation());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        holder.dynamicReleaseDay.setText(sdf.format(squareDate.getDynamicReleaseDay()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView userProfile;
        //ImageView vipLevel;
        //ImageView certifacate;
        TextView userName;
        TextView dynamicReleaseTime;
        ImageView releasePicture;
        TextView sexAndage;
        //TextView constellation;
        TextView userheight;
        TextView userweight;
        TextView mainText;
        TextView classification0;
        TextView classification1;
        TextView classification2;
        TextView classification3;
        TextView location;
        TextView dynamicReleaseDay;
        Button signButton;
        public ViewHolder(View itemView) {
            super(itemView);
            userProfile = (ImageView) itemView.findViewById(R.id.iv_leftlogo_square);
            //vipLevel = (ImageView) itemView.findViewById(R.id.square_date_levelima);
            //certifacate = (ImageView) itemView.findViewById(R.id.certification_square);
            userName = (TextView) itemView.findViewById(R.id.square_date_userName);
            dynamicReleaseTime = (TextView) itemView.findViewById(R.id.square_date_tvAge);
            releasePicture = (ImageView) itemView.findViewById(R.id.square_date_picture);
            sexAndage = (TextView) itemView.findViewById(R.id.square_date_sexandage);
            //constellation = (TextView) itemView.findViewById(R.id.square_date_constellation);
            userheight = (TextView) itemView.findViewById(R.id.square_date_userheight);
            userweight = (TextView) itemView.findViewById(R.id.square_date_userweight);
            mainText = (TextView) itemView.findViewById(R.id.square_date_maintext);
            classification0 = (TextView) itemView.findViewById(R.id.square_date_cla0);
            classification1 = (TextView) itemView.findViewById(R.id.square_date_cla1);
            classification2 = (TextView) itemView.findViewById(R.id.square_date_cla2);
            classification3 = (TextView) itemView.findViewById(R.id.square_date_cla3);
            location = (TextView) itemView.findViewById(R.id.square_date_location);
            dynamicReleaseDay = (TextView) itemView.findViewById(R.id.square_date_releaseday);
            signButton = (Button) itemView.findViewById(R.id.square_date_sign);
        }
    }
}
