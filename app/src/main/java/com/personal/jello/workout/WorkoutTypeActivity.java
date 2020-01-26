package com.personal.jello.workout;

import android.app.Dialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.personal.jello.workout.adapters.WorkoutTypeSparseArrayAdapter;
import com.personal.jello.workout.databinding.AddWorkoutTypeDialogBinding;
import com.personal.jello.workout.models.WorkoutType;
import com.personal.jello.workout.services.WorkoutTypeService;
import com.personal.jello.workout.viewModels.WorkoutTypeActivityViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class WorkoutTypeActivity extends AppCompatActivity {

    final WorkoutTypeActivity activity = this;
    private ListView listView;
    private static WorkoutTypeActivityViewModel viewModel;
    private static WorkoutTypeService typeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_type);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewModel = ViewModelProviders.of(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(WorkoutTypeActivityViewModel.class);
        typeService = new WorkoutTypeService(this.getApplication());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTypeDialog(null);
            }
        });

        listView = findViewById(R.id.list);
        WorkoutTypeSparseArrayAdapter adapter = new WorkoutTypeSparseArrayAdapter(this, getRecordArray());
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        if (view.getId() == R.id.list) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_type_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        WorkoutType type = (WorkoutType) this.listView.getItemAtPosition(info.position);
        switch (item.getItemId()) {
            case R.id.menu_edit:
                createTypeDialog(type);
                resetList();
                return true;
            case R.id.menu_delete:
                typeService.deleteType(type);
                resetList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private SparseArray<WorkoutType> getRecordArray() {
        List<WorkoutType> types = typeService.getAllTypes();
        SparseArray<WorkoutType> typeArray = new SparseArray<>();
        for (int i = 0; i < types.size(); i++) {
            typeArray.put(i, types.get(i));
        }
        return typeArray;
    }

    private void resetList() {
        //this is horrendous, I need to set up notifying/updating properly
        WorkoutTypeSparseArrayAdapter adapter = new WorkoutTypeSparseArrayAdapter(activity, getRecordArray());
        listView.setAdapter(adapter);
    }

    private void createTypeDialog(@Nullable WorkoutType type) {
        final Dialog dialog = new Dialog(activity);
        AddWorkoutTypeDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.add_workout_type_dialog, null, false);
        dialog.setContentView(binding.getRoot());

        viewModel.type = type != null ? type : new WorkoutType();
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(activity);

        Button saveButton = dialog.findViewById(R.id.dialog_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.type.typeId == null)
                    typeService.saveType(viewModel.type);
                else
                    typeService.updateType(viewModel.type);
                resetList();
                viewModel.type = null;
                dialog.dismiss();
            }
        });

        Button cancelButton = dialog.findViewById(R.id.dialog_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.type = null;
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
