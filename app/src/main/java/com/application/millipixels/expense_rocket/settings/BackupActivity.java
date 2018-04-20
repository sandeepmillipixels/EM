package com.application.millipixels.expense_rocket.settings;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.database.AllExpensesDataSource;

import com.application.millipixels.expense_rocket.model.AllExpenses;
import com.application.millipixels.expense_rocket.utils.Utility;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by millipixelsinteractive_031 on 29/01/18.
 */

public class BackupActivity extends AppCompatActivity {

    RelativeLayout backUpLayout,restoreLayout;

    AllExpensesDataSource allExpensesDataSource;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);
        ButterKnife.bind(this);
        setUpToolbar();
        backUpLayout=(RelativeLayout)findViewById(R.id.layout_backup_backup);
        restoreLayout=(RelativeLayout)findViewById(R.id.layout_restore);

        allExpensesDataSource=new AllExpensesDataSource(this);

        backUpLayout.setOnClickListener(backupListener);
        restoreLayout.setOnClickListener(restoreListener);


    }


    private View.OnClickListener backupListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            allExpensesDataSource.exportData();
            if(isWriteStoragePermissionGranted()){
                shareCsvFile();
            }


        }
    };

    private View.OnClickListener restoreListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isReadStoragePermissionGranted()){
                pickFile();
            }
        }
    };

    private void pickFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath());
        intent.setDataAndType(uri, "text/csv");
        startActivityForResult(Intent.createChooser(intent, "Choose File"),100);
    }

    public void shareCsvFile(){
        File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "expense.csv");
        Uri path = Uri.fromFile(filelocation);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
       // sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Expense Details");
        sendIntent.putExtra(Intent.EXTRA_STREAM, path);
        sendIntent.setType("text/html");
        startActivity(sendIntent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
    private void setUpToolbar(){
        toolbar.setTitle(R.string.action_settings);
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
    private void showDailog(final Uri uri){
        AlertDialog.Builder builder = new AlertDialog.Builder(BackupActivity.this);
        builder.setTitle("Restore Backup");
        builder.setCancelable(true);
        builder.setMessage("Restoring backup will overwrite the previous data");
        builder.setPositiveButton("Restore", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                try{
                    int i =0;
                    File csvfile = new File(Utility.getPath(BackupActivity.this,uri));
//                    File csvfile = new File(Environment.getExternalStorageDirectory() + "/csvfile.csv");
                    CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
                    String [] nextLine;
                    allExpensesDataSource.open();
                    allExpensesDataSource.deleteAll();
                    while ((nextLine = reader.readNext()) != null) {
                        if (i>0){
                            AllExpenses allExpenses = new AllExpenses();
                            allExpenses.setCategory_id(Long.parseLong(nextLine[0]));
                            allExpenses.setCategory_name(nextLine[1]);
                            allExpenses.setExpense_amount(nextLine[2]);
                            allExpenses.setExpense_date(nextLine[3]);
                            allExpenses.setExpense_date_milli(Long.parseLong(nextLine[4]));
                            allExpenses.setExpense_note(nextLine[5]);
                            try {
                                allExpensesDataSource.open();
                                allExpensesDataSource.createExpense(allExpenses);
                                allExpensesDataSource.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        ++i;
                        // nextLine[] is an array of values from the line
                        System.out.println(nextLine[0] + nextLine[1] + "etc...");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(BackupActivity.this, "The specified file was not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            if (uri != null){
                showDailog(uri);
            }

        }
    }

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("","Permission is granted1");

                return true;
            } else {

                Log.v("","Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("","Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("","Permission is granted2");
                return true;
            } else {
                Log.v("","Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("","Permission is granted2");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d("", "External storage2");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v("","Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                    shareCsvFile();
                    
                }else{
                    
                }
                break;

            case 3:
                Log.d("", "External storage1");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v("","Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                    pickFile();
                }else{
                    
                }
                break;
        }
    }

}
