package com.application.millipixels.expense_rocket.editor.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by millipixelsinteractive_024 on 23/04/18.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> list;
    protected Activity activity;
    protected LayoutInflater inflater;

    public BaseAdapter(Activity activity) {
        this.activity = activity;
        this.list = new ArrayList<>();
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public synchronized void add(T t) {
        list.add(t);
        sort();
        int position = list.indexOf(t);
        notifyItemInserted(position);
    }

    public synchronized void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public synchronized void addPosition(T t, int position) {
        list.add(position, t);
        sort();
        position = list.indexOf(t);
        notifyItemInserted(position);
    }

    public synchronized void removerPosition(int position) {
        T t = list.get(position);
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, list.size());
    }

    public synchronized void updatePosition(T t, int position) {
        list.set(position, t);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, list.size());
    }

    public ArrayList<T> getData() {
        return (ArrayList<T>) list;
    }

    synchronized void sort() {
    }

}

