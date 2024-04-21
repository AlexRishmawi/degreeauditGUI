package controllers;

import java.io.IOException;

import aisle.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DegreeWork;

public class SignupController {

    @FXML
    private HBox Hbox;

    @FXML
    private HBox HboxLeft;

    @FXML
    private Button backButton;

    @FXML
    private ImageView background;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField firstName;

    @FXML
    private Text goToLogIn;

    @FXML
    private CheckBox isAdvisorCheckbox;

    @FXML
    private TextField lastName;

    @FXML
    private Label login_error;

    @FXML
    private ImageView logo;

    @FXML
    private AnchorPane parent;

    @FXML
    private Button signedUp;

    @FXML
    private StackPane stackPane;

    @FXML
    private StackPane stackPaneLeft;

    @FXML
    private TextField uscID;

    @FXML
    private TextField userEmail;

    @FXML
    private PasswordField userPassword;

    @FXML
    private VBox vboxLoginInfo;

    @FXML
    private Text welcome;

    @FXML
    private Text welcome1;

    @FXML
    private Label signup_error;

    @FXML
    void goBack(ActionEvent event) throws IOException{
        App.setRoot("landing_page");
    }

    @FXML
    void goToLoginPage(MouseEvent event) throws IOException{
        App.setRoot("login_page");
    }

    @FXML
    void signupClicked(ActionEvent event) throws IOException {
        String userfirstName = firstName.getText();
        String userlastName = lastName.getText();
        String email = userEmail.getText();
        String password = userPassword.getText();
        String userconfirmPassword = confirmPassword.getText();
        String studentID = uscID.getText();
        boolean isAdvisor = isAdvisorCheckbox.isSelected();

        if(userfirstName.equals("") || userlastName.equals("") || email.equals("")
            || password.equals("") || studentID.equals("")) {
            signup_error.setText("Not blank, Bruh!");
            return;
        }

        if(!password.equals(userconfirmPassword)) {
            signup_error.setText("Confirm password must be same, Bruh!");
            return;
        }

        DegreeWork degreeWork = DegreeWork.getInstance();

        if(!degreeWork.signup(userfirstName, userlastName, email, password, studentID, isAdvisor)) {
            signup_error.setText("Account already exists.");
            return;
        }

        if(studentID.chars().toArray().length != 9) {
            signup_error.setText("Student ID must be 9 digits.");
            return;
        }

        signup_error.setText(" ");
        App.setRoot("student_dashboard_page");
    }
}   