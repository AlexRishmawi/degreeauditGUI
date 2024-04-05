module aisle {
    requires javafx.controls;
    requires javafx.fxml;

    opens aisle to javafx.fxml;
    exports aisle;
}
