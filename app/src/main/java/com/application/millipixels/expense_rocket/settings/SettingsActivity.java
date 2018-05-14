package com.application.millipixels.expense_rocket.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.category.CategoryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    RelativeLayout layout_categories, layout_backup;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setUpToolbar();
        setWidgets();
        setOnClickListeners();
    }
    private void setOnClickListeners(){

        layout_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });

        layout_backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,BackupActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setWidgets(){
        layout_categories = (RelativeLayout)findViewById(R.id.layout_categories);
        layout_backup = (RelativeLayout)findViewById(R.id.layout_backup);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
    private void setUpToolbar(){
        toolbar.setTitle(R.string.action_settings);
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
}
