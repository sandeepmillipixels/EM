package com.application.millipixels.expense_rocket.dashboard;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.adapter.DashboardAdapter;
import com.application.millipixels.expense_rocket.adapter.ViewPagerAdapter;
import com.application.millipixels.expense_rocket.addexpense.AddExpense;
import com.application.millipixels.expense_rocket.database.AllExpensesDataSource;
import com.application.millipixels.expense_rocket.fragments.MonthlyExpenseFragment;
import com.application.millipixels.expense_rocket.gallery.GalleyActivity;
import com.application.millipixels.expense_rocket.login_signup.LoginSignupActivity;
import com.application.millipixels.expense_rocket.settings.SettingsActivity;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.sql.SQLException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    @BindView(R.id.chart1)
    LineChart mChart;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    DashboardAdapter adapter;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.txtTotalAmount)
    TextView txtTotalAmount;
    AllExpensesDataSource allExpensesDataSource;
    ViewPagerAdapter viewPagerAdapter;
    float totalAmount = 0;

    FloatingActionsMenu rightLabels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        allExpensesDataSource = new AllExpensesDataSource(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        rightLabels =findViewById(R.id.fab);


        com.getbase.floatingactionbutton.FloatingActionButton open_shoe_box = new com.getbase.floatingactionbutton.FloatingActionButton(this);
        open_shoe_box.setTitle("Shoebox");
        open_shoe_box.setColorNormalResId(R.color.pink);
        open_shoe_box.setIcon(R.drawable.ic_tag_add_expense);
        rightLabels.addButton(open_shoe_box);

        open_shoe_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,GalleyActivity.class);
                intent.putExtra("dashboard","1");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(Dashboard.this).toBundle());
                }else{
                    startActivity(intent);
                }

                rightLabels.collapse();

            }
        });



//        com.getbase.floatingactionbutton.FloatingActionButton shoe_box = new com.getbase.floatingactionbutton.FloatingActionButton(this);
//        shoe_box.setTitle("Add to shoebox");
//        shoe_box.setColorNormalResId(R.color.colorPrimary);
//        shoe_box.setIcon(R.drawable.ic_collections_bookmark_black_24dp);
//        rightLabels.addButton(shoe_box);
//
//        shoe_box.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Dashboard.this,TabbedActivity.class);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(Dashboard.this).toBundle());
//                }else{
//                    startActivity(intent);
//                }
//
//                rightLabels.collapse();
//
//            }
//        });



        com.getbase.floatingactionbutton.FloatingActionButton add_expense = new com.getbase.floatingactionbutton.FloatingActionButton(this);
        add_expense.setTitle("Add a expense");
        add_expense.setIcon(R.drawable.ic_tag_add_expense);
        rightLabels.addButton(add_expense);
        rightLabels.removeButton(add_expense);
        rightLabels.addButton(add_expense);

        add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,AddExpense.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(Dashboard.this).toBundle());
                }else{
                    startActivity(intent);
                }

                rightLabels.collapse();
            }
        });


        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        mChart = findViewById(R.id.chart1);
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(false);

        // enable scaling and dragging
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);


        // x-axis limit line
        LimitLine llXAxis = new LimitLine(0f, "");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(0f, 0f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);
        llXAxis.setTextColor(Color.WHITE);

        XAxis xAxis = mChart.getXAxis();
        xAxis.enableGridDashedLine(50f, 0f, 0f);
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaximum(50f);
        leftAxis.setAxisMinimum(0f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(50f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        setData(5, 50);

//        mChart.setVisibleXRange(20);
//        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mChart.centerViewTo(20, 50, AxisDependency.LEFT);

        mChart.animateX(1000);
        //mChart.invalidate();

        mChart.getXAxis().setTextColor(Color.WHITE);
        mChart.getAxisLeft().setTextColor(Color.WHITE);

        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getAxisRight().setDrawGridLines(false);
        //mChart.getXAxis().setDrawLabels(false);
        mChart.getLegend().setEnabled(false);   // Hide the legend

        // get the legend (only possible after setting data)
//        Legend l = mChart.getLegend();
//
//        // modify the legend ...
//        l.setForm(Legend.LegendForm.LINE);


    }


    private void getTotalAmoutExpense(){
        try {
            allExpensesDataSource.open();
            totalAmount = allExpensesDataSource.getSumAllExpenses();
            allExpensesDataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txtTotalAmount.setText("$"+totalAmount);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(MonthlyExpenseFragment.newInstance(), "Monthly");
        viewPagerAdapter.addFragment(MonthlyExpenseFragment.newInstance(), "Weekly");
        viewPagerAdapter.addFragment(MonthlyExpenseFragment.newInstance(), "Daily");
        viewPager.setAdapter(viewPagerAdapter);

        setCustomFont();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.dashboard, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }


        if(id==R.id.sign_out_manage){

            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
            preferences.edit().clear().commit();

            Intent intent = new Intent(Dashboard.this, LoginSignupActivity.class);
            startActivity(intent);
            finish();

        }

        if (id == R.id.nav_manage){
            Intent intent = new Intent(Dashboard.this, SettingsActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTotalAmoutExpense();
        viewPagerAdapter.notifyDataSetChanged();

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


    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<Entry>();



        ArrayList<Integer> data1= new ArrayList<>();
        data1.add(10);
        data1.add(40);
        data1.add(15);
        data1.add(30);
        data1.add(10);
        data1.add(40);


        for (int i = 0; i <= data1.size() - 1; i++) {

            values.add(new Entry(i,data1.get(i)));
        }


//        for (int i = 0; i < count; i++) {
//
//            float val = (float) (Math.random() * range) + 3;
//            values.add(new Entry(i, val, getResources().getDrawable(R.drawable.app_round_button_drawable)));
//
//        }





        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();

            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setDrawVerticalHighlightIndicator(false);
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
//            set1 = new LineDataSet(values, "DataSet 1");
            set1 = new LineDataSet(values, "");
            set1.setDrawIcons(false);

            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the line to be drawn like this "- - - - - -"
            //set1.enableDashedLine(10f, 5f, 0f);
            // set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.WHITE);
            set1.setCircleColor(getResources().getColor(R.color.greenFloating));
            set1.setValueTextColor(Color.WHITE);
            set1.setLineWidth(1f);
            set1.setCircleRadius(4f);
            set1.setDrawCircleHole(false);

            set1.setValueTextSize(9f);
            // set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);
            set1.setDrawValues(false);
            set1.setDrawFilled(true);




            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.chart_filed);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.WHITE);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mChart.setData(data);
        }


    }

}
