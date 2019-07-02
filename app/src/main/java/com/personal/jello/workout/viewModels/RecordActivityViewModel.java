package com.personal.jello.workout.viewModels;

import android.view.View;
import android.widget.AdapterView;

import com.personal.jello.workout.models.WeightTrainingRecord;
import com.personal.jello.workout.models.WeightTrainingType;

import androidx.lifecycle.ViewModel;

public class RecordActivityViewModel extends ViewModel {
    public WeightTrainingRecord record;

    public RecordActivityViewModel() {}

    public void onTypeSelected(AdapterView<?> parent, View view, int pos, long id){
        record.type = (WeightTrainingType)parent.getAdapter().getItem(pos);
    }
}
