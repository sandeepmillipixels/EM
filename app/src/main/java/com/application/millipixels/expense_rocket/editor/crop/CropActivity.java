package com.application.millipixels.expense_rocket.editor.crop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.wang.avi.AVLoadingIndicatorView;
import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.view.GestureCropImageView;
import com.yalantis.ucrop.view.OverlayView;
import com.yalantis.ucrop.view.TransformImageView;
import com.yalantis.ucrop.view.UCropView;

import java.io.File;

public class CropActivity extends AppCompatActivity implements TransformImageView.TransformImageListener,
        CropAdapter.OnItemCropClickedListener,
        View.OnClickListener {

    private static final String INPUT_URL = "inputUrl";

    UCropView ivCrop;
    AVLoadingIndicatorView ivLoading;
    RecyclerView list;
    ImageView ivCancel;
    TextView tvTitle;
    ImageView ivCheck;
    LinearLayout controller;
    RelativeLayout rootCrop;

    private GestureCropImageView mGestureCropImageView;
    private OverlayView mOverlayView;

    private OnCropListener onCropListener;

    public void setOnCropListener(OnCropListener onCropListener) {
        this.onCropListener = onCropListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        mappingView();
        initView();
    }
    private void initView() {
        CropAdapter cropAdapter = new CropAdapter(this);
        cropAdapter.setOnItemCropClickedListener(this);
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        list.setAdapter(cropAdapter);
        new StartSnapHelper().attachToRecyclerView(list);

        cropAdapter.add(new CropModel(R.drawable.pg_sdk_edit_crop_crop11_selected, "1:1", CropModel.Type.TYPE11));
        cropAdapter.add(new CropModel(R.drawable.pg_sdk_edit_crop_crop23_selected, "2:3", CropModel.Type.TYPE23));
        cropAdapter.add(new CropModel(R.drawable.pg_sdk_edit_crop_crop32_selected, "3:2", CropModel.Type.TYPE32));
        cropAdapter.add(new CropModel(R.drawable.pg_sdk_edit_crop_crop43_selected, "4:3", CropModel.Type.TYPE43));
        cropAdapter.add(new CropModel(R.drawable.pg_sdk_edit_crop_crop34_selected, "3:4", CropModel.Type.TYPE34));
        cropAdapter.add(new CropModel(R.drawable.pg_sdk_edit_crop_crop169_selected, "16:9", CropModel.Type.TYPE169));
        cropAdapter.add(new CropModel(R.drawable.pg_sdk_edit_crop_crop916_selected, "9:16", CropModel.Type.TYPE916));

        mGestureCropImageView = ivCrop.getCropImageView();
        mOverlayView = ivCrop.getOverlayView();

        mGestureCropImageView.setScaleEnabled(true);
        mGestureCropImageView.setRotateEnabled(true);
        mGestureCropImageView.setImageToWrapCropBounds(true);
        mGestureCropImageView.setTransformImageListener(this);

        try {
            if (getIntent().getExtras() != null) {
                showLoading();
                String inputUrl = getIntent().getExtras().getString(INPUT_URL);
                assert inputUrl != null;
                mGestureCropImageView.setImageUri(Uri.fromFile(new File(inputUrl)), Uri.fromFile(new File(inputUrl)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void mappingView() {
        ivCrop = findViewById(R.id.ivCrop);
        ivLoading = findViewById(R.id.ivLoading);
        list = findViewById(R.id.list);
        ivCancel = findViewById(R.id.ivCancel);
        tvTitle = findViewById(R.id.tvTitle);
        ivCheck = findViewById(R.id.ivCheck);
        controller = findViewById(R.id.controller);
        rootCrop = findViewById(R.id.rootCrop);

        ivCancel.setOnClickListener(this);
        ivCheck.setOnClickListener(this);
        rootCrop.setOnClickListener(this);
    }
    private void saveImage() {
        showLoading();
        mGestureCropImageView.cropAndSaveImage(Bitmap.CompressFormat.PNG, 100, new BitmapCropCallback() {

            @Override
            public void onBitmapCropped(@NonNull Uri resultUri, int offsetX, int offsetY, int imageWidth, int imageHeight) {
//                if (onCropListener != null)
//                    onCropListener.onCropPhotoCompleted(resultUri.getPath());
//                back();
                Intent intent = new Intent();
                intent.putExtra(INPUT_URL,resultUri.getPath());
                setResult(Activity.RESULT_OK, intent);
                finish();

            }

            @Override
            public void onCropFailure(@NonNull Throwable t) {
                back();
            }
        });
    }

    private void back() {
        hideLoading();
        onBackPressed();
    }


    @Override
    public void onLoadComplete() {
        hideLoading();
        changeCropType(1f / 1f);
    }

    @Override
    public void onLoadFailure(@NonNull Exception e) {

    }

    @Override
    public void onRotate(float currentAngle) {

    }

    @Override
    public void onScale(float currentScale) {

    }

    @Override
    public void onBrightness(float currentBrightness) {

    }

    @Override
    public void onContrast(float currentContrast) {

    }

    @Override
    public void onItemCropClicked(CropModel.Type type) {
        switch (type) {
            case TYPE11:
                changeCropType(1f / 1f);
                break;
            case TYPE23:
                changeCropType(2f / 3f);
                break;
            case TYPE32:
                changeCropType(3f / 2f);
                break;
            case TYPE43:
                changeCropType(4f / 3f);
                break;
            case TYPE34:
                changeCropType(3f / 4f);
                break;
            case TYPE169:
                changeCropType(16f / 9f);
                break;
            case TYPE916:
                changeCropType(9f / 16f);
                break;
        }
    }

    private void hideLoading() {
        if (ivLoading != null)
            ivLoading.smoothToHide();
    }

    private void showLoading() {
        if (ivLoading != null)
            ivLoading.smoothToShow();
    }

    private void changeCropType(float crop) {
        mOverlayView.setTargetAspectRatio(crop);
        mGestureCropImageView.setTargetAspectRatio(crop);
    }

    @Override
    public void onClick(View view) {
        if (ivLoading.isShown()) return;
        if (view.getId() == R.id.ivCancel) {
            back();
        } else if (view.getId() == R.id.ivCheck) {
            saveImage();
        }
    }
}
