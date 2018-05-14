package com.application.millipixels.expense_rocket;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.application.millipixels.expense_rocket.adapter.ImageScrollAdapter;
import com.application.millipixels.expense_rocket.adapter.PagingListAdapter;
import com.application.millipixels.expense_rocket.editor.brightness.BightnessActivity;
import com.application.millipixels.expense_rocket.editor.contrast.ContrastActivity;
import com.application.millipixels.expense_rocket.editor.crop.CropActivity;
import com.application.millipixels.expense_rocket.editor.rotate.RotateActivity;
import com.application.millipixels.expense_rocket.editor.saturation.SeturationActivity;
import com.application.millipixels.expense_rocket.model.ImageList;
import com.application.millipixels.models.PagingListModal;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraFilterActivity extends AppCompatActivity implements ImageScrollAdapter.IOnActionListener,PagingListAdapter.IPagingListener {
    private static final String INPUT_URL = "inputUrl";
    ArrayList<ImageList> imageList = new ArrayList<>();
    ArrayList<PagingListModal> pagingList = new ArrayList<>();
    int selectedImage = 0;
    String PATH = "/storage/emulated/0/Pictures/1525930102844.jpg";
    @BindView(R.id.imgMain)
    ImageView imgMain;
    @BindView(R.id.rv_imageSlider)
    RecyclerView rv_imageSlider;
    @BindView(R.id.rvPaging)
    RecyclerView rvPaging;
    @BindView(R.id.imageSlider)
    RelativeLayout imageSlider;
    @BindView(R.id.btnToggle)
    ImageView btnToggle;

    @BindView(R.id.btnBack)
    ImageView btnBack;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    ImageScrollAdapter adapter;
    PagingListAdapter pagingListAdapter;
    LinearLayoutManager HorizontalLayout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_filter);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null){
            imageList = getIntent().getParcelableArrayListExtra("imageList");
        }
//        imageList.add(new ImageList(PATH,true));
//        imageList.add(new ImageList(PATH,false));
//        imageList.add(new ImageList(PATH,false));
//        imageList.add(new ImageList(PATH,false));
        setUpImageSlider();
        if (imageList.size() > 0){
            File f = new File(imageList.get(0).getImagePath());
            Picasso.with(this).load(f).into(imgMain);
        }
        createPaging();
        setPagingAdapter();
    }

    private void setPagingAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvPaging.setLayoutManager(layoutManager);
        pagingListAdapter = new PagingListAdapter(pagingList,this,this);
        HorizontalLayout = new LinearLayoutManager(CameraFilterActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvPaging.setLayoutManager(HorizontalLayout);
        rvPaging.setAdapter(pagingListAdapter);
    }

    private void createPaging() {
        for (ImageList  list :imageList){
            PagingListModal modal = new PagingListModal(list.isSelected());
            pagingList.add(modal);
        }
    }

    private void setUpImageSlider() {
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_imageSlider.setLayoutManager(RecyclerViewLayoutManager);
        rv_imageSlider.setNestedScrollingEnabled(false);
        rv_imageSlider.setHasFixedSize(true);
        adapter = new ImageScrollAdapter(imageList,this,this);
        HorizontalLayout = new LinearLayoutManager(CameraFilterActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rv_imageSlider.setLayoutManager(HorizontalLayout);
        rv_imageSlider.setAdapter(adapter);
    }

    @OnClick(R.id.btnBack)
    public void onBack(){
        super.onBackPressed();
    }


    @OnClick(R.id.btnToggle)
    public void onToggle(){
        if (imageSlider.getVisibility() ==  View.VISIBLE) {
            imageSlider.setVisibility(View.GONE);
            btnToggle.setImageResource(R.drawable.ic_bottom_arrow);
        }else {
            imageSlider.setVisibility(View.VISIBLE);
            btnToggle.setImageResource(R.drawable.ic_top_arrorw);
        }
    }

    @OnClick(R.id.btnCrop)
    public void onCrop(){
        if (imageList.size() == 0)
            return;
        Intent intent = new Intent(CameraFilterActivity.this, CropActivity.class);
        intent.putExtra(INPUT_URL,imageList.get(selectedImage).getImagePath());
        startActivityForResult(intent,200);
    }

    @OnClick(R.id.btnRotate)
    public void onRotate(){
        if (imageList.size() == 0)
            return;
        Intent intent = new Intent(CameraFilterActivity.this, RotateActivity.class);
        intent.putExtra(INPUT_URL,imageList.get(selectedImage).getImagePath());
        startActivityForResult(intent,200);
    }
    @OnClick(R.id.btnBrightness)
    public void onBrightness(){
        if (imageList.size() == 0)
            return;
        Intent intentBrightness = new Intent(CameraFilterActivity.this, BightnessActivity.class);
        intentBrightness.putExtra(INPUT_URL,imageList.get(selectedImage).getImagePath());
        startActivityForResult(intentBrightness,200);
    }

    @OnClick(R.id.btnContrast)
    public void onContrast(){
        Intent intentContrast = new Intent(CameraFilterActivity.this, ContrastActivity.class);
        intentContrast.putExtra(INPUT_URL,imageList.get(selectedImage).getImagePath());
        startActivityForResult(intentContrast,200);
    }

    @OnClick(R.id.btnSaturation)
    public void onSaturation(){
        if (imageList.size() == 0)
            return;
        Intent intentSeturation = new Intent(CameraFilterActivity.this, SeturationActivity.class);
        intentSeturation.putExtra(INPUT_URL,imageList.get(selectedImage).getImagePath());
        startActivityForResult(intentSeturation,200);
    }

    @OnClick(R.id.btnDelete)
    public void onDelete(){
        if (imageList.size() == 0)
            return;
            imageList.remove(0);
            adapter.notifyDataSetChanged();
            if (imageList.size() == 0){
               super.onBackPressed();
            }
    }

    @Override
    public void onImageClick(int pos) {
        selectedImage = pos;
        pagingListAdapter.setSelectedPage(pos);
        loadImage();
    }

    private void loadImage(){
        File f = new File(imageList.get(selectedImage).getImagePath());
//        Picasso.with(this).load(f).into(imgMain);
        if(f.exists())
        {
//            ImageView myImage = new ImageView(this);
            imgMain.setImageURI(Uri.fromFile(f));

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 200:
                if (resultCode == Activity.RESULT_OK ){
                    imageList.get(selectedImage).setImagePath(data.getStringExtra(INPUT_URL));
                    // arrayList.add(position,path);
                    adapter.notifyDataSetChanged();

                }
                break;
        }
    }

    @Override
    public void onPageClick(int pos) {
        pagingListAdapter.setSelectedPage(pos);
        adapter.setSelectedPage(pos);
        selectedImage = pos;
        loadImage();
    }
}
