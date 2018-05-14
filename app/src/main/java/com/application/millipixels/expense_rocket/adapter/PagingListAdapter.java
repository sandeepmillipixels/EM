package com.application.millipixels.expense_rocket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.models.PagingListModal;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

/**
 * Created by millipixelsinteractive_024 on 10/05/18.
 */

public class PagingListAdapter extends RecyclerView.Adapter<PagingListAdapter.MyView> {

    private List<PagingListModal> list;
    private Context context;
    PagingListAdapter.IPagingListener listener;
    int lastPos = 0;
    public class MyView extends RecyclerView.ViewHolder {

        public TextView text;
        public RelativeLayout container;

        public MyView(View view) {
            super(view);

            text = (TextView) view.findViewById(R.id.text);
            container = (RelativeLayout) view.findViewById(R.id.container);

        }
    }


    public PagingListAdapter(List<PagingListModal> horizontalList, Context context, PagingListAdapter.IPagingListener listener) {
        this.list = horizontalList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public PagingListAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.paging_item, parent, false);
        return new PagingListAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final PagingListAdapter.MyView holder, final int position) {
        holder.text.setText((position+1)+"");
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( listener != null){
                    listener.onPageClick(position);
                    list.get(lastPos).setSelected(false);
                    list.get(position).setSelected(true);
                    lastPos = position;
                    notifyDataSetChanged();
                }
            }
        });
        if (list.get(position).isSelected()){
            holder.container.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_white_fill));
            holder.text.setTextColor(context.getResources().getColor(R.color.purple));
        }else {
            holder.container.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_white_border));
            holder.text.setTextColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface IPagingListener{
        void onPageClick(int pos);
    }

    public void setSelectedPage(int pos){
        list.get(lastPos).setSelected(false);
        list.get(pos).setSelected(true);
        lastPos = pos;
        notifyDataSetChanged();
    }
}

