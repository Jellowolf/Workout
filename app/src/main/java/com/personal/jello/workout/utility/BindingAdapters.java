package com.personal.jello.workout.utility;

import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.databinding.BindingAdapter;

import com.personal.jello.workout.models.WorkoutType;

public class BindingAdapters {

    // need to put together a better adapter, but this should work for now
    @BindingAdapter("android:newValue")
    public static void setSpinnerValue(Spinner spinner, WorkoutType type) {
        SpinnerAdapter adapter = spinner.getAdapter();
        if (adapter == null) return;
        for (int i = 0; i < adapter.getCount(); i++) {
            if (type != null && ((WorkoutType)adapter.getItem(i)).typeId.equals(type.typeId))
                spinner.setSelection(i, true);
        }
    }
}
