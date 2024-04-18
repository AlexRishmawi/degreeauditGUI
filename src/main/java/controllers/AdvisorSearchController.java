package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import model.DegreeWork;
import model.Student;
import model.Advisor;

public class AdvisorSearchController {

    @FXML
    private TextField advisorSearch;

    @FXML
    private Button advisorSearchButton;

    @FXML
    private Rectangle header;

    @FXML
    private Rectangle headerShadow;

    @FXML
    private ImageView header_img;

    @FXML
    private MenuButton menuButton;

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox studentList;

    @FXML
    void searchClicked(ActionEvent event){
        String searchText = advisorSearch.getText();

        DegreeWork degreeWork = DegreeWork.getInstance();
        if(degreeWork.getCurrentUser().isStudent()) {
            return;
        }

        ArrayList<Student> searchedStudents = degreeWork.advisorSearchStudents(searchText);
        studentList.getChildren().clear();
        studentList.setSpacing(10);
        for(Student student : searchedStudents) {
            // Create a new TextField for each student
            TextField studentTextField = new TextField(student.getFirstName());
            studentTextField.setEditable(false);
            studentList.getChildren().add(studentTextField);
        }
        studentList.getChildren().add(studentList);
    }
}
