package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import aisle.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Advisor;
import model.DegreeWork;
import model.Student;

public class AdvisorSearchController implements Initializable {

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

    DegreeWork degreeWork = DegreeWork.getInstance();

    @FXML
    void searchClicked(ActionEvent event) {
        String searchText = advisorSearch.getText();

        
        if (degreeWork.getCurrentUser().isStudent()) {
            return;
        }

        ArrayList<Student> searchedStudents = degreeWork.advisorSearchStudents(searchText);
        studentList.getChildren().clear();
        for (Student student : searchedStudents) {
            HBox hbox = new HBox(10); // 10 is the spacing between elements in the HBox
            hbox.setStyle(
                    "-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5;");
            hbox.setPadding(new Insets(5, 10, 5, 10)); // Apply padding inside the HBox
            hbox.setPrefWidth(Double.MAX_VALUE); // Ensure HBox stretches to full width
            hbox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            Label studentLabel = new Label(
                    student.getFirstName() + " " + student.getLastName() + "\n" + student.getStudentID());
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
        viewButton.setStyle(
                "-fx-background-color: #73000a; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold; -fx-border-radius: 5; -fx-padding: 5 10 5 10;");
        viewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleViewDetails(student);
            }
        });
        return viewButton;
    }

    private void handleViewDetails(Student student) {
        System.out.println("View details for: " + student.getFirstName());
        if(degreeWork.getCurrentUser() instanceof Advisor) {
            displayStudent(student);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You are not an advisor");
            alert.setContentText("Only advisors can view student details");
            alert.showAndWait();
        }
    }

    private void displayStudent(Student student) {
        Stage listViewStage = new Stage();
        listViewStage.setTitle("Courses");

        ListView<String> listView = new ListView<>();
        listView.getItems().add(student.toString());

        Scene scene = new Scene(listView, 1153, 700);
        listViewStage.setScene(scene);
        listViewStage.show();
    }

    @FXML
    void logOutClicked(ActionEvent event) throws IOException{
        DegreeWork degreeWork = DegreeWork.getInstance();
        degreeWork.logout();
        
        App.setRoot("landing_page");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        headerShadow.widthProperty().bind(stackPane.widthProperty());
        header.widthProperty().bind(stackPane.widthProperty());
        studentList.setFillWidth(true);
    }
}
