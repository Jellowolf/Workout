package com.personal.jello.workout.utility;

import android.widget.Spinner;

import androidx.databinding.BindingAdapter;

import com.personal.jello.workout.models.WorkoutType;

public class BindingAdapters {

    // need to put together a better adapter, but this should work for now
    @BindingAdapter("android:newValue")
    public static void setSpinnerValue(Spinner spinner, WorkoutType type) {
        if (spinner.getAdapter() == null) return;
        for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
            if (spinner.getAdapter().getItem(i) == type)
                spinner.setSelection(i, true);
        }
    }
}
