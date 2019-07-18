package com.personal.jello.workout.models;

public enum WeightTrainingType {
    ArmCurl("Arm Curl"),
    ArmExtension("Arm Extension");

    private final String name;

    WeightTrainingType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
