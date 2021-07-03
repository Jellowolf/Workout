package com.personal.jello.workout.adapters;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SparseArrayAdapter<T> extends BaseAdapter {

    private SparseArray<T> data = new SparseArray<>();
    public void setData(SparseArray<T> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.valueAt(position);
    }

    @Override
    public long getItemId(int position) {
        return data.keyAt(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public void append(int key, T item) {
        data.append(key, item);
        notifyDataSetChanged();
    }

    public void removeItemAt(int position) {
        data.removeAt(position);
        notifyDataSetChanged();
    }

    public void refreshAdapter(SparseArray<T> data) {
        setData(data);
        notifyDataSetChanged();
    }
}
