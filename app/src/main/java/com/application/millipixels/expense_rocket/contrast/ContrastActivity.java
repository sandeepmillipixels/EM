package com.application.millipixels.expense_rocket.contrast;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

/**
 * Created by millipixelsinteractive_031 on 12/04/18.
 */

public class ContrastActivity extends AppCompatActivity implements View.OnClickListener {



    private static final String INPUT_URL = "inputUrl";

    EditImageView imageView;
    AVLoadingIndicatorView ivLoading;
    TextView tvProcess;
    LinearLayout llProcess;
    TwoLineSeekBar seekBar_contrast,seekBar_brightness,seekBar_saturation;
    ImageView ivCancel;
    TextView tvTitle;
    ImageView ivCheck;
    LinearLayout controller;
    RelativeLayout rootBrightness;

    private String inputUrl;
    private boolean start = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_contrast);

        imageView = findViewById(R.id.contrastView);



        ivLoading = findViewById(R.id.ivLoading);
        tvProcess = findViewById(R.id.tvProcess);
        llProcess = findViewById(R.id.llProcess);
        seekBar_contrast = findViewById(R.id.seekBar_contrast);
        seekBar_brightness= findViewById(R.id.seekBar_brightness);
        seekBar_saturation= findViewById(R.id.seekBar_saturation);
        ivCancel = findViewById(R.id.ivCancel);
        tvTitle = findViewById(R.id.tvTitle);
        ivCheck = findViewById(R.id.ivCheck);
        controller = findViewById(R.id.controller);
        rootBrightness = findViewById(R.id.rootBrightness);

        ivCancel.setOnClickListener(this);
        ivCheck.setOnClickListener(this);
        rootBrightness.setOnClickListener(this);

        inputUrl=getIntent().getStringExtra("path");


        showImage();
        initViewContrast();
        initViewBrightness();
        initViewSaturation();


    }
    private OnContrastListener onContrastListener;

    public void setOnContrastListener(OnContrastListener onContrastListener) {
        this.onContrastListener = onContrastListener;
    }


    private void initViewBrightness() {

        ///////Brigtness///////////////////////////
        seekBar_brightness.reset();
        seekBar_brightness.setSeekLength(-1000, 1000, 0, 1f);
        seekBar_brightness.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {




            @Override
            public void onSeekChanged(float value, float step) {
                if (llProcess.getVisibility() != View.VISIBLE && !start)
                    llProcess.setVisibility(View.VISIBLE);

                start = false;
                tvProcess.setText(Float.toString(value / 10f));
                imageView.setBright(value / 10f);
            }

            @Override
            public void onSeekStopped(float v, float v1) {

                if (llProcess.getVisibility() != View.GONE) llProcess.setVisibility(View.GONE);

            }
        });
        seekBar_brightness.setValue(0);

    }


    private void initViewSaturation() {



////////////////////////Saturation//////////////////////////////////
        seekBar_saturation.reset();
        seekBar_saturation.setSeekLength(-1000, 1000, 0, 1f);
        seekBar_saturation.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {




            @Override
            public void onSeekChanged(float value, float step) {
                if (llProcess.getVisibility() != View.VISIBLE && !start)
                    llProcess.setVisibility(View.VISIBLE);

                start = false;
                tvProcess.setText(Float.toString(value / 10f));
                imageView.setSaturation(value / 10f);
            }

            @Override
            public void onSeekStopped(float v, float v1) {

                if (llProcess.getVisibility() != View.GONE) llProcess.setVisibility(View.GONE);

            }
        });
        seekBar_saturation.setValue(0);



    }

    private void initViewContrast() {



////////////////////Contrast////////////////////////////////

        seekBar_contrast.reset();
        seekBar_contrast.setSeekLength(-1000, 1000, 0, 1f);
        seekBar_contrast.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
            @Override
            public void onSeekChanged(float value, float step) {
                if (llProcess.getVisibility() != View.VISIBLE && !start)
                    llProcess.setVisibility(View.VISIBLE);

                start = false;
                tvProcess.setText(Float.toString(value / 10f));
                imageView.setContrast(value / 10f);
            }

            @Override
            public void onSeekStopped(float v, float v1) {
                if (llProcess.getVisibility() != View.GONE) llProcess.setVisibility(View.GONE);
            }
        });
        seekBar_contrast.setValue(0);


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
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("eror", e.toString());
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


    private void back() {
        onBackPressed();
    }

    private void saveImageContrast() {
        Observable.just(inputUrl)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String url) throws Exception {
                        return saveBitmapContrast(url);
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
                        if (onContrastListener != null)
                            onContrastListener.onContrastPhotoCompleted(url);
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


    private void saveImageBrightness() {
        Observable.just(inputUrl)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String url) throws Exception {
                        return saveBitmapBrightness(url);
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
                        if (onContrastListener != null)
                            onContrastListener.onContrastPhotoCompleted(url);
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

    private void saveImageSaturation() {
        Observable.just(inputUrl)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String url) throws Exception {
                        return saveBitmapSaturations(url);
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
                        if (onContrastListener != null)
                            onContrastListener.onContrastPhotoCompleted(url);
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

    private String saveBitmapContrast(String url) {
        Bitmap bitmap = Utils.getBitmapSdcard(url);
        bitmap = Utils.contrastBitmap(bitmap, imageView.getContrast());
        Utils.saveBitmap(url, bitmap);
        if (bitmap != null && !bitmap.isRecycled()) bitmap.recycle();
        return url;
    }

    private String saveBitmapBrightness(String url) {
        Bitmap bitmap = Utils.getBitmapSdcard(url);
        bitmap = Utils.brightBitmap(bitmap, imageView.getContrast());
        Utils.saveBitmap(url, bitmap);
        if (bitmap != null && !bitmap.isRecycled()) bitmap.recycle();
        return url;
    }


    private String saveBitmapSaturations(String url) {
        Bitmap bitmap = Utils.getBitmapSdcard(url);
        bitmap = Utils.saturationBitmap(bitmap, imageView.getContrast());
        Utils.saveBitmap(url, bitmap);
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
           saveImageBrightness();
           saveImageContrast();
           saveImageSaturation();
        }
    }


}
