package frontend.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendRequestController implements Initializable {
    @FXML
    private Label lblUsername;
    @FXML
    private JFXButton btnAccept;
    @FXML
    private JFXButton btnDecline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**.
     * Sets the username for the friend request
     * @param username - user String to set label text to
     */
    public void setUsername(String username) {
        lblUsername.setText(username);
    }
}
