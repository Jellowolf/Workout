package com.personal.jello.workout.utility;

import androidx.room.TypeConverter;

import com.personal.jello.workout.models.WeightTrainingType;

import java.util.Calendar;
import java.util.Date;

public class Converters {

    @TypeConverter
    public static int WeightTrainingTypeToInt(WeightTrainingType type) {
        return type.ordinal();
    }

    @TypeConverter
    public static WeightTrainingType IntToWeightTrainingType(int type) {
        return WeightTrainingType.values()[type];
    }

    @TypeConverter
    public static Date CalendarToTimestamp(Calendar calendar) {
        return calendar.getTime();
    }

    @TypeConverter
    public static Calendar Time(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
