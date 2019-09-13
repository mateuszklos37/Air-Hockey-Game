package com.kodilla;

public class Computer {
    private int numberOfScoredGoals = 0;

    public int getNumberOfScoredGoals() {
        return numberOfScoredGoals;
    }

    public int scoresGoal(){
        return numberOfScoredGoals++;
    }
}
