package com.personal.jello.workout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.personal.jello.workout.adapters.WeightTrainingDetailSparseArrayAdapter;
import com.personal.jello.workout.controllers.WeightTrainingController;
import com.personal.jello.workout.controllers.WorkoutTypeController;
import com.personal.jello.workout.databinding.AddRecordDialogBinding;
import com.personal.jello.workout.models.WeightTrainingRecordDetail;
import com.personal.jello.workout.models.WeightTrainingRecordGeneral;
import com.personal.jello.workout.models.WorkoutType;
import com.personal.jello.workout.services.WeightTrainingRecordService;
import com.personal.jello.workout.services.WorkoutTypeService;
import com.personal.jello.workout.utility.Filters;
import com.personal.jello.workout.utility.Path;
import com.personal.jello.workout.utility.PortUtility;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.json.JavalinJson;

import static io.javalin.apibuilder.ApiBuilder.after;
import static io.javalin.apibuilder.ApiBuilder.before;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

public class RecordActivity extends AppCompatActivity {

    final RecordActivity activity = this;
    private ListView listView;
    private static RecordActivityViewModel viewModel;
    private static WeightTrainingRecordService recordService;
    private static WorkoutTypeService typeService;
    private static boolean restServiceStarted = false;

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
        fab.setOnClickListener(view -> createRecordDialog(null, true));
        listView = findViewById(R.id.list);
        WeightTrainingDetailSparseArrayAdapter adapter = new WeightTrainingDetailSparseArrayAdapter(this, getRecordArray());
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            viewModel.record = (WeightTrainingRecordDetail)parent.getAdapter().getItem(position);
            createRecordDialog(viewModel.record, false);
        });

        ImageButton refreshButton = findViewById(R.id.record_refresh_button);
        refreshButton.setOnClickListener(view -> refreshList());

        if (!restServiceStarted)
            CreateRestService();
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
                refreshList();
                return true;
            case R.id.menu_delete:
                ((WeightTrainingDetailSparseArrayAdapter)listView.getAdapter()).removeItemAt(info.position);
                recordService.deleteRecord(recordDetail.general);
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
        refreshList();
    }

    private SparseArray<WeightTrainingRecordDetail> getRecordArray() {
        List<WeightTrainingRecordDetail> records = recordService.getAllDetails();
        SparseArray<WeightTrainingRecordDetail> recordArray = new SparseArray<>();
        for (int i = 0; i < records.size(); i++) {
            WeightTrainingRecordDetail record = records.get(i);
            recordArray.put(record.general.recordId, record);
        }
        return recordArray;
    }

    private void refreshList() {
        // This is better than the original implementation, but still not great
        ((WeightTrainingDetailSparseArrayAdapter)listView.getAdapter()).refreshAdapter(getRecordArray());
    }

    private void createRecordDialog(@Nullable WeightTrainingRecordDetail recordDetail, boolean controlsEnabled) {
        final Dialog dialog = new Dialog(activity);
        AddRecordDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.add_record_dialog, null, false);
        dialog.setContentView(binding.getRoot());

        // Set the data source before the viewmodel, or the newValue binding will not work (not sure what the relevance is on this anymore)
        Spinner spinner = dialog.findViewById(R.id.dialog_types);
        ArrayAdapter<WorkoutType> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, android.R.id.text1, typeService.getAllTypes());
        spinner.setAdapter(adapter);

        if (recordDetail != null) {
            viewModel.record = recordDetail;
        }
        else {
            viewModel.record = new WeightTrainingRecordDetail();
            viewModel.record.general = new WeightTrainingRecordGeneral();
            viewModel.record.general.date = Calendar.getInstance();
        }

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(activity);

        Button saveButton = dialog.findViewById(R.id.dialog_save_button);
        saveButton.setOnClickListener(view -> {
            if (viewModel.record.general.recordId == null)
                recordService.saveRecord(viewModel.record.general);
            else
                recordService.updateRecord(viewModel.record.general);
            refreshList();
            viewModel.record = null;
            dialog.dismiss();
        });

        Button editButton = dialog.findViewById(R.id.dialog_edit_button);
        editButton.setOnClickListener(view -> setControlsEnabled(dialog, true));

        Button cancelButton = dialog.findViewById(R.id.dialog_cancel_button);
        cancelButton.setOnClickListener(view -> dialog.dismiss());
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

    private void CreateRestService() {
        Gson gson = new GsonBuilder().create();
        JavalinJson.setFromJsonMapper(gson::fromJson);
        JavalinJson.setToJsonMapper(gson::toJson);

        try {
            Javalin app = Javalin.create(config -> config.registerPlugin(new RouteOverviewPlugin("/routes"))).start(PortUtility.getAssignedPort());

            WorkoutTypeController workoutTypeController = new WorkoutTypeController(this.getApplication());
            WeightTrainingController weightTrainingController = new WeightTrainingController(this.getApplication());

            app.routes(() -> {
                before(Filters.handleLocaleChange);
                get(Path.Web.WORKOUT_TYPES, workoutTypeController.getAllWorkoutTypes);
                post(Path.Web.WEIGHT_TRAINING, weightTrainingController.postWeightTrainingRecord);
                after(Path.Web.WEIGHT_TRAINING, ctx -> refreshList());
            });

            restServiceStarted = true;
        }
        catch (Exception e) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "Failed to start sync service", duration);
            toast.show();
        }
    }
}
