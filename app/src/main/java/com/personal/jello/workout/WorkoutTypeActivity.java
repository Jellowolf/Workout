package com.personal.jello.workout;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.personal.jello.workout.adapters.WeightTrainingDetailSparseArrayAdapter;
import com.personal.jello.workout.adapters.WorkoutTypeSparseArrayAdapter;
import com.personal.jello.workout.models.WeightTrainingRecordDetail;
import com.personal.jello.workout.models.WeightTrainingRecordGeneral;
import com.personal.jello.workout.models.WorkoutType;
import com.personal.jello.workout.services.WeightTrainingTypeService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.SparseArray;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class WorkoutTypeActivity extends AppCompatActivity {

    final WorkoutTypeActivity activity = this;
    private ListView listView;
    private static WeightTrainingTypeService typeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_type);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        typeService = new WeightTrainingTypeService(this.getApplication());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listView = findViewById(R.id.list);
        WorkoutTypeSparseArrayAdapter adapter = new WorkoutTypeSparseArrayAdapter(this, getRecordArray());
        listView.setAdapter(adapter);
    }

    private SparseArray<WorkoutType> getRecordArray() {
        List<WorkoutType> types = typeService.getAllTypes();
        SparseArray<WorkoutType> typeArray = new SparseArray<>();
        for (int i = 0; i < types.size(); i++) {
            typeArray.put(i, types.get(i));
        }
        return typeArray;
    }
}
