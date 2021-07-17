package com.personal.jello.workout.translators;

import android.annotation.SuppressLint;

import com.personal.jello.workout.models.WeightTrainingRecordGeneral;
import com.personal.jello.workout.models.WorkoutType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CSVTranslator {
    @SuppressLint("DefaultLocale")
    public static String translateWorkoutTypesToCsv(List<WorkoutType> types) {
        List<String> formattedTypes = new ArrayList<>();
        for (WorkoutType type : types) {
            formattedTypes.add(String.format("%d,%s", type.typeId, type.description));
        }
        return String.join("\n", formattedTypes);
    }

    public static List<WorkoutType> translateWorkoutTypesFromCsv(List<String> types) {
        List<WorkoutType> parsedTypes = new ArrayList<>();
        for (String type : types) {
            String[] splitType = type.split(",");
            parsedTypes.add(new WorkoutType(Integer.parseInt(splitType[0]), splitType[1]));
        }
        return parsedTypes;
    }

    @SuppressLint("DefaultLocale")
    public static String translateWeightTrainingRecordsToCsv(List<WeightTrainingRecordGeneral> records) {
        List<String> formattedRecords = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss", Locale.US);
        for (WeightTrainingRecordGeneral record : records) {
            formattedRecords.add(String.format("%d,%d,%d,%d,%.2f,%s", record.recordId, record.typeId, record.sets, record.reps, record.weight, formatter.format(record.date.getTime())));
        }
        return String.join("\n", formattedRecords);
    }

    public static List<WeightTrainingRecordGeneral> translateWeightTrainingRecordsFromCsv(List<String> records) throws ParseException {
        List<WeightTrainingRecordGeneral> parsedRecords = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss", Locale.US);
        Calendar parseCalendar = Calendar.getInstance();
        for (String record : records) {
            String[] splitRecords = record.split(",");
            parseCalendar.setTime(formatter.parse(splitRecords[5]));
            parsedRecords.add(new WeightTrainingRecordGeneral(Integer.parseInt(splitRecords[0]), Integer.parseInt(splitRecords[1]), Integer.parseInt(splitRecords[2]), Integer.parseInt(splitRecords[3]), Double.parseDouble(splitRecords[4]), (Calendar) parseCalendar.clone()));
        }
        return parsedRecords;
    }
}
