package com.application.millipixels.expense_rocket.addexpense;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.category.CategoryActivity;
import com.application.millipixels.expense_rocket.dashboard.Dashboard;
import com.application.millipixels.expense_rocket.database.AllExpensesDataSource;

import com.application.millipixels.expense_rocket.gallery.GalleyActivity;
import com.application.millipixels.expense_rocket.imagepicker.ImagePicker;
import com.application.millipixels.expense_rocket.model.AllExpenses;
import com.application.millipixels.expense_rocket.shoebox.TabbedActivity;
import com.application.millipixels.expense_rocket.utils.Utility;
import com.itextpdf.text.Image;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddExpense extends AppCompatActivity {
    public static final String CACHED_IMG_KEY = "img_key";

    public static final int SECOND_PIC_REQ = 1313;
    public static final int GALLERY_ONLY_REQ = 1212;

    Image image;


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

    Bitmap photo;
    private int mYear, mMonth, mDay;

    AllExpensesDataSource allExpensesDataSource;

    ArrayList<String> imagesUri;
    ArrayList<String> tempUris;


    String path;

    Dialog dialog;
    String catName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        ButterKnife.bind(this);
        allExpensesDataSource = new AllExpensesDataSource(this);




        initToolBar();
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String path = prefs.getString(CACHED_IMG_KEY, "");
//        File cached = new File(path);
//        if (cached.exists()) {
//            Picasso.with(this).load(cached).into(firstImage);
//        }



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
    public void saveButtonClick(View v){


        amount=edtAmount.getText().toString();
        category=edtCategoryName.getText().toString();
        notes=edtNotes.getText().toString();
        date=edtDate.getText().toString();
        expense_name=edtExpenseName.getText().toString();

        if(expense_name.length()==0){
            Snackbar.make(v,"Please enter expense name.",2000).show();
//            Toast.makeText(this, "Please enter expense name.", Toast.LENGTH_SHORT).show();

        }else if(amount.length()==0){

            Snackbar.make(v,"Please enter amount.",2000).show();
//            Toast.makeText(this, "Please enter amount.", Toast.LENGTH_SHORT).show();

        }else if(category.length()==0){
            Snackbar.make(v,"Please select category.",2000).show();
//            Toast.makeText(this, "Please select category.", Toast.LENGTH_SHORT).show();

        }else if(date.length()==0){
            Snackbar.make(v,"Please select date.",2000).show();
//            Toast.makeText(this, "Please select date.", Toast.LENGTH_SHORT).show();

        }else{
            AllExpenses allExpenses = new AllExpenses();
            allExpenses.setCategory_id(catId);
            allExpenses.setCategory_name(category);
            allExpenses.setExpense_name(expense_name);
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
            Snackbar.make(v,"Expense successfully added.",2000).show();

            Intent intent=new Intent(this,Dashboard.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent,250);
            finish();

        }

    }


    @OnClick(R.id.edtCategoryName)
    public void categoryEditTextClick(){

        Intent intent=new Intent(this, CategoryActivity.class);
        intent.putExtra("select",true);
        startActivityForResult(intent,100);
    }

    @OnClick(R.id.edtDate)
    public void dateEditTextClick(){

        showdatePicker();
    }

    @OnClick(R.id.firstImage)
    public void firstImageClick(){
        takePicture();

//        if(path!=null){
//
//            openPdf(path);
//
//        }else{
//
//
//
//        int MyVersion = Build.VERSION.SDK_INT;
//        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
//            if (!checkIfAlreadyhavePermission()) {
//                requestForSpecificPermission();
//            }
//        }
//        }
    }

    @OnClick(R.id.secondImage)
    public void secondImageClick(){

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();

                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void openPdf(String path){

        File file = new File(path);

        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }
    }


    public void takePicture() {
        // width and height will be at least 600px long (optional).

        Intent intent =new Intent(AddExpense.this, GalleyActivity.class);
        startActivityForResult(intent,500);

        //ImagePicker.pickImage(AddExpense.this, "Select your image:");


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case SECOND_PIC_REQ:
                String imagePathFromResult = ImagePicker.getImagePathFromResult(AddExpense.this,
                        requestCode, resultCode, data);
                if (imagePathFromResult != null) {
                    String path = "file:///" + imagePathFromResult;
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AddExpense.this);
                    prefs.edit().putString(CACHED_IMG_KEY, imagePathFromResult).apply();
                    //Picasso.with(AddExpense.this).load(path).into(firstImage);
                }
                break;
            case GALLERY_ONLY_REQ:
                String pathFromGallery = "file:///" + ImagePicker.getImagePathFromResult(AddExpense.this, requestCode,
                        resultCode, data);
               // Picasso.with(AddExpense.this).load(pathFromGallery).into(firstImage);
                break;

            case 100:
                if (data != null && data.getExtras() != null){
                    catName = data.getStringExtra("catName");
                    edtCategoryName.setText(catName);
                }
                break;
            case 500:
                if (data != null){
                    path=data.getStringExtra("path");
                    if(path!=null){
                        Picasso.with(this).load(R.drawable.pdf_icon).into(firstImage);
                    }
                }

                break;
            default:
                if (data != null){
                    photo = ImagePicker.getImageFromResult(AddExpense.this,
                            requestCode, resultCode, data);
                    if (photo == null){
                        return;
                    }
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(getApplicationContext(), photo);

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromURI(tempUri));
                    String imagePathFromResult1 = ImagePicker.getImagePathFromResult(AddExpense.this,
                            requestCode, resultCode, data);
                    if (imagePathFromResult1 != null) {
                        String path = "file:///" + imagePathFromResult1;
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AddExpense.this);
                        prefs.edit().putString(CACHED_IMG_KEY, imagePathFromResult1).apply();
                      //  Picasso.with(AddExpense.this).load(path).into(firstImage);
                        tempUris.add(getRealPathFromURI(tempUri));
                    }
                }

        }
        InputStream is = ImagePicker.getInputStreamFromResult(AddExpense.this, requestCode, resultCode, data);
        if (is != null) {
//            textView.setText("Got input stream!");
            try {
                is.close();
            } catch (IOException ex) {
                // ignore
            }
        } else {
//            textView.setText("Failed to get input stream!");
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

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
