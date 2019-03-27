package frontend.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.springframework.security.core.parameters.P;

public class NotificationPopup {

    private static JFXDrawer drawer1;

    private static JFXDrawer drawer2;

    private static JFXDrawer drawer3;

    private static JFXDrawer drawer4;

    public static void newNotification() {
        drawer1.open();
        drawer2.open();
        drawer3.open();
        drawer4.open();
    }

    public static void addAllDrawers(AnchorPane mainPane) {
        drawer1 = new JFXDrawer();
        drawer2 = new JFXDrawer();
        drawer3 = new JFXDrawer();
        drawer4 = new JFXDrawer();
        setUpDrawers(drawer1, 1,  mainPane);
        setUpDrawers(drawer2, 2, mainPane);
        setUpDrawers(drawer3, 3, mainPane);
        setUpDrawers(drawer4, 4, mainPane);
    }

    public static void setUpDrawers(JFXDrawer drawer, int drawerNumber, AnchorPane mainPane) {
        StackPane content = new StackPane();
        content.getChildren().addAll(new Label("Testing popup"));
        drawer.setSidePane(content);
        drawer.setDefaultDrawerSize(270);
        drawer.setMinHeight(170);
        drawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
        mainPane.getChildren().addAll(drawer);
        AnchorPane.setRightAnchor(drawer, 0.0);
        if (drawerNumber == 1) {
            AnchorPane.setTopAnchor(drawer, 143.0);
        } else {
            AnchorPane.setTopAnchor(drawer, 143.0 * drawerNumber + 10);
        }
    }
}
