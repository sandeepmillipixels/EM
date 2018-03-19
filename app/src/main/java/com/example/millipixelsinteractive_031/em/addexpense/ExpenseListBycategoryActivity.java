package com.example.millipixelsinteractive_031.em.addexpense;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.millipixelsinteractive_031.em.R;
import com.example.millipixelsinteractive_031.em.adapter.ExpenseListAdapter;
import com.example.millipixelsinteractive_031.em.database.AllExpensesDataSource;
import com.example.millipixelsinteractive_031.em.model.AllExpenses;
import com.example.millipixelsinteractive_031.em.model.ExpenseCategory;
import com.example.millipixelsinteractive_031.em.utils.Utility;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpenseListBycategoryActivity extends AppCompatActivity {
    RecyclerView rvExpenseList;
    ExpenseCategory expenseCategory;
    TextView txtCategoryName,txtAmount;
    ImageView ico_categories;
    AllExpensesDataSource allExpensesDataSource;
    ArrayList<AllExpenses> expenseCategoryArrayList = new ArrayList<>();
    ExpenseListAdapter expenseListAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list_bycategory2);
        ButterKnife.bind(this);
        allExpensesDataSource = new AllExpensesDataSource(this);
        if (getIntent().getExtras() != null){
            expenseCategory = (ExpenseCategory)getIntent().getParcelableExtra("category");
        }
        initToolBar();
        setWidgets();
        getCategoryExpenseList();
        setAdapter();
        setData();
    }
    public void initToolBar() {

        toolbar.setTitle(expenseCategory.getCatName());
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
    private void setAdapter(){
        expenseListAdapter=new ExpenseListAdapter(expenseCategoryArrayList,this,expenseCategory.getColorCode());
        rvExpenseList.setLayoutManager(new LinearLayoutManager(this));
        rvExpenseList.setAdapter(expenseListAdapter);
        expenseListAdapter.notifyDataSetChanged();
    }
    private void getCategoryExpenseList(){
        Calendar calendar = Calendar.getInstance();


        String startDate = "",endDate = "";
//        if (groupBy.equalsIgnoreCase(Constants.TODAY)){
//            startDate  = Calendar.getInstance().get(Calendar.YEAR)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +" "+ "00:00:00";
//            endDate = Calendar.getInstance().get(Calendar.YEAR)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +" "+ "23:59:59";
//        }else if (groupBy.equalsIgnoreCase(Constants.WEEK)){
////            startDate  = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR);
////            endDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.YEAR);
//            startDate  = Calendar.getInstance().get(Calendar.YEAR)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+22 +" "+ "00:00:00";
//            endDate = Calendar.getInstance().get(Calendar.YEAR)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+27 +" "+ "23:59:59";
//        }else if (groupBy.equalsIgnoreCase(Constants.MONTH)){

            startDate  = Calendar.getInstance().get(Calendar.YEAR)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+calendar.getActualMinimum(Calendar.DATE) +" "+ "00:00:00";
            endDate = Calendar.getInstance().get(Calendar.YEAR)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+calendar.getActualMaximum(Calendar.DATE) +" "+ "23:59:59";
//        }else if (groupBy.equalsIgnoreCase(Constants.YEAR)){
//            startDate  = Calendar.getInstance().get(Calendar.YEAR)+"-"+1+"-"+1 +" "+ "00:00:00";
//            endDate = Calendar.getInstance().get(Calendar.YEAR)+"-"+12+"-"+31 +" "+ "23:59:59";
//        }
        try {
            allExpensesDataSource.open();
            expenseCategoryArrayList.addAll(allExpensesDataSource.getAllExpensesbyCategoryId(expenseCategory.getId(), Utility.getTimeInMilli(startDate), Utility.getTimeInMilli(endDate)));
            allExpensesDataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void setData(){
        txtAmount.setText("$"+expenseCategory.getAmount()+"");
        txtCategoryName.setText(expenseCategory.getCatName()+"");
        String catName = expenseCategory.getCatName()+"";
//        ico_categories.setText(expenseCategory.getCatName().charAt(0)+"");
//        GradientDrawable bgShape = (GradientDrawable)ico_categories.getBackground();
//        bgShape.setColor(Color.parseColor(expenseCategory.getColorCode()));
        if(catName!=null && catName.equalsIgnoreCase("Food")){

            ico_categories.setImageResource(R.drawable.ic_heart);


        }
        else if(catName!=null && catName.equalsIgnoreCase("Grocery")){


            ico_categories.setImageResource(R.drawable.ic_groceries);

        }
        else if(catName!=null && catName.equalsIgnoreCase("Traveling")){



            ico_categories.setImageResource(R.drawable.ic_luggage);

        }
        else if(catName!=null && catName.equalsIgnoreCase("Fashion")){



            ico_categories.setImageResource(R.drawable.ic_shirt);

        }
        else if(catName!=null && catName.equalsIgnoreCase("Entertainment")){



            ico_categories.setImageResource(R.drawable.ic_tickets);

        }
        else if(catName!=null && catName.equalsIgnoreCase("HealthCare")){



            ico_categories.setImageResource(R.drawable.ic_heart);

        }

    }
    private void setWidgets(){
        rvExpenseList = (RecyclerView)findViewById(R.id.rvExpenseList);
        ico_categories = (ImageView) findViewById(R.id.ico_categories);
        txtCategoryName = (TextView)findViewById(R.id.txtCategoryName);
        txtAmount = (TextView)findViewById(R.id.txtAmount);
    }
}
