package com.example.millipixelsinteractive_031.em.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.millipixelsinteractive_031.em.R;
import com.example.millipixelsinteractive_031.em.adapter.CategoryAdapter;
import com.example.millipixelsinteractive_031.em.addexpense.AddExpense;
import com.example.millipixelsinteractive_031.em.database.ExpenseCategoryDataSource;
import com.example.millipixelsinteractive_031.em.model.ExpenseCategory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by millipixelsinteractive_031 on 12/03/18.
 */

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.OnItemClickListener {

    @BindView(R.id.category_recyclerView)
    RecyclerView category_recyclerView;

    CategoryAdapter adapter;
//    List<String> arrayList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ExpenseCategoryDataSource expenseCategoryDataSource;
    ArrayList<ExpenseCategory> expenseCategoryArrayList = new ArrayList<ExpenseCategory>();
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        ButterKnife.bind(this);
        initToolBar();
        getCategories();
//        arrayList= Arrays.asList(getResources().getStringArray(R.array.categoryArray));
        adapter=new CategoryAdapter(expenseCategoryArrayList,this,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        category_recyclerView.setLayoutManager(mLayoutManager);
        category_recyclerView.setItemAnimator(new DefaultItemAnimator());
        category_recyclerView.setAdapter(adapter);
    }
    private void getCategories(){
        expenseCategoryArrayList.clear();
        expenseCategoryDataSource = new ExpenseCategoryDataSource(this);
        try {
            expenseCategoryDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        expenseCategoryArrayList.addAll(expenseCategoryDataSource.getAllCategories());
        expenseCategoryDataSource.close();

    }

    public void initToolBar() {
        toolbar.setTitle(R.string.choose_category);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                }

        );
    }
    @Override
    public void onItemClick(int pos) {
        Intent intent=new Intent();
        intent.putExtra("catName",expenseCategoryArrayList.get(pos).getCatName());
        intent.putExtra("catId",expenseCategoryArrayList.get(pos).getId());
        setResult(RESULT_OK,intent);
         finish();


    }
}
