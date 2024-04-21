package controllers;

import java.io.IOException;

import aisle.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class AppointmentController {

    @FXML
    private AnchorPane appointment;

    @FXML
    private Button goBack;

    @FXML
    void backClicked(ActionEvent event) throws IOException {
        App.setRoot("student_dashboard_page");
    }

}