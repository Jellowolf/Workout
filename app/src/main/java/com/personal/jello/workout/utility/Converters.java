package com.personal.jello.workout.utility;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class Converters {

    /*@TypeConverter
    public static Integer WeightTrainingTypeToInteger(WorkoutType type) {
        return type.id;
    }

    @TypeConverter
    public static WorkoutType IntegerToWeightTrainingType(int type) {
        return WorkoutType.values()[type];
    }*/

    @TypeConverter
    public static String CalendarToTimestamp(Calendar calendar) {
        return calendar != null ? Long.toString(calendar.getTimeInMillis()/1000) : null;
    }

    @TypeConverter
    public static Calendar CalendarFromTimestamp(String timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timestamp)*1000);
        return calendar;
    }
}
