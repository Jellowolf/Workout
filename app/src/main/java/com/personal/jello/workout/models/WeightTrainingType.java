package com.personal.jello.workout.models;

public enum WeightTrainingType {
    ArmCurl(1,"Arm Curl"),
    ArmExtension(2,"Arm Extension");

    private final String name;
    private final int value;

    WeightTrainingType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
