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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    private static final ArrayList<JFXTextField> fields = new ArrayList<>();

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXPasswordField confirmPasswordField;

    @FXML
    private JFXTextField ageField;

    @FXML
    private JFXButton signupButton;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label loginForward;

    @FXML
    private AnchorPane graphics;

    @FXML
    private ImageView background;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXTextField[] nameFields = new JFXTextField[2];
        nameFields[0] = firstNameField;
        nameFields[1] = lastNameField;
        background.fitWidthProperty().bind(graphics.widthProperty());
        background.fitHeightProperty().bind(graphics.heightProperty());
        signupButton.setOnAction(e -> {
            try {
                InputValidation.signUpValidate(nameFields, usernameField,
                        emailField, passwordField, confirmPasswordField, ageField, mainPane);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        loginForward.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> StageSwitcher.sceneSwitch(
                Main.getPrimaryStage(), Main.getSignIn()
        ));
    }

    public static ArrayList<JFXTextField> getFields() {
        return fields;
    }


}
