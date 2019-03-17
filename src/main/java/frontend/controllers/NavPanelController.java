package frontend.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import frontend.Main;
import frontend.StageSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class NavPanelController implements Initializable {

    @FXML
    private Button myProfile;

    @FXML
    private Button home;

    @FXML
    private Button activity;

    @FXML
    private Button friends;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO: Add scene change to profile page
        home.setOnAction(e -> StageSwitcher.sceneSwitch(
                Main.getPrimaryStage(), Main.getHomepage()));
        activity.setOnAction(e -> StageSwitcher.sceneSwitch(
                Main.getPrimaryStage(), Main.getActivities()));
        friends.setOnAction(e -> StageSwitcher.sceneSwitch(
                Main.getPrimaryStage(), Main.getFriendsPage()));
    }


    /**
     * Set up the navbar for any page.
     * @param drawer - the drawer containing the nav panel
     * @param menu - the hamburger button
     * @throws IOException - if fails to load the navigationpane
     */
    public static void setup(JFXDrawer drawer, JFXHamburger menu) throws IOException {
        VBox box = FXMLLoader.load(NavPanelController.class.getResource(
                "/frontend/fxmlPages/navigationpane.fxml"));
        box.setMinHeight(drawer.getDefaultDrawerSize());


        drawer.setVisible(false);
        drawer.setSidePane(box);


        //Handle nav panel
        HamburgerBasicCloseTransition burgerTask1 = new HamburgerBasicCloseTransition(menu);
        burgerTask1.setRate(-1);
        menu.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            burgerTask1.setRate(burgerTask1.getRate() * -1);
            burgerTask1.play();
            if (drawer.isShown()) {
                drawer.close();
                drawer.setVisible(false);
            } else {
                drawer.open();
                drawer.setVisible(true);
            }
        });
    }






}
