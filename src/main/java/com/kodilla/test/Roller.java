package com.kodilla.test;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Roller {
    private double xPos;
    private double yPos;
    private int radius;
    Circle circle;
    private double dx;
    private double dy;

    Roller(int radius, double xPos, double yPos) {
        this.radius = radius;
        this.xPos = xPos;
        this.yPos = yPos;
        this.circle = new Circle(radius, Color.DARKBLUE);
    }
    public void drawRoller(Pane grid){
        circle.setLayoutX(xPos);
        circle.setLayoutY(yPos);
        grid.getChildren().add(circle);
    }

    public double setRollerDx(double dx){
        return this.dx = dx;
    }

    public double setRollerDy(double dy){
        return this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }
}