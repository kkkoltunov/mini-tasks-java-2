package ru.hse.cs.sayhellojavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private TextField textField;

    @FXML
    void onHelloButtonClick() {
        Alert alert;
        if (textField.getText().isBlank()) {
            alert = new Alert(Alert.AlertType.INFORMATION, "Приветcтвую анонима!");
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION, textField.getText() + ", привет!");
        }
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.show();
    }

    @FXML
    void onMenuHelpClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Автор шедевра: Колтунов Кирилл Константинович.");
        alert.show();
    }
}
