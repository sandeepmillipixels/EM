package com.application.millipixels.expense_rocket.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.database.ExpenseCategoryDataSource;

import com.application.millipixels.expense_rocket.model.ExpenseCategory;
import com.application.millipixels.expense_rocket.utils.Utility;

import java.sql.SQLException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEditCategoryActivity extends AppCompatActivity {
    Button btnCancel,btnSave;
    String categoryName = "";
    EditText edtCategory;
    CoordinatorLayout coordinatorLayout;
    boolean isNew = false;
    String title = "Add Category";
    ExpenseCategory expenseCategory;
    ExpenseCategoryDataSource expenseCategoryDataSource;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_category);
        ButterKnife.bind(this);
        expenseCategoryDataSource = new ExpenseCategoryDataSource(this);
        setWidgets();
        if (getIntent().getExtras() != null){
            isNew = getIntent().getBooleanExtra("isNew",false);
            if (isNew){
                expenseCategory = new ExpenseCategory();
            }else {
                title = "Edit Category";
                expenseCategory = getIntent().getParcelableExtra("category");
                edtCategory.setText(expenseCategory. getCatName());
            }
        }
        setListeners();
        setUpToolbar();

    }
    private void setUpToolbar(){
        toolbar.setTitle(title);
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

    private void setWidgets(){
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnSave = (Button)findViewById(R.id.btnSave);
        edtCategory = (EditText) findViewById(R.id.edtCategory);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
    }
    private void setListeners(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryName = edtCategory.getText().toString().trim();
                if (categoryName.length() == 0){
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Please enter category.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }else {
                    try {
                        expenseCategoryDataSource.open();
                        ArrayList<ExpenseCategory> categories = expenseCategoryDataSource.getAllCategories(categoryName);
                        if (categories.size() == 0){
                            if (isNew){
                                expenseCategory.setCatName(categoryName);
                                expenseCategory.setColorCode(Utility.getRandomColor());
                                expenseCategoryDataSource.createCategory(expenseCategory);
                            }else {
                                expenseCategory.setCatName(categoryName);
                                expenseCategoryDataSource.updateCategory(expenseCategory.getId(),expenseCategory);
                            }
                            expenseCategoryDataSource.close();
                            Intent intent = new Intent();
                            setResult(RESULT_OK,intent);
                            finish();
                        }else {
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, "Category already exist.", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
