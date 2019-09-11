package com.kodilla;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import javafx.util.Duration;


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
        roller.relocate(145, 215);
        Circle usersPaddle = new Circle(15, Color.DARKBLUE);
        usersPaddle.relocate(145, 380);
        Circle computersPaddle = new Circle(15, Color.GREENYELLOW);
        computersPaddle.relocate(145, 50);
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
                    double dx = 1.2;
                    double dy = 1.8;

                    @Override
                    public void handle(ActionEvent t) {
                    //move roller
                        roller.setLayoutX(roller.getLayoutX()+dx);
                        roller.setLayoutY(roller.getLayoutY()+dy);

                        //bounds
                        Bounds bounds = grid.getBoundsInLocal();

                        //2 x if - if roller hits the bounds reverse it's velocity direction
                        if(roller.getLayoutX() >= (bounds.getMaxX()-roller.getRadius()-16)
                                || roller.getLayoutX() <= (bounds.getMinX() + roller.getRadius()+16)){
                            dx = -dx;
                        }

                        if(roller.getLayoutY() >= (bounds.getMaxY()-roller.getRadius()-16)
                                || roller.getLayoutY() <= (bounds.getMinY() + roller.getRadius()+16)){
                            dy = -dy;
                        }
                    }
                }));
        timeline.setCycleCount(timeline.INDEFINITE);
        timeline.play();
    }
}
