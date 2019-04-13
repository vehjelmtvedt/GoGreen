package frontend.gui;

import frontend.controllers.FriendRequestController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class FriendRequest {

    /**.
     * Add a friend request GUI element to the notification panel
     * @param container - element to add friend request to
     * @param fromUser - the username that send a request
     * @throws IOException - if something loads incorrectly
     */
    public void newFriendRequest(VBox container, String fromUser) throws IOException {
        FXMLLoader requestLoader = new FXMLLoader(
                Main.class.getResource("/frontend/fxmlPages/FriendRequest.fxml"));
        Parent request = requestLoader.load();

        FriendRequestController controller = requestLoader.getController();
        controller.setUsername(fromUser);

        AnchorPane friendRequest = (AnchorPane) request;
        //set ID for the new friend request
        friendRequest.setId(fromUser);
        container.getChildren().add(friendRequest);
    }
}
