module ru.hse.cs.javafxtime {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru.hse.cs.javafxtime to javafx.fxml;
    exports ru.hse.cs.javafxtime;
}