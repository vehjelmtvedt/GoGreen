package frontend;

import backend.data.User;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StageSwitcher {
    public static void buttonSwitch(Button button, Stage from, Scene to) {
        button.setOnAction(e -> from.setScene(to));
    }

    public static void loginSwitch(Stage from, Scene to) {
        from.setScene(to);
    }

    public static void closeApp(Button button, Stage from) {
        button.setOnAction(e -> from.close());
    }

    public static void sceneSwitch(Stage from, Scene to) {
        from.setScene(to);
    }

    public static void friendsPageSwitch(Button button, Stage from, Scene to, User passed) {
        button.setOnAction(e -> from.setScene(to));
        FriendspageController.setUser(passed);
    }
}
