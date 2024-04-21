package controllers;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import aisle.App;

public class LandingController implements Initializable {

    @FXML
    private HBox Hbox;

    @FXML
    private HBox HboxLeft;

    @FXML
    private ImageView logo;

    @FXML
    private AnchorPane parent;

    @FXML
    private StackPane stackPane;

    @FXML
    private StackPane stackPaneLeft;

    @FXML
    private Button tologin;

    @FXML
    private Button tosignup;

    @FXML
    private Text welcome;

    @FXML
    private StackPane stackBackground;

    @FXML
    private ImageView background;
    
    @FXML
    private void onLoginClicked(ActionEvent event) throws IOException {
        App.setRoot("login_page");
    }

    @FXML
    private void onSignupClicked(ActionEvent event) throws IOException {
        App.setRoot("signup_page");
    }

    private void sizes() {
        HboxLeft.prefWidthProperty().bind(parent.widthProperty().multiply(0.45));
        stackPaneLeft.prefWidthProperty().bind(parent.widthProperty().multiply(0.45));
        Hbox.prefWidthProperty().bind(parent.widthProperty().multiply(0.55));
        stackPane.prefWidthProperty().bind(parent.widthProperty().multiply(0.55));
        logo.fitWidthProperty().bind(stackPaneLeft.widthProperty().multiply(0.65));
        logo.fitHeightProperty().bind(stackPaneLeft.heightProperty().multiply(0.65));

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
