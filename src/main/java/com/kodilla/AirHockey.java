package com.kodilla;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import java.util.Timer;
import java.util.TimerTask;
import javafx.stage.StageStyle;


public class AirHockey extends Application {
    private Image imageback = new Image("graphics/Table.png");
    private Image myPaddle = new Image("graphics/MyPaddle.png");
    private Image opponentPaddle = new Image("graphics/OpponentsPaddle.png");
    private Image puck = new Image("graphics/Puck.png");

    public static void main (String[] args){
        Timer timer = new Timer();
        for (int i =0; i<10; i++){

            System.out.println("That's message");
        }
//        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setHgap(1);
        grid.setVgap(1);
        ImageView usersPaddle = new ImageView(myPaddle);
        ImageView computersPaddle = new ImageView(opponentPaddle);
        ImageView roller = new ImageView(puck);
        grid.setBackground(background);
        grid.add(usersPaddle, 90, 230, 30, 30);
        grid.add(computersPaddle, 90, 20, 30, 30);
        grid.add(roller, 110, 140);

        Scene scene = new Scene(grid, 300, 450, Color.BLACK);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Air Hockey");
        primaryStage.setScene(scene);
        primaryStage.show();



    }
}
