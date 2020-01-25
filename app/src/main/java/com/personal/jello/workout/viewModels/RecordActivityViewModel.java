package com.personal.jello.workout.viewModels;

import android.app.Application;
import android.view.View;
import android.widget.AdapterView;

import androidx.lifecycle.AndroidViewModel;

import com.personal.jello.workout.models.WeightTrainingRecordDetail;
import com.personal.jello.workout.models.WorkoutType;

public class RecordActivityViewModel extends AndroidViewModel {
    public WeightTrainingRecordDetail record;

    public RecordActivityViewModel(Application application) {
        super(application);
    }

    public void onTypeSelected(AdapterView<?> parent, View view, int pos, long id){
        record.type = (WorkoutType)parent.getAdapter().getItem(pos);
        record.general.typeId = record.type.typeId;
    }
}
