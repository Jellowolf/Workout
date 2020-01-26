package com.personal.jello.workout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.personal.jello.workout.adapters.WeightTrainingDetailSparseArrayAdapter;
import com.personal.jello.workout.databinding.AddRecordDialogBinding;
import com.personal.jello.workout.models.WeightTrainingRecordDetail;
import com.personal.jello.workout.models.WeightTrainingRecordGeneral;
import com.personal.jello.workout.models.WorkoutType;
import com.personal.jello.workout.services.WeightTrainingRecordService;
import com.personal.jello.workout.services.WorkoutTypeService;
import com.personal.jello.workout.viewModels.RecordActivityViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.SparseArray;
import android.view.LayoutInflater;
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
                final Dialog dialog = new Dialog(activity);
                AddRecordDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.add_record_dialog, null, false);
                dialog.setContentView(binding.getRoot());

                viewModel.record = new WeightTrainingRecordDetail();
                viewModel.record.general = new WeightTrainingRecordGeneral();
                viewModel.record.general.date = Calendar.getInstance();
                binding.setViewModel(viewModel);
                binding.setLifecycleOwner(activity);

                Spinner spinner = dialog.findViewById(R.id.dialog_types);
                ArrayAdapter<WorkoutType> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, android.R.id.text1, typeService.getAllTypes());
                spinner.setAdapter(adapter);

                Button saveButton = dialog.findViewById(R.id.dialog_save_button);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recordService.saveRecord(viewModel.record.general);
                        resetList();
                        viewModel.record = null;
                        dialog.dismiss();
                    }
                });

                Button cancelButton = dialog.findViewById(R.id.dialog_cancel_button);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        listView = findViewById(R.id.list);
        WeightTrainingDetailSparseArrayAdapter adapter = new WeightTrainingDetailSparseArrayAdapter(this, getRecordArray());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewModel.record = (WeightTrainingRecordDetail)parent.getAdapter().getItem(position);

                // Temporarily recycling the dialog code from above
                final Dialog dialog = new Dialog(activity);
                AddRecordDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.add_record_dialog, null, false);
                dialog.setContentView(binding.getRoot());

                // Set the data source before the viewmodel, or the newValue binding will not work
                Spinner spinner = dialog.findViewById(R.id.dialog_types);
                ArrayAdapter<WorkoutType> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, android.R.id.text1, typeService.getAllTypes());
                spinner.setAdapter(adapter);

                binding.setViewModel(viewModel);
                binding.setLifecycleOwner(activity);

                Button saveButton = dialog.findViewById(R.id.dialog_save_button);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recordService.updateRecord(viewModel.record.general);
                        resetList();
                        viewModel.record = null;
                        dialog.dismiss();
                    }
                });

                Button deleteButton = dialog.findViewById(R.id.dialog_delete_button);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recordService.deleteRecord(viewModel.record.general);
                        resetList();
                        viewModel.record = null;
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
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
}
