package com.application.millipixels.expense_rocket;

import android.*;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.application.millipixels.expense_rocket.editor.EditType;
import com.application.millipixels.expense_rocket.editor.StartSnapHelper;
import com.application.millipixels.expense_rocket.editor.adapter.EditAdapter;
import com.application.millipixels.expense_rocket.editor.brightness.BightnessActivity;
import com.application.millipixels.expense_rocket.editor.contrast.ContrastActivity;
import com.application.millipixels.expense_rocket.editor.crop.CropActivity;
import com.application.millipixels.expense_rocket.editor.rotate.RotateActivity;
import com.application.millipixels.expense_rocket.editor.saturation.SeturationActivity;
import com.application.millipixels.expense_rocket.gallery.GalleyActivity;
import com.application.millipixels.expense_rocket.imagepicker.ImagePicker;
import com.application.millipixels.expense_rocket.shoebox.SlidingImage_Adapter;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditImageActivity extends AppCompatActivity implements
        EditAdapter.OnItemEditPhotoClickedListener{

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
    public static String PAGE = "page";
    public static String PATH = "path";

    ProgressDialog dialog;

    Image image;

    String path;

    boolean visibility=false;

    ArrayList<String> tempUris;
    MenuItem registrar;
    boolean shoebox = false ;
    @BindView(R.id.navigation)
    RecyclerView navigation;
    String dateTime;
    private static final String INPUT_URL = "inputUrl";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        ButterKnife.bind(this);
        mPager =findViewById(R.id.pager);

        dialog=new ProgressDialog(this);

        dialog.setTitle("Please wait...");
        dialog.setMessage("Converting images to PDF.");


        show_box_hint_textView=findViewById(R.id.show_box_hint_textView);


        android.support.design.widget.FloatingActionButton add_images_floating_button=findViewById(R.id.add_images_floating_button);

        initToolBar();

        arrayList=new ArrayList<>();

        tempUris=new ArrayList<>();

        if(arrayList.size()==0){
            show_box_hint_textView.setVisibility(View.VISIBLE);
            visibility=true;

            ImagePicker.pickImage(EditImageActivity.this, "Select your image:");

        }

        adapter=new SlidingImage_Adapter(EditImageActivity.this,arrayList);
        mPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        add_images_floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestForSpecificPermission();
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
        initView();
    }

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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ImagePicker.pickImage(EditImageActivity.this, "Select your image:");
                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void createPdf() {

        if (arrayList.size() == 0) {
            if (tempUris.size() == 0) {
                return;
            } else {
                arrayList = (ArrayList<String>) tempUris.clone();
            }
        }


        new EditImageActivity.CreatingPdf().execute();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {

                    String path=data.getExtras().getString("path");
                    int position=data.getExtras().getInt("position");

                    // arrayList.remove(currentPage);

                    arrayList.set(position,path);

                    // arrayList.add(position,path);
                    adapter.notifyDataSetChanged();

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }


            case SECOND_PIC_REQ:
                String imagePathFromResult = ImagePicker.getImagePathFromResult(EditImageActivity.this,
                        requestCode, resultCode, data);

                break;
            case GALLERY_ONLY_REQ:
                String pathFromGallery = "file:///" + ImagePicker.getImagePathFromResult(EditImageActivity.this, requestCode,
                        resultCode, data);
                break;

            case 100:
                if (data != null && data.getExtras() != null){

                }
                break;
            default:
                if (data != null){

                    photo = ImagePicker.getImageFromResult(EditImageActivity.this,
                            requestCode, resultCode, data);
                    if (photo == null){
                        return;
                    }

                    show_box_hint_textView.setVisibility(View.GONE);
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(getApplicationContext(), photo);

                    if (tempUri != null) {
                        arrayList.add(getRealPathFromURI(tempUri));
                        visibility=true;
                        registrar.setVisible(visibility);
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    if (arrayList.size() == 0 && shoebox){
                        finish();
                    }
                }

        }

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

    public class CreatingPdf extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            path="";

            path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PDFfiles/";

            File folder = new File(path);
            if (!folder.exists()) {
                boolean success = folder.mkdir();
                if (!success) {
                    Toast.makeText(EditImageActivity.this, "Error on creating application folder", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }

//            path= path + System.currentTimeMillis() + ".jpg";
//            try {
//                FileOutputStream fileOutputStream = new FileOutputStream(path);
//                photo.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
//                fileOutputStream.flush();
//                fileOutputStream.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            path = path + dateTime + ".pdf";



            Document document = new Document(PageSize.A4, 0, 0, 0, 0);

            Rectangle documentRect = document.getPageSize();

            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));


                document.open();

                for (int i = 0; i < arrayList.size(); i++) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 70, stream);

                    image = Image.getInstance(arrayList.get(i));

                    document.add(image);

                    document.newPage();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            document.close();
            arrayList.clear();
            tempUris.clear();




            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();

            dateTime="";

            finish();

            openShoeBox();

            //openPdf();

        }
    }


    private void openShoeBox(){
        Intent intent=new Intent(this, GalleyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    void openPdf() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);

        registrar = menu.findItem(R.id.action_done);
        registrar.setVisible(!visibility);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        if (item.getItemId() == R.id.action_done){
            dateTime = currentDateFormat();
            storeCameraPhoto(photo,dateTime);
            createPdf();
        }
        return super.onOptionsItemSelected(item);

    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
    }



    private void storeCameraPhoto(Bitmap bitmap,String currentDate){

        path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PDFfiles/JPEG/";

        File folder = new File(path);
        if (!folder.exists()) {
            boolean success = folder.mkdir();
            if (!success) {
                Toast.makeText(EditImageActivity.this, "Error on creating application folder", Toast.LENGTH_SHORT).show();

            }
        }

        path= path+currentDate + ".jpg";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private void initView() {

        EditAdapter editAdapter = new EditAdapter(this);
        editAdapter.setOnItemEditPhotoClickedListener(this);
        navigation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        navigation.setAdapter(editAdapter);
        new StartSnapHelper().attachToRecyclerView(navigation);

        editAdapter.add(EditType.Crop);
        editAdapter.add(EditType.Rotate);
        editAdapter.add(EditType.Saturation);
        editAdapter.add(EditType.Brightness);
        editAdapter.add(EditType.Contrast);

    }

    @Override
    public void onItemEditPhotoClicked(EditType type) {
        switch (type) {
            case Crop:
                Intent intent = new Intent(EditImageActivity.this, CropActivity.class);
                intent.putExtra(INPUT_URL,arrayList.get(currentPage));
                startActivity(intent);
//                ((EditPhotoActivity) getActivity()).addFragmentToStack(CropFragment.create(outputUrl, this));
                break;
            case Rotate:
                Intent intentRotate = new Intent(EditImageActivity.this, RotateActivity.class);
                intentRotate.putExtra(INPUT_URL,arrayList.get(currentPage));
                startActivity(intentRotate);
//                ((EditPhotoActivity) getActivity()).addFragmentToStack(RotateFragment.create(outputUrl, this));
                break;
            case Saturation:
                Intent intentSeturation = new Intent(EditImageActivity.this, SeturationActivity.class);
                intentSeturation.putExtra(INPUT_URL,arrayList.get(currentPage));
                startActivity(intentSeturation);
//                ((EditPhotoActivity) getActivity()).addFragmentToStack(SaturationFragment.create(outputUrl, this));
                break;
            case Brightness:
                Intent intentBrightness = new Intent(EditImageActivity.this, BightnessActivity.class);
                intentBrightness.putExtra(INPUT_URL,arrayList.get(currentPage));
                startActivity(intentBrightness);
//                ((EditPhotoActivity) getActivity()).addFragmentToStack(BrightnessFragment.create(outputUrl, this));
                break;
            case Contrast:
                Intent intentContrast = new Intent(EditImageActivity.this, ContrastActivity.class);
                intentContrast.putExtra(INPUT_URL,arrayList.get(currentPage));
                startActivity(intentContrast);
//                ((EditPhotoActivity) getActivity()).addFragmentToStack(ContrastFragment.create(outputUrl, this));
                break;
        }
    }
}
