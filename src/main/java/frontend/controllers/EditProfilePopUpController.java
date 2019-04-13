package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import data.LoginDetails;
import data.User;
import frontend.gui.Events;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tools.Requests;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditProfilePopUpController implements Initializable {
    public static HomepageController homepageController;
    public static ProfilePageController profilePageController;
    private static User thisUser;
    private static LoginDetails thisLoginDetails;
    private static final List<ImageView> avatarList = new ArrayList<>();

    @FXML
    private HBox avatarZone;
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField age;
    @FXML
    private JFXButton close;
    @FXML
    private JFXButton firstNameSave;
    @FXML
    private JFXButton lastNameSave;
    @FXML
    private JFXButton ageSave;

    public static void setUser(User user) {
        thisUser = user;
    }

    public static void setLoginDetails(LoginDetails loginDetails) {
        thisLoginDetails = loginDetails;
    }

    /**
     * Allows any text field to become editable.
     * Also helps to reduce code duplication.
     * @param button The Button Used to save the changes.
     * @param textField The textField to be edited.
     * @param editableVariable The variable to be edited.
     */
    private static void editableFields(JFXButton button, JFXTextField textField,
                                       String editableVariable) {
        button.setOnAction(e -> {
            if (!(textField.getText().isEmpty())) {
                textField.setUnFocusColor(Color.BLACK);
                if (editableVariable.equals("firstName")) {
                    homepageController.updateFirstName(textField.getText());
                    profilePageController.updateFirstName(textField.getText());
                }
                if (editableVariable.equals("lastName")) {
                    homepageController.updateLastName(textField.getText());
                    profilePageController.updateLastName(textField.getText());
                }
                Requests.instance.editProfile(
                        thisLoginDetails, editableVariable, textField.getText());
            } else {
                textField.setUnFocusColor(Color.RED);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //add hover events for the buttons that edit the profile information;
        Events.addSaveButtonHover(firstNameSave);
        Events.addSaveButtonHover(lastNameSave);
        Events.addSaveButtonHover(ageSave);
        Events.addJfxButtonHover(close);

        firstName.setText(thisUser.getFirstName());
        lastName.setText(thisUser.getLastName());
        age.setText(Integer.toString(thisUser.getAge()));

        close.setOnAction( e -> {
            Stage stage = (Stage) close.getScene().getWindow();
            stage.close();
        });

        ageSave.setOnAction(e -> {
            if ( (age.getText().matches("^[0-9]*$")) && (!(age.getText().isEmpty())) ) {
                age.setUnFocusColor(javafx.scene.paint.Color.BLACK);
                Requests.instance.editProfile(
                        thisLoginDetails, "age", Integer.parseInt(age.getText()));
                profilePageController.updateAge(age.getText());
            } else {
                age.setUnFocusColor(Color.RED);
            }
        });

        editableFields(firstNameSave, firstName,"firstName");
        editableFields(lastNameSave,lastName,"lastName");

        int count = 1;
        for (int i = 1; i <= 12; i++) {

            ImageView avatarImage = new ImageView();

            //set the cursor to be Cursor.Hand whenever it is hovered
            avatarImage.setCursor(Cursor.HAND);

            javafx.scene.image.Image path = new Image("avatars/" + count + ".jpg");
            avatarImage.setId(Integer.toString(count));
            avatarImage.setFitHeight(150);
            avatarImage.setFitWidth(150);
            avatarImage.setImage(path);

            avatarZone.getChildren().add(avatarImage);
            Separator separator = new Separator();
            separator.setOrientation(Orientation.VERTICAL);
            avatarZone.getChildren().add(separator);

            avatarList.add(avatarImage);

            count ++;
        }
        Events.unCheckImages(avatarList, thisUser, thisLoginDetails);
    }
}
