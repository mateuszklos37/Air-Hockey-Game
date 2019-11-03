package com.kodilla.test;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

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

    public void rollerAndComputerMotion(Pane grid){
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20),
                new EventHandler<ActionEvent>() {
                    boolean isRunning = FALSE;
                    boolean computerScoredGoal = FALSE;
                    boolean userScoredGoal = FALSE;
                    boolean userWon = FALSE;
                    boolean computerWon = FALSE;
                    boolean endOfGame = FALSE;
                    boolean computerBounceAble;
                    boolean userBounceAble;
                    TextDrawer labelComputerScored = new TextDrawer("Computer scored a goal");
                    TextDrawer labelUserScored = new TextDrawer("You scored a goal");
                    TextDrawer labelGameOver = new TextDrawer("Computer won! Press escape to exit window");
                    TextDrawer labelYouWon = new TextDrawer("Congratulations, You won! Press escape to exit window");
                    double rollerdx = 0;
                    double rollerdy = 0;
                    double paddlesdx = 0;
                    Random velGenerator = new Random();
                    int frame = 16;
                    @Override
                    public void handle(ActionEvent t) {
                        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                        switch(event.getCode()){
                            case SPACE: {
                                if (isRunning == FALSE) {
                                    if(!endOfGame) {
                                        rollerdx = ((velGenerator.nextDouble()) * 2) + 2;
                                        rollerdy = ((velGenerator.nextDouble()) * 2) + 2;
                                        paddlesdx = ((velGenerator.nextDouble()) * 2) + 2;
                                        computerBounceAble = TRUE;
                                        userBounceAble = TRUE;
                                        System.out.println(isRunning);
                                    }
                                }
                            }
                            case ENTER: {
                                if(!endOfGame) {
                                    if (computerScoredGoal == TRUE) {
                                        roller.circle.setLayoutX(155);
                                        roller.circle.setLayoutY(225);
                                        usersPaddle.rectangle.setLayoutX(125);
                                        computersPaddle.rectangle.setLayoutX(125);
                                        grid.getChildren().remove(labelComputerScored.label);
                                        computerScoredGoal = FALSE;
                                    } else if (userScoredGoal == TRUE) {
                                        roller.circle.setLayoutX(155);
                                        roller.circle.setLayoutY(225);
                                        usersPaddle.rectangle.setLayoutX(125);
                                        computersPaddle.rectangle.setLayoutX(125);
                                        grid.getChildren().remove(labelUserScored.label);
                                        userScoredGoal = FALSE;
                                    }
                                }
                                break;
                            }
                            case ESCAPE: {
                                if (endOfGame) {
                                    primaryStage.close();
                                }
                                break;
                            }
                            case LEFT: {
                                if(usersPaddle.rectangle.getLayoutX() > bounds.getMinX() + frame) {
                                    usersPaddle.setDx(-3.0);
                                    usersPaddle.rectangle.setLayoutX(usersPaddle.rectangle.getLayoutX() + usersPaddle.getDx());
                                    break;
                                }
                            }
                            case RIGHT: {
                                if(usersPaddle.rectangle.getLayoutX() + usersPaddle.rectangle.getWidth() < bounds.getMaxX()-4) {
                                    usersPaddle.setDx(3.0);
                                    usersPaddle.rectangle.setLayoutX(usersPaddle.rectangle.getLayoutX() + usersPaddle.getDx());
                                }
                                break;
                            }
                     }
            }
        });
                        if(rollerdy > 0 && rollerdx > 0 && paddlesdx > 0){
                            isRunning = TRUE;
                        }
                        roller.circle.setLayoutX(roller.circle.getLayoutX() + rollerdx);
                        roller.circle.setLayoutY(roller.circle.getLayoutY() + rollerdy);
                        computersPaddle.rectangle.setLayoutX(computersPaddle.rectangle.getLayoutX() + paddlesdx);

                        //2 x if - if roller hits the bounds reverse it's velocity direction
                        if (roller.circle.getLayoutX() >= (bounds.getMaxX() - roller.circle.getRadius() - frame)
                                || roller.circle.getLayoutX() <= (bounds.getMinX() + roller.circle.getRadius() + frame)) {
                            rollerdx = -rollerdx;
                            computerBounceAble = TRUE;
                            userBounceAble = TRUE;
                        }
                        if (roller.circle.getLayoutY() >= (bounds.getMaxY() - roller.circle.getRadius() - frame)
                                || roller.circle.getLayoutY() <= (bounds.getMinY() + roller.circle.getRadius() + frame)) {
                            rollerdy = -rollerdy;
                            computerBounceAble = TRUE;
                            userBounceAble = TRUE;
                        }

                        if (computersPaddle.rectangle.getLayoutX() >= (bounds.getMaxX() - computersPaddle.rectangle.getWidth() - frame)
                                || computersPaddle.rectangle.getLayoutX() <= (bounds.getMinX() + frame)) {
                            paddlesdx = -paddlesdx;
                        }
                        //roller bouncing paddle: dy
                        if(computerBounceAble) {
                            if ((roller.circle.getLayoutY() - roller.circle.getRadius() <= computersPaddle.rectangle.getLayoutY() + computersPaddle.rectangle.getHeight())
                                    && ((roller.circle.getLayoutX() - (roller.circle.getRadius() / 2)) <= computersPaddle.rectangle.getLayoutX() + computersPaddle.rectangle.getWidth())
                                    && ((roller.circle.getLayoutX() + (roller.circle.getRadius() / 2)) >= computersPaddle.rectangle.getLayoutX())
                                    && ((roller.circle.getLayoutY() + roller.circle.getRadius()) >= computersPaddle.rectangle.getLayoutY())) {
                                rollerdy = -rollerdy;
                                computerBounceAble = FALSE;
                                userBounceAble = TRUE;
                            }
                            //roller bouncing paddle: dx
                            if ((roller.circle.getLayoutY() + (roller.circle.getRadius() / 2) >= computersPaddle.rectangle.getLayoutY())
                                    && (roller.circle.getLayoutY() - (roller.circle.getRadius() / 2) <= computersPaddle.rectangle.getLayoutY() + computersPaddle.rectangle.getHeight())
                                    && (roller.circle.getLayoutX() - roller.circle.getRadius() <= computersPaddle.rectangle.getLayoutX() + computersPaddle.rectangle.getWidth())
                                    && (roller.circle.getLayoutX() + roller.circle.getRadius() >= computersPaddle.rectangle.getLayoutX())) {
                                rollerdx = -rollerdx;
                                computerBounceAble = FALSE;
                                userBounceAble = TRUE;
                            }
                        }
                        if(userBounceAble){
                            //roller bouncing users paddle: dy
                            if ((roller.circle.getLayoutY() - roller.circle.getRadius() <= usersPaddle.rectangle.getLayoutY() + usersPaddle.rectangle.getHeight())
                                    && ((roller.circle.getLayoutX() - (roller.circle.getRadius() / 2)) <= usersPaddle.rectangle.getLayoutX() + usersPaddle.rectangle.getWidth())
                                    && ((roller.circle.getLayoutX() + (roller.circle.getRadius() / 2)) >= usersPaddle.rectangle.getLayoutX())
                                    && ((roller.circle.getLayoutY() + roller.circle.getRadius()) >= usersPaddle.rectangle.getLayoutY())) {
                                rollerdy = -rollerdy;
                                userBounceAble = FALSE;
                                computerBounceAble = TRUE;
                            }
                            //roller bouncing paddle: dx
                            if ((roller.circle.getLayoutY() + (roller.circle.getRadius() / 2) >= usersPaddle.rectangle.getLayoutY())
                                    && (roller.circle.getLayoutY() - (roller.circle.getRadius() / 2) <= usersPaddle.rectangle.getLayoutY() + usersPaddle.rectangle.getHeight())
                                    && (roller.circle.getLayoutX() - roller.circle.getRadius() <= usersPaddle.rectangle.getLayoutX() + usersPaddle.rectangle.getWidth())
                                    && (roller.circle.getLayoutX() + roller.circle.getRadius() >= usersPaddle.rectangle.getLayoutX())) {
                                rollerdx = -rollerdx;
                                userBounceAble = FALSE;
                                computerBounceAble = TRUE;
                            }
                        }
                        //scoring a goal by player event
                        if (roller.circle.getLayoutX() >= computersGoal.rectangle.getLayoutX()
                                && roller.circle.getLayoutX() <= computersGoal.rectangle.getLayoutX() + computersGoal.rectangle.getWidth()
                                && roller.circle.getLayoutY() - roller.circle.getRadius() <= bounds.getMinY() + frame) {
                            rollerdx = 0;
                            rollerdy = 0;
                            paddlesdx = 0;
                            roller.circle.setLayoutY(roller.circle.getLayoutY()+3);
                            isRunning = FALSE;
                            usersPaddle.scoresGoal();
                            userScoredGoal = TRUE;
                            if(userScoredGoal == TRUE && usersPaddle.getNumberOfScoredGoals()<3){
                                    labelUserScored.label.relocate(105, 150);
                                    grid.getChildren().add(labelUserScored.label);
                            }
                            if (userScoredGoal && usersPaddle.getNumberOfScoredGoals() == 3 ){
                                userWon = TRUE;
                            }
                            System.out.println("Player scored a goal!");
                            System.out.println("Result of the game: Player: " + usersPaddle.getNumberOfScoredGoals() + " vs. computer: " + computersPaddle.getNumberOfScoredGoals());
                        }
                        //scoring a goal by computer event
                        if (roller.circle.getLayoutX() >= usersGoal.rectangle.getLayoutX()
                                && roller.circle.getLayoutX() <= usersGoal.rectangle.getLayoutX() + usersGoal.rectangle.getWidth()
                                && roller.circle.getLayoutY() + roller.circle.getRadius() >= bounds.getMaxY() - frame) {
                            rollerdx = 0;
                            rollerdy = 0;
                            paddlesdx = 0;
                            roller.circle.setLayoutY(roller.circle.getLayoutY()-3);
                            computersPaddle.scoresGoal();
                            isRunning = FALSE;
                            computerScoredGoal = TRUE;
                            if(computerScoredGoal == TRUE && computersPaddle.getNumberOfScoredGoals()<3){
                                    labelComputerScored.label.relocate(105, 150);
                                    grid.getChildren().add(labelComputerScored.label);
                            }
                            if (computerScoredGoal && computersPaddle.getNumberOfScoredGoals() == 3 ){
                                computerWon = TRUE;
                            }
                            System.out.println("Computer scored a goal!");
                            System.out.println("Result of the game: Player: " + usersPaddle.getNumberOfScoredGoals() + " vs. computer: " + computersPaddle.getNumberOfScoredGoals());
                        }

                        if (computerWon){
                            labelGameOver.label.relocate(60, 150);
                            grid.getChildren().add(labelGameOver.label);
                            computerWon = FALSE;
                            endOfGame = TRUE;
                        }

                        if(userWon){
                            labelYouWon.label.relocate(60,150);
                            grid.getChildren().add(labelYouWon.label);
                            userWon = FALSE;
                            endOfGame = TRUE;
                        }
                    }
                }
        ));
        timeline.setCycleCount(timeline.INDEFINITE);
        timeline.play();
    }
}
