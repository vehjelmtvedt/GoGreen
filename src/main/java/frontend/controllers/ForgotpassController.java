package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import frontend.gui.Dialog;
import frontend.gui.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotpassController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXButton confirm;

    @FXML
    private JFXTextField securityQuestion;

    @FXML
    private JFXPasswordField newPassword;

    @FXML
    private Label forgotLabel;

    @FXML
    private Label questionLabel;

    @FXML
    private Label newPassLabel;

    @FXML
    private JFXButton dismiss;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        confirm.setOnAction(e -> {
            if (securityQuestion.getText().isEmpty()) {
                try {
                    Dialog.show("No input",
                            "You have not answered the security question",
                            "OK", "error", false);
                    securityQuestion.setUnFocusColor(Paint.valueOf("rgb(240, 80, 107)"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (newPassword.getText().isEmpty()) {
                try {
                    Dialog.show("No input",
                            "You have not entered a new password",
                            "OK", "error", false);
                    newPassword.setUnFocusColor(Paint.valueOf("rgb(240, 80, 107)"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            //send request to server to check if security question is correct
            //if it is, change the password and notify the user

        });
        try {
            setFonts();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dismiss.setOnAction(e -> {
            Stage stage = (Stage) dismiss.getScene().getWindow();
            stage.setOpacity(0.7);
            stage.close();
        });
    }

    /**
     * Set fonts for every label in scene.
     * @throws IOException - if fails to load the fonts.
     */
    public void setFonts() throws IOException {
        forgotLabel.setFont(Main.getRobotoThin(39.0));
        questionLabel.setFont(Main.getRobotoThin(17.0));
        newPassLabel.setFont(Main.getRobotoThin(17.0));
    }
}
