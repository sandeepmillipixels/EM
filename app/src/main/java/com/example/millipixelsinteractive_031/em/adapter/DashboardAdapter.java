package com.example.millipixelsinteractive_031.em.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.millipixelsinteractive_031.em.R;
import com.example.millipixelsinteractive_031.em.model.Data;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by millipixelsinteractive_031 on 31/01/18.
 */

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {

    ArrayList<Data>arrayList;
    String symbol;

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView categoryNameTextView,dateTextView,amountTextView,categoryTextView,statusTextView;
        ImageView expenseImage;
        RelativeLayout layout_dashboard_item;

        public MyViewHolder(View view) {
            super(view);
            categoryNameTextView = view.findViewById(R.id.categoryNameTextView);
            dateTextView = view.findViewById(R.id.dateTextView);
            amountTextView = view.findViewById(R.id.amountTextView);
            categoryTextView = view.findViewById(R.id.categoryTextView);
            expenseImage=view.findViewById(R.id.expenseImageView);
            statusTextView=view.findViewById(R.id.statusTextView);
            layout_dashboard_item=view.findViewById(R.id.layout_dashboard_item);
        }
    }

    public DashboardAdapter(ArrayList<Data>arrayList, Context context) {
        this.arrayList=arrayList;
        this.context=context;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {



        holder.categoryNameTextView.setText(arrayList.get(position).getCategory());
        holder.categoryTextView.setText(arrayList.get(position).getCategory());
        holder.dateTextView.setText(arrayList.get(position).getDate());
        holder.amountTextView.setText("$"+arrayList.get(position).getAmount());


        String catName=arrayList.get(position).getCategory();


        if(catName!=null && catName.equalsIgnoreCase("Food")){

            holder.expenseImage.setImageResource(R.drawable.ic_heart);
            holder.layout_dashboard_item.setBackgroundColor(Color.parseColor("#fcf7f7"));

        }
        else if(catName!=null && catName.equalsIgnoreCase("Grocery")){

            holder.layout_dashboard_item.setBackgroundColor(Color.parseColor("#f0f7fb"));
            holder.expenseImage.setImageResource(R.drawable.ic_groceries);

        }
        else if(catName!=null && catName.equalsIgnoreCase("Traveling")){

            holder.layout_dashboard_item.setBackgroundColor(Color.parseColor("#fcf7ec"));

            holder.expenseImage.setImageResource(R.drawable.ic_luggage);

        }
        else if(catName!=null && catName.equalsIgnoreCase("Fashion")){

            holder.layout_dashboard_item.setBackgroundColor(Color.parseColor("#fcf7f7"));

            holder.expenseImage.setImageResource(R.drawable.ic_shirt);

        }
        else if(catName!=null && catName.equalsIgnoreCase("Entertainment")){

            holder.layout_dashboard_item.setBackgroundColor(Color.parseColor("#f6f7f9"));

            holder.expenseImage.setImageResource(R.drawable.ic_tickets);

        }
        else if(catName!=null && catName.equalsIgnoreCase("HealthCare")){

            holder.layout_dashboard_item.setBackgroundColor(Color.parseColor("#fcf7f7"));

            holder.expenseImage.setImageResource(R.drawable.ic_heart);

        }



//
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" +arrayList.get(position).getImage();
//
//        Picasso.with(context).load(new File(path)).into(holder.expenseImage);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private Bitmap getImageFileFromSDCard(String filename){
        Bitmap bitmap = null;
        File imageFile = new File(filename);
        try {
            FileInputStream fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }



}
