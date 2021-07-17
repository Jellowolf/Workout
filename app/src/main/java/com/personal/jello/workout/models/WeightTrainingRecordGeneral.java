package com.personal.jello.workout.models;

import java.util.Calendar;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "weightTrainingRecord", indices = {@Index(value = "type_id")},
        foreignKeys = @ForeignKey(entity = WorkoutType.class, parentColumns = "typeId", childColumns = "type_id"))
public class WeightTrainingRecordGeneral extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    public Integer recordId;

    @ColumnInfo(name = "type_id")
    public Integer typeId;

    @ColumnInfo(name = "sets")
    public Integer sets;

    @ColumnInfo(name = "reps")
    public Integer reps;

    @ColumnInfo(name = "weight")
    public Double weight;

    @ColumnInfo(name = "date")
    public Calendar date;

    public WeightTrainingRecordGeneral() {}

    public WeightTrainingRecordGeneral(Integer recordId, Integer typeId, Integer sets, Integer reps, Double weight, Calendar date) {
        this.recordId = recordId;
        this.typeId = typeId;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.date = date;
    }

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

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof WeightTrainingRecordGeneral))
            return false;
        WeightTrainingRecordGeneral typedObject = (WeightTrainingRecordGeneral)object;
        if (!this.typeId.equals(typedObject.typeId))
            return false;
        if (!this.sets.equals(typedObject.sets))
            return false;
        if (!this.reps.equals(typedObject.reps))
            return false;
        if (!this.weight.equals(typedObject.weight))
            return false;
        if (this.date.compareTo(typedObject.date) != 0)
            return false;
        return true;
    }
}
