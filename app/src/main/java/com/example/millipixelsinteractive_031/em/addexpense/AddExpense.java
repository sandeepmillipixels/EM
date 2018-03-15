package com.example.millipixelsinteractive_031.em.addexpense;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.millipixelsinteractive_031.em.R;
import com.example.millipixelsinteractive_031.em.category.CategoryActivity;
import com.example.millipixelsinteractive_031.em.database.SqliteDatabaseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddExpense extends AppCompatActivity {


    @BindView(R.id.amountEditTex)
    EditText amountEditTex;

    @BindView(R.id.categoryEditText)
    EditText categoryEditText;

    @BindView(R.id.notesEditText)
    EditText notesEditText;

    @BindView(R.id.dateEditText)
    EditText dateEditText;

    @BindView(R.id.saveButton)
    Button saveButton;

    @BindView(R.id.firstImage)
    ImageView firstImage;

    @BindView(R.id.secondImage)
    ImageView secondImage;

    SqliteDatabaseClass db;

    String amount,category,notes,date;


    String filePath;

    private static final int CAMERA_REQUEST = 1888;

    private static final int STORAGE_REQUEST = 1000;

    String catName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        ButterKnife.bind(this);

        db=new SqliteDatabaseClass(this);

    }

    @OnClick(R.id.saveButton)
    public void saveButtonClick(){

        amount=amountEditTex.getText().toString();
        category=categoryEditText.getText().toString();
        notes=notesEditText.getText().toString();
        date=dateEditText.getText().toString();


        if(amount.length()==0 && category.length()==0){

            Toast.makeText(this, "Amount and Category Filled are mandatory", Toast.LENGTH_SHORT).show();

        }else if(amount.length()==0){

            Toast.makeText(this, "Please fill amount", Toast.LENGTH_SHORT).show();

        }else if(category.length()==0){

            Toast.makeText(this, "Please Select Category", Toast.LENGTH_SHORT).show();

        }else{
            db.insertRecords(amount,category,notes,date,filePath);
            finish();
        }


    }

    @OnClick(R.id.categoryEditText)
    public void categoryEditTextClick(){

        Intent intent=new Intent(this, CategoryActivity.class);
        startActivityForResult(intent,100);
    }

    @OnClick(R.id.firstImage)
    public void firstImageClick(){

       // if(cameraPermission()!=true && StoragePermission()!=true){
            takePicture();

//        }else{
//            cameraPermission();
//            StoragePermission();
//        }

    }

    @OnClick(R.id.secondImage)
    public void secondImageClick(){

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),
                        "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                        .show();
                finish();
            } else if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),
                        "FlagUp Requires Access to Your Storage.",
                        Toast.LENGTH_SHORT).show();
                finish();
            } else {
                takePicture();
            }
        }
    }


    public void takePicture() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");


            String partFilename = currentDateFormat();
            try {
                storeCameraPhotoInSDCard(photo, partFilename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            filePath= "photo_" + partFilename + ".jpg";


            photo = getImageFileFromSDCard(filePath);

            firstImage.setImageBitmap(photo);
        }else if (requestCode == 100 && resultCode == RESULT_OK){
            if (data != null && data.getExtras() != null){
                catName = data.getStringExtra("catName");
                categoryEditText.setText(catName);
            }
        }
    }



    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private void storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate) throws FileNotFoundException {
        File outputFile = new File(Environment.getExternalStorageDirectory(), "photo_" + currentDate + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Bitmap getImageFileFromSDCard(String filename){
        Bitmap bitmap = null;
        File imageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" + filename);
        try {
            FileInputStream fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }



}
