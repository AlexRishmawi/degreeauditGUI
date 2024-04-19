package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import aisle.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import model.DegreeWork;
import model.Student;

public class StudentDashboardController implements Initializable{

    @FXML
    private Label ID;

    @FXML
    private Label classLevel;

    @FXML
    private Label collegeLabel;

    @FXML
    private Label degreeLabel;

    @FXML
    private Rectangle header;

    @FXML
    private Rectangle headerShadow;

    @FXML
    private ImageView header_img;

    @FXML
    private Label level;

    @FXML
    private MenuItem logout;

    @FXML
    private Label majorLabel;

    @FXML
    private MenuButton menuButton;

    @FXML
    private PieChart pieChart;

    @FXML
    private MenuItem plan;

    @FXML
    private StackPane stackPane;

    @FXML
    private StackedBarChart<?, ?> stackedProgress;

    @FXML
    private Label studentName;
    
    @FXML
    void logOutClicked(ActionEvent event) throws IOException{
        DegreeWork degreeWork = DegreeWork.getInstance();
        degreeWork.logout();

        App.setRoot("landing_page");
    }

    @FXML
    void semesterPlanClicked(ActionEvent event) throws IOException {
        App.setRoot("semester_plan_page");
    }

    private void setupPieChart() {
        DegreeWork degreeWork = DegreeWork.getInstance();
        
        Student student = degreeWork.getCurrentUser().isStudent() ? (Student) degreeWork.getCurrentUser() : degreeWork.getCurrentStudent();
    
        ObservableList <PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Completed", student.getCompletedCourse().size()),
            new PieChart.Data("In Progress", student.getCurrentSemester().getCourses().size()),
            new PieChart.Data("Not Started", student.getDegree().getTotalCreditRequired() - (student.getCompletedCourse().size() + student.getCurrentSemester().getCourses().size()))
        );

        pieChart.setData(pieChartData);
        pieChart.setLegendVisible(true);
        pieChart.setLabelsVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        headerShadow.widthProperty().bind(stackPane.widthProperty());
        header.widthProperty().bind(stackPane.widthProperty());

        DegreeWork degreeWork = DegreeWork.getInstance();
        
        Student student = degreeWork.getCurrentUser().isStudent() ? (Student) degreeWork.getCurrentUser() : degreeWork.getCurrentStudent();

        if(student == null) {
            System.out.println("Student is null");
        }

        setupPieChart();

        studentName.setText(student.getFirstName() + " " + student.getLastName());
        classLevel.setText(student.getLevel().toString());
        level.setText(student.getDegree().getDegreeType());
        ID.setText(student.getStudentID());

        degreeLabel.setText(student.getDegree().getDegreeType());
        majorLabel.setText(student.getDegree().toString());

    }
}
