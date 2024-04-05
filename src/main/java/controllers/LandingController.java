package controllers;

import aisle.App;

import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LandingController {
    @FXML
    private void onLoginClicked(ActionEvent event) throws IOException {
        App.setRoot("login_page");
    }

    @FXML
    private void onSignupClicked(ActionEvent event) throws IOException {
        App.setRoot("signup_page");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
