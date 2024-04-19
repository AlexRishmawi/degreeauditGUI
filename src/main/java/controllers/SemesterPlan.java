package controllers;

import aisle.App;

import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.DegreeWork;
import model.Student;
import model.Advisor;

public class SemesterPlan implements Initializable {
    private DegreeWork degreeWork = DegreeWork.getInstance();
    private ArrayList<Semester> semesterPlan = degreeWork.getSemesterPlan();
    private VBox semesterPlan_box;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generateSemesterList();
    }

    private void generateSemesterList() {
        semesterPlan_box.getChildren().clear();
        for(Semester semester: semesterPlan) {
            TextField semesterYear = new TextField(semester.getYear());
            TextField semesterSeason = new TextField(semester.getSeason().toString());
            TextField semesterCredit = new TextField(semester.getCreditLimit());
            
            Button showCoursesButton = new Button("Show Courses");
            showCoursesButton.setOnAction(event -> {
                displayCoursesForSemester(semester);
            });
            semesterPlan_box.getChildren().addAll(semesterYear, semesterSeason, semesterCredit);
        }
    }

    private void displayCoursesForSemester(Semester semester) {
        ArrayList<Course> courses = semester.getCourses();

        for (Course course: courses) {
            System.out.println(course.toStringCourseAbbr());
        }
    }
}
