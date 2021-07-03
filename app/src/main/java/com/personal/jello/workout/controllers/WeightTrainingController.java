package com.personal.jello.workout.controllers;

import android.app.Application;

import com.personal.jello.workout.models.WeightTrainingRecordSaveRequest;
import com.personal.jello.workout.services.WeightTrainingRecordService;

import io.javalin.http.Handler;
import io.javalin.plugin.json.JavalinJson;

public class WeightTrainingController {
    private WeightTrainingRecordService recordService;

    public WeightTrainingController(Application app) {
        recordService = new WeightTrainingRecordService(app);
    }

    public Handler postWeightTrainingRecord = ctx -> {
        WeightTrainingRecordSaveRequest requestData = JavalinJson.fromJson(ctx.body(), WeightTrainingRecordSaveRequest.class);
        recordService.saveRecords(requestData.data);
    };
}
