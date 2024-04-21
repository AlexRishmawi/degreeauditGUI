package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import aisle.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import model.Advisor;
import model.DegreeWork;
import model.Student;
import model.User;

import javafx.fxml.Initializable;

public class advisorDashboardController implements Initializable {

    @FXML
    private Label ADVISOR_EMAIL;

    @FXML
    private Label ADVISOR_ID;

    @FXML
    private Label ADVISOR_NAME;

    @FXML
    private Label STUDENT_CLASS;

    @FXML
    private Label STUDENT_EMAIL;

    @FXML
    private Label STUDENT_ID;

    @FXML
    private Label STUDENT_LEVEL;

    @FXML
    private Label STUDENT_NAME;

    @FXML
    private ImageView STUDENT_PICTURE;

    @FXML
    private Rectangle header;

    @FXML
    private Rectangle headerShadow;

    @FXML
    private ImageView header_img;

    @FXML
    private MenuItem logout;

    @FXML
    private MenuButton menuButton;

    @FXML
    private PieChart pieChart;

    @FXML
    private MenuItem plan;

    @FXML
    private StackPane stackPane;

    @FXML
    private StackedBarChart<Number, String> stackedProgress;

    @FXML
    private Button STUDENT_SEARCH;

    @FXML
    void studentSearchClicked(ActionEvent event) throws IOException {
        App.setRoot("student_search_page");
    }

    @FXML
    void logOutClicked(ActionEvent event) throws IOException {
        DegreeWork degreeWork = DegreeWork.getInstance();
        degreeWork.logout();

        App.setRoot("landing_page");
    }

    @FXML
    void semesterPlanClicked(ActionEvent event) throws IOException {
        App.setRoot("semester_plan_page");
    }

    DegreeWork degreeWork = DegreeWork.getInstance();

    Student student = degreeWork.getCurrentUser().isStudent() ? (Student) degreeWork.getCurrentUser()
            : degreeWork.getCurrentStudent();

    private void setupPieChart() {

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Completed", student.getCompletedCourse().size()),
                new PieChart.Data("In Progress", student.getCurrentSemester().getCourses().size()),
                new PieChart.Data("Not Started", student.getDegree().getTotalCreditRequired()
                        - (student.getCompletedCourse().size() + student.getCurrentSemester().getCourses().size())));

        pieChart.setData(pieChartData);
        pieChart.setLegendVisible(true);
        pieChart.setLabelsVisible(false);
    }

    @SuppressWarnings("unchecked")
    private void setupStackedBarChart() {
        CategoryAxis yAxis = new CategoryAxis();
        NumberAxis xAxis = new NumberAxis();

        yAxis.getStyleClass().add("axis-label");
        xAxis.getStyleClass().add("axis-label");

        XYChart.Series<Number, String> series1 = new XYChart.Series<>();
        series1.setName("Category 1");
        series1.getData().add(new XYChart.Data<>(50, "Course 1"));
        series1.getData().add(new XYChart.Data<>(20, "Course 2"));
        series1.getData().add(new XYChart.Data<>(30, "Course 3"));
        series1.getData().add(new XYChart.Data<>(10, "Course 4"));

        XYChart.Series<Number, String> series2 = new XYChart.Series<>();
        series2.setName("Category 2");
        series2.getData().add(new XYChart.Data<>(50, "Course 1"));
        series2.getData().add(new XYChart.Data<>(80, "Course 2"));
        series2.getData().add(new XYChart.Data<>(70, "Course 3"));
        series2.getData().add(new XYChart.Data<>(90, "Course 4"));

        stackedProgress.getData().addAll(series1, series2);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        headerShadow.widthProperty().bind(stackPane.widthProperty());
        header.widthProperty().bind(stackPane.widthProperty());

        if (student == null) {
            System.out.println("Student is null");
        }

        setupPieChart();
        setupStackedBarChart();

        ADVISOR_EMAIL.setText(student.getAdvisor().getEmail());
        ADVISOR_ID.setText(student.getAdvisor().getID().toString());
        ADVISOR_NAME.setText(student.getAdvisor().getFirstName() + " " + student.getAdvisor().getLastName());

        STUDENT_EMAIL.setText(student.getEmail());
        STUDENT_ID.setText(student.getStudentID());
        STUDENT_NAME.setText(student.getFirstName() + " " + student.getLastName());

    }

}
