package com.application.millipixels.expense_rocket.cropimage;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.shoebox.TabbedActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by millipixelsinteractive_031 on 10/04/18.
 */

public class ImageCropActivity extends AppCompatActivity {

    @BindView(R.id.cropImageView)
    CropImageView cropImageView;

    @BindView(R.id.fabCrop)
    FloatingActionButton crop_button;


    String path;

    Uri uri;

    Bitmap bitmap;

    Toolbar toolbar;

    Bitmap cropped;

    int page=0;

    boolean visibility=false;
    MenuItem registrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_image);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar_crop_image);

        initToolBar();


        path=getIntent().getStringExtra("path");
        page = getIntent().getIntExtra(TabbedActivity.PAGE,0);

        if(path!=null){
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFile(path,bmOptions);
            uri=getImageUri(this,bitmap);

            cropImageView.setImageUriAsync(uri);
// or (prefer using uri for performance and better user experience)
            cropImageView.setImageBitmap(bitmap);

        }

        cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            @Override
            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {

            }
        });


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {

        if(inImage!=null){
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);

        }
        return Uri.parse(path);

    }


    @OnClick(R.id.fabCrop)
    void doneOnClick(){

        cropImageView.getCroppedImageAsync();
        cropped = cropImageView.getCroppedImage();
        cropImageView.setImageBitmap(cropped);

        visibility=true;
        registrar.setVisible(visibility);


    }


    @OnClick(R.id.fabRotate)
    void cropImageView(){

        cropImageView.rotateImage(90);
    }

    public void initToolBar() {

        toolbar.setTitle(R.string.crop_image);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crop, menu);

        registrar = menu.findItem(R.id.action_crop);
        registrar.setVisible(visibility);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_crop){

            Uri uriPath=getImageUri(this,cropped);

            String path=getRealPathFromURI(uriPath);

            Intent intent=new Intent();
            intent.putExtra("path",path);
            intent.putExtra("position",page);
            setResult(RESULT_OK,intent);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


}
