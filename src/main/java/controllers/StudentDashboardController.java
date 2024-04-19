package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
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
    private PieChart pieChart;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label studentName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        headerShadow.widthProperty().bind(stackPane.widthProperty());
        header.widthProperty().bind(stackPane.widthProperty());
        
        DegreeWork degreeWork = DegreeWork.getInstance();
        
        Student student = degreeWork.getCurrentUser().isStudent() ? (Student) degreeWork.getCurrentUser() : degreeWork.getCurrentStudent();

        if(student == null) {
            System.out.println("Student is null");
        }

        ObservableList <PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Completed", student.getCompletedCourse().size()),
            new PieChart.Data("In Progress", student.getCurrentSemester().getCourses().size()),
            new PieChart.Data("Not Started", student.getDegree().getTotalCreditRequired() - (student.getCompletedCourse().size() + student.getCurrentSemester().getCourses().size()))
        );

        pieChart.setData(pieChartData);
        pieChart.setLegendVisible(true);
        pieChart.setLabelsVisible(false);
        
        studentName.setText(student.getFirstName() + " " + student.getLastName());
        classLevel.setText(student.getLevel().toString());
        level.setText(student.getDegree().getDegreeType());
        ID.setText(student.getStudentID());
        studentName.setText(student.getFirstName() + " " + student.getLastName());
        classLevel.setText(student.getLevel().toString());
        level.setText(student.getDegree().getDegreeType());
        ID.setText(student.getStudentID());
    }
}
