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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
    private static final String passPattern =
            "((?=.*[a-z]).{6,15})";
    private static final String emailPattern =
            "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+";


    /**.
     * Validation for signing in
     * @param emailField email input field
     * @param passField password input field
     * @param form form containing input fields
     */
    public static void signInValidate(TextField emailField,
                                      PasswordField passField, GridPane form) {

        LoginDetails loginDetails = new LoginDetails(emailField.getText(), passField.getText());

        String response = Requests.sendRequest(1, loginDetails, new User());
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

    /**.
     * Validation for input in sign up form
     * @param textFields - contains all text fields except those below
     * @param passField User's password field
     * @param passReField User's re-password field
     * @param form Form containing input fields
     * @param stage Stage of application
     */
    public static void signUpValidate(ArrayList<TextField> textFields,
                                      PasswordField passField, PasswordField passReField,
                                      GridPane form, Stage stage) {

        /*
        textFields[0] = firstNameField
        textFields[1] = lastNameField
        textFields[2] = userNameField
        textFields[3] = emailField
        textFields[4] = ageField
         */

        String[] errorMessages = new String[5];
        errorMessages[0] = "Please enter your First Name";
        errorMessages[1] = "Please enter your Last Name";
        errorMessages[2] = "Please enter a username";
        errorMessages[3] = "Please enter your email";
        errorMessages[4] = "Please enter your age";


        for (int i = 0; i < textFields.size(); i++) {
            if (textFields.get(i).getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                        "Form Error!", errorMessages[i]);
            }
        }


        //        if (firstNameField.getText().isEmpty()) {
        //            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
        //                    "Form Error!", "Please enter your First Name");
        //            return;
        //        }
        //        if (lastNameField.getText().isEmpty()) {
        //            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
        //                    "Form Error!", "Please enter your Last Name");
        //            return;
        //        }
        //        if (usernameField.getText().isEmpty()) {
        //            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
        //                    "Form Error!", "Please enter a username");
        //            return;
        //        }
        //        if (emailField.getText().isEmpty()) {
        //            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
        //                    "Form Error!", "Please enter your email");
        //            return;
        //        }
        //        if (validateEmail(textFields.get(3), form)) {
        //            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
        //                    "Form Error!", "Please enter a valid email");
        //            return;
        //        }

        validateEmail(textFields.get(3), form);

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

        validatePassword(passField, form);

        //        if (!validatePassword(passField)) {
        //            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
        //                    "Form Error!", "Please enter a valid password");
        //            return;
        //        }
        //        if (ageField.getText().isEmpty()) {
        //            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
        //                    "Form Error!", "Please enter your age");
        //            return;
        //        }
        if (!validateAge(textFields.get(4))) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                    "Form Error!", "Please enter a valid age number");
            return;
        }

        User user = new User(textFields.get(0).getText(),
                textFields.get(1).getText(),
                Integer.parseInt(textFields.get(4).getText()), textFields.get(2).getText(),
                textFields.get(3).getText(), passField.getText());

        String response = Requests.sendRequest(2, new LoginDetails(), user);

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

    private static boolean validatePassword(TextField input, GridPane form) {
        String pass = input.getText();
        Pattern pattern = Pattern.compile(passPattern);
        Matcher matcher = pattern.matcher(pass);
        if (matcher.matches()) {
            System.out.println("Password is: " + pass);
            return true;
        }
        showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(),
                "Form Error!", "Please enter a valid password");
        return false;
    }

    private static boolean validateEmail(TextField input, GridPane form) {
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
        dialogPane.getStylesheets().add(Main.getCssIntro());
        dialogPane.setId("alertDialog");
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(window);
        alert.show();
    }
}
