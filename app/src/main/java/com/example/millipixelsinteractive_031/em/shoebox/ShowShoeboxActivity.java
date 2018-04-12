package com.example.millipixelsinteractive_031.em.shoebox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.millipixelsinteractive_031.em.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowShoeboxActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_shoebox)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shoebox);
        ButterKnife.bind(this);
        initToolBar();
    }
    public void initToolBar() {

        toolbar.setTitle(R.string.shoebox);
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
