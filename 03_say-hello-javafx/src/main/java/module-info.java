module ru.hse.cs.sayhellojavafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru.hse.cs.sayhellojavafx to javafx.fxml;
    exports ru.hse.cs.sayhellojavafx;
}