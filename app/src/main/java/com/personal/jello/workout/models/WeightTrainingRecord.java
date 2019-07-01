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
    public Calendar date;

    @Bindable
    public String getWeight() {
        return Double.toString(this.weight);
    }

    public void setWeight(String value) {
        if (value != null && !value.isEmpty() && this.weight != Double.parseDouble(value))
            this.weight = Double.parseDouble(value);
    }

    @Bindable
    public String getSets() {
        return Integer.toString(this.sets);
    }

    public void setSets(String value) {
        if (value != null && !value.isEmpty() && this.sets != Integer.parseInt(value))
            this.sets = Integer.parseInt(value);
    }

    @Bindable
    public String getReps() {
        return Integer.toString(this.reps);
    }

    public void setReps(String value) {
        if (value != null && !value.isEmpty() && this.reps != Integer.parseInt(value))
            this.reps = Integer.parseInt(value);
    }

    @Bindable
    public int getDay() {
        return date.get(Calendar.DAY_OF_MONTH);
    }

    public void setDay(int value) {
        date.set(Calendar.DAY_OF_MONTH, value);
    }

    @Bindable
    public int getMonth() {
        return date.get(Calendar.MONTH);
    }

    public void setMonth(int value) {
        date.set(Calendar.MONTH, value);
    }

    @Bindable
    public int getYear() {
        return date.get(Calendar.YEAR);
    }

    public void setYear(int value) {
        date.set(Calendar.YEAR, value);
    }
}
