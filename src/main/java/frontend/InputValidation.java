package frontend;

import backend.data.LoginDetails;
import backend.data.User;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
     * Validation for signing in
     *
     * @param emailField email input field
     * @param passField  password input field
     * @param form       form containing input fields
     */
    public static void signInValidate(TextField emailField,
                                      PasswordField passField, GridPane form) {

        LoginDetails loginDetails = new LoginDetails(emailField.getText(), passField.getText());

        String response = Requests.loginRequest(loginDetails);
        System.out.println(response);
        if (response != null && !response.isEmpty()) {
            showAlert(Alert.AlertType.CONFIRMATION, form.getScene().getWindow(), "Login successful",
                    "Welcome to GoGreen, " + response);
            General.resetFields(SignIn.getFields());
            StageSwitcher.loginSwitch(Main.getPrimaryStage(), Main.getHomepage());

        } else {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
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

        User user = new User(nameFields[0].getText(),
                nameFields[1].getText(),
                Integer.parseInt(ageField.getText()), emailField.getText(),
                usernameField.getText(), passField.getText());

        String response = Requests.signupRequest(user);

        if (response != null) {
            if (response.equals("success")) {
                showAlert(Alert.AlertType.CONFIRMATION, form.getScene().getWindow(),
                        "Registration Successful!",
                        "Go to login screen and enter your new credentials!");
                General.resetFields(SignUp.getFields());
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
            return age >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean signUpValidateFields(TextField[] nameFields,
                                                TextField usernameField, GridPane form) {
        if (nameFields[0].getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter your First Name");
            return false;
        }
        if (nameFields[1].getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter your Last Name");
            return false;
        }
        if (usernameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter a username");
            return false;
        }
        return true;
    }

    private static boolean signUpValidatePass(TextField emailField,
                                              PasswordField passField, PasswordField passReField,
                                              TextField ageField, GridPane form) {
        if (emailField.getText().isEmpty() || !validateEmail(emailField)) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter a valid email");
            return false;
        }
        if (passField.getText().isEmpty() || !validatePassword(passField)) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter a valid password");
            return false;
        }
        if (passReField.getText().isEmpty() || !passReField.getText().equals(passField.getText())) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Passwords do not match");
            return false;
        }
        if (ageField.getText().isEmpty() || !validateAge(ageField)) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
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

    private static void showAlert(Alert.AlertType alertType,
                                  Window window, String title, String message) {
        Alert alert = new Alert(alertType);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Main.getCssIntro());
        dialogPane.setId("alertDialog");
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(window);
        alert.show();
    }
}