package com.application.millipixels.expense_rocket.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.expense_rocket.model.ImageList;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by millipixelsinteractive_024 on 10/05/18.
 */

public class ImageScrollAdapter extends RecyclerView.Adapter<ImageScrollAdapter.MyView> {

    private List<ImageList> list;
    private Context context;
    IOnActionListener listener;
    int lastPos = 0;
    public class MyView extends RecyclerView.ViewHolder {

        public ImageView image;
        public RelativeLayout container;

        public MyView(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.image);
            container = (RelativeLayout) view.findViewById(R.id.container);

        }
    }


    public ImageScrollAdapter(List<ImageList> horizontalList, Context context, IOnActionListener listener) {
        this.list = horizontalList;
        this.context = context;
        this.listener = listener;
    }
    @Override public long getItemId(int position) { return position; }
    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        File f = new File(list.get(position).getImagePath());
        Picasso.with(context).load(f).resize(72,96).into(holder.image);
//        if(f.exists())
//        {
////            ImageView myImage = new ImageView(this);
//            holder.image.setImageURI(Uri.fromFile(f));
//
//        }
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( listener != null){
                    listener.onImageClick(position);
                    list.get(lastPos).setSelected(false);
                    list.get(position).setSelected(true);
                    lastPos = position;
                    notifyDataSetChanged();
                }
            }
        });
        if (list.get(position).isSelected()){
            holder.container.setBackgroundColor(context.getResources().getColor(R.color.dark_purple));
        }else {
            holder.container.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface IOnActionListener{
        void onImageClick(int pos);
    }
    public void setSelectedPage(int pos){
        list.get(lastPos).setSelected(false);
        list.get(pos).setSelected(true);
        lastPos = pos;
        notifyDataSetChanged();
    }
}
