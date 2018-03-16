package com.example.millipixelsinteractive_031.em.addexpense;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.millipixelsinteractive_031.em.R;
import com.example.millipixelsinteractive_031.em.category.CategoryActivity;
import com.example.millipixelsinteractive_031.em.database.AllExpensesDataSource;


import com.example.millipixelsinteractive_031.em.model.AllExpenses;
import com.example.millipixelsinteractive_031.em.utils.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddExpense extends AppCompatActivity {


    @BindView(R.id.edtAmount)
    EditText edtAmount;
    
    @BindView(R.id.edtExpenseName)
    EditText edtExpenseName;

    @BindView(R.id.edtCategoryName)
    EditText edtCategoryName;

    @BindView(R.id.edtNotes)
    EditText edtNotes;

    @BindView(R.id.edtDate)
    EditText edtDate;

    @BindView(R.id.saveButton)
    Button saveButton;

    @BindView(R.id.firstImage)
    ImageView firstImage;

    @BindView(R.id.secondImage)
    ImageView secondImage;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String amount,category,notes,date,expense_name;

    long catId = 0;

    String filePath;

    private static final int CAMERA_REQUEST = 1888;

    private static final int STORAGE_REQUEST = 1000;

    String catName;
    private int mYear, mMonth, mDay, mHour, mMinute;

    AllExpensesDataSource allExpensesDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        ButterKnife.bind(this);

        initToolBar();
    }

    public void initToolBar() {

        toolbar.setTitle(R.string.add_expense);
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

    @OnClick(R.id.saveButton)
    public void saveButtonClick(){

        amount=edtAmount.getText().toString();
        category=edtCategoryName.getText().toString();
        notes=edtNotes.getText().toString();
        date=edtDate.getText().toString();
        expense_name=edtExpenseName.getText().toString();


        if(expense_name.length()==0){

            Toast.makeText(this, "Please enter expense name.", Toast.LENGTH_SHORT).show();

        }else if(amount.length()==0){


            Toast.makeText(this, "Please enter amount.", Toast.LENGTH_SHORT).show();

        }else if(category.length()==0){

            Toast.makeText(this, "Please select category.", Toast.LENGTH_SHORT).show();

        }else if(date.length()==0){

            Toast.makeText(this, "Please select date.", Toast.LENGTH_SHORT).show();

        }else{
            AllExpenses allExpenses = new AllExpenses();
            allExpenses.setCategory_id(catId);
            allExpenses.setCategory_name(category);
            allExpenses.setExpense_name(amount);
            allExpenses.setExpense_amount(amount);
            allExpenses.setExpense_date(date+" "+ Utility.getCurrentTime());
            allExpenses.setExpense_date_milli(Utility.getTimeInMilli(date+" "+ Utility.getCurrentTime()));
            allExpenses.setExpense_note(notes);
            try {
                allExpensesDataSource.open();
                allExpensesDataSource.createExpense(allExpenses);
                allExpensesDataSource.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    @OnClick(R.id.edtCategoryName)
    public void categoryEditTextClick(){

        Intent intent=new Intent(this, CategoryActivity.class);
        startActivityForResult(intent,100);
    }

    @OnClick(R.id.edtDate)
    public void dateEditTextClick(){

        showdatePicker();
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
                category = data.getStringExtra("catName");
                catId = data.getLongExtra("catId",0);
                edtCategoryName.setText(catName);
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

    private void showdatePicker(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date = year + "-" + (monthOfYear+1)  +"-"+ dayOfMonth ;
                        edtDate.setText(date);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
