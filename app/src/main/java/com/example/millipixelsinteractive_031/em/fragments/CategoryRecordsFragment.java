package com.example.millipixelsinteractive_031.em.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by millipixelsinteractive_031 on 28/03/18.
 */

public class CategoryRecordsFragment extends Fragment {

    ExpenseCategory expenseCategory;
    RecyclerView recyclerView;
    TextView txtCategoryName,txtAmount;
    ImageView ico_categories;
    ArrayList<AllExpenses> expenseCategoryArrayList = new ArrayList<>();
    ExpenseListAdapter expenseListAdapter;
    AllExpensesDataSource allExpensesDataSource;

    // TODO: Rename and change types and number of parameters
    public static CategoryRecordsFragment newInstance() {
        CategoryRecordsFragment fragment = new CategoryRecordsFragment();

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

        View rootView = inflater.inflate(R.layout.category_record_fragment,
                container, false);

        recyclerView=rootView.findViewById(R.id.rvExpenseList);

        return rootView;
    }

    private void setAdapter(){
        expenseListAdapter=new ExpenseListAdapter(expenseCategoryArrayList,getActivity(),expenseCategory.getColorCode());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(expenseListAdapter);
        expenseListAdapter.notifyDataSetChanged();
    }
    private void getCategoryExpenseList(){
        Calendar calendar = Calendar.getInstance();


        String startDate = "",endDate = "";

        startDate  = Calendar.getInstance().get(Calendar.YEAR)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+calendar.getActualMinimum(Calendar.DATE) +" "+ "00:00:00";
        endDate = Calendar.getInstance().get(Calendar.YEAR)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+calendar.getActualMaximum(Calendar.DATE) +" "+ "23:59:59";

        try {
            allExpensesDataSource.open();
            expenseCategoryArrayList.addAll(allExpensesDataSource.getAllExpensesbyCategoryId(expenseCategory.getId(), Utility.getTimeInMilli(startDate), Utility.getTimeInMilli(endDate)));
            allExpensesDataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    @Override
    public void onResume() {
        super.onResume();

        allExpensesDataSource = new AllExpensesDataSource(getActivity());

        if (getActivity().getIntent().getExtras() != null){
            expenseCategory = getActivity().getIntent().getParcelableExtra("category");
        }
        getCategoryExpenseList();
        setAdapter();

    }
}
