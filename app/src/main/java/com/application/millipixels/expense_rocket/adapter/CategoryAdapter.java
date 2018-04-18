package com.application.millipixels.expense_rocket.adapter;

import android.content.Context;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.model.ExpenseCategory;


import java.util.List;

/**
 * Created by millipixelsinteractive_031 on 12/03/18.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    List<ExpenseCategory> arrayList;
    String symbol;
    Context context;
    private final OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView catTextview;
        ImageView catImageView;
        RelativeLayout cat_layout;

        public MyViewHolder(View view) {
            super(view);
            catTextview = view.findViewById(R.id.catTextview);
            catImageView=view.findViewById(R.id.catImageView);
            cat_layout=view.findViewById(R.id.cat_layout);
        }
    }

    public CategoryAdapter(List<ExpenseCategory> arrayList, Context context, OnItemClickListener listener) {
        this.arrayList = arrayList;
        this.context = context;
        this.listener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.catTextview.setText(arrayList.get(position).getCatName());

        String catName=arrayList.get(position).getCatName();

        if(catName!=null && catName.equalsIgnoreCase("Food")){
            holder.catImageView.setImageResource(R.drawable.ic_heart);
            holder.cat_layout.setBackgroundColor(Color.parseColor("#fcf7f7"));
        }
        else if(catName!=null && catName.equalsIgnoreCase("Grocery")){
            holder.cat_layout.setBackgroundColor(Color.parseColor("#f0f7fb"));
            holder.catImageView.setImageResource(R.drawable.ic_groceries);
        }
        else if(catName!=null && catName.equalsIgnoreCase("Traveling")){
            holder.cat_layout.setBackgroundColor(Color.parseColor("#fcf7ec"));
            holder.catImageView.setImageResource(R.drawable.ic_luggage);
        }
        else if(catName!=null && catName.equalsIgnoreCase("Fashion")){
            holder.cat_layout.setBackgroundColor(Color.parseColor("#fcf7f7"));
            holder.catImageView.setImageResource(R.drawable.ic_shirt);
        }
        else if(catName!=null && catName.equalsIgnoreCase("Entertainment")){
            holder.cat_layout.setBackgroundColor(Color.parseColor("#f6f7f9"));
            holder.catImageView.setImageResource(R.drawable.ic_tickets);
        }
        else if(catName!=null && catName.equalsIgnoreCase("HealthCare")){
            holder.cat_layout.setBackgroundColor(Color.parseColor("#fcf7f7"));
            holder.catImageView.setImageResource(R.drawable.ic_heart);

        }
        holder.cat_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface OnItemClickListener {

        void onItemClick(int  pos);
    }

}