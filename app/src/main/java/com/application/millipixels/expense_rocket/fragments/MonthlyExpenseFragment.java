package com.application.millipixels.expense_rocket.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.adapter.DashboardAdapter;
import com.application.millipixels.expense_rocket.database.AllExpensesDataSource;
import com.application.millipixels.expense_rocket.database.ExpenseCategoryDataSource;
import com.application.millipixels.expense_rocket.model.ExpenseCategory;
import com.application.millipixels.expense_rocket.utils.Utility;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthlyExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthlyExpenseFragment extends Fragment {

    ArrayList<ExpenseCategory> expenseCategoryArrayList = new ArrayList<>();
    ExpenseCategoryDataSource expenseCategoryDataSource;
    AllExpensesDataSource allExpensesDataSource;
    RecyclerView recyclerView;
    DashboardAdapter adapter;
    public MonthlyExpenseFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MonthlyExpenseFragment newInstance() {
        MonthlyExpenseFragment fragment = new MonthlyExpenseFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dashboard_fragment,
                container, false);
        recyclerView=rootView.findViewById(R.id.recyclerView);
        expenseCategoryDataSource = new ExpenseCategoryDataSource(getActivity());
        allExpensesDataSource = new AllExpensesDataSource(getActivity());

        return rootView;
    }



    private void setAdapter(){
            adapter=new DashboardAdapter(expenseCategoryArrayList,getActivity());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
    }

    private void getMonthlyDate(){
        Calendar calendar = Calendar.getInstance();


            String startDate = "",endDate = "";
            startDate  = Calendar.getInstance().get(Calendar.YEAR)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+calendar.getActualMinimum(Calendar.DATE) +" "+ "00:00:00";
            endDate = Calendar.getInstance().get(Calendar.YEAR)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+calendar.getActualMaximum(Calendar.DATE) +" "+ "23:59:59";


        try {
            expenseCategoryArrayList.clear();
            expenseCategoryDataSource.open();
            expenseCategoryArrayList.addAll(expenseCategoryDataSource.getAllCategories());
            expenseCategoryDataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            allExpensesDataSource.open();

            for (ExpenseCategory expenseCategory : expenseCategoryArrayList){
                float sumLocal = allExpensesDataSource.getSum(expenseCategory.getCatName(), Utility.getTimeInMilli(startDate),Utility.getTimeInMilli(endDate));
                if (sumLocal > 0)
                expenseCategory.setAmount(sumLocal);
            }
            allExpensesDataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Iterator<ExpenseCategory> iterator = expenseCategoryArrayList.iterator(); iterator.hasNext(); ) {
            ExpenseCategory value = iterator.next();
            if (value.getAmount() == 0) {
                iterator.remove();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getMonthlyDate();
        setAdapter();
    }
}
