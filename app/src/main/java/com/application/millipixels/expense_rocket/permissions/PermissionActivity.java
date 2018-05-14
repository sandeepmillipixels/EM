package com.application.millipixels.expense_rocket.permissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.model.PermissionsModel;
import com.application.millipixels.expense_rocket.model.PermissionsModel;
import com.application.millipixels.expense_rocket.utils.Constants;
import com.application.millipixels.expense_rocket.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PermissionActivity extends AppCompatActivity{


    @BindView(R.id.back_button_permission)
    ImageView back_button_permission;

    @BindView(R.id.permission_type_img)
    ImageView permission_type_img;

    @BindView(R.id.permission_title)
    TextView permission_title;

    @BindView(R.id.permission_desc)
    TextView permission_desc;

    @BindView(R.id.grant_access)
    Button grant_access;


    @BindView(R.id.remind_me_later)
    Button remind_me_later;

    PermissionsModel permissionsModel;

    int permissionType = 2;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission);
        ButterKnife.bind(this);

        permissionsModel = Utility.getPermission(permissionType,this);

        if(!checkPermission()){

            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    permissionType);
        }

        else{

            setText(permissionsModel.getPermissionTitle(),permissionsModel.getPermissionDesc(),permissionsModel.getImagePath());

        }

    }

    @OnClick(R.id.back_button_permission)
         public void backPerssed(){

           onBackPressed();

    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();

        finish();
    }

    @OnClick(R.id.remind_me_later)
    public void remindMeLaterClick(){

        finish();

    }

    public boolean checkPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            return true;

        }else{
            return false;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == permissionType) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                setText(permissionsModel.getPermissionTitle(),permissionsModel.getPermissionDesc(),permissionsModel.getImagePath());

                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }

    }

    public void setText(String title,String desc,int image){

        permission_type_img.setImageResource(image);

        permission_title.setText(title);

        permission_desc.setText(desc);


    }

}
