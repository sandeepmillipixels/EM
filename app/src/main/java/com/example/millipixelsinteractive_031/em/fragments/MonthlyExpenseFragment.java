package com.example.millipixelsinteractive_031.em.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.millipixelsinteractive_031.em.R;
import com.example.millipixelsinteractive_031.em.adapter.DashboardAdapter;
import com.example.millipixelsinteractive_031.em.database.SqliteDatabaseClass;
import com.example.millipixelsinteractive_031.em.model.Data;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthlyExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthlyExpenseFragment extends Fragment {

    SqliteDatabaseClass db;

    DashboardAdapter adapter;

    ArrayList<Data> arrayList;

    RecyclerView recyclerView;

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
        db=new SqliteDatabaseClass(getActivity());



        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        arrayList= db.getAllRecords();

        if(arrayList.size()!=0){
            adapter=new DashboardAdapter(arrayList,getActivity());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

        }

    }
}
