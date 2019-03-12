package frontend;

import backend.data.User;
import frontend.controllers.HomepageController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StageSwitcher {
    public static void buttonSwitch(Button button, Stage from, Scene to) {
        button.setOnAction(e -> from.setScene(to));
    }

    public static void loginSwitch(Stage from, Scene to, User user) {
        HomepageController.setUser(user);
        from.setScene(to);
    }

    public static void closeApp(Button button, Stage from) {
        button.setOnAction(e -> from.close());
    }

    public static void sceneSwitch(Stage from, Scene to) {
        from.setScene(to);
    }
}
