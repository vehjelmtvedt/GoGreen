package frontend;

import backend.data.LoginDetails;
import backend.data.User;
import frontend.controllers.ActivitiesController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
    private static final String passPattern =
            "((?=.*[a-z]).{6,15})";
    private static final String emailPattern =
            "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+";


    /**
     * .
     * Validation for signing in
     *
     * @param emailField email input field
     * @param passField  password input field
     * @param form       form containing input fields
     */
    public static void signInValidate(TextField emailField,
                                      PasswordField passField, GridPane form) {

        LoginDetails loginDetails = new LoginDetails(emailField.getText(), passField.getText());

        User loggedUser = Requests.loginRequest(loginDetails);
        System.out.println(loggedUser);
        if (loggedUser != null) {
            General.showAlert(Alert.AlertType.CONFIRMATION,
                    form.getScene().getWindow(), "Login successful",
                    "Welcome to GoGreen, " + loggedUser.getFirstName() + " "
                            + loggedUser.getLastName() + "!");
            ActivitiesController.setUser(loggedUser);

            //testing
            try {
                FXMLLoader loader1 = new FXMLLoader(
                        Main.class.getResource("/frontend/fxmlPages/Homepage.fxml"));
                FXMLLoader loader2 = new FXMLLoader(
                        Main.class.getResource("/frontend/fxmlPages/Activities.fxml"));
                FXMLLoader loader3 = new FXMLLoader(
                        Main.class.getResource("/frontend/fxmlPages/FriendPage.fxml"));
                Parent root1 = loader1.load();
                Parent root2 = loader2.load();
                Parent root3 = loader3.load();
                Scene homepage = new Scene(root1, General.getBounds()[0], General.getBounds()[1]);
                Scene activities = new Scene(root2, General.getBounds()[0], General.getBounds()[1]);
                Scene friendPage = new Scene(root3, General.getBounds()[0], General.getBounds()[1]);

                //setup scenes
                Main.setActivities(activities);
                Main.setHomepage(homepage);
                Main.setFriendPage(friendPage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //testing

            General.resetFields(SignIn.getFields());

            StageSwitcher.loginSwitch(Main.getPrimaryStage(), Main.getHomepage(), loggedUser);

        } else {
            General.showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Login failed", "Incorrect credentials. Try again");
        }
    }

    /**
     * .
     * Validation for input in sign up form
     *
     * @param nameFields    array of fields containing the user's name
     * @param usernameField User's username name field
     * @param emailField    User's email field
     * @param passField     User's password field
     * @param passReField   User's re-password field
     * @param ageField      User's age field
     * @param form          Form containing input fields
     */
    public static void signUpValidate(TextField[] nameFields,
                                      TextField usernameField, TextField emailField,
                                      PasswordField passField, PasswordField passReField,
                                      TextField ageField, GridPane form) {

        if (!signUpValidateFields(nameFields, usernameField, form)) {
            return;
        }
        if (!signUpValidatePass(emailField, passField, passReField, ageField, form)) {
            return;
        }

        //send requests to the server to see if username and password already exist
        //before proceeding to the questionnaire page
        String username = usernameField.getText();
        String email = emailField.getText();

        if (Requests.validateUserRequest(username)) {
            General.showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Username Error!", "A user already exists with this username."
                            + "Use another username");
            return;
        }

        if (Requests.validateUserRequest(email)) {
            General.showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Email Error!", "A user already exists with this email."
                            + "Use another email");
            return;
        }

        User user = new User(nameFields[0].getText(),
                nameFields[1].getText(),
                Integer.parseInt(ageField.getText()), emailField.getText(),
                usernameField.getText(), passField.getText());

        General.resetFields(SignUp.getFields());
        StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Questionnaire.createScene(user, form));
    }

    private static boolean validateAge(TextField input) {
        try {
            int age = Integer.parseInt(input.getText());
            return age >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean signUpValidateFields(TextField[] nameFields,
                                                TextField usernameField, GridPane form) {
        if (nameFields[0].getText().isEmpty()) {
            General.showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter your First Name");
            return false;
        }
        if (nameFields[1].getText().isEmpty()) {
            General.showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter your Last Name");
            return false;
        }
        if (usernameField.getText().isEmpty()) {
            General.showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter a username");
            return false;
        }
        return true;
    }

    private static boolean signUpValidatePass(TextField emailField,
                                              PasswordField passField, PasswordField passReField,
                                              TextField ageField, GridPane form) {
        if (emailField.getText().isEmpty() || !validateEmail(emailField)) {
            General.showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter a valid email");
            return false;
        }

        if (passField.getText().isEmpty() || !validatePassword(passField)) {
            General.showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter a valid password");
            return false;
        }
        if (passReField.getText().isEmpty() || !passReField.getText().equals(passField.getText())) {
            General.showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Passwords do not match");
            return false;
        }
        if (ageField.getText().isEmpty() || !validateAge(ageField)) {
            General.showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter a valid age number");
            return false;
        }
        return true;
    }

    private static boolean validatePassword(TextField input) {
        String pass = input.getText();
        Pattern pattern = Pattern.compile(passPattern);
        Matcher matcher = pattern.matcher(pass);
        if (matcher.matches()) {
            System.out.println("Password is: " + pass);
            return true;
        }
        return false;
    }

    private static boolean validateEmail(TextField input) {
        String email = input.getText();
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            System.out.println("Email is: " + email);
            return true;
        }
        return false;
    }
}