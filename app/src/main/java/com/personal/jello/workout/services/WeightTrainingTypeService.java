package com.personal.jello.workout.services;

import android.app.Application;

import com.personal.jello.workout.database.WeightTrainingTypeRepository;
import com.personal.jello.workout.models.WorkoutType;

import java.util.List;

public class WeightTrainingTypeService {
    private static WeightTrainingTypeRepository weightTrainingTypeRepository;

    public WeightTrainingTypeService(Application application) {
        weightTrainingTypeRepository = new WeightTrainingTypeRepository(application);
    }

    public WorkoutType getType() {
        return weightTrainingTypeRepository.getAllTypes().get(0);
    }

    public List<WorkoutType> getAllTypes() {
        return weightTrainingTypeRepository.getAllTypes();
    }

    public void saveType(WorkoutType type) {
        weightTrainingTypeRepository.insertType(type);
    }

    public void updateType(WorkoutType type) {
        weightTrainingTypeRepository.updateType(type);
    }

    public void deleteType(WorkoutType type) {
        weightTrainingTypeRepository.deleteType(type);
    }
}
