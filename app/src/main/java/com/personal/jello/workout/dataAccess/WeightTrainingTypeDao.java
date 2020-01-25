package com.personal.jello.workout.dataAccess;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.personal.jello.workout.models.WorkoutType;

import java.util.List;

@Dao
public interface WeightTrainingTypeDao {
    @Query("SELECT * FROM WorkoutType")
    List<WorkoutType> getAll();

    @Insert
    void insert(WorkoutType...types);

    @Update
    void update(WorkoutType...types);

    @Delete
    void delete(WorkoutType...types);
}
