package frontend;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class SignIn {
    private static Button signUpButton = new Button();
    private static final ArrayList<TextField> fields = new ArrayList<>();

    /**.
     * Creates scene for SignIn page
     * @return scene for SignIn
     */
    public static Scene createScene() {
        BorderPane border = new BorderPane();
        border.setId("mainLayoutSignIn");
        GridPane signInForm = createForm();
        border.setCenter(signInForm);

        Scene signIn = new Scene(border, General.getBounds()[0], General.getBounds()[1]);
        addUiControls(signInForm);

        return signIn;
    }

    /**.
     * Creates GridPane for SignIn form
     * @return GridPane form
     */
    public static GridPane createForm() {
        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, 400);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, 250);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        return gridPane;
    }

    /**.
     * Add User Interface to the SignIn form
     * @param grid GridPane containing form
     */
    private static void addUiControls(GridPane grid) {
        Label headerLabel = new Label("User login");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setPadding(new Insets(0, 100, 0, 0));
        headerLabel.setId("headerLabel");
        grid.add(headerLabel, 0, 0, 2, 1);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));


        signUpButton = new Button("Sign up");
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

        Label emailLabel = new Label("Email or\nUsername : ");
        grid.add(emailLabel, 0, 1);

        // Add Email Text Field
        TextField emailField = new TextField();
        emailField.setPrefHeight(40);
        grid.add(emailField, 1, 1);

        fields.add(emailField);

        // Add Password Label
        Label passwordLabel = new Label("Password : ");
        grid.add(passwordLabel, 0, 2);

        // Add Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        grid.add(passwordField, 1, 2);

        fields.add(passwordField);
        signInButton.setOnAction(e ->
                InputValidation.signInValidate(emailField, passwordField, grid));

        grid.add(buttons, 1, 3, 1, 1);
        GridPane.setHalignment(buttons, HPos.CENTER);
        GridPane.setMargin(buttons, new Insets(20, 0, 20, 0));
    }

    public static Button getSignUpButton() {
        return signUpButton;
    }

    public static ArrayList<TextField> getFields() {
        return fields;
    }
}
