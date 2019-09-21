package com.kodilla.test;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainAirHockey extends Application {
    public static void main (String args[]){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane grid = new Pane();
        GameManager gameManager = new GameManager(primaryStage, grid);
        gameManager.drawScene();
        gameManager.rollerAndComputerMotion(grid);
    }
}
