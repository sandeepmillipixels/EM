package com.application.millipixels.expense_rocket.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.adapter.CategoryAdapter;
import com.application.millipixels.expense_rocket.database.ExpenseCategoryDataSource;
import com.application.millipixels.expense_rocket.model.ExpenseCategory;

import java.sql.SQLException;
import java.util.ArrayList;

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
    boolean isSelect = false ;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        setupWindowAnimations();
        ButterKnife.bind(this);
        initToolBar();
        getCategories();
        setAdapter();

//        arrayList= Arrays.asList(getResources().getStringArray(R.array.categoryArray));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this,AddEditCategoryActivity.class);
                intent.putExtra("isNew",true);
                startActivityForResult(intent,100);
            }
        });
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("select")){
            isSelect = true ;
            fab.setVisibility(View.GONE);
        }

    }


    private void setupWindowAnimations(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(500);
            slide.setSlideEdge(Gravity.LEFT);
            getWindow().setReenterTransition(slide);
            getWindow().setExitTransition(slide);
        }
    }
    private void setAdapter(){
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
        if (isSelect){
            Intent intent=new Intent();
            intent.putExtra("catName",expenseCategoryArrayList.get(pos).getCatName());
            intent.putExtra("catId",expenseCategoryArrayList.get(pos).getId());
            setResult(RESULT_OK,intent);
            finish();
        }else {
//            final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants()
            Intent intent = new Intent(CategoryActivity.this,AddEditCategoryActivity.class);
            intent.putExtra("isNew",false);
            intent.putExtra("category",expenseCategoryArrayList.get(pos));
            startActivityForResult(intent,100);
        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            getCategories();
            setAdapter();
        }
    }
}
