package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

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
    private JFXButton resetButton;

    ObservableList<String> secQuestions;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillSecurityQuestions();
        resetButton.setOnAction(e -> {
            //Send request to request password change here
            Stage thisStage = (Stage) resetButton.getScene().getWindow();
            thisStage.close();
        });

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

    public int getSecurityQuestionID() {
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
