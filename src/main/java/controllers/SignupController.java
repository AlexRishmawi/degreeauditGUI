package controllers;

import java.io.IOException;
import java.util.ResourceBundle;

import aisle.App;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SignupController implements Initializable {
    @FXML
    private TextField txt_firstName;

    @FXML
    private TextField txt_lastName;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_confirmPassword;

    @FXML
    private Label signup_error;

    @FXML
    private void signupClicked(MouseEvent event) throws IOException {
        String firstName = txt_firstName.getText();
        String lastName = txt_lastName.getText();
        String email = txt_email.getText();
        String password = txt_password.getText();
        String confirmPassword = txt_confirmPassword.getText();

        if(firstName.equals("") || lastName.equals("") || email.equals("")
            || password.equals("") || confirmPassword.equals("")) 
        {
            signup_error.setText("Not blank, Bruh!");
            return;
        }

        if(!password.equals(confirmPassword)) {
            signup_error.setText("Confirm password must be same, Bruh!");
        }

        // DegreeWork degreeWord = new DegreeWord();

        // if(!degreeWord.createUser("student", firstName, lastName, email, password)) {
        //     signup_error.setText("Sorry, this user coudn't be created. I don't know why");
        //     return;
        // }

        // degreeWord.login(email, password);
        // User user = degreeWord.getCurrentUser();
        // App.setRoot("student_page");

    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        App.setRoot("landing_page");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
