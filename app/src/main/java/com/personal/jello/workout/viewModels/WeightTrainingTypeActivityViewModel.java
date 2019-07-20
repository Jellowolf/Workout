package com.personal.jello.workout.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.personal.jello.workout.models.WeightTrainingType;

public class WeightTrainingTypeActivityViewModel extends AndroidViewModel {
    public WeightTrainingType type;

    public WeightTrainingTypeActivityViewModel(Application application) {
        super(application);
    }
}
