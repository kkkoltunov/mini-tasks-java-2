module ru.hse.cs.sayhellojavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens ru.hse.cs.sayhellojavafx to javafx.fxml;
    exports ru.hse.cs.sayhellojavafx;
}