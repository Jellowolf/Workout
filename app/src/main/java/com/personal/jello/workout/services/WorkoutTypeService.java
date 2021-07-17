package com.personal.jello.workout.services;

import android.app.Application;

import com.personal.jello.workout.database.WorkoutTypeRepository;
import com.personal.jello.workout.models.WorkoutType;

import java.util.List;

public class WorkoutTypeService {
    private static WorkoutTypeRepository workoutTypeRepository;

    public WorkoutTypeService(Application application) {
        workoutTypeRepository = new WorkoutTypeRepository(application);
    }

    public WorkoutType getType() {
        return workoutTypeRepository.getAllTypes().get(0);
    }

    public List<WorkoutType> getAllTypes() {
        return workoutTypeRepository.getAllTypes();
    }

    public void saveType(WorkoutType type) {
        workoutTypeRepository.insertType(type);
    }

    public void saveTypes(WorkoutType[] types) {
        workoutTypeRepository.insertTypes(types);
    }

    public void updateType(WorkoutType type) {
        workoutTypeRepository.updateType(type);
    }

    public void deleteType(WorkoutType type) {
        workoutTypeRepository.deleteType(type);
    }

    public void deleteAllTypes() {
        workoutTypeRepository.deleteAllTypes();
    }

    public boolean checkIfDeleteValid(Integer typeId) {
        return workoutTypeRepository.checkIfDeleteValid(typeId);
    }
}
