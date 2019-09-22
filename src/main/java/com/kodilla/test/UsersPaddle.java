package com.kodilla.test;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UsersPaddle {

    private double xPos;
    private double yPos;
    Rectangle rectangle;
    private int scoredGoals;
    private double dx;

    UsersPaddle (int width, int height, double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rectangle = new Rectangle(width, height, Color.INDIANRED);
        this.scoredGoals = 0;
    }

    public void drawUsersPaddle(Pane grid){
        rectangle.setLayoutX(this.xPos);
        rectangle.setLayoutY(this.yPos);
        grid.getChildren().add(rectangle);
    }

    public int scoresGoal(){
        return scoredGoals++;
    }

    public int getNumberOfScoredGoals(){
        return scoredGoals;
    }

    public int setNumberOfScoredGoals(int numberOfScoredGoals){
        return scoredGoals = numberOfScoredGoals;
    }

    public double setDx(double dx){
            return this.dx = dx;
    }

    public double getDx(){
        return this.dx;
    }
}
