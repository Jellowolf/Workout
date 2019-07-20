package com.personal.jello.workout.models;

import androidx.room.Embedded;

public class WeightTrainingRecordDetail {

    @Embedded
    public WeightTrainingRecordGeneral general;

    @Embedded
    public WeightTrainingType type;
}
