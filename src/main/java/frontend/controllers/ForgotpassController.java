package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import frontend.gui.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import tools.Requests;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotpassController implements Initializable {

    @FXML
    private JFXComboBox secQuestion;

    @FXML
    private JFXTextField secAnswer;

    @FXML
    private JFXPasswordField newPassword;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXButton resetButton;

    private ObservableList<String> secQuestions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillSecurityQuestions();
        resetButton.setOnAction(e -> {
            if (validateEmpty()) {
                return;
            }

            boolean accepted = Requests.instance.forgotPass(emailField.getText(),
                    getSecurityQuestionid(), secAnswer.getText(), newPassword.getText());

            if (accepted) {
                Stage thisStage = (Stage) resetButton.getScene().getWindow();
                thisStage.close();
                try {
                    Dialog.show("Password Changed!", "We were able to change your password.",
                            "DISMISS", "sucess", false);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                return;
            } else {
                try {
                    Dialog.show("Oops...", "Something went wrong. Try again later",
                            "DISMISS", "error", false);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }


        });

    }

    private boolean validateEmpty() {
        if (secAnswer.getText().isEmpty()) {
            try {
                Dialog.show("Form Error!", "Please enter an answer",
                        "DISMISS", "error", false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return true;
        }
        if (secQuestion.getValue() == null) {
            try {
                Dialog.show("Form Error!", "Please select a security question",
                        "DISMISS", "error", false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return true;
        }
        if (newPassword.getText().isEmpty()) {
            try {
                Dialog.show("Form Error!", "Please enter a new password",
                        "DISMISS", "error", false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return true;
        }

        if (emailField.getText().isEmpty()) {
            try {
                Dialog.show("Form Error!", "Please enter your email",
                        "DISMISS", "error", false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private void fillSecurityQuestions() {
        secQuestions = FXCollections.observableArrayList(
                "What was your childhood nickname?",
                "In what city did you meet your spouse/significant other?",
                "What is the name of your favorite childhood friend?",
                "What street did you live on in third grade?",
                "What is your oldest sibling’s birthday month and year? (e.g., January 1900)",
                "What is the middle name of your youngest child?",
                "What is your oldest sibling's middle name?",
                "What school did you attend for sixth grade?"
        );
        secQuestion.setItems(secQuestions);

    }

    /**
     * Gets ID of the security question.
     *
     * @return - ID of the question
     */
    public int getSecurityQuestionid() {
        if (secQuestion.getValue() == null) {
            return -1;
        }
        for (int i = 0; i < 7; i++) {
            if (secQuestion.getValue().toString().equals(secQuestions.get(i))) {
                return i;
            }
        }
        return -1;
    }


}
