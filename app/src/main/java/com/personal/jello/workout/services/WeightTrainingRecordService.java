package com.personal.jello.workout.services;

import android.app.Application;

import com.personal.jello.workout.database.WeightTrainingRecordRepository;
import com.personal.jello.workout.models.WeightTrainingRecordDetail;
import com.personal.jello.workout.models.WeightTrainingRecordGeneral;

import java.util.List;

public class WeightTrainingRecordService {
    private static WeightTrainingRecordRepository weightTrainingRecordRepository;

    public WeightTrainingRecordService(Application application) {
        weightTrainingRecordRepository = new WeightTrainingRecordRepository(application);
    }

    public WeightTrainingRecordGeneral getRecord() {
        return weightTrainingRecordRepository.getAllRecords().get(0);
    }

    public List<WeightTrainingRecordGeneral> getAllRecords() {
        return weightTrainingRecordRepository.getAllRecords();
    }

    public List<WeightTrainingRecordDetail> getAllDetails() {
        return weightTrainingRecordRepository.getAllDetails();
    }

    public void saveRecord(WeightTrainingRecordGeneral record) {
        weightTrainingRecordRepository.insertRecord(record);
    }

    public void updateRecord(WeightTrainingRecordGeneral record) {
        weightTrainingRecordRepository.updateRecord(record);
    }

    public void deleteRecord(WeightTrainingRecordGeneral record) {
        weightTrainingRecordRepository.deleteRecord(record);
    }
}
