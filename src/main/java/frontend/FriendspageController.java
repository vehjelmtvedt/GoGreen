package frontend;

import backend.data.User;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


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
            Pane friendPane = new Pane();
            friendPane.setMinHeight(50);
            friendPane.setBackground(new Background(new BackgroundFill(Color.web("#F123"), CornerRadii.EMPTY, Insets.EMPTY)));

            friendPane.setMinWidth(root.getMaxWidth());
            Label friendLabel = new Label(username);
            friendPane.getChildren().add(friendLabel);
            root.getChildren().add(friendPane);
        }
        scrollPaneFriends.setContent(root);
        scrollPaneFriends.setPannable(true);
    }

    public static void setUser(User passedUser) {
        user = passedUser;
    }


}
