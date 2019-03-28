package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import frontend.gui.InputValidation;
import frontend.gui.Main;
import frontend.gui.StageSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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

    @FXML
    private Label line1;

    @FXML
    private Label line2;

    @FXML
    private Label line3;

    @FXML
    private Label signup;

    @FXML
    private Label goGreen;

    @FXML
    private JFXComboBox secQuestion;

    @FXML
    private JFXTextField secAnswer;

    ObservableList<String> secQuestions;

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
                        emailField, passwordField, confirmPasswordField, ageField, getSecurityQuestionID(secQuestion), secAnswer, mainPane);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        loginForward.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> StageSwitcher.sceneSwitch(
                Main.getPrimaryStage(), Main.getSignIn()
        ));
        try {
            setFonts();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPane.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                signupButton.fire();
            }
        });
        fillSecurityQuestions(secQuestion);
    }

    public int getSecurityQuestionID(JFXComboBox SQ) {
        for (int i = 0; i < 8; i++) {
            if (SQ.getValue().toString().equals(secQuestions.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private void fillSecurityQuestions(JFXComboBox secQuestion) {
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
     * Adds fonts to all labels.
     */
    public void setFonts() throws IOException {
        goGreen.setFont(Main.getReenieBeanie(100));
        line1.setFont(Main.getReenieBeanie(40));
        line2.setFont(Main.getReenieBeanie(40));
        line3.setFont(Main.getReenieBeanie(50));
        signup.setFont(Main.getRobotoThin(45));
        loginForward.setFont(Main.getRobotoThin(45));
        signupButton.setFont(Main.getRobotoThin(28));
    }

    public static ArrayList<JFXTextField> getFields() {
        return fields;
    }


}
