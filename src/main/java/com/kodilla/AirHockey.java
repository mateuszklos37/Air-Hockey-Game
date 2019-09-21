package com.kodilla;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.Random;


public class AirHockey extends Application {
    private Image imageback = new Image("graphics/Table.png");

    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Player player = new Player();
        Computer computer = new Computer();
        //background
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        Label goalLabel = new Label();
        goalLabel.setTextFill(Color.DARKBLUE);
        goalLabel.setFont(new Font("Arial", 12));
        //roller, users and computers paddles
        Circle roller = new Circle(10, Color.ORANGE);
        roller.relocate(145, 200);
        Rectangle usersPaddle = new Rectangle(60, 10, Color.INDIANRED);
        usersPaddle.relocate(125, 375);
        Rectangle computersPaddle = new Rectangle(60, 10, Color.GREENYELLOW);
        computersPaddle.relocate(125, 70);
        Rectangle usersGoal = new Rectangle(60, 2, Color.INDIANRED);
        usersGoal.relocate(125, 440);
        Rectangle computersGoal = new Rectangle(60, 2, Color.GREENYELLOW);
        computersGoal.relocate(125, 15);
        Pane grid = new Pane();
        grid.setBackground(background);
        grid.getChildren().add(computersPaddle);
        grid.getChildren().add(roller);
        grid.getChildren().add(usersPaddle);
        grid.getChildren().add(usersGoal);
        grid.getChildren().add(computersGoal);
        //scene
        Scene scene = new Scene(grid, 300, 450, Color.BLACK);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Air Hockey");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            double usersPaddledx;
            Bounds bounds = grid.getBoundsInLocal();
            int frame = 16;
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()){
                    case LEFT: {
                        if(usersPaddle.getLayoutX() > bounds.getMinX() + frame) {
                            usersPaddledx = -3.0;
                            usersPaddle.setLayoutX(usersPaddle.getLayoutX() + usersPaddledx);
                            break;
                        }
                    }
                    case RIGHT: {
                        if(usersPaddle.getLayoutX() + usersPaddle.getWidth() < bounds.getMaxX() - frame) {
                            usersPaddledx = 3.0;
                            usersPaddle.setLayoutX(usersPaddle.getLayoutX() + usersPaddledx);
                        }
                        break;
                    }
                }
            }
        });

        //movement of roller
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20),
                new EventHandler<ActionEvent>() {
                    double rollerdx = 4.5;
                    double rollerdy = 4.8;
                    double paddlesdx = 2.5;
                    @Override
                    public void handle(ActionEvent t) {
                        //move roller
                            roller.setLayoutX(roller.getLayoutX() + rollerdx);
                            roller.setLayoutY(roller.getLayoutY() + rollerdy);
                            computersPaddle.setLayoutX(computersPaddle.getLayoutX() + paddlesdx);
                            //bounds
                            Bounds bounds = grid.getBoundsInLocal();
                            int frame = 16;
                                //2 x if - if roller hits the bounds reverse it's velocity direction
                                if (roller.getLayoutX() >= (bounds.getMaxX() - roller.getRadius() - frame)
                                        || roller.getLayoutX() <= (bounds.getMinX() + roller.getRadius() + frame)) {
                                    rollerdx = -rollerdx;
                                }
                                if (roller.getLayoutY() >= (bounds.getMaxY() - roller.getRadius() - frame)
                                        || roller.getLayoutY() <= (bounds.getMinY() + roller.getRadius() + frame)) {
                                    rollerdy = -rollerdy;
                                }

                                if (computersPaddle.getLayoutX() >= (bounds.getMaxX() - computersPaddle.getWidth() - frame)
                                        || computersPaddle.getLayoutX() <= (bounds.getMinX() + frame)) {
//                            Random generator = new Random();
//                            int generated = generator.nextInt(100);
//                            System.out.println("Bouncing property: " + generated);
//                            if(generated<95)
//                                paddlesdx = -paddlesdx;
//                            else
//                                paddlesdx = -paddlesdx + 0.01*generated;
                                    paddlesdx = -paddlesdx;
                                }
                                //roller bouncing paddle: dy
                                if ((roller.getLayoutY() - roller.getRadius() <= computersPaddle.getLayoutY() + computersPaddle.getHeight())
                                        && (roller.getLayoutX() <= computersPaddle.getLayoutX() + computersPaddle.getWidth())
                                        && (roller.getLayoutX() >= computersPaddle.getLayoutX())
                                        && (roller.getLayoutY() + roller.getRadius() >= computersPaddle.getLayoutY())) {
                                    rollerdy = -rollerdy;
                                }
                                //roller bouncing paddle: dx
                                if ((roller.getLayoutY() + (roller.getRadius() / 2) >= computersPaddle.getLayoutY())
                                        && (roller.getLayoutY() - (roller.getRadius() / 2) <= computersPaddle.getLayoutY() + computersPaddle.getHeight())
                                        && (roller.getLayoutX() - roller.getRadius() <= computersPaddle.getLayoutX() + computersPaddle.getWidth())
                                        && (roller.getLayoutX() + roller.getRadius() >= computersPaddle.getLayoutX())) {
                                    rollerdx = -rollerdx;
                                }
                                //roller bouncing users paddle: dy
                                if ((roller.getLayoutY() - roller.getRadius() <= usersPaddle.getLayoutY() + usersPaddle.getHeight())
                                        && (roller.getLayoutX() <= usersPaddle.getLayoutX() + usersPaddle.getWidth())
                                        && (roller.getLayoutX() >= usersPaddle.getLayoutX())
                                        && (roller.getLayoutY() + roller.getRadius() >= usersPaddle.getLayoutY())) {
                                    rollerdy = -rollerdy;
                                }
                                //roller bouncing paddle: dx
                                if ((roller.getLayoutY() + (roller.getRadius() / 2) >= usersPaddle.getLayoutY())
                                        && (roller.getLayoutY() - (roller.getRadius() / 2) <= usersPaddle.getLayoutY() + usersPaddle.getHeight())
                                        && (roller.getLayoutX() - roller.getRadius() <= usersPaddle.getLayoutX() + usersPaddle.getWidth())
                                        && (roller.getLayoutX() + roller.getRadius() >= usersPaddle.getLayoutX())) {
                                    rollerdx = -rollerdx;
                                }
                                //scoring a goal by player event
                                if (roller.getLayoutX() >= computersGoal.getLayoutX()
                                        && roller.getLayoutX() <= computersGoal.getLayoutX() + computersGoal.getWidth()
                                        && roller.getLayoutY() - roller.getRadius() <= bounds.getMinY() + frame) {
//                                    goalLabel.setText("You scored a goal!");
//                                    goalLabel.relocate(150, 200);
//                                    grid.getChildren().add(goalLabel);
                                    player.scoresGoal();
                                    System.out.println("Player scored a goal!");
                                    System.out.println("Result of the game: Player: " + player.getNumberOfScoredGoals() + " vs. computer: " + computer.getNumberOfScoredGoals());
                                }
                                //scoring a goal by computer event
                                if (roller.getLayoutX() >= usersGoal.getLayoutX()
                                        && roller.getLayoutX() <= usersGoal.getLayoutX() + usersGoal.getWidth()
                                        && roller.getLayoutY() + roller.getRadius() >= bounds.getMaxY() - frame) {
                                    computer.scoresGoal();
//                                    goalLabel.setText("Computer scored a goal!");
//                                    goalLabel.relocate(150, 200);
//                                    grid.getChildren().add(goalLabel);
                                    System.out.println("Computer scored a goal!");
                                    System.out.println("Result of the game: Player: " + player.getNumberOfScoredGoals() + " vs. computer: " + computer.getNumberOfScoredGoals());
                                }
                        if (player.getNumberOfScoredGoals() > 3 || computer.getNumberOfScoredGoals() > 3){
                            primaryStage.close();
                        }
                    }
                    }
        ));
        timeline.setCycleCount(timeline.INDEFINITE);
        timeline.play();
    }
}
