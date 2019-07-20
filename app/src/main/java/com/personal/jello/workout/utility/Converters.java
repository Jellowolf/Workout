package com.personal.jello.workout.utility;

import androidx.room.TypeConverter;

import com.personal.jello.workout.models.WeightTrainingType;

import java.util.Calendar;
import java.util.Date;

public class Converters {

    /*@TypeConverter
    public static Integer WeightTrainingTypeToInteger(WeightTrainingType type) {
        return type.id;
    }

    @TypeConverter
    public static WeightTrainingType IntegerToWeightTrainingType(int type) {
        return WeightTrainingType.values()[type];
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
