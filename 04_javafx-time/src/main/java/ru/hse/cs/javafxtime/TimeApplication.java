package ru.hse.cs.javafxtime;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TimeApplication extends Application {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 200;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TimeApplication.class.getResource("time-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle("Your clock");
        stage.setScene(scene);
        stage.setMinWidth(450);
        stage.setMaxWidth(800);
        stage.setMinHeight(250);
        stage.setMaxHeight(400);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}