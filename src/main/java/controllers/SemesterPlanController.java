package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import aisle.App;

import java.net.URL;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.ListView;
import model.DegreeWork;
import model.Semester;
import model.Student;
import model.Course;

public class SemesterPlanController implements Initializable {
    private DegreeWork degreeWork = DegreeWork.getInstance();
    private ArrayList<Semester> semesterPlan = degreeWork.getSemesterPlan();

    @FXML
    private VBox semesterPlan_box;

    @FXML
    private Label ID;

    @FXML
    private Label studentName;

    @FXML
    private Label email;

    @FXML
    private Label level;

    @FXML
    private Rectangle header;

    @FXML
    private Rectangle headerShadow;

    @FXML
    private ImageView header_img;

    @FXML
    private Label degreeLabel;

    @FXML
    private Label majorLabel;

    @FXML
    void generateClick(ActionEvent event) throws IOException {
        if (semesterPlan_box != null) {
            semesterPlan_box.getChildren().clear();
        }

        for (Semester semester : semesterPlan) {
            HBox semesterContainer = new HBox(15);
            semesterContainer.getStyleClass().add("semester-container");

            Label semesterInfoLabel = new Label("Semester:");
            semesterInfoLabel.getStyleClass().add("semester-info-label");
            Text semesterInfoText = new Text(
                    semester.getSeason().toString() + " " + Integer.toString(semester.getYear()));
            semesterInfoText.getStyleClass().add("semester-info-text");
            semesterContainer.getChildren().addAll(semesterInfoLabel, semesterInfoText, new Text("   "));

            Label semesterCreditString = new Label("---   Credit:");
            semesterCreditString.getStyleClass().add("semester-info-label");
            Text semesterCredit = new Text(Integer.toString(semester.getCreditLimit()));
            semesterCredit.getStyleClass().add("semester-info-text");
            semesterContainer.getChildren().addAll(semesterCreditString, semesterCredit);

            Button showCoursesButton = new Button("Detail");
            showCoursesButton.getStyleClass().add("show-courses-button");
            showCoursesButton.setOnAction(e -> {
                displayCoursesForSemester(semester);
            });
            semesterContainer.getChildren().addAll(new Text("   "), showCoursesButton);

            semesterPlan_box.getChildren().addAll(semesterContainer);

        }
    }

    private void displayCoursesForSemester(Semester semester) {
        ArrayList<Course> courses = semester.getCourses();
        Stage listViewStage = new Stage();
        listViewStage.setTitle("Courses");

        ListView<String> listView = new ListView<>();
        for (Course course : courses) {
            listView.getItems().add(course.toString());
        }

        Scene scene = new Scene(listView, 1153, 700);
        listViewStage.setScene(scene);
        listViewStage.show();
    }

    @FXML
    void logOutClicked(ActionEvent event) throws IOException {
        DegreeWork degreeWork = DegreeWork.getInstance();
        degreeWork.logout();

        App.setRoot("landing_page");
    }

    @FXML
    void dashboardClicked(ActionEvent event) throws IOException {
        App.setRoot("student_dashboard_page");
    }

    @FXML
    void courseSearchClicked(ActionEvent event) throws IOException {
        App.setRoot("student_search_page");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Student student = (Student) degreeWork.getCurrentUser();
        studentName.setText(student.getFirstName() + " " + student.getLastName());
        level.setText(student.getLevel().toString());
        ID.setText(student.getStudentID());
        email.setText(student.getEmail());
        degreeLabel.setText("Bachelor of Science");
        majorLabel.setText(student.getDegree().getSubject());
    }
}