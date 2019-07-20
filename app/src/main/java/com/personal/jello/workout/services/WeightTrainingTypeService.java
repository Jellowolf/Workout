package com.personal.jello.workout.services;

import android.app.Application;

import com.personal.jello.workout.database.WeightTrainingTypeRepository;
import com.personal.jello.workout.models.WeightTrainingType;

import java.util.List;

public class WeightTrainingTypeService {
    private static WeightTrainingTypeRepository weightTrainingTypeRepository;

    public WeightTrainingTypeService(Application application) {
        weightTrainingTypeRepository = new WeightTrainingTypeRepository(application);
    }

    public WeightTrainingType getType() {
        return weightTrainingTypeRepository.getAllTypes().get(0);
    }

    public List<WeightTrainingType> getAllTypes() {
        return weightTrainingTypeRepository.getAllTypes();
    }

    public void saveType(WeightTrainingType type) {
        weightTrainingTypeRepository.insertType(type);
    }

    public void updateType(WeightTrainingType type) {
        weightTrainingTypeRepository.updateType(type);
    }

    public void deleteType(WeightTrainingType type) {
        weightTrainingTypeRepository.deleteType(type);
    }
}
