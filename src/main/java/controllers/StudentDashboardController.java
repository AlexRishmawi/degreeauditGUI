package controllers;
 
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class StudentDashboardController implements Initializable {

    @FXML
    private Label ID;

    @FXML
    private Label studentName;

    @FXML
    private Label classLevel;

    @FXML
    private Rectangle header;

    @FXML
    private Rectangle headerShadow;

    @FXML
    private ImageView header_img;

    @FXML
    private Label level;

    @FXML
    private MenuButton menuButton;

    @FXML
    private StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        headerShadow.widthProperty().bind(stackPane.widthProperty());
        header.widthProperty().bind(stackPane.widthProperty());
        
        DegreeWork degreeWork = DegreeWork.getInstance();
        if (degreeWork.getCurrentUser().isStudent()) {
            Student student = (Student) degreeWork.getCurrentUser();

            if(student == null) {
                System.out.println("Student is null");
            }
            
            studentName.setText(student.getFirstName() + " " + student.getLastName());
            classLevel.setText(student.getLevel().toString());
            level.setText(student.getDegree().getDegreeType());
            ID.setText(student.getStudentID());
        }

    }
}
