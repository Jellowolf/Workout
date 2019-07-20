package com.personal.jello.workout.adapters;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.personal.jello.workout.models.WeightTrainingRecordDetail;

import java.text.SimpleDateFormat;

public class WeightTrainingDetailSparseArrayAdapter extends SparseArrayAdapter<WeightTrainingRecordDetail> {

    private final LayoutInflater inflater;
    public WeightTrainingDetailSparseArrayAdapter(Context context, SparseArray<WeightTrainingRecordDetail> data) {
        inflater = LayoutInflater.from(context);
        setData(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView result = (TextView) convertView;
        if (result == null) {
            result = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, null);
        }
        WeightTrainingRecordDetail record = getItem(position);
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
        result.setText(record.type.toString() + " - " + formatter.format(record.general.date.getTime()));
        return result;
    }
}
