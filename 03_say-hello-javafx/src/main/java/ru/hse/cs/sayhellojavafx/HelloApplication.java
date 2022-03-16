package ru.hse.cs.sayhellojavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 200;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle("Здороваемся с вами бесплатно!");
        stage.setScene(scene);
        stage.setMinWidth(300);
        stage.setMaxWidth(500);
        stage.setMinHeight(150);
        stage.setMaxHeight(300);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}