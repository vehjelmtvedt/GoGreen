package frontend;

import backend.data.LoginDetails;
import backend.data.User;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
    private static final String passPattern =
            "((?=.*[a-z]).{6,15})";
    private static final String emailPattern =
            "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+";

    /**
     * .
     *
     * @param emailField email field
     * @param passField  password field
     * @param form       form where fields are taken from
     * @param stage      current stage
     */
    public static void signInValidate(TextField emailField,
                                      PasswordField passField, GridPane form, Stage stage) {

        LoginDetails loginDetails = new LoginDetails(emailField.getText(), passField.getText());

        String response = Requests.sendRequest(1, loginDetails, new User());
        System.out.println(response);
        if (response != null && !response.isEmpty()) {
            showAlert(Alert.AlertType.CONFIRMATION, form.getScene().getWindow(), "Login successful",
                    "Welcome to GoGreen, " + response);
            SetupStructure.resetFields(null, null, null, emailField, passField, null, null);

            StageSwitcher.loginSwitch(stage, Homepage.setHomepage());

        } else {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Login failed", "Incorrect credentials. Try again");
        }
    }

    /**
     * .
     *
     * @param firstNameField first name field
     * @param lastNameField  last name field
     * @param emailField     email field
     * @param passField      password field
     * @param ageField       age field
     * @param form           form where fields are at
     * @param stage          current stage
     */
    public static void signUpValidate(TextField firstNameField, TextField lastNameField,
                                      TextField usernameField, TextField emailField,
                                      PasswordField passField, PasswordField passReField,
                                      TextField ageField, GridPane form, Stage stage) {
        if (firstNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter your First Name");
            return;
        }
        if (lastNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter your Last Name");
            return;
        }
        if (usernameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter a username");
            return;
        }
        if (emailField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter your email");
            return;
        }
        if (validateEmail(emailField)) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter a valid email");
            return;
        }
        if (passField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter a password");
            return;
        }
        if (passReField.getText().isEmpty() || !passReField.getText().equals(passField.getText())) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Passwords do not match");
            return;
        }
        if (!validatePassword(passField)) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter a valid password");
            return;
        }
        if (ageField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter your age");
            return;
        }
        if (!validateAge(ageField)) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter a valid age number");
            return;
        }

        User user = new User(firstNameField.getText(),
                lastNameField.getText(),
                Integer.parseInt(ageField.getText()), usernameField.getText(),
                emailField.getText(), passField.getText());

        String response = Requests.sendRequest(2, new LoginDetails(), user);

        if (response != null) {
            if (response.equals("success")) {
                showAlert(Alert.AlertType.CONFIRMATION, form.getScene().getWindow(),
                        "Registration Successful!",
                        "Go to login screen and enter your new credentials!");
                SetupStructure.resetFields(firstNameField, lastNameField, usernameField,
                        emailField, passField, passReField, ageField);
            } else if (response.equals("username exists")) {
                showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                        "Email Error!", "An user already exists with this username."
                                + "Use another username");
            } else {
                showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                        "Email Error!", "An user already exists with this email."
                                + "Use another email");
            }
        }
    }

    private static boolean validateAge(TextField input) {
        try {
            int age = Integer.parseInt(input.getText());
            if (age >= 0) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
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
            return false;
        }
        return true;
    }

    private static void showAlert(Alert.AlertType alertType,
                                  Window window, String title, String message) {
        Alert alert = new Alert(alertType);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Main.getCss());
        dialogPane.setId("alertDialog");
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(window);
        alert.show();
    }
}
