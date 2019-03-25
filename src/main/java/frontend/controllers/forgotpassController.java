package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import frontend.gui.Dialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class forgotpassController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXButton confirm;

    @FXML
    private JFXTextField securityQuestion;

    @FXML
    private JFXPasswordField newPassword;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        confirm.setOnAction(e -> {
            if (securityQuestion.getText().isEmpty()) {
                try {
                    Dialog.show(mainPane, "No input",
                            "You have not answered the security question",
                            "OK", "error");
                    securityQuestion.setUnFocusColor(Paint.valueOf("rgb(240, 80, 107)"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (newPassword.getText().isEmpty()) {
                try {
                    Dialog.show(mainPane, "No input",
                            "You have not answered the security question",
                            "OK", "error");
                    newPassword.setUnFocusColor(Paint.valueOf("rgb(240, 80, 107)"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            //send request to server to check if security question is correct
            //if it is, change the password and notify the user

        });

    }
}
