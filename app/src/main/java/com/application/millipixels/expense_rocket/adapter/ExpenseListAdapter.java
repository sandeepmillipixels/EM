package com.application.millipixels.expense_rocket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.model.AllExpenses;

import java.util.List;

/**
 * Created by millipixelsinteractive_024 on 16/03/18.
 */

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ProductViewHolder> {

    List<AllExpenses> category;
    Context context;
    String color ;

    public class ProductViewHolder extends RecyclerView.ViewHolder {


        TextView txtDate,txtAmount,txtNote,txtName;
        public ProductViewHolder(View view) {
            super(view);
            txtDate = (TextView)view.findViewById(R.id.txtDate);
            txtName = (TextView)view.findViewById(R.id.txtName);
            txtAmount = (TextView)view.findViewById(R.id.txtAmount);
            txtNote = (TextView)view.findViewById(R.id.txtNote);
        }
    }
    public ExpenseListAdapter(List<AllExpenses> category, Context context,String color) {
        this.category = category;
        this.context = context;
        this.color = color;

    }

    @Override
    public ExpenseListAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_list_item, parent, false);
        return new ExpenseListAdapter.ProductViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ExpenseListAdapter.ProductViewHolder holder, final int position) {
        AllExpenses expenseCategory = category.get(position);
        holder.txtNote.setText(expenseCategory.getExpense_note());
        holder.txtName.setText(expenseCategory.getExpense_name());
        holder.txtAmount.setText("$"+expenseCategory.getExpense_amount()+"");
        holder.txtDate.setText(expenseCategory.getExpense_date()+"");
//        holder.txtAmount.setTextColor(Color.parseColor(color));
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

}





