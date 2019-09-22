package com.kodilla.test;

import com.kodilla.Computer;
import com.kodilla.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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
                    TextDrawer labelComputerScored = new TextDrawer("Computer scored a goal");
                    TextDrawer labelUserScored = new TextDrawer("You scored a goal");
                    TextDrawer labelGameOver = new TextDrawer("Computer won! Press escape to exit window");
                    TextDrawer labelYouWon = new TextDrawer("Congratulations, You won! Press escape to exit window");
                    double rollerdx = 0;
                    double rollerdy = 0;
                    double paddlesdx = 0;
                    int frame = 16;
                    @Override
                    public void handle(ActionEvent t) {
                        //move roller
                        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        int e;
                        @Override
                        public void handle(KeyEvent event) {
                        switch(event.getCode()){
                            case SPACE: {
                                if (isRunning == FALSE) {
                                    rollerdx = 2.5;
                                    rollerdy = 1.8;
                                    paddlesdx = 2.0;
                                    System.out.println(isRunning);
                                }
                            }
                            case ENTER: {
                                if (computerScoredGoal == TRUE) {
                                    roller.circle.setLayoutX(155);
                                    roller.circle.setLayoutY(225);
                                    usersPaddle.rectangle.setLayoutX(125);
                                    computersPaddle.rectangle.setLayoutX(125);
                                    grid.getChildren().remove(labelComputerScored.label);
                                    computerScoredGoal = FALSE;
                                }
                                else if (userScoredGoal == TRUE) {
                                    roller.circle.setLayoutX(155);
                                    roller.circle.setLayoutY(225);
                                    usersPaddle.rectangle.setLayoutX(125);
                                    computersPaddle.rectangle.setLayoutX(125);
                                    grid.getChildren().remove(labelUserScored.label);
                                    userScoredGoal = FALSE;
                                }
                            }
                            case ESCAPE: {
                                if (endOfGame) {
                                    primaryStage.close();
                                }
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
                            computersPaddle.scoresGoal();
                            rollerdx = 0;
                            rollerdy = 0;
                            paddlesdx = 0;
                            roller.circle.setLayoutY(roller.circle.getLayoutY()-3);
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
