package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import aisle.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
    if (degreeWork.getCurrentUser().isStudent()) {
        return;
    }

    ArrayList<Student> searchedStudents = degreeWork.advisorSearchStudents(searchText);
        studentList.getChildren().clear();
        for (Student student : searchedStudents) {
            HBox hbox = new HBox(10); // 10 is the spacing between elements in the HBox
            hbox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5;");
            hbox.setPadding(new Insets(5, 10, 5, 10)); // Apply padding inside the HBox
            hbox.setPrefWidth(Double.MAX_VALUE); // Ensure HBox stretches to full width
            hbox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            Label studentLabel = new Label(student.getFirstName() + " " + student.getLastName() + "\n" + student.getStudentID());
            Button viewButton = createViewDetailsButton(student);
            studentLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-margin: 10;");
            

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS); // Makes the spacer expandable

            hbox.getChildren().addAll(studentLabel, spacer, viewButton);
            VBox.setMargin(hbox, new Insets(5)); // Uniform margin around the HBox
            studentList.getChildren().add(hbox);
        }
    }
    
    private Button createViewDetailsButton(Student student) {
        Button viewButton = new Button("View Details");
        viewButton.setStyle("-fx-background-color: #73000a; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold; -fx-border-radius: 5; -fx-padding: 5 10 5 10;");
        viewButton.setOnAction(event -> handleViewDetails(student));
        return viewButton;
    }

    private void handleViewDetails(Student student) {
        try {
            System.out.println("View details for: " + student.getFirstName());
            DegreeWork.getInstance().setCurrentStudent(student.getID());
            App.setRoot("student_dashboard_page");
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot load the student details page.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        headerShadow.widthProperty().bind(stackPane.widthProperty());
        header.widthProperty().bind(stackPane.widthProperty());
    }
}
