package com.personal.jello.workout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.personal.jello.workout.models.WeightTrainingRecordGeneral;
import com.personal.jello.workout.models.WorkoutType;
import com.personal.jello.workout.services.WeightTrainingRecordService;
import com.personal.jello.workout.services.WorkoutTypeService;
import com.personal.jello.workout.translators.CSVTranslator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private static WeightTrainingRecordService recordService;
    private static WorkoutTypeService typeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        if (recordService == null)
            recordService = new WeightTrainingRecordService(this.getApplication());
        if (typeService == null)
            typeService = new WorkoutTypeService(this.getApplication());
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

        // This might not be the best way to handle preference clicking, was having issues with
        // using an intent based solution
        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            if (preference.getKey().equals(getString(R.string.workoutType))) {
                FragmentActivity activity = getActivity();
                Intent recordIntent = new Intent(activity, WorkoutTypeActivity.class);
                activity.startActivity(recordIntent);
                return true;
            }
            if (preference.getKey().equals(getString(R.string.exportData))) {
                exportData(getContext());
                return true;
            }
            if (preference.getKey().equals(getString(R.string.importData))) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("Warning")
                    .setMessage("All existing data will be deleted and replaced with imported data, are you sure you want to continue?")
                    .setPositiveButton(android.R.string.yes, (dialog, positiveButton) -> importData(getContext()))
                    .setNegativeButton(android.R.string.no, null)
                    .show();
                return true;
            }
            if (preference.getKey().equals(getString(R.string.deleteDuplicateData))) {
                deleteDuplicateData(getContext());
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private static void exportData(Context context) {
        try {
            // save workout types
            File file = new File(context.getExternalFilesDir(null), "Workout_Types.csv");
            FileOutputStream output = new FileOutputStream(file, false);
            String translationString = CSVTranslator.translateWorkoutTypesToCsv(typeService.getAllTypes());
            output.write(translationString.getBytes());
            output.flush();
            output.close();

            // save weight training records
            file = new File(context.getExternalFilesDir(null), "Weight_Training_Records.csv");
            output = new FileOutputStream(file, false);
            translationString = CSVTranslator.translateWeightTrainingRecordsToCsv(recordService.getAllRecords());
            output.write(translationString.getBytes());
            output.flush();
            output.close();

            Toast.makeText(context, "Backups successfully created", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void importData(Context context) {
        try {
            // Check that backups actually exist before deleting all of the records
            File workoutTypesFile = new File(context.getExternalFilesDir(null), "Workout_Types.csv");
            File weightTrainingRecordsFile = new File(context.getExternalFilesDir(null), "Weight_Training_Records.csv");

            if (!(workoutTypesFile.exists() && weightTrainingRecordsFile.exists())) {
                Toast.makeText(context, "Error: There are no backups to import", Toast.LENGTH_SHORT).show();
                return;
            }

            // delete existing records
            recordService.deleteAllRecords();
            typeService.deleteAllTypes();

            // save workout types
            FileInputStream input = new FileInputStream(workoutTypesFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            List<String> readerLines = new ArrayList<>();
            String readerLine;
            while ((readerLine = reader.readLine()) != null) {
                readerLines.add(readerLine);
            }
            List<WorkoutType> importedTypes = CSVTranslator.translateWorkoutTypesFromCsv(readerLines);
            typeService.saveTypes(importedTypes.toArray(new WorkoutType[0]));

            // save weight training records
            input = new FileInputStream(weightTrainingRecordsFile);
            reader = new BufferedReader(new InputStreamReader(input));
            readerLines = new ArrayList<>();
            while ((readerLine = reader.readLine()) != null) {
                readerLines.add(readerLine);
            }
            List<WeightTrainingRecordGeneral> importedRecords = CSVTranslator.translateWeightTrainingRecordsFromCsv(readerLines);
            recordService.saveRecords(importedRecords.toArray(new WeightTrainingRecordGeneral[0]));

            Toast.makeText(context, "Backups successfully imported", Toast.LENGTH_SHORT).show();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    // this shouldn't really be necessary in the long run, but the fitbit app still has some strange behavior
    private static void deleteDuplicateData(Context context) {
        List<WeightTrainingRecordGeneral> records = recordService.getAllRecords();
        List<WeightTrainingRecordGeneral> uniqueRecords = new ArrayList<>();
        List<WeightTrainingRecordGeneral> duplicateRecords = new ArrayList<>();
        boolean duplicateFound = false;

        for (WeightTrainingRecordGeneral record : records) {
            for (WeightTrainingRecordGeneral uniqueRecord : uniqueRecords) {
                if (uniqueRecord.equals(record)) {
                    duplicateRecords.add(record);
                    duplicateFound = true;
                    break;
                }
            }
            if (!duplicateFound)
                uniqueRecords.add(record);
            else
                duplicateFound = false;
        }
        recordService.deleteRecords(duplicateRecords.toArray(new WeightTrainingRecordGeneral[0]));

        Toast.makeText(context, "Duplicate Data Deleted", Toast.LENGTH_SHORT).show();
    }
}