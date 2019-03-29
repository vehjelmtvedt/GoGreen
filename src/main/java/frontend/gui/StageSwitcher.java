package frontend.gui;

import com.jfoenix.controls.JFXDrawer;
import data.User;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StageSwitcher {

    public static JFXDrawer homeDrawer;
    public static JFXDrawer friendsDrawer;
    public static JFXDrawer activityDrawer;

    public static void buttonSwitch(Button button, Stage from, Scene to) {
        button.setOnAction(e -> from.setScene(to));
    }

    public static void loginSwitch(Stage from, Scene to, User user) {
        from.setScene(to);
    }

    public static void closeApp(Button button, Stage from) {
        button.setOnAction(e -> from.close());
    }

    /**
     * Changes scene of any stage (usually primary).
     * @param from - the current stage
     * @param to - the scene to change to
     */
    public static void sceneSwitch(Stage from, Scene to) {
        homeDrawer.close();
        friendsDrawer.close();
        activityDrawer.close();
        from.setScene(to);
    }
}
