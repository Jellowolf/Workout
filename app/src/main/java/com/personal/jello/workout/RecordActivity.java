package com.personal.jello.workout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.personal.jello.workout.adapters.WeightTrainingDetailSparseArrayAdapter;
import com.personal.jello.workout.databinding.AddRecordDialogBinding;
import com.personal.jello.workout.databinding.AddWorkoutTypeDialogBinding;
import com.personal.jello.workout.models.WeightTrainingRecordDetail;
import com.personal.jello.workout.models.WeightTrainingRecordGeneral;
import com.personal.jello.workout.models.WorkoutType;
import com.personal.jello.workout.services.WeightTrainingRecordService;
import com.personal.jello.workout.services.WorkoutTypeService;
import com.personal.jello.workout.viewModels.RecordActivityViewModel;

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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.List;

public class RecordActivity extends AppCompatActivity {

    final RecordActivity activity = this;
    private ListView listView;
    private static RecordActivityViewModel viewModel;
    private static WeightTrainingRecordService recordService;
    private static WorkoutTypeService typeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewModel = ViewModelProviders.of(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(RecordActivityViewModel.class);
        recordService = new WeightTrainingRecordService(this.getApplication());
        typeService = new WorkoutTypeService(this.getApplication());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRecordDialog(null, true);
            }
        });
        listView = findViewById(R.id.list);
        WeightTrainingDetailSparseArrayAdapter adapter = new WeightTrainingDetailSparseArrayAdapter(this, getRecordArray());
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewModel.record = (WeightTrainingRecordDetail)parent.getAdapter().getItem(position);
                createRecordDialog(viewModel.record, false);
            }
        });
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
        WeightTrainingRecordDetail recordDetail = (WeightTrainingRecordDetail) this.listView.getItemAtPosition(info.position);
        switch (item.getItemId()) {
            case R.id.menu_edit:
                createRecordDialog(recordDetail, true);
                resetList();
                return true;
            case R.id.menu_delete:
                recordService.deleteRecord(recordDetail.general);
                resetList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(activity, SettingsActivity.class);
            activity.startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // there's probably a more elegant way, but this is accounting for an edited type
    @Override
    public void onResume() {
        super.onResume();
        resetList();
    }

    private SparseArray<WeightTrainingRecordDetail> getRecordArray() {
        List<WeightTrainingRecordDetail> records = recordService.getAllDetails();
        List<WeightTrainingRecordGeneral> general = recordService.getAllRecords();
        SparseArray<WeightTrainingRecordDetail> recordArray = new SparseArray<>();
        for (int i = 0; i < records.size(); i++) {
            recordArray.put(i, records.get(i));
        }
        return recordArray;
    }

    private void resetList() {
        //this is horrendous, I need to set up notifying/updating properly
        WeightTrainingDetailSparseArrayAdapter adapter = new WeightTrainingDetailSparseArrayAdapter(activity, getRecordArray());
        listView.setAdapter(adapter);
    }

    private void createRecordDialog(@Nullable WeightTrainingRecordDetail recordDetail, boolean controlsEnabled) {
        if (recordDetail != null) {
            viewModel.record = recordDetail;
        }
        else {
            viewModel.record = new WeightTrainingRecordDetail();
            viewModel.record.general = new WeightTrainingRecordGeneral();
            viewModel.record.general.date = Calendar.getInstance();
        }

        final Dialog dialog = new Dialog(activity);
        AddRecordDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.add_record_dialog, null, false);
        dialog.setContentView(binding.getRoot());

        // Set the data source before the viewmodel, or the newValue binding will not work (not sure what the relevance is on this anymore)
        Spinner spinner = dialog.findViewById(R.id.dialog_types);
        ArrayAdapter<WorkoutType> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, android.R.id.text1, typeService.getAllTypes());
        spinner.setAdapter(adapter);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(activity);

        Button saveButton = dialog.findViewById(R.id.dialog_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.record.general.recordId == null)
                    recordService.saveRecord(viewModel.record.general);
                else
                    recordService.updateRecord(viewModel.record.general);
                resetList();
                viewModel.record = null;
                dialog.dismiss();
            }
        });

        Button editButton = dialog.findViewById(R.id.dialog_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setControlsEnabled(dialog, true);
            }
        });

        Button cancelButton = dialog.findViewById(R.id.dialog_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        setControlsEnabled(dialog, controlsEnabled);
        dialog.show();
    }

    private void setControlsEnabled(final Dialog dialog, boolean isEnabled) {
        dialog.findViewById(R.id.dialog_types).setEnabled(isEnabled);
        dialog.findViewById(R.id.dialog_sets).setEnabled(isEnabled);
        dialog.findViewById(R.id.dialog_reps).setEnabled(isEnabled);
        dialog.findViewById(R.id.dialog_weight).setEnabled(isEnabled);
        dialog.findViewById(R.id.dialog_date).setEnabled(isEnabled);
        dialog.findViewById(R.id.dialog_save_button).setVisibility(isEnabled ? View.VISIBLE : View.INVISIBLE);
        dialog.findViewById(R.id.dialog_edit_button).setVisibility(isEnabled ? View.INVISIBLE : View.VISIBLE);
    }
}
