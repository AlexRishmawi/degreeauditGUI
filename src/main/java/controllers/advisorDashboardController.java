package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import aisle.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import model.Course;
import model.DegreeWork;
import model.ElectiveCategory;
import model.Student;
import model.Advisor;

public class AdvisorDashboardController implements Initializable{

    @FXML
    private Label ID;

    @FXML
    private Label advisorEmail;

    @FXML
    private Label advisorName;

    @FXML
    private Button appointmentButton;

    @FXML
    private Label collegeLabel;

    @FXML
    private Label degreeLabel;

    @FXML
    private Label email;

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
    private StackedBarChart<Number, String> stackedProgress;

    @FXML
    private Label studentName;

    @FXML
    private VBox degreeProgress;
    
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

    @FXML
    void notSetupClicked(ActionEvent event) throws IOException {
        App.setRoot("appointment_page");
    }
    
    DegreeWork degreeWork = DegreeWork.getInstance();
        
    Student student = degreeWork.getCurrentUser().isStudent() ? (Student) degreeWork.getCurrentUser() : ((Advisor) degreeWork.getCurrentUser()).getCurrentStudent();

    private void setupPieChart() {
    
        ObservableList <PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Completed", student.getCompletedCourse().size()),
            new PieChart.Data("In Progress", student.getCurrentSemester().getCourses().size()),
            new PieChart.Data("Not Started", student.getDegree().getTotalCreditRequired() - (student.getCompletedCourse().size() + student.getCurrentSemester().getCourses().size()))
        );

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
        if(student instanceof Student) {
            headerShadow.widthProperty().bind(stackPane.widthProperty());
            header.widthProperty().bind(stackPane.widthProperty());
            degreeProgress.setFillWidth(true);

            if(student == null) {
                System.out.println("Student is null");
                return;
            }

            setupPieChart();
            setupStackedBarChart();

            studentName.setText(student.getFirstName() + " " + student.getLastName());
            level.setText(student.getLevel().toString());
            email.setText(student.getEmail());
            ID.setText(student.getStudentID());

            degreeLabel.setText(student.getDegree().getSubject());
            majorLabel.setText(student.getDegree().toString());

            advisorName.setText(student.getAdvisor().getFirstName() + " " + student.getAdvisor().getLastName());
            advisorEmail.setText(student.getAdvisor().getEmail());

            //Degree Progress
        
            //Major
            degreeProgress.getChildren().clear();
            HashMap<Course, Integer> majorCourses = ((Student) degreeWork.getCurrentUser()).getDegree().getMajorCourses();
            
            VBox sectionBox = new VBox(10);
            sectionBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5;");
            sectionBox.setPadding(new Insets(5, 10, 5, 10)); // Apply padding inside the HBox
            sectionBox.setPrefWidth(Double.MAX_VALUE); // Ensure HBox stretches to full width
            sectionBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            Label sectionTitle = new Label("Major in " + student.getDegree().getSubject());
            Line separater = new Line();

            for(Course course : majorCourses.keySet()) {
                HBox courseBox = new HBox(5);
                if(student.getCompletedCourse().containsKey(course)) {
                    courseBox.setStyle("-fx-background-color: green; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5;");
                } else {
                    courseBox.setStyle("-fx-background-color: red; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5;");
                }

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                
                Label courseName = new Label(course.toStringCourseAbbr() + ": " + course.getCourseName());
                Label courseCredits = new Label(Integer.toString(course.getCreditHours()));

                courseBox.getChildren().addAll(courseName, spacer, courseCredits);
                sectionBox.getChildren().addAll(courseBox);
            }

            VBox.setMargin(sectionBox, new Insets(5));

            //Electives
            VBox otherBox = new VBox(10);
            otherBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5;");
            otherBox.setPadding(new Insets(5, 10, 5, 10)); // Apply padding inside the HBox
            otherBox.setPrefWidth(Double.MAX_VALUE); // Ensure HBox stretches to full width
            otherBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            Label otherTitle = new Label("Other Requirements");
            
            for(ElectiveCategory elective : student.getDegree().getElectiveList()) {
                HBox electiveBox = new HBox(5);
                
                if(degreeWork.electiveCategoryCompleted(elective)) {
                    electiveBox.setStyle("-fx-background-color: green; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5;");
                } else {
                    electiveBox.setStyle("-fx-background-color: red; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5;");
                }

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                
                Label electiveName = new Label(elective.getType());
                Label electiveCredits = new Label(Integer.toString(elective.getCreditsRequired()));

                electiveBox.getChildren().addAll(electiveName, spacer, electiveCredits);
                otherBox.getChildren().addAll(electiveBox);
            }

            degreeProgress.getChildren().addAll(sectionTitle, separater, sectionBox, new Line(), otherTitle, new Line(), otherBox);

        } else {
            return;
        }
    }
}
