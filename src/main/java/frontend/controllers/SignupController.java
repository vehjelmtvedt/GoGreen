package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import frontend.gui.Events;
import frontend.gui.InputValidation;
import frontend.gui.Main;
import frontend.gui.StageSwitcher;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import tools.Requests;

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
    private Label line4;

    @FXML
    private Label line5;

    @FXML
    private Label signup;

    @FXML
    private Label goGreen;

    @FXML
    private Label lblSaved;

    @FXML
    private Label lblTotalUsers;

    @FXML
    private JFXComboBox secQuestion;

    @FXML
    private JFXTextField secAnswer;

    private ObservableList<String> secQuestions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblSaved.setText(Math.floor(Requests.instance.getTotalCO2Saved()) + " KG");
        lblTotalUsers.setText(Requests.instance.getTotalUsers() + " Users");

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10000), ae -> {
            lblSaved.setText(Math.floor(Requests.instance.getTotalCO2Saved()) + " KG");
            lblTotalUsers.setText(Requests.instance.getTotalUsers() + " Users");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        //create arrays for fields to clean up code usage in Input Validation
        JFXTextField[] nameFields = new JFXTextField[2]; //first & last name
        JFXPasswordField[] passFields = new JFXPasswordField[2]; // passwords
        nameFields[0] = firstNameField;
        nameFields[1] = lastNameField;
        passFields[0] = passwordField;
        passFields[1] = confirmPasswordField;

        JFXTextField[] primaryFields = new JFXTextField[2]; //email && username
        primaryFields[0] = emailField;
        primaryFields[1] = usernameField;

        background.fitWidthProperty().bind(graphics.widthProperty());
        background.fitHeightProperty().bind(graphics.heightProperty());
        signupButton.setOnAction(e -> {
            try {
                boolean succeeded = InputValidation.signUpValidate(nameFields, primaryFields,
                        passFields, ageField, getSecurityQuestionid(), secAnswer);

                //Reset fields if everything went alright
                if (succeeded) {
                    firstNameField.setText(null);
                    lastNameField.setText(null);
                    emailField.setText(null);
                    ageField.setText(null);
                    usernameField.setText(null);
                    passwordField.setText(null);
                    confirmPasswordField.setText(null);
                    secAnswer.setText(null);
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        loginForward.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            StageSwitcher.signInUpSwitch(Main.getPrimaryStage(), Main.getSignIn());
        });
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

        //add required events
        Events.addLoginHover(signupButton);
    }

    /**
     * Gets the security question ID.
     *
     * @return - the ID of the question
     */
    public int getSecurityQuestionid() {
        if (secQuestion.getValue() == null) {
            return -1;
        }
        for (int i = 0; i < 8; i++) {
            if (secQuestion.getValue().toString().equals(secQuestions.get(i))) {
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
                "What is your oldest sibling’s birthday month and year?"
                        + " (e.g., January 1900)",
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
        line4.setFont(Main.getReenieBeanie(40));
        line5.setFont(Main.getReenieBeanie(40));
        lblSaved.setFont(Main.getReenieBeanie(30));
        lblTotalUsers.setFont(Main.getReenieBeanie(30));
        signup.setFont(Main.getRobotoThin(45));
        loginForward.setFont(Main.getRobotoThin(45));
        signupButton.setFont(Main.getRobotoThin(28));
    }

    public static ArrayList<JFXTextField> getFields() {
        return fields;
    }


}
