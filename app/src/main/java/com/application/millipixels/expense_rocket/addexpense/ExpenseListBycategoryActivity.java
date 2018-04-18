package com.application.millipixels.expense_rocket.addexpense;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.adapter.ViewPagerAdapter;
import com.application.millipixels.expense_rocket.database.AllExpensesDataSource;
import com.application.millipixels.expense_rocket.fragments.CategoryRecordsFragment;
import com.application.millipixels.expense_rocket.model.ExpenseCategory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpenseListBycategoryActivity extends AppCompatActivity {




    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabLayout_record)
    TabLayout tabLayout;

    @BindView(R.id.viewPager_record)
    ViewPager viewPager;

    @BindView(R.id.txtTotalAmount)
    TextView txtTotalAmount;

    ViewPagerAdapter viewPagerAdapter;
    AllExpensesDataSource allExpensesDataSource;

    ExpenseCategory expenseCategory;

    float totlaExpense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list_bycategory2);
        ButterKnife.bind(this);


        allExpensesDataSource = new AllExpensesDataSource(this);



        if (getIntent().getExtras() != null){
            expenseCategory = getIntent().getParcelableExtra("category");
        }

        String categoryName=expenseCategory.getCatName();

        totlaExpense=allExpensesDataSource.getSum(categoryName);

        txtTotalAmount.setText(String.valueOf("$"+totlaExpense));

        initToolBar();
        viewPager.setOffscreenPageLimit(3);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

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

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(CategoryRecordsFragment.newInstance(), "Monthly");
        viewPagerAdapter.addFragment(CategoryRecordsFragment.newInstance(), "Weekly");
        viewPagerAdapter.addFragment(CategoryRecordsFragment.newInstance(), "Daily");
        viewPager.setAdapter(viewPagerAdapter);

        setCustomFont();
    }

    public void setCustomFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();

        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

            int tabChildsCount = vgTab.getChildCount();

            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf"));
                }
            }
        }
    }

}
