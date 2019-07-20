package com.personal.jello.workout.models;

import java.util.Calendar;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weightTrainingRecord")
public class WeightTrainingRecord extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @ColumnInfo(name = "type")
    public WeightTrainingType type;

    @ColumnInfo(name = "sets")
    public Integer sets;

    @ColumnInfo(name = "reps")
    public Integer reps;

    @ColumnInfo(name = "weight")
    public Double weight;

    @ColumnInfo(name = "date")
    public Calendar date;

    @Bindable
    public String getWeight() {
        return this.weight != null ? Double.toString(this.weight) : null;
    }

    public void setWeight(String value) {
        if (value != null && !value.isEmpty() && (this.weight == null || this.weight != Double.parseDouble(value)))
            this.weight = Double.parseDouble(value);
    }

    @Bindable
    public String getSets() {
        return this.sets != null ? Integer.toString(this.sets) : null;
    }

    public void setSets(String value) {
        if (value != null && !value.isEmpty() && (this.sets == null || this.sets != Integer.parseInt(value)))
            this.sets = Integer.parseInt(value);
    }

    @Bindable
    public String getReps() {
        return this.reps != null ? Integer.toString(this.reps) : null;
    }

    public void setReps(String value) {
        if (value != null && !value.isEmpty() && (this.reps == null || this.reps != Integer.parseInt(value)))
            this.reps = Integer.parseInt(value);
    }

    @Bindable
    public int getDay() {
        return this.date.get(Calendar.DAY_OF_MONTH);
    }

    public void setDay(int value) {
        this.date.set(Calendar.DAY_OF_MONTH, value);
    }

    @Bindable
    public int getMonth() {
        return this.date.get(Calendar.MONTH);
    }

    public void setMonth(int value) {
        this.date.set(Calendar.MONTH, value);
    }

    @Bindable
    public int getYear() {
        return this.date.get(Calendar.YEAR);
    }

    public void setYear(int value) {
        this.date.set(Calendar.YEAR, value);
    }
}
