package ru.hse.cs.javafxtime;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeController {
    @FXML
    private Label dataHoursLabel;

    @FXML
    public void initialize() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Date date = new Date();
                    SimpleDateFormat formatForTimeNow = new SimpleDateFormat("hh:mm:ss");
                    Platform.runLater(() -> dataHoursLabel.setText(formatForTimeNow.format(date)));
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