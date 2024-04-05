package controllers;

import aisle.App;

import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController implements Initializable{
    @FXML
    private TextField txt_userEmail;
    
    @FXML
    private TextField txt_userPassword;

    @FXML
    private Label login_error;

    @FXML
    private void loginClicked(MouseEvent event) throws IOException {
        String userEmail = txt_userEmail.getText();
        String password = txt_userPassword.getText();

        // DegreeWord degreeWord = new DegreeWord();
        // if(!degreeWord.login(userEmail, password)) {
        //     login_error.setText("Invalid login credentials.");
        //     return;
        // }

        // App.setRoot("user_home");
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        App.setRoot("landing_page");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
