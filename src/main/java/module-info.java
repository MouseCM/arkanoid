module com {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.arkanoid to javafx.fxml;
    opens com.controller to javafx.fxml;
    exports com.arkanoid;
}
