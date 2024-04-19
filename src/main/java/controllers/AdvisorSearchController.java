package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import aisle.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import model.DegreeWork;
import model.Student;

public class AdvisorSearchController implements Initializable{

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
    void searchClicked(ActionEvent event) {
        String searchText = advisorSearch.getText();

        DegreeWork degreeWork = DegreeWork.getInstance();
        if(degreeWork.getCurrentUser().isStudent()) {
            return;
        }

        ArrayList<Student> searchedStudents = degreeWork.advisorSearchStudents(searchText);
        studentList.getChildren().clear();
        studentList.setSpacing(10);
        for (Student student : searchedStudents) {
            HBox hbox = new HBox(10); // 10 is the spacing between elements in the HBox
            hbox.setStyle("-fx-background-color: white;");  // Set the background color to white
        
            Label studentLabel = new Label(student.getFirstName() + " " + student.getLastName());
            Button viewButton = new Button("View Details");
            viewButton.setOnAction(e -> {
                try {
                    degreeWork.setCurrentStudent(student.getID());
                    App.setRoot("student_dashboard_page");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
        
            hbox.getChildren().addAll(studentLabel, viewButton);
            studentList.getChildren().add(hbox);
        }
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
