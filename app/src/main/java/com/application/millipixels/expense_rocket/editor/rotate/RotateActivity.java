package com.application.millipixels.expense_rocket.editor.rotate;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

public class RotateActivity extends AppCompatActivity implements TwoLineSeekBar.OnSeekChangeListener, View.OnClickListener {
    private static final String INPUT_URL = "inputUrl";

    RotateView rotateView;
    AVLoadingIndicatorView ivLoading;
    TextView tvProcess;
    LinearLayout llProcess;
    TwoLineSeekBar seekBar;
    ImageView ivCancel;
    TextView tvTitle;
    ImageView ivCheck;
    LinearLayout controller;
    RelativeLayout rootRotate;

    private String inputUrl;
    private boolean start = true;
    private OnRotateListener onRotateListener;

    public void setOnRotateListener(OnRotateListener onRotateListener) {
        this.onRotateListener = onRotateListener;
    }

    private void mappingView() {
        rotateView = findViewById(R.id.rotateView);
        ivLoading = findViewById(R.id.ivLoading);
        tvProcess = findViewById(R.id.tvProcess);
        llProcess = findViewById(R.id.llProcess);
        seekBar = findViewById(R.id.seekBar1);
        ivCancel = findViewById(R.id.ivCancel);
        tvTitle = findViewById(R.id.tvTitle);
        ivCheck = findViewById(R.id.ivCheck);
        controller = findViewById(R.id.controller);
        rootRotate = findViewById(R.id.rootRotate);

        ivCancel.setOnClickListener(this);
        ivCheck.setOnClickListener(this);
        rootRotate.setOnClickListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);
        mappingView();
        initView();
    }
    private void initView() {

        seekBar.reset();
        seekBar.setSeekLength(0, 360, 0, 1);
        seekBar.setOnSeekChangeListener(this);
        seekBar.setValue(0);

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
                        rotateView.setImageBitmap(bitmap);
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
                        if (onRotateListener != null)
                            onRotateListener.onRotatePhotoCompleted(url);
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

    private String saveBitmap(String inputUrl) {
        Bitmap bitmap = Utils.getBitmapSdcard(inputUrl);
        bitmap = Utils.rotateBitmap(bitmap, rotateView.getRotate());
        Utils.saveBitmap(inputUrl, bitmap);
        return inputUrl;
    }

    @Override
    public void onSeekChanged(float value, float step) {
        if (llProcess.getVisibility() != View.VISIBLE && !start)
            llProcess.setVisibility(View.VISIBLE);

        start = false;
        tvProcess.setText(Integer.toString((int) (value)));
        rotateView.setRotate((int) value);
    }

    @Override
    public void onSeekStopped(float value, float step) {
        if (llProcess.getVisibility() != View.GONE) llProcess.setVisibility(View.GONE);
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

