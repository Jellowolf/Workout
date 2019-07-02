package com.personal.jello.workout;

import android.app.Dialog;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.personal.jello.workout.databinding.AddDialogBinding;
import com.personal.jello.workout.models.WeightTrainingRecord;
import com.personal.jello.workout.models.WeightTrainingType;
import com.personal.jello.workout.viewModels.RecordActivityViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Calendar;

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
        viewModel = ViewModelProviders.of(this).get(RecordActivityViewModel.class);

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

                Button dialogButton = dialog.findViewById(R.id.dialog_button);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.record = null;
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        listView = findViewById(R.id.list);
        String[] listValues = new String[] {
                "Bluh",
                "Bleh",
                "Blah"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listValues);
        listView.setAdapter(adapter);
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
}
