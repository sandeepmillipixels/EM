package com.example.millipixelsinteractive_031.em.shoebox;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.millipixelsinteractive_031.em.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageFilterActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_shoebox)
    Toolbar toolbar;
    int page = 0;
    String path = "";
    @BindView(R.id.imgFilter)
    ImageView imgFilter;
    SeekBar  seekBright, seekContrast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_filter);
        ButterKnife.bind(this);
        seekBright = (SeekBar) findViewById(R.id.seekBright);
        seekContrast = (SeekBar) findViewById(R.id.seekContrast);
        initToolBar();
        if (getIntent().getExtras() != null){
            path = getIntent().getStringExtra(TabbedActivity.PATH);
            page = getIntent().getIntExtra(TabbedActivity.PAGE,0);
            Uri uri = Uri.fromFile(new File(path));

            Picasso.with(this).load(uri)
                    .into(imgFilter);
        }
        setListeners();
    }

    private void setListeners() {
        seekBright.setMax(510);
        seekBright.setProgress(255);
        seekContrast.setMax(90);
        seekContrast.setProgress(0);
        seekBright.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                imgFilter.setColorFilter(setBrightness(progress));
                imgFilter.buildDrawingCache();
                Bitmap bmap = imgFilter.getDrawingCache();
//                imgFilter.setImageBitmap(bmap);
//                brightnessValue = progress - 255;
//                processingBitmap_Brightness(bmap);
                imgFilter.setImageBitmap(changeBitmapContrastBrightness(bmap,(int)(seekContrast.getProgress()+10)/10,(progress-255)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekContrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                imgFilter.buildDrawingCache();
                Bitmap bmap = imgFilter.getDrawingCache();
                imgFilter.setImageBitmap(changeBitmapContrastBrightness(bmap,(int)(progress+10)/10,(seekBright.getProgress()-255)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public static Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast, float brightness) {
        Log.e("","brightness"+brightness);
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }
    public void initToolBar() {

        toolbar.setTitle(R.string.filter);
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
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save){
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
