package com.application.millipixels.expense_rocket.gallery;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.application.millipixels.expense_rocket.EditImageActivity;
import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;
import com.application.millipixels.expense_rocket.pdf_opener.PdfOpenActivity;


import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

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

    private static final int MY_CAMERA_REQUEST_CODE = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gallery);

        ButterKnife.bind(this);

        int cameraPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
        int writeStoragePermission=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readStoragePermission=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);

        setUpToolbar();

        dashboard=getIntent().getStringExtra("dashboard");


        if (cameraPermission == PackageManager.PERMISSION_DENIED && writeStoragePermission ==PackageManager.PERMISSION_DENIED && readStoragePermission ==PackageManager.PERMISSION_DENIED){


            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);


        }else{


            path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PDFfiles/JPEG/";
            getFiles();



        }




    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }

    private void getFiles() {
        if(path!=null){
            f = new File(path);

            if (!f.exists()) {
                boolean success = f.mkdir();
                if (!success) {
                    Toast.makeText(this, "Error on creating application folder", Toast.LENGTH_SHORT).show();

                }
            }

            File file[] = f.listFiles();

            if(file!=null){
                tempList = Arrays.asList(file);

                adapter=new GalleryAdapter(this,tempList,this);
                setAdapter();
            }


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
        Intent intent=new Intent(GalleyActivity.this, EditImageActivity.class);
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
         //if (dashboard!= null &&dashboard.equals("1")){
             String path=tempList.get(pos).getAbsoluteFile().getPath();

             StringTokenizer st = new StringTokenizer(path, "/");
             String storage = st.nextToken();
             String emulated = st.nextToken();
             String zero = st.nextToken();
             String pdfFiles = st.nextToken();
             String JPEG = st.nextToken();
             String jpgPath = st.nextToken();

             jpgPath=jpgPath.replace(".jpg",".pdf");

             jpgPath = "/storage/emulated/0/PDFfiles/"+jpgPath;

             Intent intent=new Intent(GalleyActivity.this, PdfOpenActivity.class);
             intent.putExtra("path",jpgPath);
             startActivity(intent);

//         }else {
//             String path=tempList.get(pos).getAbsoluteFile().getPath();
//
//             Intent intent = new Intent();
//             intent.putExtra("path",path);
//             setResult(500,intent);
//             finish();
//         }

    }

}
