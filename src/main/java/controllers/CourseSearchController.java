package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import model.Course;
import model.DegreeWork;

public class CourseSearchController implements Initializable{

    @FXML
    private TextField courseSearch;

    @FXML
    private Button courseSearchButton;

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
    private VBox container;

    @FXML
    private ScrollPane myScrollPane;

    @Override
public void initialize(URL location, ResourceBundle resources) {
    headerShadow.widthProperty().bind(stackPane.widthProperty());
    header.widthProperty().bind(stackPane.widthProperty());
    container.setFillWidth(true); // Ensuring that the VBox inside the ScrollPane expands to fill the width.
}

@FXML
void searchClicked(ActionEvent event) {
    String searchText = courseSearch.getText();

    DegreeWork degreeWork = DegreeWork.getInstance();
    if(!degreeWork.getCurrentUser().isStudent()){
        return;
    }

    ArrayList<Course> searchedCourses = degreeWork.studentCourseSearch(searchText);
    container.getChildren().clear();
    for (Course course : searchedCourses) {
        HBox hbox = new HBox(10);
        hbox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5;");
        hbox.setPadding(new Insets(5, 10, 5, 10)); // Apply padding inside the HBox
        hbox.setPrefWidth(Double.MAX_VALUE); // Ensure HBox stretches to full width
        hbox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label courseLabel = new Label(course.getCourseName());
        Button viewButton = createViewDetailsButton(course);
        courseLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-margin: 10;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        hbox.getChildren().addAll(courseLabel, new Region(), viewButton);
        VBox.setMargin(hbox, new Insets(5));
        container.getChildren().add(hbox);
    }
}

private void handleViewDetails(Course course) {
    try {
        System.out.println("View details for: " + course.getCourseName());
        // Implement navigation logic
    } catch (Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading the details.");
        alert.showAndWait();
    }
}

   
    private Button createViewDetailsButton(Course course) {
        Button viewButton = new Button("View Details");
        viewButton.setStyle("-fx-background-color: #73000a; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold; -fx-border-radius: 5; -fx-padding: 5 10 5 10;");
        viewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleViewDetails(course);
            }
        });
        return viewButton;
    }

    
}
