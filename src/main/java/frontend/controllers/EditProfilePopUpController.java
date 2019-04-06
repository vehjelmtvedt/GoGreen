package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import data.LoginDetails;
import data.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tools.Requests;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProfilePopUpController extends ProfilePageController implements Initializable {

    private static User thisUser;

    private static LoginDetails thisLoginDetails;

    @FXML
    private HBox avatarZone;

    @FXML
    private JFXButton save;

    @FXML
    private JFXButton close;

    public static void setUser(User user) {
        thisUser = user;
    }

    public static void setLoginDetails(LoginDetails loginDetails) {
        thisLoginDetails = loginDetails;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        close.setOnAction( e -> {
            Stage stage = (Stage) close.getScene().getWindow();
            stage.close();
        });

        int count = 1;
        for (int i = 1; i <= 12; i++) {

            ImageView avatarimage = new ImageView();
            javafx.scene.image.Image path = new Image("avatars/" + count + ".jpg");
            avatarimage.setId(Integer.toString(count));
            avatarimage.setFitHeight(150);
            avatarimage.setFitWidth(150);
            avatarimage.setImage(path);

            avatarZone.getChildren().add(avatarimage);
            Separator separator = new Separator();
            separator.setOrientation(Orientation.VERTICAL);
            avatarZone.getChildren().add(separator);

            avatarimage.setOnMouseClicked(e -> {
                avatarimage.setImage(new Image("avatars/13.jpg"));
                Requests.editProfile(thisLoginDetails, "avatar", avatarimage.getId());
            });

            count ++;
        }

    }
}
