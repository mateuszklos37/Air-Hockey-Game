package com.kodilla;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
        //background
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        //roller, users and computers paddles
        Circle roller = new Circle(10, Color.ORANGE);
        roller.relocate(145, 200);
        Rectangle usersPaddle = new Rectangle(60, 10, Color.INDIANRED);
        usersPaddle.relocate(125, 375);
        Rectangle computersPaddle = new Rectangle(60, 10, Color.GREENYELLOW);
        computersPaddle.relocate(125, 70);
        Pane grid = new Pane();
        grid.setBackground(background);
        grid.getChildren().add(computersPaddle);
        grid.getChildren().add(roller);
        grid.getChildren().add(usersPaddle);
        //scene
        Scene scene = new Scene(grid, 300, 450, Color.BLACK);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Air Hockey");
        primaryStage.setScene(scene);
        primaryStage.show();
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
                        if(roller.getLayoutX() >= (bounds.getMaxX()-roller.getRadius()-frame)
                                || roller.getLayoutX() <= (bounds.getMinX() + roller.getRadius()+frame)){
                            rollerdx = -rollerdx;
                        }
                        if(roller.getLayoutY() >= (bounds.getMaxY()-roller.getRadius()-frame-200)
                                || roller.getLayoutY() <= (bounds.getMinY() + roller.getRadius()+frame)){
                            rollerdy = -rollerdy;
                        }

                        if(computersPaddle.getLayoutX() >= (bounds.getMaxX()-computersPaddle.getWidth()-frame)
                                || computersPaddle.getLayoutX() <= (bounds.getMinX() + frame)){
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
                        if((roller.getLayoutY() - roller.getRadius() <= computersPaddle.getLayoutY() + computersPaddle.getHeight())
                        && (roller.getLayoutX() <= computersPaddle.getLayoutX() + computersPaddle.getWidth())
                        && (roller.getLayoutX() >= computersPaddle.getLayoutX())
                        && (roller.getLayoutY() + roller.getRadius() >= computersPaddle.getLayoutY())){
                                rollerdy = -rollerdy;
                        }
                        //roller bouncing paddle: dx
                        if((roller.getLayoutY() >= computersPaddle.getLayoutY())
                                && (roller.getLayoutY() <= computersPaddle.getLayoutY() + computersPaddle.getHeight())
                                && (roller.getLayoutX() - roller.getRadius() <= computersPaddle.getLayoutX() + computersPaddle.getWidth())
                                && (roller.getLayoutX() + roller.getRadius() >= computersPaddle.getLayoutX())){
                            rollerdx = -rollerdx;
                        }
                    }
                }
        ));
        timeline.setCycleCount(timeline.INDEFINITE);
        timeline.play();
    }
}
