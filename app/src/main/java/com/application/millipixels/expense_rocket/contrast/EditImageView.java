package com.application.millipixels.expense_rocket.contrast;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.FloatRange;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Hoang Anh Tuan on 12/12/2017.
 */

class EditImageView extends AppCompatImageView {

    private float saturation;
    private float bright;
    private float contrast;
    private PublishSubject<Float> subject;

    public EditImageView(Context context) {
        super(context);
        initViewContrast();
        initViewBrightness();
        initViewSaturation();
    }

    public EditImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewContrast();
        initViewBrightness();
        initViewSaturation();
    }

    public EditImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewContrast();
        initViewBrightness();
        initViewSaturation();
    }

    private void initViewContrast() {
        subject = PublishSubject.create();
        subject.debounce(0, TimeUnit.MILLISECONDS)
//                .filter(new Predicate<Float>() {
//                    @Override
//                    public boolean test(Float contrast) throws Exception {
//                        return true;
//                    }
//                })
                .distinctUntilChanged()
                .switchMap(new Function<Float, ObservableSource<ColorMatrixColorFilter>>() {
                    @Override
                    public ObservableSource<ColorMatrixColorFilter> apply(Float value) throws Exception {
                        return postContrast(value);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ColorMatrixColorFilter>() {
                    @Override
                    public void accept(ColorMatrixColorFilter colorMatrixColorFilter) throws Exception {
                        setColorFilter(colorMatrixColorFilter);
                    }
                });
    }

    private void initViewSaturation() {
        subject = PublishSubject.create();
        subject.debounce(0, TimeUnit.MILLISECONDS)
//                .filter(new Predicate<Float>() {
//                    @Override
//                    public boolean test(Float brightness) throws Exception {
//                        return true;
//                    }
//                })
                .distinctUntilChanged()
                .switchMap(new Function<Float, ObservableSource<ColorMatrixColorFilter>>() {
                    @Override
                    public ObservableSource<ColorMatrixColorFilter> apply(Float value) throws Exception {
                        return postSaturation(value);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ColorMatrixColorFilter>() {
                    @Override
                    public void accept(ColorMatrixColorFilter colorMatrixColorFilter) throws Exception {
                        setColorFilter(colorMatrixColorFilter);
                    }
                });
    }

    private void initViewBrightness() {
        subject = PublishSubject.create();
        subject.debounce(0, TimeUnit.MILLISECONDS)
//                .filter(new Predicate<Float>() {
//                    @Override
//                    public boolean test(Float brightness) throws Exception {
//                        return true;
//                    }
//                })
                .distinctUntilChanged()
                .switchMap(new Function<Float, ObservableSource<ColorMatrixColorFilter>>() {
                    @Override
                    public ObservableSource<ColorMatrixColorFilter> apply(Float value) throws Exception {
                        return postBrightness(value);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ColorMatrixColorFilter>() {
                    @Override
                    public void accept(ColorMatrixColorFilter colorMatrixColorFilter) throws Exception {
                        setColorFilter(colorMatrixColorFilter);
                    }
                });
    }



    private Observable<ColorMatrixColorFilter> postSaturation(float value) {
        return Observable.just(saturation(value));
    }



    private Observable<ColorMatrixColorFilter> postBrightness(float value) {
        return Observable.just(brightness(value));
    }


    public float getContrast() {
        return contrast;
    }

    public void setContrast(@FloatRange(from = -180.0f, to = 180.0f) float contrast) {
        this.contrast = contrast / 180.0f + 1.0f;
        subject.onNext(this.contrast);
    }

    public float getBright() {
        return bright;
    }

    public void setBright(@FloatRange(from = -100, to = 100) float bright) {
        this.bright = bright;
        subject.onNext(this.bright);
    }


    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(@FloatRange(from = 0, to = 100) float saturation) {
        this.saturation = saturation / 100f;
        subject.onNext(this.saturation);
    }



    private Observable<ColorMatrixColorFilter> postContrast(float value) {
        return Observable.just(contrast(value));
    }

    private ColorMatrixColorFilter contrast(float value) {
        float scale = value;
        float[] array = new float[]{
                scale, 0, 0, 0, 0,
                0, scale, 0, 0, 0,
                0, 0, scale, 0, 0,
                0, 0, 0, 1, 0};
        ColorMatrix matrix = new ColorMatrix(array);
        return new ColorMatrixColorFilter(matrix);
    }

    private ColorMatrixColorFilter brightness(float value) {
        ColorMatrix cmB = new ColorMatrix();
        cmB.set(new float[]{
                1, 0, 0, 0, value,
                0, 1, 0, 0, value,
                0, 0, 1, 0, value,
                0, 0, 0, 1, 0});
        return new ColorMatrixColorFilter(cmB);
    }

    private ColorMatrixColorFilter saturation(float value) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(value);
        return new ColorMatrixColorFilter(colorMatrix);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
