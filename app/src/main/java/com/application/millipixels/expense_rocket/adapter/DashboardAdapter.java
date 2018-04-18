package com.application.millipixels.expense_rocket.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.addexpense.ExpenseListBycategoryActivity;
import com.application.millipixels.expense_rocket.model.ExpenseCategory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by millipixelsinteractive_031 on 31/01/18.
 */

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {

    ArrayList<ExpenseCategory>arrayList;
    String symbol;

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView categoryNameTextView,dateTextView,amountTextView,categoryTextView,statusTextView,percentTextView;
        ImageView expenseImage;
        SeekBar seekBar;
        RelativeLayout layout_dashboard_item;

        public MyViewHolder(View view) {
            super(view);
            categoryNameTextView = view.findViewById(R.id.categoryNameTextView);
            seekBar = (SeekBar)view.findViewById(R.id.seekBar);
//            dateTextView = view.findViewById(R.id.dateTextView);
            amountTextView = view.findViewById(R.id.amountTextView);
            percentTextView = view.findViewById(R.id.percentTextView);
//            categoryTextView = view.findViewById(R.id.categoryTextView);
            expenseImage=view.findViewById(R.id.expenseImageView);
//            statusTextView=view.findViewById(R.id.statusTextView);
            layout_dashboard_item=view.findViewById(R.id.layout_dashboard_item);
        }
    }

    public DashboardAdapter(ArrayList<ExpenseCategory>arrayList, Context context) {
        this.arrayList=arrayList;
        this.context=context;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.seekBar.setEnabled(false);

        holder.categoryNameTextView.setText(arrayList.get(position).getCatName());
        int progress = (int)(arrayList.get(position).getAmount()*100)/300;
        holder.amountTextView.setText("$"+arrayList.get(position).getAmount());

        holder.layout_dashboard_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ExpenseListBycategoryActivity.class);
                intent.putExtra("category",arrayList.get(position));
                context.startActivity(intent);
            }
        });

        String catName=arrayList.get(position).getCatName();


        if(catName!=null && catName.equalsIgnoreCase("Food")){

            holder.expenseImage.setImageResource(R.drawable.ic_heart);
            holder.layout_dashboard_item.setBackgroundColor(Color.parseColor("#fcf7f7"));
            holder.seekBar.setProgressDrawable(getDrawable(Color.RED));

        }
        else if(catName!=null && catName.equalsIgnoreCase("Grocery")){

            holder.layout_dashboard_item.setBackgroundColor(Color.parseColor("#f0f7fb"));
            holder.expenseImage.setImageResource(R.drawable.ic_groceries);
            holder.seekBar.setProgressDrawable(getDrawable(Color.GREEN));

        }else if(catName!=null && catName.equalsIgnoreCase("Food and Drinks")){

            holder.layout_dashboard_item.setBackgroundColor(Color.parseColor("#f0f7fb"));
            holder.expenseImage.setImageResource(R.drawable.ic_groceries);
            holder.seekBar.setProgressDrawable(getDrawable(Color.GRAY));

        }
        else if(catName!=null && catName.equalsIgnoreCase("Traveling")){

            holder.layout_dashboard_item.setBackgroundColor(Color.parseColor("#fcf7ec"));

            holder.expenseImage.setImageResource(R.drawable.ic_luggage);
            holder.seekBar.setProgressDrawable(getDrawable(Color.BLUE));

        }
        else if(catName!=null && catName.equalsIgnoreCase("Fashion")){

            holder.layout_dashboard_item.setBackgroundColor(Color.parseColor("#fcf7f7"));

            holder.expenseImage.setImageResource(R.drawable.ic_shirt);
            holder.seekBar.setProgressDrawable(getDrawable(Color.BLACK));
        }
        else if(catName!=null && catName.equalsIgnoreCase("Entertainment")){

            holder.layout_dashboard_item.setBackgroundColor(Color.parseColor("#f6f7f9"));

            holder.expenseImage.setImageResource(R.drawable.ic_tickets);
            holder.seekBar.setProgressDrawable(getDrawable(Color.YELLOW));
        }
        else if(catName!=null && catName.equalsIgnoreCase("HealthCare")){

            holder.layout_dashboard_item.setBackgroundColor(Color.parseColor("#fcf7f7"));

            holder.expenseImage.setImageResource(R.drawable.ic_heart);
            holder.seekBar.setProgressDrawable(getDrawable(Color.CYAN));

        }
        holder.percentTextView.setText(progress+"%");
//        holder.seekBar.setProgress(progress);






//        Drawable drawable =  holder.seekBar.getProgressDrawable();


//        if (drawable instanceof LayerDrawable){
//            Log.e("","");
//            LayerDrawable layerDrawable = (LayerDrawable)drawable;
//            GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable
//                    .findDrawableByLayerId(android.R.id.progress);
//            gradientDrawable.setStroke(2,Color.BLACK); // change color
//            holder.seekBar.setProgressDrawable(layerDrawable);
//        }

        holder.seekBar.setProgress(progress);

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private LayerDrawable getDrawable(int color){
        LayerDrawable layerDrawable = (LayerDrawable) context.getResources()
                .getDrawable(R.drawable.progress);


        ClipDrawable gradientDrawable = (ClipDrawable) layerDrawable
                .findDrawableByLayerId(R.id.gradientDrawble);

        ((GradientDrawable)gradientDrawable.getDrawable()).setStroke(8,color); // change color
        ((GradientDrawable)gradientDrawable.getDrawable()).setCornerRadius(5);
        return layerDrawable;
    }


}
