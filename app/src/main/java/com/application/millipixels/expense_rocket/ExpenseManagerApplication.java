package com.application.millipixels.expense_rocket;

import android.app.Application;


import com.application.millipixels.expense_rocket.database.ExpenseCategoryDataSource;
import com.application.millipixels.expense_rocket.model.ExpenseCategory;
import com.application.millipixels.expense_rocket.utils.Utility;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

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
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
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
