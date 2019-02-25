package Frontend;

import Backend.data.LoginDetails;
import Backend.data.User;
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
    private static final String passPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15})";
    private static final String emailPattern = "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+";

    public static void signInValidate(TextField emailField, PasswordField passField, GridPane form, Stage stage){
        if(validateEmail(emailField)) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(), "Typing Error!", "Please enter a valid email");
            return;
        }
        LoginDetails loginDetails = new LoginDetails(emailField.getText(), passField.getText());

        Requests requests = new Requests();
        String response = requests.sendRequest(1, loginDetails, new User());

        if(response != null && response.equals("success")) {
            showAlert(Alert.AlertType.CONFIRMATION, form.getScene().getWindow(), "Login successful",
                    "Welcome to GoGreen!");
            SetupStructure.resetFields(null, null, emailField, passField, null);
        }
        else {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(), "Login failed", "Incorrect credentials. Try again");
        }
    }
    public static void signUpValidate(TextField firstNameField, TextField lastNameField,
                                      TextField emailField, PasswordField passField, TextField ageField, GridPane form, Stage stage){
        if(firstNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(), "Form Error!", "Please enter your First Name");
            return;
        }
        if(lastNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(), "Form Error!", "Please enter your Last Name");
            return;
        }
        if(emailField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(), "Form Error!", "Please enter your email");
            return;
        }
        if(validateEmail(emailField)) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(), "Form Error!", "Please enter a valid email");
            return;
        }
        if(passField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(), "Form Error!", "Please enter a password");
            return;
        }
        if(!validatePassword(passField)) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(), "Form Error!", "Please enter a valid password");
            return;
        }
        if(ageField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(), "Form Error!", "Please enter your age");
            return;
        }
        if(!validateAge(ageField, ageField.getText())) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(), "Form Error!", "Please enter a valid age number");
            return;
        }

        User user = new User(firstNameField.getText(), lastNameField.getText(), Integer.parseInt(ageField.getText()),
                emailField.getText(), passField.getText());

        Requests requests = new Requests();
        String response = requests.sendRequest(2, new LoginDetails(), user);

        if(response != null) {
            if (response.equals("success")) {
                showAlert(Alert.AlertType.CONFIRMATION, form.getScene().getWindow(), "Registration Successful!",
                        "Go to login screen and enter your new credentials!");
                SetupStructure.resetFields(firstNameField, lastNameField, emailField, passField, ageField);
            } else {
                showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(), "Email Error!", "An user already exists with this email address. " +
                        "Use another email");
            }
        }
    }
    private static boolean validateAge(TextField input, String message){
        try{
            int age = Integer.parseInt(input.getText());
            if(age >= 0){
                System.out.println("User's age is: " + age);
                return true;
            }
            System.out.println("Error: " + message + " is not a valid number");
            return false;
        }catch (NumberFormatException e){
            System.out.println("Error: " + message + " is not a number");
            return false;
        }
    }

    private static boolean validatePassword(TextField input){
        String pass = input.getText();
        Pattern p = Pattern.compile(passPattern);
        Matcher m = p.matcher(pass);
        if(m.matches()){
            System.out.println("Password is: " +  pass);
            return true;
        }
        System.out.println("Error: " + pass + " is not a valid password");
        return false;
    }

    private static boolean validateEmail(TextField input){
        String email = input.getText();
        Pattern p = Pattern.compile(emailPattern);
        Matcher m = p.matcher(email);
        if(m.matches()){
            System.out.println("Email is: " + email);
            return false;
        }
        System.out.println("Error: " + email + " is not a valid email");
        return true;
    }
    private static void showAlert(Alert.AlertType alertType, Window window, String title, String message){
        Alert alert = new Alert(alertType);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Main.getCSS());
        dialogPane.setId("alertDialog");
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(window);
        alert.show();
    }
}