package controllers;
 
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;

public class StudentDashboardController implements Initializable {

    @FXML
    private ImageView header_img;

    @FXML
    private MenuButton menuButton;

    @FXML
    private Label studentName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DegreeWork degreeWork = DegreeWork.getInstance();
        degreeWork.login("tHill@email.sc.edu", "password");
        Student student = (Student) degreeWork.getCurrentUser();
        

        if(student == null) {
            System.out.println("Student is null");
        }
        
        studentName.setText(student.toStringAccount());
    }
}
