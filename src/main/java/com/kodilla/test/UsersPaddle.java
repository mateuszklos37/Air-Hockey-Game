package com.kodilla.test;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UsersPaddle {

    private double xPos;
    private double yPos;
    Rectangle rectangle;
    private int scoredGoals;

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

    public void setPosition(Scene scene, Pane grid, Bounds bounds){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            double dx;
            int frame = 16;
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()){
                    case LEFT: {
                        if(rectangle.getLayoutX() > bounds.getMinX() + frame) {
                            dx = -3.0;
                            rectangle.setLayoutX(rectangle.getLayoutX() + dx);
                            break;
                        }
                    }
                    case RIGHT: {
                        if(rectangle.getLayoutX() + rectangle.getWidth() < bounds.getMaxX()-4) {
                            dx = 3.0;
                            rectangle.setLayoutX(rectangle.getLayoutX() + dx);
                        }
                        break;
                    }
                }
            }
        });
    }
}
