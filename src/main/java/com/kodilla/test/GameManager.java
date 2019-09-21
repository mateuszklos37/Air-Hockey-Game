package com.kodilla.test;

import com.kodilla.Computer;
import com.kodilla.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameManager {
    Stage primaryStage;
    Pane grid;
    Scene scene;
    Bounds bounds;
    UsersPaddle usersPaddle = new UsersPaddle(60, 10, 125, 375);
    Roller roller = new Roller(10, 155.0, 225.0);
    ComputersPaddle computersPaddle = new ComputersPaddle(60, 10, 125, 70);
    Goal usersGoal = new Goal(125, 440, Color.INDIANRED);
    Goal computersGoal = new Goal(125, 15, Color.GREENYELLOW);
    private Image imageback = new Image("graphics/Table.png");
    GameManager(Stage primaryStage, Pane grid){
        this.primaryStage = primaryStage;
        this.grid = grid;
        this.scene = new Scene(grid, 300, 450, Color.BLACK);
        this.bounds = grid.getBoundsInLocal();
    }
    //
    public void drawScene(){
        //background
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        usersPaddle.drawUsersPaddle(grid);
        roller.drawRoller(grid);
        computersPaddle.drawComputersPaddle(grid);
        usersGoal.drawGoal(grid);
        computersGoal.drawGoal(grid);
        grid.setBackground(background);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Air Hockey");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void usersPaddleMotion(){
        usersPaddle.setPosition(this.scene, grid, bounds);
    }

    public void rollerAndComputerMotion(Pane grid){
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20),
                new EventHandler<ActionEvent>() {
                    double rollerdx = 2.8;
                    double rollerdy = 3.5;
                    double paddlesdx = 2.5;
                    @Override
                    public void handle(ActionEvent t) {
                        //move roller
                        roller.circle.setLayoutX(roller.circle.getLayoutX() + rollerdx);
                        roller.circle.setLayoutY(roller.circle.getLayoutY() + rollerdy);
                        computersPaddle.rectangle.setLayoutX(computersPaddle.rectangle.getLayoutX() + paddlesdx);
                        int frame = 16;
                        //2 x if - if roller hits the bounds reverse it's velocity direction
                        if (roller.circle.getLayoutX() >= (bounds.getMaxX() - roller.circle.getRadius() - frame)
                                || roller.circle.getLayoutX() <= (bounds.getMinX() + roller.circle.getRadius() + frame)) {
                            rollerdx = -rollerdx;
                        }
                        if (roller.circle.getLayoutY() >= (bounds.getMaxY() - roller.circle.getRadius() - frame)
                                || roller.circle.getLayoutY() <= (bounds.getMinY() + roller.circle.getRadius() + frame)) {
                            rollerdy = -rollerdy;
                        }

                        if (computersPaddle.rectangle.getLayoutX() >= (bounds.getMaxX() - computersPaddle.rectangle.getWidth() - frame)
                                || computersPaddle.rectangle.getLayoutX() <= (bounds.getMinX() + frame)) {
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
                        if ((roller.circle.getLayoutY() - roller.circle.getRadius() <= computersPaddle.rectangle.getLayoutY() + computersPaddle.rectangle.getHeight())
                                && (roller.circle.getLayoutX() <= computersPaddle.rectangle.getLayoutX() + computersPaddle.rectangle.getWidth())
                                && (roller.circle.getLayoutX() >= computersPaddle.rectangle.getLayoutX())
                                && (roller.circle.getLayoutY() + roller.circle.getRadius() >= computersPaddle.rectangle.getLayoutY())) {
                            rollerdy = -rollerdy;
                        }
                        //roller bouncing paddle: dx
                        if ((roller.circle.getLayoutY() + (roller.circle.getRadius() / 2) >= computersPaddle.rectangle.getLayoutY())
                                && (roller.circle.getLayoutY() - (roller.circle.getRadius() / 2) <= computersPaddle.rectangle.getLayoutY() + computersPaddle.rectangle.getHeight())
                                && (roller.circle.getLayoutX() - roller.circle.getRadius() <= computersPaddle.rectangle.getLayoutX() + computersPaddle.rectangle.getWidth())
                                && (roller.circle.getLayoutX() + roller.circle.getRadius() >= computersPaddle.rectangle.getLayoutX())) {
                            rollerdx = -rollerdx;
                        }
                        //roller bouncing users paddle: dy
                        if ((roller.circle.getLayoutY() - roller.circle.getRadius() <= usersPaddle.rectangle.getLayoutY() + usersPaddle.rectangle.getHeight())
                                && (roller.circle.getLayoutX() <= usersPaddle.rectangle.getLayoutX() + usersPaddle.rectangle.getWidth())
                                && (roller.circle.getLayoutX() >= usersPaddle.rectangle.getLayoutX())
                                && (roller.circle.getLayoutY() + roller.circle.getRadius() >= usersPaddle.rectangle.getLayoutY())) {
                            rollerdy = -rollerdy;
                        }
                        //roller bouncing paddle: dx
                        if ((roller.circle.getLayoutY() + (roller.circle.getRadius() / 2) >= usersPaddle.rectangle.getLayoutY())
                                && (roller.circle.getLayoutY() - (roller.circle.getRadius() / 2) <= usersPaddle.rectangle.getLayoutY() + usersPaddle.rectangle.getHeight())
                                && (roller.circle.getLayoutX() - roller.circle.getRadius() <= usersPaddle.rectangle.getLayoutX() + usersPaddle.rectangle.getWidth())
                                && (roller.circle.getLayoutX() + roller.circle.getRadius() >= usersPaddle.rectangle.getLayoutX())) {
                            rollerdx = -rollerdx;
                        }
                        //scoring a goal by player event
                        if (roller.circle.getLayoutX() >= computersGoal.rectangle.getLayoutX()
                                && roller.circle.getLayoutX() <= computersGoal.rectangle.getLayoutX() + computersGoal.rectangle.getWidth()
                                && roller.circle.getLayoutY() - roller.circle.getRadius() <= bounds.getMinY() + frame) {
//                                    goalLabel.setText("You scored a goal!");
//                                    goalLabel.relocate(150, 200);
//                                    grid.getChildren().add(goalLabel);
                            usersPaddle.scoresGoal();
                            System.out.println("Player scored a goal!");
                            System.out.println("Result of the game: Player: " + usersPaddle.getNumberOfScoredGoals() + " vs. computer: " + computersPaddle.getNumberOfScoredGoals());
                        }
                        //scoring a goal by computer event
                        if (roller.circle.getLayoutX() >= usersGoal.rectangle.getLayoutX()
                                && roller.circle.getLayoutX() <= usersGoal.rectangle.getLayoutX() + usersGoal.rectangle.getWidth()
                                && roller.circle.getLayoutY() + roller.circle.getRadius() >= bounds.getMaxY() - frame) {
                            computersPaddle.scoresGoal();
                            System.out.println("Computer scored a goal!");
                            System.out.println("Result of the game: Player: " + usersPaddle.getNumberOfScoredGoals() + " vs. computer: " + computersPaddle.getNumberOfScoredGoals());
                        }
                        if (usersPaddle.getNumberOfScoredGoals() > 3 || computersPaddle.getNumberOfScoredGoals() > 3){
                            primaryStage.close();
                        }
                    }
                }
        ));
        timeline.setCycleCount(timeline.INDEFINITE);
        timeline.play();
    }
}
