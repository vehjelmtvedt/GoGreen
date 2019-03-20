package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import frontend.gui.Events;
import frontend.gui.InputValidation;
import frontend.gui.Main;
import frontend.gui.StageSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //add hover events for different elements
        Events.addButtonCursor(loginButton);
        Events.addLabelCursor(signupForward);
        Events.addInputTextCursor(usernameField);
        Events.addInputPassCursor(passwordField);

        loginButton.setOnAction(e -> {
            InputValidation.signInValidate(usernameField,
                    passwordField, mainPane);
            usernameField.setText(null);
            passwordField.setText(null);
        });
        background.fitWidthProperty().bind(graphics.widthProperty());
        background.fitHeightProperty().bind(graphics.heightProperty());
        signupForward.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> StageSwitcher.sceneSwitch(
                Main.getPrimaryStage(), Main.getSignUp()));

    }
}
