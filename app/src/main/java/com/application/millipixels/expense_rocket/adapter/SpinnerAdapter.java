package com.application.millipixels.expense_rocket.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.model.CountryCodeData;

import java.util.List;

/**
 * Created by millipixelsinteractive_031 on 19/03/18.
 */

public class SpinnerAdapter extends ArrayAdapter<CountryCodeData> {

    Context context;
    int resource;
    List<CountryCodeData> data;


    public SpinnerAdapter(Context context, int resource,List<CountryCodeData> data) {
        super(context, resource,data);
        this.context=context;
        this.resource=resource;
        this.data=data;

    }


    @Override
    public int getCount(){
        return data.size();
    }

    @Override
    public CountryCodeData getItem(int position){
        return data.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        com.application.millipixels.expense_rocket.typeface.FontsClass label = new com.application.millipixels.expense_rocket.typeface.FontsClass(context);
        label.setTextColor(Color.BLACK);
        label.setPadding(10,0,10,0);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(data.get(position).getName());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.custom_country_item,null);

        TextView label = view.findViewById(R.id.code_textview);

        label.setText(data.get(position).getName());
        return label;
    }

}
