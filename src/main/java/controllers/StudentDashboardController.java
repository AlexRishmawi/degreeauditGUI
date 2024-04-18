package controllers;
 
import model.*;

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
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class StudentDashboardController implements Initializable {

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
    private MenuItem logOut;

    @FXML
    private MenuButton menuButton;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label studentName;

    @FXML
    private PieChart progressPie;

    @FXML
    void logOutClicked(ActionEvent event) throws IOException{
        DegreeWork degreeWork = DegreeWork.getInstance();
        degreeWork.logout();

        App.setRoot("landing_page");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        

        headerShadow.widthProperty().bind(stackPane.widthProperty());
        header.widthProperty().bind(stackPane.widthProperty());
        
        DegreeWork degreeWork = DegreeWork.getInstance();
        if (degreeWork.getCurrentUser().isStudent()) {
            Student student = (Student) degreeWork.getCurrentUser();

        if(student == null) {
            System.out.println("Student is null");
        }

        int completed = student.getCompletedCourse().size();
        int progress = student.getCurrentSemester().getCourses().size();
        int total = student.getDegree().getTotalCreditRequired();
        int not_started = total - (completed + progress);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Completed", completed),
            new PieChart.Data("In Progress", progress),
            new PieChart.Data("Not Started", not_started)
        );

        progressPie.setData(pieChartData);
        progressPie.setLabelsVisible(true);
        
        studentName.setText(student.getFirstName() + " " + student.getLastName());
        ID.setText(student.getStudentID());
        classLevel.setText(student.getLevel().toString());
    }
}
