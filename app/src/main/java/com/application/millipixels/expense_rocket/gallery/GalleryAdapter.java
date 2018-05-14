package com.application.millipixels.expense_rocket.gallery;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.pdf_opener.PdfOpenActivity;

import java.io.File;
import java.util.List;

/**
 * Created by millipixelsinteractive_031 on 16/04/18.
 */

public class GalleryAdapter extends BaseAdapter {

    Activity context;
    List<File>list;
    IOnclick iOnclick;

    int pos;

    public GalleryAdapter(Activity context, List<File> list,IOnclick iOnclick) {

        this.context=context;
        this.list=list;
        this.iOnclick=iOnclick;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {



        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.custom_gallery, null);
        }

        final ImageView imageView = convertView.findViewById(R.id.imageview_gallery);
        final TextView nameTextView = convertView.findViewById(R.id.textview_gallery);


                File imgFile = new  File(list.get(position).getPath());


        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());


            imageView.setImageBitmap(myBitmap);

        }

        pos=position+1;

        nameTextView.setText("Slip "+pos);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iOnclick.onClick(position);

//                Intent intent=new Intent(context, PdfOpenActivity.class);
//                intent.putExtra("path",path);
//                context.startActivity(intent);


              //  openPdf(path);
            }
        });


        return convertView;
    }






    public interface IOnclick{
        public void onClick(int pos);
    }
}
