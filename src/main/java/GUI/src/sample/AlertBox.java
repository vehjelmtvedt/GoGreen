package GUI.src.sample;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class AlertBox {

    public static void submitValidate(TextField firstNameField, TextField lastNameField,
                                      TextField emailField, TextField passField, GridPane form){
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
        if(passField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, form.getScene().getWindow(), "Form Error!", "Please enter a password");
            return;
        }

        showAlert(Alert.AlertType.CONFIRMATION, form.getScene().getWindow(), "Registration Successful!",
                "Welcome " + firstNameField.getText() + " " + lastNameField.getText());
    }

    private static void showAlert(Alert.AlertType alertType, Window window, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(window);
        alert.show();
    }
}
