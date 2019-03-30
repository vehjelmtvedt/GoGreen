package frontend.gui;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import frontend.controllers.NavPanelController;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NavPanel {

    /**
     * Adds a nav panel to the page.
     * @param main - root pane
     * @param header - header pane
     * @param menu - hamburger icon
     * @return - the drawer added to the page
     * @throws IOException - if fails to load the content of the drawer
     */
    public static JFXDrawer addNavPanel(AnchorPane main,
                                        AnchorPane header, JFXHamburger menu) throws IOException {
        JFXDrawer drawer = new JFXDrawer();
        AnchorPane.setLeftAnchor(drawer, 0.0);
        AnchorPane.setBottomAnchor(drawer, 0.0);
        AnchorPane.setTopAnchor(drawer, header.getPrefHeight());
        drawer.setPrefHeight(main.getHeight() - header.getHeight());
        drawer.setDefaultDrawerSize(270);
        VBox box = FXMLLoader.load(NavPanelController.class.getResource(
                "/frontend/fxmlPages/navigationpane.fxml"));
        drawer.setSidePane(box);
        box.prefHeightProperty().bind(drawer.prefHeightProperty());
        drawer.setVisible(false);
        main.getChildren().addAll(drawer);

        HamburgerBasicCloseTransition burgerTask1 = new HamburgerBasicCloseTransition(menu);
        burgerTask1.setRate(-1);
        menu.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            burgerTask1.setRate(burgerTask1.getRate() * -1);
            burgerTask1.play();
            if (drawer.isOpened()) {
                drawer.close();
                drawer.setVisible(false);
            } else {
                drawer.open();
                drawer.setVisible(true);
            }
        });
        return drawer;
    }
}
