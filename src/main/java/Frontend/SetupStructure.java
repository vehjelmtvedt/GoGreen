package Frontend;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SetupStructure {
    public static GridPane createRegistrationForm() {
        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, 250);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, 250);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        return gridPane;
    }

    public static void addUIControls(GridPane gridPane, int type, Stage currStage, Scene nextScene) {
        Label headerLabel = new Label("Sign-up Form");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setId("headerLabel");
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        if (type == 1) {
            //add header label

            // Add First Name Label
            Label firstNameLabel = new Label("First Name : ");
            gridPane.add(firstNameLabel, 0, 1);

            // Add First Name Text Field
            TextField firstNameField = new TextField();
            firstNameField.setPrefHeight(40);
            firstNameField.setPromptText("First Name");
            gridPane.add(firstNameField, 1, 1);

            // Add Last Name Label
            Label lastNameLabel = new Label("Last Name : ");
            gridPane.add(lastNameLabel, 0, 2);

            // Add Last Name Text Field
            TextField lastNameField = new TextField();
            lastNameField.setPrefHeight(40);
            lastNameField.setPromptText("Last Name");
            gridPane.add(lastNameField, 1, 2);

            // Add Email Label
            Label emailLabel = new Label("Email ID : ");
            gridPane.add(emailLabel, 0, 3);

            // Add Email Text Field
            TextField emailField = new TextField();
            emailField.setPrefHeight(40);
            emailField.setPromptText("Email");
            gridPane.add(emailField, 1, 3);

            // Add Password Label
            Label passwordLabel = new Label("Password : ");
            gridPane.add(passwordLabel, 0, 4);

            // Add Password Field
            PasswordField passwordField = new PasswordField();
            passwordField.setPrefHeight(40);
            passwordField.setPromptText("New Password");
            gridPane.add(passwordField, 1, 4);

            // Add Age Label
            Label ageLabel = new Label("Age : ");
            gridPane.add(ageLabel, 0, 5);

            // Add Age Text Field
            TextField ageField = new TextField();
            ageField.setPrefHeight(40);
            ageField.setPromptText("Your age");
            gridPane.add(ageField, 1, 5);

            // Add Submit Button
            Button signUpButton = new Button("Sign up");
            signUpButton.setId("submitButton");
            signUpButton.setPrefHeight(40);
            signUpButton.setDefaultButton(true);
            signUpButton.setPrefWidth(100);
            signUpButton.setOnAction(e -> {
                InputValidation.submitValidate(firstNameField, lastNameField, emailField, passwordField, ageField, gridPane);
            });

            Button signInButton = new Button("Sign in");
            signInButton.setId("signInButton");
            signInButton.setPrefHeight(40);
            signInButton.setDefaultButton(true);
            signInButton.setPrefWidth(100);

            HBox buttons = new HBox();
            buttons.getChildren().addAll(signInButton, signUpButton);
            gridPane.add(buttons, 1, 6, 2, 1);
        }
        if (type == 2) {
            //reset login label
            headerLabel.setText("User login");
            // Add Email Label
            Label emailLabel = new Label("Email : ");
            gridPane.add(emailLabel, 0, 1);

            // Add Email Text Field
            TextField emailField = new TextField();
            emailField.setPrefHeight(40);
            gridPane.add(emailField, 1, 1);

            // Add Password Label
            Label passwordLabel = new Label("Password : ");
            gridPane.add(passwordLabel, 0, 2);

            // Add Password Field
            PasswordField passwordField = new PasswordField();
            passwordField.setPrefHeight(40);
            gridPane.add(passwordField, 1, 2);

            // Add Submit Button
            Button signInButton = new Button("Sign in");
            Button signUpButton = new Button("Sign up");
            signInButton.setId("signInButton");
            signInButton.setPrefHeight(40);
            signInButton.setDefaultButton(true);
            signInButton.setPrefWidth(100);

            signUpButton.setId("signUpButton");
            signUpButton.setPrefHeight(40);
            signUpButton.setDefaultButton(true);
            signUpButton.setPrefWidth(100);

            StageSwitcher.buttonSwitch(signUpButton, currStage, nextScene);

            HBox buttons = new HBox();
            buttons.getChildren().addAll(signInButton, signUpButton);
            gridPane.add(buttons, 1, 3, 1, 1);
            GridPane.setHalignment(buttons, HPos.CENTER);
            GridPane.setMargin(buttons, new Insets(20, 0, 20, 0));
        }
    }
}
