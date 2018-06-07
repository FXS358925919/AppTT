package com.TT.SincereAgree.pocket;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.TT.SincereAgree.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 冯雪松 on 2017-11-26.
 */

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.ViewHolder> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy\nMM-dd");
    private List<Bill> list;
    public BillsAdapter(List<Bill> list){
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_item,parent,false);

        final ViewHolder viewHolder = new ViewHolder(view);
        /*viewHolder.billView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Bill Bill = list.get(position);
                Toast.makeText(v.getContext(),"you click view"+ Bill.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.billImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Bill Bill = list.get(position);
                Toast.makeText(v.getContext(),"you click image"+ Bill.getName(),Toast.LENGTH_SHORT).show();
            }
        });*/

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bill Bill = list.get(position);
        holder.billImage.setImageResource(Bill.getImageId());
        holder.billName.setText(Bill.getName());
        int income = Bill.getIncome();
        if (income >= 0) {
            holder.billIncome.setText("+" + Bill.getIncome());
        } else {
            holder.billIncome.setText(Bill.getIncome()+"");
        }

        holder.billDate.setText(sdf.format(Bill.getDate()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View billView;
        ImageView billImage;
        TextView billIncome;
        TextView billName;
        TextView billDate;
        public ViewHolder(View itemView) {
            super(itemView);
            billImage = (ImageView) itemView.findViewById(R.id.pocket_bill_image);
            billName = (TextView) itemView.findViewById(R.id.pocket_bill_name);
            billDate = (TextView) itemView.findViewById(R.id.pocket_bill_date);
            billIncome = (TextView) itemView.findViewById(R.id.pocket_bill_income);
            billView = itemView;
        }
    }
}
