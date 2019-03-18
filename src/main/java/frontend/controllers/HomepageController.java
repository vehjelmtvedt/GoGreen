package frontend.controllers;

import data.LoginDetails;
import data.User;
import frontend.gui.Main;
import frontend.gui.StageSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomepageController {

    private static User thisUser;

    private static LoginDetails thisLoginDetails;

    @FXML
    Button friendsButton;
    @FXML
    Button activitiesButton;
    @FXML
    Button exitButton;
    @FXML
    Button logoutButton;

    @FXML
    private void initialize() {
        friendsButton.setOnAction(e ->
                StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getFriendsPage()));
        activitiesButton.setOnAction(e ->
                StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getActivities()));
        logoutButton.setOnAction(e ->
                StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getSignIn()));
        exitButton.setOnAction(e -> Main.getPrimaryStage().close());
    }

    public static void setUser(User user) {
        thisUser = user;
    }

    public static void setLoginDetails(LoginDetails loginDetails) {
        thisLoginDetails = loginDetails;
    }
}
