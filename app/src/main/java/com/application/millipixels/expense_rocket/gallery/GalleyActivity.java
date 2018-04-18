package com.application.millipixels.expense_rocket.gallery;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.pdf_opener.PdfOpenActivity;
import com.application.millipixels.expense_rocket.shoebox.TabbedActivity;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by millipixelsinteractive_031 on 16/04/18.
 */

public class GalleyActivity extends AppCompatActivity implements GalleryAdapter.IOnclick {


    @BindView(R.id.toolbar_gallery)
    Toolbar toolbar;

    @BindView(R.id.gridview)
    GridView gridView;

    @BindView(R.id.fab_shoebox)
    FloatingActionButton floatingButton;

    GalleryAdapter adapter;

    String path;
    File f;

    List<File> tempList;


    String dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gallery);

        ButterKnife.bind(this);


        setUpToolbar();

        dashboard=getIntent().getStringExtra("dashboard");

        path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PDFfiles/";
        getFiles();




    }

    private void getFiles() {
        if(path!=null){
            f = new File(path);
            File file[] = f.listFiles();

            tempList = Arrays.asList(file);

            adapter=new GalleryAdapter(this,tempList,this);
            setAdapter();
        }
    }


    private void setUpToolbar(){
        toolbar.setTitle(R.string.shoebox_gallery);
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

    private void setAdapter(){
        gridView.setAdapter(adapter);
    }

    @OnClick(R.id.fab_shoebox)
     void fab_shoeboxClick(){
        Intent intent=new Intent(GalleyActivity.this, TabbedActivity.class);
        intent.putExtra("shoebox",true);
        startActivityForResult(intent,100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            getFiles();
        }
    }

    @Override
    public void onClick(int pos) {
         if (dashboard!= null &&dashboard.equals("1")){
             String path=tempList.get(pos).getAbsoluteFile().getPath();

                             Intent intent=new Intent(GalleyActivity.this, PdfOpenActivity.class);
                intent.putExtra("path",path);
                startActivity(intent);
         }else {
             String path=tempList.get(pos).getAbsoluteFile().getPath();




             Intent intent = new Intent();
             intent.putExtra("path",path);
             setResult(500,intent);
             finish();
         }

    }
}
