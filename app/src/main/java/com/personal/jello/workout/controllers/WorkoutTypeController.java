package com.personal.jello.workout.controllers;

import android.app.Application;

import com.personal.jello.workout.models.WorkoutType;
import com.personal.jello.workout.services.WorkoutTypeService;

import java.util.List;

import io.javalin.http.Handler;

public class WorkoutTypeController {
    private WorkoutTypeService typeService;

    public WorkoutTypeController(Application app) {
        typeService = new WorkoutTypeService(app);
    }

    public Handler getAllWorkoutTypes = ctx -> {
        List<WorkoutType> types = typeService.getAllTypes();
        ctx.json(types);
    };
}
