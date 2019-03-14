package frontend.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class NavPanelController {


    public void initialize() {

    }


    public void setup(JFXDrawer drawer, JFXHamburger menu) throws IOException {
        VBox box = FXMLLoader.load(NavPanelController.class.getResource("/frontend/fxmlPages/navigationpane.fxml"));

        drawer.setVisible(false);
        drawer.setSidePane(box);

        //Handle nav panel
        HamburgerBasicCloseTransition burgerTask1 = new HamburgerBasicCloseTransition(menu);
        burgerTask1.setRate(-1);
        menu.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            burgerTask1.setRate(burgerTask1.getRate() * -1);
            burgerTask1.play();
            //drawer.setVisible(true);
            if (drawer.isOpened()) {
                drawer.close();
                drawer.setVisible(false);
            } else {
                drawer.setVisible(true);
                drawer.open();
            }
        });


    }






}
