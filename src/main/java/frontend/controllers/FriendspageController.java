package frontend.controllers;

import backend.data.User;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;


public class FriendspageController {

    private static User user;

    @FXML
    private AnchorPane main;

    @FXML
    private Button getFriends;

    @FXML
    private ScrollPane scrollPaneFriends;

    @FXML
    private JFXHamburger menu;

    @FXML
    private JFXDrawer drawer;

    public void initialize() throws IOException {
        NavPanelController.setup(drawer, menu, main);
        getFriends.setOnAction(e -> fillFriendsPane());

    }

    public void fillFriendsPane() {
        VBox root = new VBox();

        for (String username : user.getFriends()) {
            Pane friendPane = new Pane();
            friendPane.setMinHeight(50);
            friendPane.prefWidthProperty().bind(root.widthProperty());
            friendPane.setBackground(new Background(new BackgroundFill(Color.web("#4245f4"), CornerRadii.EMPTY, Insets.EMPTY)));
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
