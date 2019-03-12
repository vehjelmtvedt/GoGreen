package frontend;

import backend.data.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomepageController {

    private static User thisUser;

    @FXML
    Button FriendsButton;


    public void initialize() {
        FriendsButton.setOnAction(e -> StageSwitcher.friendsPageSwitch(Main.getPrimaryStage(), Main.getFriendsPage(), thisUser));
    }

    public static void setUser(User user) {
        thisUser = user;
    }

}
