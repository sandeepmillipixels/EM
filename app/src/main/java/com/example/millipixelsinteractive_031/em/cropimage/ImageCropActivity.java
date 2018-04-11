package com.example.millipixelsinteractive_031.em.cropimage;

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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.millipixelsinteractive_031.em.R;
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

    @BindView(R.id.done_crop_button)
    Button done_crop_button;

    String path;

    Uri uri;

    Bitmap bitmap;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_image);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.toolbar_crop_image);

        initToolBar();

        path=getIntent().getStringExtra("path");

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
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    @OnClick(R.id.fabCrop)
    void doneOnClick(){

        cropImageView.getCroppedImageAsync();
// or
        Bitmap cropped = cropImageView.getCroppedImage();
        cropImageView.setImageBitmap(cropped);
    }


    @OnClick(R.id.fabRotate)
    void cropImageView(){

//        cropImageView.getCroppedImageAsync();
// or
//        Bitmap cropped = cropImageView.rotateImage(90);
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

    @OnClick(R.id.done_crop_button)
    void doneCroping(){

        cropImageView.getCroppedImageAsync();
// or
        bitmap= cropImageView.getCroppedImage();

        Intent intent=new Intent();
        intent.putExtra("uri",bitmap);
        setResult(RESULT_OK,intent);
        finish();

    }




}
