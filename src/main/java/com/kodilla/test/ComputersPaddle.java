package com.kodilla.test;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ComputersPaddle {

    private double xPos;
    private double yPos;
    Rectangle rectangle;
    private double dx;
    private int scoredGoals;

    ComputersPaddle (int width, int height, double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rectangle = new Rectangle(width, height, Color.GREENYELLOW);
        this.scoredGoals = 0;
    }

    public void drawComputersPaddle(Pane grid){
        rectangle.setLayoutX(xPos);
        rectangle.setLayoutY(yPos);
        grid.getChildren().add(rectangle);
    }

    public int scoresGoal(){
        return scoredGoals++;
    }

    public int getNumberOfScoredGoals(){
        return scoredGoals;
    }

    //public int setNumberOfScoredGoals(int numberOfScoredGoals){
        //return scoredGoals = numberOfScoredGoals;
    //}

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }
}
