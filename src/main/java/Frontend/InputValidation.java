package Frontend;

import Backend.data.LoginDetails;
import Backend.data.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
    public static void signInValidate(TextField emailField, TextField passField, GridPane form){
        if(!validateEmail(emailField, emailField.getText())) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(), "Typing Error!", "Please enter a valid email");
            return;
        }
        LoginDetails loginDetails = new LoginDetails(emailField.getText(), passField.getText());

        Requests.sendRequest(1, loginDetails, new User());
//        try{
//            URL url = new URL("http://localhost:8080/login");
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("POST");
//            con.setDoOutput(true);
//            con.setDoInput(true);
//            con.setRequestProperty("Content-Type", "application/json");
//
//            ObjectMapper mapper = new ObjectMapper();
//            String json = mapper.writeValueAsString(loginDetails);
//
//            con.getOutputStream().write(json.getBytes(Charset.forName("UTF-8")));
//            con.getOutputStream().flush();
//
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuilder response = new StringBuilder();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//        System.out.println();
    }
    public static void signUpValidate(TextField firstNameField, TextField lastNameField,
                                      TextField emailField, TextField passField, TextField ageField, GridPane form){
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
        if(!validateEmail(emailField, emailField.getText())) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(), "Form Error!", "Please enter a valid email");
            return;
        }
        if(passField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(), "Form Error!", "Please enter a password");
            return;
        }
        if(!validatePassword(passField, passField.getText())) {
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

        Requests.sendRequest(2, new LoginDetails(), user);

        showAlert(Alert.AlertType.CONFIRMATION, form.getScene().getWindow(), "Registration Successful!",
                "Welcome " + firstNameField.getText() + " " + lastNameField.getText() + "!!!");
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

    private static boolean validatePassword(TextField input, String message){
        String pass = input.getText();
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15})");
        Matcher m = p.matcher(pass);
        if(m.matches()){
            System.out.println("Password is: " +  pass);
            return true;
        }
        System.out.println("Error: " + pass + " is not a valid password");
        return false;
    }

    private static boolean validateEmail(TextField input, String message){
        String email = input.getText();
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(email);
        if(m.matches()){
            System.out.println("Email is: " + email);
            return true;
        }
        System.out.println("Error: " + email + " is not a valid email");
        return false;
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