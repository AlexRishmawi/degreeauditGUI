package controllers;
 
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

import aisle.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class PleaseProvideControllerClassName implements Initializable {

    @FXML
    private Label studentName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DegreeWork degreeWork = DegreeWork.getInstance();
        Student user = (Student)degreeWork.getCurrentUser();

        studentName.setText(user.getFirstName());
    }
}
