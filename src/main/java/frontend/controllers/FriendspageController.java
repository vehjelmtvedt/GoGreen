package frontend.controllers;

import backend.data.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class FriendspageController {

    private static User user;

    @FXML
    private Button getFriends;

    public void initialize() {

        getFriends.setOnAction(e -> fillFriendsPane());

    }

    public void fillFriendsPane() {
        System.out.println(user.toString());
    }

    public static void setUser(User passedUser) {
        user = passedUser;
    }


}
