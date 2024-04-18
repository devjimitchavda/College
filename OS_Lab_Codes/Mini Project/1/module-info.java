module module-info {
    requires javafx.controls;
    requires javafx.fxml;
    opens yourPackageName to javafx.graphics, javafx.fxml;
}