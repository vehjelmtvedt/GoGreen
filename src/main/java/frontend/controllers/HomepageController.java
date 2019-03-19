package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import data.LoginDetails;
import data.User;
import frontend.gui.Main;
import frontend.gui.StageSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {

    private static User thisUser;

    private static LoginDetails thisLoginDetails;

    @FXML
    private JFXButton friendsButton;
    @FXML
    private JFXButton activitiesButton;
    @FXML
    private JFXButton profileButton;
    @FXML
    private JFXButton logoutButton;

    @FXML
    private JFXHamburger menu;
    @FXML
    private JFXDrawer drawer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            NavPanelController.setup(drawer, menu);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        friendsButton.setOnAction(e ->
//                StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getFriendsPage()));
//        activitiesButton.setOnAction(e ->
//                StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getActivities()));
//        logoutButton.setOnAction(e ->
//                StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getSignIn()));
//        profileButton.setOnAction(e ->
//                StageSwitcher.sceneSwitch(Main.getPrimaryStage() , Main.getProfilePage()));

//        exitButton.setOnAction(e -> Main.getPrimaryStage().close());
    }

    public static void setUser(User user) {
        thisUser = user;
    }

    public static void setLoginDetails(LoginDetails loginDetails) {
        thisLoginDetails = loginDetails;
    }
}
