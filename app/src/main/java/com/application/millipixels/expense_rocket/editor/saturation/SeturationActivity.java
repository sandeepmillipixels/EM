package com.application.millipixels.expense_rocket.editor.saturation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.hoanganhtuan95ptit.library.TwoLineSeekBar;
import com.wang.avi.AVLoadingIndicatorView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SeturationActivity extends AppCompatActivity implements View.OnClickListener, TwoLineSeekBar.OnSeekChangeListener {

    private static final String INPUT_URL = "inputUrl";

    SaturationView saturationView;
    AVLoadingIndicatorView ivLoading;
    TextView tvProcess;
    LinearLayout llProcess;
    TwoLineSeekBar seekBar;
    ImageView ivCancel;
    TextView tvTitle;
    ImageView ivCheck;
    LinearLayout controller;
    RelativeLayout rootBrightness;

    private String inputUrl;
    private boolean start = true;
    private OnSaturationListener onSaturationListener;

    public void setOnSaturationListener(OnSaturationListener onSaturationListener) {
        this.onSaturationListener = onSaturationListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seturation);
        mappingView();
        initView();
        
    }
    private void mappingView() {
        saturationView = findViewById(R.id.saturationView);
        ivLoading = findViewById(R.id.ivLoading);
        tvProcess = findViewById(R.id.tvProcess);
        llProcess = findViewById(R.id.llProcess);
        seekBar = findViewById(R.id.seekBar);
        ivCancel = findViewById(R.id.ivCancel);
        tvTitle = findViewById(R.id.tvTitle);
        ivCheck = findViewById(R.id.ivCheck);
        controller = findViewById(R.id.controller);
        rootBrightness = findViewById(R.id.rootBrightness);

        ivCancel.setOnClickListener(this);
        ivCheck.setOnClickListener(this);
        rootBrightness.setOnClickListener(this);
    }
    private void initView() {

        seekBar.reset();
        seekBar.setSeekLength(0, 1000, 0, 1f);
        seekBar.setOnSeekChangeListener(this);
        seekBar.setValue(1000);

        if (getIntent().getExtras() != null) {
            inputUrl = getIntent().getExtras().getString(INPUT_URL);
            showImage();
        }
    }

    private void showImage() {
        Observable.just(inputUrl)
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String url) throws Exception {
                        return getBitmap(url);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        showLoading();
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        saturationView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
    }

    private Bitmap getBitmap(String inputUrl) {
        Bitmap bitmap = Utils.getBitmapSdcard(inputUrl);
        bitmap = Utils.scaleDown(bitmap);
        return bitmap;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSeekChanged(float value, float step) {
        if (llProcess.getVisibility() != View.VISIBLE && !start)
            llProcess.setVisibility(View.VISIBLE);

        start = false;
        tvProcess.setText(Float.toString(value / 10f));
        saturationView.setSaturation(value / 10f);
    }

    @Override
    public void onSeekStopped(float value, float step) {
        if (llProcess.getVisibility() != View.GONE) llProcess.setVisibility(View.GONE);
    }


    private void back() {
        onBackPressed();
    }

    private void saveImage() {
        Observable.just(inputUrl)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String url) throws Exception {
                        return saveBitmap(url);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        showLoading();
                    }

                    @Override
                    public void onNext(String url) {
//                        if (onSaturationListener != null)
//                            onSaturationListener.onSaturationPhotoCompleted(url);
                        Intent intent = new Intent();
                        intent.putExtra(INPUT_URL,url);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        back();
                    }
                });
    }

    private String saveBitmap(String url) {
        Bitmap bitmap = Utils.getBitmapSdcard(url);
        Bitmap bitmapSaturation = Utils.saturationBitmap(bitmap, saturationView.getSaturation());
        Utils.saveBitmap(url, bitmapSaturation);
        if (bitmap != null && !bitmap.isRecycled()) bitmap.recycle();
        return url;
    }

    private void hideLoading() {
        if (ivLoading != null)
            ivLoading.smoothToHide();
    }

    private void showLoading() {
        if (ivLoading != null)
            ivLoading.smoothToShow();
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

