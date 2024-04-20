package controllers;

import aisle.App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.net.URL;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import model.DegreeWork;
import model.Semester;
import model.Student;
import model.Advisor;
import model.Course;

public class SemesterPlanController implements Initializable {
    private DegreeWork degreeWork = DegreeWork.getInstance();
    private ArrayList<Semester> semesterPlan = degreeWork.getSemesterPlan();

    @FXML
    private VBox semesterPlan_box;


    @FXML
    void generateClick(ActionEvent event) throws IOException{
        if (semesterPlan_box != null) {
            semesterPlan_box.getChildren().clear();
        }

        for(Semester semester: semesterPlan) {
            HBox container = new HBox(10);

            TextField semesterYear = new TextField(Integer.toString(semester.getYear()));
            TextField semesterSeason = new TextField(semester.getSeason().toString());
            TextField semesterCredit = new TextField(Integer.toString(semester.getCreditLimit()));
            
            Button showCoursesButton = new Button("Show Courses");
            showCoursesButton.setOnAction(e -> {
                displayCoursesForSemester(semester);
            });

            container.getChildren().addAll(semesterYear, semesterSeason, semesterCredit, showCoursesButton);
            semesterPlan_box.getChildren().addAll(container);
        }
    }

    private void displayCoursesForSemester(Semester semester) {
        ArrayList<Course> courses = semester.getCourses();
        Stage listViewStage = new Stage();
        listViewStage.setTitle("Courses");

        ListView<String> listView = new ListView<>();
        for (Course course: courses) {
            listView.getItems().add(course.toString());
        }

        Scene scene = new Scene(listView, 1153, 700);
        listViewStage.setScene(scene);
        listViewStage.show();
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}