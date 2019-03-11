package frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;


public class FriendspageController {

    @FXML
    private Button getFriends;

    @FXML
    private Text headerlabel;

    public void initialize() {

        getFriends.setOnAction(e -> System.out.println("Button pressed"));

    }


}
