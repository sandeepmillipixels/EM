package com.example.millipixelsinteractive_031.em.shoebox;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.millipixelsinteractive_031.em.R;
import com.example.millipixelsinteractive_031.em.cropimage.ImageCropActivity;
import com.example.millipixelsinteractive_031.em.imagepicker.ImagePicker;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TabbedActivity extends AppCompatActivity {


    public static final int SECOND_PIC_REQ = 1313;
    public static final int GALLERY_ONLY_REQ = 1212;

    ArrayList<String> arrayList;
    SlidingImage_Adapter adapter;

    @BindView(R.id.toolbar_shoebox)
    Toolbar toolbar;

    int i=0;

    private ViewPager mPager;
    private static int currentPage = 0;

    TextView show_box_hint_textView;

    Bitmap photo;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_filter:

                    return true;
                case R.id.navigation_crop:

                    if(arrayList.size()!=0){
                        Intent intent=new Intent(TabbedActivity.this, ImageCropActivity.class);
                        intent.putExtra("path",arrayList.get(currentPage).toString());
                        startActivityForResult(intent,CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
                    }

                    return true;

                case R.id.navigation_delete:

                    if(arrayList.size()!=0){
                        arrayList.remove(currentPage);
                        if(currentPage==0){
                            show_box_hint_textView.setVisibility(View.VISIBLE);
                        }
                        adapter.notifyDataSetChanged();
                    }


                    return true;
            }
            return false;

        }
    };

    public void initToolBar() {

        toolbar.setTitle(R.string.shoebox);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        ButterKnife.bind(this);

        mPager =findViewById(R.id.pager);

         show_box_hint_textView=findViewById(R.id.show_box_hint_textView);

        android.support.design.widget.FloatingActionButton add_images_floating_button=findViewById(R.id.add_images_floating_button);

        initToolBar();

        arrayList=new ArrayList<>();

        if(arrayList.size()==0){
            show_box_hint_textView.setVisibility(View.VISIBLE);

        }

        adapter=new SlidingImage_Adapter(TabbedActivity.this,arrayList);
        mPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        add_images_floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.pickImage(TabbedActivity.this, "Select your image:");
            }
        });


        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:



//                CropImage.ActivityResult result = CropImage.getActivityResult(data);
//                if (resultCode == RESULT_OK) {
//                    Uri resultUri = result.getUri();
//
//                    arrayList.add(currentPage,getRealPathFromURI(resultUri));
//
//                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                    Exception error = result.getError();
//                }


            case SECOND_PIC_REQ:
                String imagePathFromResult = ImagePicker.getImagePathFromResult(TabbedActivity.this,
                        requestCode, resultCode, data);

                break;
            case GALLERY_ONLY_REQ:
                String pathFromGallery = "file:///" + ImagePicker.getImagePathFromResult(TabbedActivity.this, requestCode,
                        resultCode, data);
                break;

            case 100:
                if (data != null && data.getExtras() != null){

                }
                break;
            default:
                if (data != null){

                    photo = ImagePicker.getImageFromResult(TabbedActivity.this,
                            requestCode, resultCode, data);
                    if (photo == null){
                        return;
                    }
                    show_box_hint_textView.setVisibility(View.GONE);
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(getApplicationContext(), photo);

                    if (tempUri != null) {
                        arrayList.add(getRealPathFromURI(tempUri));
                    }
                    adapter.notifyDataSetChanged();
                }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

}
