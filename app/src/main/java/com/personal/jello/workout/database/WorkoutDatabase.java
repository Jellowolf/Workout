package com.personal.jello.workout.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.personal.jello.workout.dataAccess.WeightTrainingDao;
import com.personal.jello.workout.models.WeightTrainingRecord;

@Database(entities = {WeightTrainingRecord.class}, version = 1)
public abstract class WorkoutDatabase extends RoomDatabase {
    private static WorkoutDatabase INSTANCE;
    public abstract WeightTrainingDao weightTrainingDao();

    public static WorkoutDatabase getWorkoutDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WorkoutDatabase.class, "workout-database").build();
        }
        return INSTANCE;
    }
}
