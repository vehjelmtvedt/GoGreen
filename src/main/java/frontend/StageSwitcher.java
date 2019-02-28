package frontend;

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
}
