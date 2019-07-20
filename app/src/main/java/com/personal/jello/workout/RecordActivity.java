package com.personal.jello.workout;

import android.app.Dialog;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.personal.jello.workout.adapters.WeightTrainingRecordSparseArrayAdapter;
import com.personal.jello.workout.databinding.AddDialogBinding;
import com.personal.jello.workout.models.WeightTrainingRecord;
import com.personal.jello.workout.models.WeightTrainingType;
import com.personal.jello.workout.viewModels.RecordActivityViewModel;
import com.personal.jello.workout.viewModels.RecordActivityViewModelFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewModel = ViewModelProviders.of(this, new RecordActivityViewModelFactory(this.getApplication())).get(RecordActivityViewModel.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(activity);
                AddDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.add_dialog, null, false);
                dialog.setContentView(binding.getRoot());

                viewModel.record = new WeightTrainingRecord();
                viewModel.record.date = Calendar.getInstance();
                binding.setViewModel(viewModel);
                binding.setLifecycleOwner(activity);

                Spinner spinner = dialog.findViewById(R.id.dialog_types);
                ArrayAdapter<WeightTrainingType> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, android.R.id.text1, WeightTrainingType.values());
                spinner.setAdapter(adapter);

                Button saveButton = dialog.findViewById(R.id.dialog_save_button);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.saveRecord();
                        resetList();
                        viewModel.record = null;
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        listView = findViewById(R.id.list);
        WeightTrainingRecordSparseArrayAdapter adapter = new WeightTrainingRecordSparseArrayAdapter(this, getRecordArray());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewModel.record = (WeightTrainingRecord)parent.getAdapter().getItem(position);

                // Temporarily recycling the dialog code from above
                final Dialog dialog = new Dialog(activity);
                AddDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.add_dialog, null, false);
                dialog.setContentView(binding.getRoot());

                // Set the data source before the viewmodel, or the newValue binding will not work
                Spinner spinner = dialog.findViewById(R.id.dialog_types);
                ArrayAdapter<WeightTrainingType> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, android.R.id.text1, WeightTrainingType.values());
                spinner.setAdapter(adapter);

                binding.setViewModel(viewModel);
                binding.setLifecycleOwner(activity);

                Button saveButton = dialog.findViewById(R.id.dialog_save_button);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.updateRecord();
                        resetList();
                        viewModel.record = null;
                        dialog.dismiss();
                    }
                });

                Button deleteButton = dialog.findViewById(R.id.dialog_delete_button);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.deleteRecord();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private SparseArray<WeightTrainingRecord> getRecordArray() {
        List<WeightTrainingRecord> records = viewModel.getAllRecords();
        SparseArray<WeightTrainingRecord> recordArray = new SparseArray<>();
        for (int i = 0; i < records.size(); i++) {
            recordArray.put(i, records.get(i));
        }
        return recordArray;
    }

    private void resetList() {
        //this is horrendous, I need to set up notifying/updating properly
        WeightTrainingRecordSparseArrayAdapter adapter = new WeightTrainingRecordSparseArrayAdapter(activity, getRecordArray());
        listView.setAdapter(adapter);
    }
}
