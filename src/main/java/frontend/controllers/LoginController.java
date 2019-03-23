package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import frontend.gui.InputValidation;
import frontend.gui.Main;
import frontend.gui.StageSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private AnchorPane graphics;

    @FXML
    private ImageView background;

    @FXML
    private Label signupForward;

    @FXML
    private Label goGreen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setOnAction(e -> {
            try {
                InputValidation.signInValidate(usernameField,
                        passwordField, mainPane);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            usernameField.setText(null);
            passwordField.setText(null);
        });

        background.fitWidthProperty().bind(graphics.widthProperty());
        background.fitHeightProperty().bind(graphics.heightProperty());
        signupForward.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> StageSwitcher.sceneSwitch(
                Main.getPrimaryStage(), Main.getSignUp()));
    }

    public static void setFonts() {

    }
}
