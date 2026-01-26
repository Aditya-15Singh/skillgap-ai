package com.skillgap.dto;

public class CareerGoalDto {
    private String goalName;

    public CareerGoalDto() {
    }

    public CareerGoalDto(String goalName) {
        this.goalName = goalName;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }
}
