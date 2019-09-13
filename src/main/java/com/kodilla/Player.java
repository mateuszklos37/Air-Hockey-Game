package com.kodilla;

public class Player {
    private int numberOfScoredGoals = 0;

    public int getNumberOfScoredGoals() {
        return numberOfScoredGoals;
    }

    public int scoresGoal(){
        return numberOfScoredGoals++;
    }
}
