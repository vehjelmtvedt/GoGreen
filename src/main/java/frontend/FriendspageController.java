package frontend;

import backend.data.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class FriendspageController {

    private static User user;

    @FXML
    private Button getFriends;

    @FXML
    private ScrollPane scrollPaneFriends;

    public void initialize() {

        getFriends.setOnAction(e -> fillFriendsPane());

    }

    public void fillFriendsPane() {
        VBox root = new VBox();
        for (String username : user.getFriends()) {
            Label friendLabel = new Label(username);
            root.getChildren().add(friendLabel);
        }
        scrollPaneFriends.setContent(root);
        scrollPaneFriends.setPannable(true);
    }

    public static void setUser(User passedUser) {
        user = passedUser;
    }


}
