package controllers;

import aisle.App;

import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.DegreeWork;
import model.Student;
import model.Advisor;

public class LoginController implements Initializable{
    @FXML
    private TextField txt_userEmail;
    
    @FXML
    private TextField txt_userPassword;

    // @FXML
    // private Label login_error;

    @FXML
    void loginClicked(ActionEvent event) throws IOException{
        String userEmail = txt_userEmail.getText();
        String password = txt_userPassword.getText();

        DegreeWork degreeWork = DegreeWork.getInstance();
        if(!degreeWork.login(userEmail, password)) {
            // login_error.setText("Invalid login credentials.");
            System.out.println("Invalid login credentials.");
            return;
        }

        if(degreeWork.getCurrentUser() instanceof Student) {
            Student student = (Student) degreeWork.getCurrentUser();
            

            if(student == null) {
                System.out.println("Student is null");
            }

            App.setRoot("student_dashboard_page");
        } else if(degreeWork.getCurrentUser() instanceof Advisor) {
            App.setRoot("advisor_search_page");
        }
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        App.setRoot("landing_page");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
