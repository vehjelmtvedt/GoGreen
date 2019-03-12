package frontend;

import backend.data.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomepageController {

    private static User thisUser;

    @FXML
    Button FriendsButton;

    @FXML
    Button LogoutButton;

    @FXML
    Button ActivitiesButton;

    public void initialize() {
        FriendsButton.setOnAction(e -> StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getFriendsPage()));
        ActivitiesButton.setOnAction(e -> StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getActivities()));
    }

    public void logout() {
        LogoutButton.setOnAction(e -> StageSwitcher.sceneSwitch(Main.getPrimaryStage(), Main.getSignIn()));
    }
    public static void setUser(User user) {
        thisUser = user;
    }
}
