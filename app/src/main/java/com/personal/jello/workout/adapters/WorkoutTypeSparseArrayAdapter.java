package com.personal.jello.workout.adapters;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.personal.jello.workout.models.WorkoutType;

public class WorkoutTypeSparseArrayAdapter extends SparseArrayAdapter<WorkoutType> {

    private final LayoutInflater inflater;
    public WorkoutTypeSparseArrayAdapter(Context context, SparseArray<WorkoutType> data) {
        inflater = LayoutInflater.from(context);
        setData(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView result = (TextView)convertView;
        if (result == null) {
            result = (TextView)inflater.inflate(android.R.layout.simple_list_item_1, null);
        }
        WorkoutType type = getItem(position);
        result.setText(type.toString());
        return result;
    }

}
