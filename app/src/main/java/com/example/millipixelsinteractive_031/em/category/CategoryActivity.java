package com.example.millipixelsinteractive_031.em.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.millipixelsinteractive_031.em.R;
import com.example.millipixelsinteractive_031.em.adapter.CategoryAdapter;
import com.example.millipixelsinteractive_031.em.addexpense.AddExpense;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by millipixelsinteractive_031 on 12/03/18.
 */

public class CategoryActivity extends Activity implements CategoryAdapter.OnItemClickListener {

    @BindView(R.id.category_recyclerView)
    RecyclerView category_recyclerView;

    CategoryAdapter adapter;
    List<String> arrayList;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.category);

        ButterKnife.bind(this);

        arrayList= Arrays.asList(getResources().getStringArray(R.array.categoryArray));

        adapter=new CategoryAdapter(arrayList,this,this);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        category_recyclerView.setLayoutManager(mLayoutManager);
        category_recyclerView.setItemAnimator(new DefaultItemAnimator());

        category_recyclerView.setAdapter(adapter);



    }

    @Override
    public void onItemClick(int pos) {
        Intent intent=new Intent();
        intent.putExtra("catName",arrayList.get(pos));
        setResult(RESULT_OK,intent);
         finish();


    }
}
