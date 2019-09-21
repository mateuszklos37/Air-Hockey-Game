package com.kodilla.test;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Goal {
    private double xPos;
    private double yPos;
    Rectangle rectangle;
    private int width = 60;
    private int height = 2;
    private Color color;

    public Goal (double xPos, double yPos, Color color) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
        this.rectangle = new Rectangle(width, height, color);
    }

    public void drawGoal(Pane grid){
        rectangle.setLayoutX(this.xPos);
        rectangle.setLayoutY(this.yPos);
        grid.getChildren().add(rectangle);
    }
}
