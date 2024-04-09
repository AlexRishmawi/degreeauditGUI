module aisle {
    requires javafx.controls;
    requires javafx.fxml;

    opens aisle to javafx.fxml;
    exports aisle;

    opens controllers to javafx.fxml;
    exports controllers;

    opens model to javafx.fxml;
    exports model;
}
