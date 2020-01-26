package com.personal.jello.workout.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.personal.jello.workout.models.WorkoutType;

public class WorkoutTypeActivityViewModel extends AndroidViewModel {
    public WorkoutType type;

    public WorkoutTypeActivityViewModel(Application application) {
        super(application);
    }
}
