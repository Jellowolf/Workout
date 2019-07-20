package com.personal.jello.workout.models;

import androidx.databinding.BaseObservable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weightTrainingType")
public class WeightTrainingType extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    public Integer typeId;

    @ColumnInfo(name = "description")
    public String description;

    @Override
    public String toString() {
        return description;
    }
}
