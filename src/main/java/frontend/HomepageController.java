package frontend;

import backend.data.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomepageController {

    private static User thisUser;

    @FXML
    Button friendsButton;
    @FXML
    Button activitiesButton;
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
    }

    public static void setUser(User user) {
        thisUser = user;
    }
}
