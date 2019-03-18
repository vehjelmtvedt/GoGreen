package frontend.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SignUp {
    private static final ArrayList<TextField> fields = new ArrayList<>();

    /**.
     * Creates scene for SignUp form
     * @return Scene for SignUp
     */
    public static Scene createScene() {
        BorderPane border = new BorderPane();
        border.setId("mainLayoutSignUp");
        GridPane signUpForm = SignIn.createForm();
        border.setCenter(signUpForm);

        Scene signUp = new Scene(border, General.getBounds()[0], General.getBounds()[1]);
        addUiControls(signUpForm, Main.getPrimaryStage(), Main.getSignIn());

        return signUp;
    }

    /**.
     * Adds user interface to SignUp form
     * @param grid gridPane containing SignUp form
     * @param currStage current Stage to go from
     * @param prevScene SignIn scene
     */
    private static void addUiControls(GridPane grid, Stage currStage, Scene prevScene) {
        Label headerLabel = new Label("Sign-up Form");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setPadding(new Insets(0, 80, 0, 0));
        headerLabel.setId("headerLabel");
        grid.add(headerLabel, 0, 0, 2, 1);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));


        Button signUpButton = new Button("Sign up");
        signUpButton.setId("signUpButton");
        signUpButton.setPrefHeight(40);
        signUpButton.setDefaultButton(true);
        signUpButton.setPrefWidth(100);

        Button signInButton = new Button("Sign in");
        signInButton.setId("signInButton");
        signInButton.setPrefHeight(40);
        signInButton.setDefaultButton(true);
        signInButton.setPrefWidth(100);

        HBox buttons = new HBox();
        buttons.setPadding(new Insets(10, 0, 0, 0));
        buttons.getChildren().addAll(signInButton, signUpButton);

        // Add First Name Label
        Label firstNameLabel = new Label("First Name : ");
        grid.add(firstNameLabel, 0, 1);

        // Add First Name Text Field
        TextField firstNameField = new TextField();
        firstNameField.setPrefHeight(40);
        firstNameField.setPromptText("First Name");
        grid.add(firstNameField, 1, 1);
        fields.add(firstNameField);

        // Add Last Name Label
        Label lastNameLabel = new Label("Last Name : ");
        grid.add(lastNameLabel, 0, 2);

        // Add Last Name Text Field
        TextField lastNameField = new TextField();
        lastNameField.setPrefHeight(40);
        lastNameField.setPromptText("Last Name");
        grid.add(lastNameField, 1, 2);
        fields.add(lastNameField);
        //Add Username Label
        Label usernameLabel = new Label("Username : ");
        grid.add(usernameLabel, 0, 3);

        //Add Username Text Field
        TextField usernameField = new TextField();
        usernameField.setPrefHeight(40);
        usernameField.setPromptText("Username");
        grid.add(usernameField, 1, 3);
        fields.add(usernameField);

        // Add Email Label
        Label emailLabel = new Label("Email ID : ");
        grid.add(emailLabel, 0, 4);

        // Add Email Text Field
        TextField emailField = new TextField();
        emailField.setPrefHeight(40);
        emailField.setPromptText("Email");
        grid.add(emailField, 1, 4);
        fields.add(emailField);

        // Add Password Label
        Label passwordLabel = new Label("Password : ");
        grid.add(passwordLabel, 0, 5);

        // Add Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        passwordField.setPromptText("New Password");
        grid.add(passwordField, 1, 5);
        fields.add(passwordField);

        // Add Password Confirm Label
        Label passReLabel = new Label("Re-Password : ");
        grid.add(passReLabel, 0, 6);

        // Add Password Confirm field
        PasswordField passReField = new PasswordField();
        passReField.setPrefHeight(40);
        passReField.setPromptText("Confirm Password");
        grid.add(passReField, 1, 6);
        fields.add(passReField);

        // Add Age Label
        Label ageLabel = new Label("Age : ");
        grid.add(ageLabel, 0, 7);

        // Add Age Text Field
        TextField ageField = new TextField();
        ageField.setPrefHeight(40);
        ageField.setPromptText("Your age");
        grid.add(ageField, 1, 7);
        fields.add(ageField);

        TextField[] nameFields = new TextField[2];
        nameFields[0] = firstNameField;
        nameFields[1] = lastNameField;

        //on clicking the sign up validate, first validate the user input.
        signUpButton.setOnAction(e ->
                InputValidation.signUpValidate(nameFields, usernameField,
                        emailField, passwordField, passReField, ageField, grid));

        //add scene switching from sign up page to sign in page
        StageSwitcher.buttonSwitch(signInButton, currStage, prevScene);

        grid.add(buttons, 1, 8, 2, 1);
    }

    public static ArrayList<TextField> getFields() {
        return fields;
    }
}
