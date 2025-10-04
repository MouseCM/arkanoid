module com.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.arkanoid to javafx.fxml;
    exports com.arkanoid;
}
