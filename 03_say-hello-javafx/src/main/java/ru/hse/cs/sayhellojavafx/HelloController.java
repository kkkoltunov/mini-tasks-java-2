package ru.hse.cs.sayhellojavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private Button buttonHello;

    @FXML
    private TextField textField;

    @FXML
    void onHelloButtonClick(ActionEvent event) {
        Alert alert;
        if (textField.getText().isBlank() || textField.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.INFORMATION, "Приветcтвую анонима!");
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION, textField.getText() + ", привет!");
        }
        alert.show();
    }

    @FXML
    void onMenuHelpClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Автор шедевра: Колтунов Кирилл Константинович.");
        alert.show();
    }
}
