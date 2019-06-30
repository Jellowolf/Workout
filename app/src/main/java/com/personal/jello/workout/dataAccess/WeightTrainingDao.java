package com.personal.jello.workout.dataAccess;

import com.personal.jello.workout.models.WeightTrainingRecord;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WeightTrainingDao {
    @Query("SELECT * FROM weightTrainingRecord")
    List<WeightTrainingRecord> getAll();

    @Insert
    void insert(WeightTrainingRecord...records);

    @Update
    void update(WeightTrainingRecord...records);

    @Delete
    void delete(WeightTrainingRecord...records);
}
