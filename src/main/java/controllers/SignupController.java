package controllers;

import java.io.IOException;
import java.util.ResourceBundle;

import aisle.App;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.DegreeWork;

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

    // @FXML
    // private TextField txt_studentID;

    // @FXML
    // private Label signup_error;

    @FXML
    void signupClicked(ActionEvent event) throws IOException {
        String firstName = txt_firstName.getText();
        String lastName = txt_lastName.getText();
        String email = txt_email.getText();
        String password = txt_password.getText();
        String confirmPassword = txt_confirmPassword.getText();
        //String studentID = txt_studentID.getText();

        if(firstName.equals("") || lastName.equals("") || email.equals("")
            || password.equals("") || confirmPassword.equals("")) 
        {
            // signup_error.setText("Not blank, Bruh!");
            System.out.println("Not blank, Bruh!");
            return;
        }

        if(!password.equals(confirmPassword)) {
            // signup_error.setText("Confirm password must be same, Bruh!");
            System.out.println("Confirm password must be same, Bruh!");
            return;
        }

        DegreeWork degreeWork = DegreeWork.getInstance();

        if(!degreeWork.signup(firstName, lastName, email, password, "X83012475", false)) {
            // signup_error.setText("Account already exists.");
            System.out.println("Account already exists.");
            return;
        }

        App.setRoot("student_dashboard_page");
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        App.setRoot("landing_page");
    }
    
    private void sizes () {
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
