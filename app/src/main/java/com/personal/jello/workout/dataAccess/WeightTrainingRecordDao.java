package com.personal.jello.workout.dataAccess;

import com.personal.jello.workout.models.WeightTrainingRecordDetail;
import com.personal.jello.workout.models.WeightTrainingRecordGeneral;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WeightTrainingRecordDao {
    @Query("SELECT * FROM WeightTrainingRecord")
    List<WeightTrainingRecordGeneral> getAll();

    @Query("SELECT * FROM WeightTrainingRecord JOIN WorkoutType ON WeightTrainingRecord.type_id = WorkoutType.typeId")
    List<WeightTrainingRecordDetail> getAllDetails();

    @Insert
    void insert(WeightTrainingRecordGeneral...records);

    @Update
    void update(WeightTrainingRecordGeneral...records);

    @Delete
    void delete(WeightTrainingRecordGeneral...records);

    @Query("DELETE FROM WeightTrainingRecord")
    void deleteAll();
}
