module dev.aisle {
    requires javafx.controls;
    requires javafx.fxml;

    opens dev.aisle to javafx.fxml;
    exports dev.aisle;
}
