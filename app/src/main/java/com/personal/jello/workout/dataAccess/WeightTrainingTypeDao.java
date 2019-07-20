package com.personal.jello.workout.dataAccess;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.personal.jello.workout.models.WeightTrainingType;

import java.util.List;

@Dao
public interface WeightTrainingTypeDao {
    @Query("SELECT * FROM WeightTrainingType")
    List<WeightTrainingType> getAll();

    @Insert
    void insert(WeightTrainingType...types);

    @Update
    void update(WeightTrainingType...types);

    @Delete
    void delete(WeightTrainingType...types);
}
