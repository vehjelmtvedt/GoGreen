package frontend.controllers;

import backend.data.User;
import frontend.Main;
import frontend.StageSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomepageController {

    private static User thisUser;

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
                StageSwitcher.friendPageSwitch(Main.getPrimaryStage(), Main.getFriendsPage(), thisUser));
        activitiesButton.setOnAction(e ->
                StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getActivities()));
        logoutButton.setOnAction(e ->
                StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getSignIn()));
        exitButton.setOnAction(e -> Main.getPrimaryStage().close());
    }

    public static void setUser(User user) {
        thisUser = user;
    }
}
