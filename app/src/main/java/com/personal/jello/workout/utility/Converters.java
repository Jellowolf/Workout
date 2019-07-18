package com.personal.jello.workout.utility;

import androidx.room.TypeConverter;

import com.personal.jello.workout.models.WeightTrainingType;

public class Converters {

    @TypeConverter
    public static int WeightTrainingTypeToInt(WeightTrainingType type) {
        return type.ordinal();
    }

    @TypeConverter
    public static WeightTrainingType IntToWeightTrainingType(int type) {
        return WeightTrainingType.values()[type];
    }
}
