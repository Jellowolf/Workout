package com.personal.jello.workout.viewModels;

import com.personal.jello.workout.models.WeightTrainingRecord;

import androidx.lifecycle.ViewModel;

public class RecordActivityViewModel extends ViewModel {
    public WeightTrainingRecord record;

    public RecordActivityViewModel() {
        record = new WeightTrainingRecord();
    }
}
