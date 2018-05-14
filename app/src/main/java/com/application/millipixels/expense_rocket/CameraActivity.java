package com.application.millipixels.expense_rocket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.camera2.CaptureRequest;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.camera2.Camera2BasicFragment;
import com.application.millipixels.expense_rocket.camera2.CameraModes;
import com.application.millipixels.expense_rocket.contrast.ContrastActivity;
import com.application.millipixels.expense_rocket.cropimage.ImageCropActivity;
import com.application.millipixels.expense_rocket.model.ImageList;
import com.application.millipixels.expense_rocket.shoebox.BottomNavigationViewHelper;
import com.application.millipixels.expense_rocket.shoebox.TabbedActivity;
import com.theartofdev.edmodo.cropper.CropImage;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class CameraActivity extends AppCompatActivity {
    @BindView(R.id.btnCamera)
    ImageView btnCamera;

    @BindView(R.id.container)
    FrameLayout container;

    @BindView(R.id.btnAuto)
    LinearLayout btnAuto;

    @BindView(R.id.btnSunny)
    LinearLayout btnSunny;

    @BindView(R.id.btnCloudy)
    LinearLayout btnCloudy;

    @BindView(R.id.btnFluroescent)
    LinearLayout btnFluroescent;

    @BindView(R.id.btnTungsten)
    LinearLayout btnTungsten;

    @BindView(R.id.txtPageCount)
    TextView txtPageCount;

    @BindView(R.id.btnCancel)
    TextView btnCancel;

    @BindView(R.id.btnFlash)
    RelativeLayout btnFlash;


    ArrayList<ImageList> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        setUpCamera();
    }

    @OnClick(R.id.btnCamera)
    public void onTakeShot(){
        Camera2BasicFragment fragment = (Camera2BasicFragment) getSupportFragmentManager().findFragmentByTag("CameraFragment");
        fragment.takePicture();
    }

    @OnClick(R.id.btnPage)
    public void onPageTapped(){
        if (arrayList.size() > 0){
            Intent intent = new Intent(this,CameraFilterActivity.class);
            intent.putExtra("imageList",arrayList);
            startActivity(intent);
        }

    }

    private void setUpCamera(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, Camera2BasicFragment.newInstance(),"CameraFragment")
                .commit();
    }

    @OnClick(R.id.btnCancel)
    public void onCancel(){
        super.onBackPressed();
    }

    @OnClick(R.id.txtDone)
    public void onDone(){
        if (arrayList.size() > 0){
            Intent intent = new Intent(this,CameraFilterActivity.class);
            intent.putExtra("imageList",arrayList);
            startActivity(intent);
        }
    }

    @OnClick(R.id.btnAuto)
    public void onAutoMode(){
        selectCameraMode(CameraModes.AUTO_MODE);
    }@OnClick(R.id.btnSunny)
    public void onSunnyMode(){
        selectCameraMode(CameraModes.SYNNY_MODE);
    }@OnClick(R.id.btnCloudy)
    public void onCloudyMode(){
        selectCameraMode(CameraModes.CLOUDY_MODE);
    }@OnClick(R.id.btnFluroescent)
    public void onFluroescentMode(){
        selectCameraMode(CameraModes.FLUROESCENT_MODE);
    }@OnClick(R.id.btnTungsten)
    public void onTungstenMode(){
        selectCameraMode(CameraModes.TUNGSTEN_MODE);
    }

    private void selectCameraMode(CameraModes cameraModes){
        Camera2BasicFragment fragment = (Camera2BasicFragment) getSupportFragmentManager().findFragmentByTag("CameraFragment");
        fragment.changeCameraMode(cameraModes);
    }

    public void addImageToList(String path){
        if (arrayList.size() > 0){
            arrayList.add(new ImageList(path,true));
        }else {
            arrayList.add(new ImageList(path,false));
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtPageCount.setText(arrayList.size()+"");
            }
        });

    }

    @OnClick(R.id.btnFlash)
    public void onFlashToggle(){
//        Camera2BasicFragment fragment = (Camera2BasicFragment) getSupportFragmentManager().findFragmentByTag("CameraFragment");
//        fragment.setFLASH_MODE(CaptureRequest.FLASH_MODE_SINGLE);
    }

}
