package com.personal.jello.workout.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.personal.jello.workout.dataAccess.WeightTrainingRecordDao;
import com.personal.jello.workout.dataAccess.WeightTrainingTypeDao;
import com.personal.jello.workout.models.WeightTrainingRecordGeneral;
import com.personal.jello.workout.models.WorkoutType;
import com.personal.jello.workout.utility.Converters;

@Database(entities = {WeightTrainingRecordGeneral.class, WorkoutType.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class WorkoutDatabase extends RoomDatabase {
    private static WorkoutDatabase INSTANCE;
    public abstract WeightTrainingRecordDao weightTrainingRecordDao();
    public abstract WeightTrainingTypeDao weightTrainingTypeDao();

    public static WorkoutDatabase getWorkoutDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WorkoutDatabase.class, "workout-database").fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
