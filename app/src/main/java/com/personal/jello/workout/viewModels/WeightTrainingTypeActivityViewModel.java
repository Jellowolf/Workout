package com.personal.jello.workout.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.personal.jello.workout.models.WorkoutType;

public class WeightTrainingTypeActivityViewModel extends AndroidViewModel {
    public WorkoutType type;

    public WeightTrainingTypeActivityViewModel(Application application) {
        super(application);
    }
}
