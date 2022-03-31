package ru.hse.cs.javafxtime;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeController {
    @FXML
    private Label dataHoursLabel;

    @FXML
    public void initialize() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    LocalTime time = LocalTime.now();
                    Platform.runLater(() -> dataHoursLabel.setText(time.format(DateTimeFormatter.ofPattern("hh:mm:ss"))));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}