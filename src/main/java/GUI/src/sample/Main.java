package GUI.src.sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;

    private GridPane createRegistrationForm() {
        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }

    private void addUIControls(GridPane gridPane, int type) {
        Label headerLabel = new Label("Sign-up Form");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        if (type == 1) {
            // Add Email Label
            Label emailLabel = new Label("*Email ID : ");
            gridPane.add(emailLabel, 0, 1);

            // Add Email Text Field
            TextField emailField = new TextField();
            emailField.setPrefHeight(40);
            gridPane.add(emailField, 1, 1);

            // Add Password Label
            Label passwordLabel = new Label("*Password : ");
            gridPane.add(passwordLabel, 0, 2);

            // Add Password Field
            PasswordField passwordField = new PasswordField();
            passwordField.setPrefHeight(40);
            gridPane.add(passwordField, 1, 2);

            // Add First Name Label
            Label firstNameLabel = new Label("*First Name : ");
            gridPane.add(firstNameLabel, 0, 3);

            // Add First Name Text Field
            TextField firstNameField = new TextField();
            firstNameField.setPrefHeight(40);
            gridPane.add(firstNameField, 1, 3);

            // Add Last Name Label
            Label lastNameLabel = new Label("*Last Name : ");
            gridPane.add(lastNameLabel, 0, 4);

            // Add Last Name Text Field
            TextField lastNameField = new TextField();
            lastNameField.setPrefHeight(40);
            gridPane.add(lastNameField, 1, 4);

            // Add Age Label
            Label ageLabel = new Label("Age : ");
            gridPane.add(ageLabel, 0, 5);

            // Add Age Text Field
            TextField ageField = new TextField();
            ageField.setPrefHeight(40);
            gridPane.add(ageField, 1, 5);

            // Add Submit Button
            Button submitButton = new Button("Submit");
            submitButton.setPrefHeight(40);
            submitButton.setDefaultButton(true);
            submitButton.setPrefWidth(100);
            gridPane.add(submitButton, 0, 6, 2, 1);
            GridPane.setHalignment(submitButton, HPos.CENTER);
            GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));
        }
        if (type == 2) {
            headerLabel.setText("Login form");
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
            Button loginButton = new Button("Login");
            loginButton.setPrefHeight(40);
            loginButton.setDefaultButton(true);
            loginButton.setPrefWidth(100);
            gridPane.add(loginButton, 0, 3, 2, 1);
            GridPane.setHalignment(loginButton, HPos.CENTER);
            GridPane.setMargin(loginButton, new Insets(20, 0, 20, 0));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //setup main window
        window = primaryStage;
        window.setTitle("Login/Sign-up");

        //close button handling
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        //create borderPane for quick form setup
        BorderPane mainLayout = new BorderPane();

        //initialise forms
        GridPane signupForm = createRegistrationForm();
        GridPane loginForm = createRegistrationForm();

        //add the ui controls to the form
        addUIControls(signupForm, 1);
        addUIControls(loginForm, 2);

        //set top/right/bottom/center/left for main borderPane
        mainLayout.setRight(signupForm);
        mainLayout.setLeft(loginForm);

        //set final primary stage aka window
        Scene scene = new Scene(mainLayout, 300, 300);
        window.setScene(scene);
        window.setMaximized(true);
        window.show();
    }

    private void closeProgram(){
        boolean answer = ConfirmBox.display("Close request safety", "Are you sure you want to close this application?");
        if(answer){
            System.out.println("Contents are saved!(not true)");
            window.close();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
