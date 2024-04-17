package controllers;
 
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class StudentDashboardController implements Initializable {

    @FXML
    private Label studentName;

    @FXML
    private StackPane stackPane;

    @FXML
    private Rectangle headerBackground;

    @FXML
    private Rectangle headerForeground;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DegreeWork degreeWork = DegreeWork.getInstance();
        Student user = (Student)degreeWork.getCurrentUser();

        studentName.setText(user.getFirstName());
    }
}
