package frontend.gui;

import com.jfoenix.controls.JFXDrawer;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageSwitcher {

    public static JFXDrawer homeDrawer;
    public static JFXDrawer friendsDrawer;
    public static JFXDrawer activityDrawer;
    public static JFXDrawer profileDrawer;

    /**
     * Changes scene of any stage (usually primary).
     * @param from - the current stage
     * @param to - the scene to change to
     */
    public static void sceneSwitch(Stage from, Scene to) {
        homeDrawer.close();
        friendsDrawer.close();
        activityDrawer.close();
        homeDrawer.setVisible(false);
        friendsDrawer.setVisible(false);
        activityDrawer.setVisible(false);
        from.setScene(to);
    }

    /**.
     * Changes scene of any stage (usually primary).
     * It's used in the login/sign up/ questionnaire pages.
     * @param from - the current stage
     * @param to - the scene to change to
     */
    public static void signInUpSwitch(Stage from, Scene to) {
        from.setScene(to);
    }
}
