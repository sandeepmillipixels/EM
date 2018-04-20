package com.application.millipixels.expense_rocket.pdf_opener;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.addexpense.AddExpense;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by millipixelsinteractive_031 on 16/04/18.
 */

public class PdfOpenActivity extends AppCompatActivity implements OnPageChangeListener,OnLoadCompleteListener {


    @BindView(R.id.toolbar_open_pdf)
    Toolbar toolbar;

    @BindView(R.id.pdfView)
    PDFView pdfView;

    String path;

    MenuItem registrar;

    Integer pageNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.open_pdf);

        ButterKnife.bind(this);

        initToolBar();

        path=getIntent().getStringExtra("path");

        if(path!=null){

            displayFromAsset(path);

           // openPdf(path);
        }

    }
    private void displayFromAsset(String assetFileName) {


        File file=new File(assetFileName);


      pdfView.fromFile(file)
                .defaultPage(pageNumber)
                .enableSwipe(true)

                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();


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


    void openPdf(String path) {

        File pdfFile = new File(path);



        try {

            if (pdfFile.exists()) {

                Uri uri = Uri.fromFile(pdfFile);

                Intent objIntent = new Intent(Intent.ACTION_VIEW);

                objIntent.setDataAndType(uri, "application/pdf");

                objIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(objIntent);

            } else {

                Toast.makeText(this, "File NotFound",

                        Toast.LENGTH_SHORT).show();

            }

        } catch (ActivityNotFoundException e) {

            Toast.makeText(this,

                    "No Viewer Application Found", Toast.LENGTH_SHORT)

                    .show();

        } catch (Exception e) {

            e.printStackTrace();

        }






//        File file = new File(path);
//
//        Intent target = new Intent(Intent.ACTION_VIEW);
//        target.setDataAndType(Uri.fromFile(file),"application/pdf");
//        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//
//        Intent intent = Intent.createChooser(target, "Open File");
//        try {
//            startActivity(intent);
//        } catch (ActivityNotFoundException e) {
//            // Instruct the user to install a PDF reader here, or something
//        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pdf, menu);

        registrar = menu.findItem(R.id.action_done);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        if (item.getItemId() == R.id.action_add_expense){
            Intent intent=new Intent(this, AddExpense.class);
            intent.putExtra("path",path);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", path, page + 1, pageCount));
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

}
