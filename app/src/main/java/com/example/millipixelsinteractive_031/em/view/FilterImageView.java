package com.example.millipixelsinteractive_031.em.view;

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
 * Created by millipixelsinteractive_024 on 12/04/18.
 */

public class FilterImageView extends AppCompatImageView {

    private float bright;
    private float contrast;
    private float saturation;
    private PublishSubject<Float> subjectContrast;
    private PublishSubject<Float> subjectBrightness;
    private PublishSubject<Float> subjectSeturation;

    public FilterImageView(Context context) {
        super(context);
        initViewBrightness();
        initViewContrast();
        initViewSeturation();
    }

    public FilterImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewBrightness();
        initViewContrast();
        initViewSeturation();
    }

    public FilterImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewBrightness();
        initViewContrast();
        initViewSeturation();
    }

    private void initViewBrightness() {
        subjectBrightness = PublishSubject.create();
        subjectBrightness.debounce(0, TimeUnit.MILLISECONDS)
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

    public float getBright() {
        return bright;
    }

    public void setBright(@FloatRange(from = -100, to = 100) float bright) {
        this.bright = bright;
        subjectBrightness.onNext(this.bright);
    }

    private Observable<ColorMatrixColorFilter> postBrightness(float value) {
        return Observable.just(brightness(value));
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


    private void initViewContrast() {
        subjectContrast = PublishSubject.create();
        subjectContrast.debounce(0, TimeUnit.MILLISECONDS)
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

    public float getContrast() {
        return contrast;
    }

    public void setContrast(@FloatRange(from = -180.0f, to = 180.0f) float contrast) {
        this.contrast = contrast / 180.0f + 1.0f;
        subjectContrast.onNext(this.contrast);
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



    private void initViewSeturation() {
        subjectSeturation = PublishSubject.create();
        subjectSeturation.debounce(0, TimeUnit.MILLISECONDS)
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
                        return postSeturation(value);
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

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(@FloatRange(from = 0, to = 100) float saturation) {
        this.saturation = saturation / 100f;
        subjectSeturation.onNext(this.saturation);
    }

    private Observable<ColorMatrixColorFilter> postSeturation(float value) {
        return Observable.just(seturation(value));
    }

    private ColorMatrixColorFilter seturation(float value) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(value);
        return new ColorMatrixColorFilter(colorMatrix);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}

