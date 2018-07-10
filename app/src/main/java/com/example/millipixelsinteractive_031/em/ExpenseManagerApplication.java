package com.example.millipixelsinteractive_031.em;

import android.app.Application;


import com.example.millipixelsinteractive_031.em.database.ExpenseCategoryDataSource;
import com.example.millipixelsinteractive_031.em.model.ExpenseCategory;
import com.example.millipixelsinteractive_031.em.utils.Utility;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by millipixelsinteractive_024 on 22/01/18.
 */

public class ExpenseManagerApplication extends Application{
    ArrayList<ExpenseCategory> arrayList = new ArrayList<>();
    ExpenseCategoryDataSource expenseCategoryDataSource;
    @Override
    public void onCreate() {
        super.onCreate();
        expenseCategoryDataSource = new ExpenseCategoryDataSource(getApplicationContext());
        try {
            expenseCategoryDataSource.open();
            if (expenseCategoryDataSource.getAllCategories().size()  == 0){
                initCategories();
                for (ExpenseCategory expenseCategory : arrayList){
                    expenseCategoryDataSource.createCategory(expenseCategory);
                }
                expenseCategoryDataSource.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void initCategories(){
        arrayList.clear();
        arrayList.add(new ExpenseCategory("Food",Utility.getRandomColor()));
        arrayList.add(new ExpenseCategory("Grocery", Utility.getRandomColor()));
        arrayList.add(new ExpenseCategory("Traveling",Utility.getRandomColor()));
        arrayList.add(new ExpenseCategory("Fashion",Utility.getRandomColor()));
        arrayList.add(new ExpenseCategory("HealthCare",Utility.getRandomColor()));
    }
}
