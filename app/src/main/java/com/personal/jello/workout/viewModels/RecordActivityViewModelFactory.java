package com.personal.jello.workout.viewModels;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RecordActivityViewModelFactory implements ViewModelProvider.Factory {
    private Application application;

    public RecordActivityViewModelFactory(Application app) {
        application = app;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new RecordActivityViewModel(application);
    }
}
