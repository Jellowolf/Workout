package com.personal.jello.workout.viewModels;

import android.app.Application;
import android.view.View;
import android.widget.AdapterView;

import com.personal.jello.workout.database.WorkoutRepository;
import com.personal.jello.workout.models.WeightTrainingRecord;
import com.personal.jello.workout.models.WeightTrainingType;

import androidx.lifecycle.AndroidViewModel;

public class RecordActivityViewModel extends AndroidViewModel {
    private WorkoutRepository workoutRepository;
    public WeightTrainingRecord record;

    public RecordActivityViewModel(Application application) {
        super(application);
        workoutRepository = new WorkoutRepository(application);
    }

    public void onTypeSelected(AdapterView<?> parent, View view, int pos, long id){
        record.type = (WeightTrainingType)parent.getAdapter().getItem(pos);
    }

    public void saveRecord(View view) {
        workoutRepository.insertRecord(record);
    }

    public void getRecord() {
        record = workoutRepository.getAllRecords().get(0);
    }
}
