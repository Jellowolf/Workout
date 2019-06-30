package com.personal.jello.workout.models;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weightTrainingRecord")
public class WeightTrainingRecord {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "type")
    public WeightTrainingType type;

    @ColumnInfo(name = "sets")
    public int sets;

    @ColumnInfo(name = "reps")
    public int reps;

    @ColumnInfo(name = "weight")
    public double weight;

    @ColumnInfo(name = "date")
    public Date date;
}
