package frontend.controllers;

import backend.data.User;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;


public class FriendspageController {

    private static User user;

    @FXML
    private Label goGreen;

    @FXML
    private Button getFriends;

    @FXML
    private ScrollPane scrollPaneFriends;

    @FXML
    private JFXHamburger menu;

    @FXML
    private JFXDrawer drawer;

    public void initialize() throws IOException {
        NavPanelController.setup(drawer, menu);
        //fillFriendsPane(); //TODO: UNCOMMENT THIS WHEN CHANGING TO HOMEPAGE IN MAIN

    }

    public void fillFriendsGraph() {
        //Compare with 5 friends
        //Get C02 carbon emission for those friends
        //Make chart
        //Make it pretty

        
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
