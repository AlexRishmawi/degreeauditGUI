package controllers;

import aisle.App;

import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
import model.Student;
import model.Advisor;

public class LoginController implements Initializable{

    @FXML
    private HBox Hbox;

    @FXML
    private HBox HboxLeft;

    @FXML
    private Button backButton;

    @FXML
    private Text goToSignUp;

    @FXML
    private Button loggedin;

    @FXML
    private Label login_error;

    @FXML
    private ImageView logo;

    @FXML
    private AnchorPane parent;

    @FXML
    private StackPane stackPane;

    @FXML
    private StackPane stackPaneLeft;

    @FXML
    private TextField txt_userEmail;

    @FXML
    private PasswordField txt_userPassword;

    @FXML
    private VBox vboxLoginInfo;

    @FXML
    private Text welcome;

    @FXML
    private Text welcome1;

    @FXML
    private ImageView background;

    @FXML
    private StackPane stackBackground;

    @FXML
    void goBack(ActionEvent event) throws IOException{
        App.setRoot("landing_page");
    }

    @FXML
    void goToSignUpPage(MouseEvent event) throws IOException{
        App.setRoot("signup_page");
    }
    @FXML
    void gotoForgetPasswordPage(MouseEvent event) throws IOException{
        App.setRoot("forgot_password");
    }


    @FXML
    void loginClicked(ActionEvent event) throws IOException{
        String userEmail = txt_userEmail.getText();
        String password = txt_userPassword.getText();

        DegreeWork degreeWork = DegreeWork.getInstance();
        if(!degreeWork.login(userEmail, password)) {
            login_error.setText("Invalid login credentials.");  
            return;
        } else {
            login_error.setText(" ");
        }

        if(degreeWork.getCurrentUser() instanceof Student) {
            App.setRoot("student_dashboard_page");
        } else if(degreeWork.getCurrentUser() instanceof Advisor) {
            App.setRoot("advisor_search_page");
        }
    }

    private void sizes () {
        HboxLeft.prefWidthProperty().bind(parent.widthProperty().multiply(0.45));
        stackPaneLeft.prefWidthProperty().bind(parent.widthProperty().multiply(0.45));
        Hbox.prefWidthProperty().bind(parent.widthProperty().multiply(0.55));
        stackPane.prefWidthProperty().bind(parent.widthProperty().multiply(0.55));
        logo.fitWidthProperty().bind(stackPaneLeft.widthProperty().multiply(0.65));
        logo.fitHeightProperty().bind(stackPaneLeft.heightProperty().multiply(0.65));

        vboxLoginInfo.prefWidthProperty().bind(stackPane.widthProperty().multiply(0.45));

        stackBackground.prefHeightProperty().bind(parent.heightProperty().multiply(1));
        stackBackground.prefWidthProperty().bind(parent.widthProperty().multiply(1));
        background.fitWidthProperty().bind(stackBackground.widthProperty().multiply(1));
        background.fitHeightProperty().bind(stackBackground.heightProperty().multiply(1));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sizes();
    }
}